package ca.mikegabelmann.db.freemarker;

import ca.mikegabelmann.codegen.java.JavaNamingType;
import ca.mikegabelmann.codegen.java.lang.JavaMethodNamePrefix;
import ca.mikegabelmann.codegen.java.lang.classbody.*;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaConstructorModifier;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaFieldModifier;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaMethodModifier;
import ca.mikegabelmann.codegen.util.NameUtil;
import ca.mikegabelmann.codegen.util.PrintJavaUtil;
import ca.mikegabelmann.codegen.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.torque.ReferenceType;
import org.apache.torque.SqlDataType;
import org.jetbrains.annotations.NotNull;

import javax.persistence.FetchType;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author mgabe
 */
public class Entity {
    /** Logger. */
    private static final Logger LOG = LogManager.getLogger(Entity.class);

    /** Do not instantiate this class. */
    private Entity() {}


    /**
     *
     * @param tw
     * @param schema
     * @return
     */
    public static JavaAnnotation getTableAnnotation(@NotNull TableWrapper tw, String schema) {
        JavaAnnotation ann = new JavaAnnotation("Table");
        ann.add("name", tw.getTableType().getName());

        if (StringUtil.isNotBlankOrNull(schema)) {
            ann.add("schema", schema);
        }

        return ann;
    }

    /**
     *
     * @param cw
     * @return
     */
    public static JavaAnnotation getOneToOne(@NotNull ColumnWrapper cw) {
        JavaAnnotation ann = new JavaAnnotation("OneToOne");

        if (cw.getId().toUpperCase().endsWith("_CODE")) {
            ann.add("fetch", FetchType.EAGER);

        } else {
            ann.add("fetch", FetchType.LAZY);
        }

        return ann;
    }

    /**
     *
     * @return
     */
    public static JavaAnnotation getManyToOne() {
        JavaAnnotation ann = new JavaAnnotation("ManyToOne");
        ann.add("fetch", FetchType.LAZY);

        return ann;
    }

    /**
     *
     * @param fkw
     * @return
     */
    public static JavaAnnotation getJoinColumn(@NotNull ForeignKeyWrapper fkw) {
        JavaAnnotation ja;

        if (fkw.isCompositeKey()) {
            ja = new JavaAnnotation("JoinColumns");

            //TODO: @JoinColumns, @JoinColumn
            Map<String, ColumnWrapper> hashedColumns = fkw.getColumns().stream().collect(Collectors.toMap(c -> c.getName(), Function.identity()));

            List<JavaAnnotation> jaList = new ArrayList<>();

            for (ReferenceType rt : fkw.getForeignKeyType().getReference()) {
                ColumnWrapper c = hashedColumns.get(rt.getLocal());

                if (c != null) {
                    jaList.add(Entity.getJoinColumn(rt, c));
                }
            }

            ja.add("", jaList);

        } else {
            //TODO: @JoinColumn
            ja = Entity.getJoinColumn(fkw.getForeignKeyType().getReference().get(0), fkw.getColumns().get(0));

        }

        return ja;
    }

    /**
     *
     * @param referenceType
     * @param columnWrapper
     * @return
     */
    private static JavaAnnotation getJoinColumn(ReferenceType referenceType, ColumnWrapper columnWrapper) {
        JavaAnnotation ann = new JavaAnnotation("JoinColumn");
        ann.add("name", referenceType.getLocal());
        ann.add("referencedColumnName", referenceType.getForeign());
        ann.add("nullable", !columnWrapper.isRequired());

        //PROPERTIES: columnDefinition, foreignKey, insertable, table, unique, updatable

        return ann;
    }

    /**
     *
     * @param cw
     * @return
     */
    public static JavaAnnotation getColumnAnnotation(@NotNull ColumnWrapper cw) {
        JavaAnnotation a = new JavaAnnotation("Column");
        a.add("name", cw.getId());

        if (cw.getColumnType().isPrimaryKey()) {
            a.add("insertable", Boolean.FALSE);
            a.add("updatable", Boolean.FALSE);
            a.add("unique", Boolean.TRUE);
        }

        if (cw.getColumnType().isRequired()) {
            a.add("nullable", Boolean.FALSE);
        }

        if (cw.getColumnType().getSize() != null) {
            a.add("length", cw.getColumnType().getSize().intValue());
        }

        a.add("columnDefinition", cw.getColumnType().getType().value());

        //TODO: precision/scale, size, length
        //TODO: table

        return a;
    }

    /**
     *
     * @param cw
     * @return
     */
    public static JavaAnnotation getTemporalAnnotation(@NotNull ColumnWrapper cw) {
        JavaAnnotation a = new JavaAnnotation("Temporal");

        if (cw.getColumnType().getType().equals(SqlDataType.DATE)) {
            a.add("value", TemporalType.DATE);

        } else if (cw.getColumnType().getType().equals(SqlDataType.TIME)) {
            a.add("value", TemporalType.TIME);

        } else if (cw.getColumnType().getType().equals(SqlDataType.TIMESTAMP)) {
            a.add("value", TemporalType.TIMESTAMP);
        }

        LOG.debug("import={}", cw.getCanonicalName());

        return a;
    }

