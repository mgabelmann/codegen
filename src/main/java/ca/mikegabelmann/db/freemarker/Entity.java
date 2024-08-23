package ca.mikegabelmann.db.freemarker;

import ca.mikegabelmann.codegen.java.JavaNamingType;
import ca.mikegabelmann.codegen.java.lang.JavaMethodNamePrefix;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaAnnotation;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaArgument;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaConstructor;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaField;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaImport;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaMethod;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaPackage;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaReturnType;
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

import jakarta.persistence.FetchType;
import jakarta.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    public static JavaAnnotation getTableAnnotation(@NotNull TableWrapper tw, @NotNull String type, String schema) {
        JavaAnnotation ann = new JavaAnnotation(type);
        ann.add("name", tw.getTableType().getName());

        if (StringUtil.isNotBlankOrNull(schema)) {
            ann.add("schema", schema);
        }

        return ann;
    }

    //TESTING
    public static String tableAnnotation(@NotNull TableWrapper tw, @NotNull String type, String schema) {
        tw.addImport(type);

        JavaAnnotation ann = Entity.getTableAnnotation(tw, type, schema);

        return PrintJavaUtil.getAnnotation(ann);
    }


    /**
     *
     * @param cw
     * @return
     */
    public static JavaAnnotation getOneToOne(@NotNull ColumnWrapper cw) {
        JavaAnnotation ann = new JavaAnnotation("jakarta.persistence.OneToOne");

        if (cw.getId().toUpperCase().endsWith("_CODE")) {
            ann.add("fetch", FetchType.EAGER);

        } else {
            ann.add("fetch", FetchType.LAZY);
        }

        cw.addImport("jakarta.persistence.FetchType");

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
     * @return
     */
    public static JavaAnnotation getOneToMany() {
        JavaAnnotation ann = new JavaAnnotation("OneToMany");
        //TODO: cascade
        //TODO: fetch
        //TODO: *mappedBy
        ann.add("mappedBy", "FIXME");
        //TODO: orphanRemoval
        //TODO: targetEntity

        return ann;
    }

    /**
     *
     * @return
     */
    public static JavaAnnotation getManyToMany() {
        JavaAnnotation ann = new JavaAnnotation("ManyToMany");
        //TODO: cascade
        //TODO: fetch
        //TODO: *mappedBy
        ann.add("mappedBy", "FIXME");
        //TODO: targetEntity

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
            ja = new JavaAnnotation("jakarta.persistence.JoinColumns");

            //TODO: @JoinColumns, @JoinColumn
            Map<String, ColumnWrapper> hashedColumns = fkw.getColumns().stream().collect(Collectors.toMap(ColumnWrapper::getName, Function.identity()));

            List<JavaAnnotation> jaList = new ArrayList<>();

            for (ReferenceType rt : fkw.getForeignKeyType().getReference()) {
                ColumnWrapper c = hashedColumns.get(rt.getLocal());

                if (c != null) {
                    jaList.add(Entity.getJoinColumn(rt, c));
                }
            }

            ja.add("value", jaList);

            //TODO: foreignKey (if necessary)

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
        JavaAnnotation ann = new JavaAnnotation("jakarta.persistence.JoinColumn");
        //TODO: columnDefinition
        //TODO: foreignKey
        //TODO: insertable
        ann.add("name", referenceType.getLocal());
        ann.add("nullable", !columnWrapper.isRequired());
        ann.add("referencedColumnName", referenceType.getForeign());
        //TODO: table
        //TODO: unique
        //TODO: updatable

        return ann;
    }

    /**
     *
     * @param cw
     * @return
     */
    public static JavaAnnotation getColumnAnnotation(@NotNull ColumnWrapper cw) {
        JavaAnnotation a = new JavaAnnotation("jakarta.persistence.Column");
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

        //TODO: sizes
            //TODO: precision/scale,
            //TODO: size
            //TODO: length
        //TODO: table

        return a;
    }

    /**
     *
     * @param cw
     * @return
     */
    public static JavaAnnotation getTemporalAnnotation(@NotNull ColumnWrapper cw) {
        JavaAnnotation a = new JavaAnnotation("jakarta.persistence.Temporal");

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

    public static String printPackage(@NotNull final TableWrapper table) {
        return PrintJavaUtil.getPackage(new JavaPackage(table.getPackageName()));
    }

    /**
     * Get all imports for use in Java code.
     * @param table
     * @return
     */
    public static String printImports(@NotNull final TableWrapper table) {
        Set<String> imports = table.getAllImports();

        return imports.stream().map(a -> PrintJavaUtil.getImport(new JavaImport(a))).collect(Collectors.joining(System.lineSeparator()));
    }

    /**
     * Get Java annotation formatted for use in Java code.
     * @param annotation annotation
     * @return Java annotation
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
                field.addAnnotation(new JavaAnnotation("jakarta.persistence.Id"));

                //TODO: get @GeneratedValue and @SequenceGenerator if applicable
                /*if (column.getColumnType().isAutoIncrement()) {
                    //@GeneratedValue(strategy=GenerationType.AUTO, generator="addressSeq")
                    //@SequenceGenerator(name="addressSeq", sequenceName="ADDRESS_SEQ")
                }*/

                //get @Column
                field.addAnnotation(Entity.getColumnAnnotation(lkw.getColumns().get(0)));

            } else {
                //get @EmbeddedId
                field.addAnnotation(new JavaAnnotation("jakarta.persistence.EmbeddedId"));
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
            //only applies to packages java.util.* or java.sql.*
            if (cw.isTemporal() && !cw.getCanonicalName().contains("java.time")) {
                field.addAnnotation(Entity.getTemporalAnnotation(cw));
            }

            //get @Column
            field.addAnnotation(Entity.getColumnAnnotation(cw));

        } else {

        }

        for (JavaAnnotation ann : field.getAnnotations()) {
            column.addImport(ann.getCanonicalName());
        }

        return PrintJavaUtil.getField(field);
    }

    /**
     *
     * @param wrapper
     * @return
     */
    public static String getter(@NotNull final AbstractWrapper wrapper) {
        JavaReturnType returnType = new JavaReturnType(wrapper.getSimpleName());

        //TODO: determine annotations to add

        JavaMethod method = new JavaMethod(NameUtil.getJavaName(JavaNamingType.UPPER_CAMEL_CASE, wrapper.getId()));
        method.addModifier(JavaMethodModifier.PUBLIC);
        method.setJavaReturnType(returnType);
        method.setNamePrefix(JavaMethodNamePrefix.GET);

        method.getBody().append(PrintJavaUtil.getFieldReturnValue(wrapper.getVariableName(), true));

        return PrintJavaUtil.getMethod(method);
    }

    /**
     *
     * @param wrapper
     * @return
     */
    public static String setter(@NotNull final AbstractWrapper wrapper) {
        JavaMethod method = new JavaMethod(NameUtil.getJavaName(JavaNamingType.UPPER_CAMEL_CASE, wrapper.getId()));
        method.addModifier(JavaMethodModifier.PUBLIC);
        method.setJavaReturnType(null);
        method.setNamePrefix(JavaMethodNamePrefix.SET);
        method.addArgument(new JavaArgument(wrapper.getSimpleName(), wrapper.getVariableName(), true));
        method.getBody().append(PrintJavaUtil.getFieldAssignment(wrapper.getVariableName(), true));

        return PrintJavaUtil.getMethod(method);
    }

    /**
     *
     * @param table
     * @return
     */
    public static String constructorNoArgs(@NotNull final TableWrapper table) {
        JavaConstructor con = new JavaConstructor(table.getCanonicalName());
        con.addModifier(JavaConstructorModifier.PUBLIC);

        return PrintJavaUtil.getConstructor(con);
    }

    /**
     *
     * @param table
     * @return
     */
    public static String constructorArgs(@NotNull final TableWrapper table, boolean allArgs) {
        JavaConstructor con = new JavaConstructor(table.getCanonicalName());
        con.addModifier(JavaConstructorModifier.PUBLIC);

        Collection<AbstractWrapper> columns = table.getColumns();

        for (AbstractWrapper column : columns) {
            if (allArgs || column.isRequired()) {
                //LOG.trace("constructor arg type={}", column.getCanonicalName());
                con.addArgument(new JavaArgument(column.getSimpleName(), column.getVariableName(), true));

                if (column instanceof ColumnWrapper) {
                    //do not add keys or foreign key imports as they are in same package
                    table.addImport(column.getCanonicalName());
                }
            }
        }

        return PrintJavaUtil.getConstructor(con);
    }

    //FIXME: JavaMethod does not currently allow custom method bodies
    public static String toStringGenerator(@NotNull final TableWrapper table) {
        JavaAnnotation ja1 = new JavaAnnotation("Override");

        JavaMethod jm1 = new JavaMethod("toString");
        jm1.addModifier(JavaMethodModifier.PUBLIC);
        jm1.setJavaReturnType(new JavaReturnType("java.lang.String"));
        jm1.addAnnotation(ja1);



        return PrintJavaUtil.getMethod(jm1);
/*
    @Override
    public String toString() {
        return "ColumnWrapper{" +
                "columnType=" + columnType +
                '}';
    }
 */
    }

}
