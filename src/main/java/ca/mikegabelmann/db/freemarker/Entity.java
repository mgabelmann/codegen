package ca.mikegabelmann.db.freemarker;

import ca.mikegabelmann.codegen.java.JavaNamingType;
import ca.mikegabelmann.codegen.java.lang.JavaMethodNamePrefix;
import ca.mikegabelmann.codegen.java.lang.classbody.*;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaConstructorModifier;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaFieldModifier;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaMethodModifier;
import ca.mikegabelmann.codegen.util.NameUtil;
import ca.mikegabelmann.codegen.util.PrintJavaUtil;
import org.apache.torque.SqlDataType;
import org.jetbrains.annotations.NotNull;

import javax.persistence.FetchType;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author mgabe
 */
public class Entity {
    /** Do not instantiate this class. */
    private Entity() {}


    /**
     *
     * @param column
     * @return
     */
    public static String field(@NotNull final AbstractWrapper column) {
        JavaField field = new JavaField(column.getSimpleName(), column.getVariableName());
        field.addModifier(JavaFieldModifier.PRIVATE);

        //TODO: determine annotations to add

        //@Id
        if (column instanceof LocalKeyWrapper) {
            LocalKeyWrapper lkw = (LocalKeyWrapper) column;

            field.addAnnotation(new JavaAnnotation("Id"));

            if (!lkw.isCompositeKey()) {
                //TODO: get @Column

                //TODO: get @GeneratedValue and @SequenceGenerator if applicable
                /*if (column.getColumnType().isAutoIncrement()) {
                    //@GeneratedValue(strategy=GenerationType.AUTO, generator="addressSeq")
                    //@SequenceGenerator(name="addressSeq", sequenceName="ADDRESS_SEQ")
                }*/
            }
         }

        if (column instanceof ForeignKeyWrapper) {
            ForeignKeyWrapper fkw = (ForeignKeyWrapper) column;

            //TODO: get @OneToOne
            //TODO: get @ManyToOne
            //TODO: get @ManyToMany

            //TODO: @JoinColumns if > 1 column with @JoinColumn, otherwise @JoinColumn

            if (fkw.isCompositeKey()) {
                //@JoinColumns
                List<JavaAnnotation> colAnns = new ArrayList<>();
                for (ColumnWrapper col : fkw.getColumns()) {
                    JavaAnnotation a = new JavaAnnotation("JoinColumn");
                    a.add("name", col.getName());
                    a.add("nullable", !col.isRequired());
                    a.add("referencedColumnName", "TODO");
                }

                JavaAnnotation jt2 = new JavaAnnotation("JoinColumns");
                jt2.add("", colAnns);
                field.addAnnotation(jt2);

            } else {
                //@JoinColumn
                ColumnWrapper col = fkw.getColumns().get(0);

                JavaAnnotation a = new JavaAnnotation("JoinColumn");
                a.add("name", col.getName());
                a.add("nullable", !col.isRequired());
                a.add("referencedColumnName", "TODO");

                field.addAnnotation(a);
            }

            /*
            //get @ManyToOne
            JavaAnnotation jt1 = new JavaAnnotation("ManyToOne");

            if (fkw.getId().endsWith("_CODE")) {
                jt1.add("fetch", FetchType.EAGER);

            } else {
                jt1.add("fetch", FetchType.LAZY);
            }

            field.addAnnotation(jt1);

            //get @JoinColumn
            JavaAnnotation jt2 = new JavaAnnotation("JoinColumn");
            jt2.add("name", fkw.getId());
            jt2.add("nullable", !fkw.isRequired());
            //TODO: insertable
            //TODO: updatable

            field.addAnnotation(jt2);
             */
        }

        if (column instanceof ColumnWrapper) {
            ColumnWrapper cw = (ColumnWrapper) column;

            //get @Temporal
            if (cw.isTemporal()) {
                field.addAnnotation(Entity.getTemporalAnnotation(cw));
            }

            //get @Column
            field.addAnnotation(Entity.getColumnAnnotation(cw));
        }

        return PrintJavaUtil.getField(field);
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
            a.add("updatable", Boolean.FALSE);
        }

        if (cw.getColumnType().isRequired()) {
            a.add("nullable", Boolean.FALSE);
        }

        if (cw.getColumnType().getSize() != null) {
            a.add("length", cw.getColumnType().getSize().intValue());
        }

        //TODO: precision/scale, size, length, etc.

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

        return a;
    }

    /**
     *
     * @param column
     * @return
     */
    public static String getter(@NotNull final ColumnWrapper column) {
        JavaReturnType returnType = new JavaReturnType(column.getSimpleName(), column.getVariableName());

        //TODO: determine annotations to add

        JavaMethod method = new JavaMethod(column.getSimpleName(), NameUtil.getJavaName(JavaNamingType.UPPER_CAMEL_CASE, column.getId()));
        method.addModifier(JavaMethodModifier.PUBLIC);
        method.setJavaReturnType(returnType);
        method.setNamePrefix(JavaMethodNamePrefix.GET);

        return PrintJavaUtil.getMethod(method);
    }

    /**
     *
     * @param column
     * @return
     */
    public static String setter(@NotNull final ColumnWrapper column) {

        //TODO: determine annotations to add

        JavaMethod method = new JavaMethod(column.getSimpleName(), NameUtil.getJavaName(JavaNamingType.UPPER_CAMEL_CASE, column.getId()));
        method.addModifier(JavaMethodModifier.PUBLIC);
        method.setJavaReturnType(null);
        method.setNamePrefix(JavaMethodNamePrefix.SET);
        method.addArgument(new JavaArgument(column.getSimpleName(), column.getVariableName()));

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

        //FIXME:
        Collection<ColumnWrapper> columns = allArgs ? table.getColumnsNonKeyList() : table.getColumnsNonKeyList();

        for (ColumnWrapper column : columns) {
            con.addArgument(new JavaArgument(column.getSimpleName(), column.getVariableName()));
        }

        return PrintJavaUtil.getConstructor(con);
    }

}