    /**
     *
     * @param annotation
     * @return
     */
    public static String annotation(@NotNull final JavaAnnotation annotation) {
        return PrintJavaUtil.getAnnotation(annotation);
    }

    /**
     *
     * @param column
     * @return
     */
    public static String field(@NotNull final AbstractWrapper column) {
        JavaField field = new JavaField(column.getSimpleName(), column.getVariableName());
        field.addModifier(JavaFieldModifier.PRIVATE);

        //TODO: determine annotations to add

        if (column instanceof LocalKeyWrapper) {
            LocalKeyWrapper lkw = (LocalKeyWrapper) column;

            if (!lkw.isCompositeKey()) {
                //get @Id
                field.addAnnotation(new JavaAnnotation("Id"));

                //TODO: get @GeneratedValue and @SequenceGenerator if applicable
                /*if (column.getColumnType().isAutoIncrement()) {
                    //@GeneratedValue(strategy=GenerationType.AUTO, generator="addressSeq")
                    //@SequenceGenerator(name="addressSeq", sequenceName="ADDRESS_SEQ")
                }*/

                //get @Column
                field.addAnnotation(Entity.getColumnAnnotation(lkw.getColumns().get(0)));

            } else {
                //get @EmbeddedId
                field.addAnnotation(new JavaAnnotation("EmbeddedId"));
            }

        } else if (column instanceof ForeignKeyWrapper) {
            ForeignKeyWrapper fkw = (ForeignKeyWrapper) column;

            //NOTE: need a better way to detect OneToOne, ManyToOne, ManyToMany
            if (!fkw.isCompositeKey() && fkw.getColumns().get(0).getColumnType().isPrimaryKey()) {
                //get @OneToOne
                field.addAnnotation(Entity.getOneToOne(fkw.getColumns().get(0)));

            } else if (fkw.isCompositeKey() && ! fkw.getColumns().get(0).getColumnType().isPrimaryKey()) {
                //get @ManyToOne
                field.addAnnotation(Entity.getManyToOne());

            } else {
                //TODO: detect @ManyToMany

            }

            //get @JoinColumns if > 1 column with @JoinColumn, otherwise @JoinColumn
            field.addAnnotation(Entity.getJoinColumn(fkw));

        } else if (column instanceof ColumnWrapper) {
            ColumnWrapper cw = (ColumnWrapper) column;

            //get @Temporal
            if (cw.isTemporal()) {
                field.addAnnotation(Entity.getTemporalAnnotation(cw));
            }

            //get @Column
            field.addAnnotation(Entity.getColumnAnnotation(cw));

        } else {

        }

        return PrintJavaUtil.getField(field);
    }

    /**
     *
     * @param wrapper
     * @return
     */
    public static String getter(@NotNull final AbstractWrapper wrapper) {
        JavaReturnType returnType = new JavaReturnType(wrapper.getSimpleName(), wrapper.getVariableName());

        //TODO: determine annotations to add

        JavaMethod method = new JavaMethod(wrapper.getSimpleName(), NameUtil.getJavaName(JavaNamingType.UPPER_CAMEL_CASE, wrapper.getId()));
        method.addModifier(JavaMethodModifier.PUBLIC);
        method.setJavaReturnType(returnType);
        method.setNamePrefix(JavaMethodNamePrefix.GET);

        return PrintJavaUtil.getMethod(method);
    }

    /**
     *
     * @param wrapper
     * @return
     */
    public static String setter(@NotNull final AbstractWrapper wrapper) {
        JavaMethod method = new JavaMethod(wrapper.getSimpleName(), NameUtil.getJavaName(JavaNamingType.UPPER_CAMEL_CASE, wrapper.getId()));
        method.addModifier(JavaMethodModifier.PUBLIC);
        method.setJavaReturnType(null);
        method.setNamePrefix(JavaMethodNamePrefix.SET);
        method.addArgument(new JavaArgument(wrapper.getSimpleName(), wrapper.getVariableName()));

        return PrintJavaUtil.getMethod(method);
    }

    /**
     *
     * @param table
     * @return
     */
    public static String constructorNoArgs(@NotNull final TableWrapper table) {
        JavaConstructor con = new JavaConstructor(table.getCanonicalName(), table.getSimpleName());
        con.addModifier(JavaConstructorModifier.PUBLIC);

        return PrintJavaUtil.getConstructor(con);
    }

    /**
     *
     * @param table
     * @return
     */
    public static String constructorArgs(@NotNull final TableWrapper table, boolean allArgs) {
        JavaConstructor con = new JavaConstructor(table.getCanonicalName(), table.getSimpleName());
        con.addModifier(JavaConstructorModifier.PUBLIC);

        Collection<AbstractWrapper> columns = table.getColumns();

        for (AbstractWrapper column : columns) {
            if (allArgs || column.isRequired()) {
                con.addArgument(new JavaArgument(column.getSimpleName(), column.getVariableName()));
            }
        }

        return PrintJavaUtil.getConstructor(con);
    }

}
