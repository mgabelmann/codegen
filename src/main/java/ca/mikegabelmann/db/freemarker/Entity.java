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

import javax.persistence.TemporalType;
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
    public static String field(@NotNull final ColumnWrapper column) {
        JavaField field = new JavaField(column.getSimpleName(), column.getVariableName());
        field.addModifier(JavaFieldModifier.PRIVATE);

        //TODO: determine annotations to add

/*
<table name="contact_groups" baseClass="" abstract="false" javaName="ContactGroups" description="" xmlns="http://db.apache.org/torque/5.0/templates/database">
    <column name="contact_id" type="INTEGER" primaryKey="false" autoIncrement="false" required="false" javaName="contactId" description=""/>
    <column name="group_id" type="INTEGER" primaryKey="false" autoIncrement="false" required="true" javaName="groupId" description=""/>
    <foreign-key foreignTable="contacts">
        <reference foreign="remote1_id" local="local1_id"/>
    </foreign-key>
    <foreign-key foreignTable="groups">
        <reference foreign="remote1_id" local="local1_id"/>
        <reference foreign="remote2_id" local="local2_id"/>
    </foreign-key>
</table>
*/

        //@Id
        if (column.getColumnType().isPrimaryKey()) {
            field.addAnnotation(new JavaAnnotation("Id"));
        }

        //@Column
        JavaAnnotation a = new JavaAnnotation("Column");
        a.add("name", column.getColumnType().getName());

        if (column.getColumnType().isPrimaryKey()) {
            a.add("updatable", Boolean.FALSE);
        }

        if (column.getColumnType().isRequired()) {
            a.add("nullable", Boolean.FALSE);
        }

        if (column.getColumnType().getSize() != null) {
            a.add("length", column.getColumnType().getSize().intValue());
        }

        //TODO: precision/scale, size, length

        field.addAnnotation(a);

        if (column.getColumnType().isAutoIncrement()) {
            //TODO: need to add annotations for sequence generator
            //@GeneratedValue(strategy=GenerationType.AUTO, generator="addressSeq")
            //@SequenceGenerator(name="addressSeq", sequenceName="ADDRESS_SEQ")
        }

        //@Temporal
        if (column.isTemporal()) {
            JavaAnnotation t = new JavaAnnotation("Temporal");

            if (column.getColumnType().getType().equals(SqlDataType.DATE)) {
                t.add("value", TemporalType.DATE);

            } else if (column.getColumnType().getType().equals(SqlDataType.TIME)) {
                t.add("value", TemporalType.TIME);

            } else if (column.getColumnType().getType().equals(SqlDataType.TIMESTAMP)) {
                t.add("value", TemporalType.TIMESTAMP);
            }

            field.addAnnotation(t);
        }


        return PrintJavaUtil.getField(field);
    }

    /**
     *
     * @param column
     * @return
     */
    public static String getter(@NotNull final ColumnWrapper column) {
        JavaReturnType returnType = new JavaReturnType(column.getSimpleName(), column.getVariableName());

        //TODO: determine annotations to add

        JavaMethod method = new JavaMethod(column.getSimpleName(), NameUtil.getJavaName(JavaNamingType.UPPER_CAMEL_CASE, column.getColumnType().getName()));
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

        JavaMethod method = new JavaMethod(column.getSimpleName(), NameUtil.getJavaName(JavaNamingType.UPPER_CAMEL_CASE, column.getColumnType().getName()));
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

        List<ColumnWrapper> columns = allArgs ? table.getAllColumns() : table.getAllColumns();//table.getRequiredColumns();

        for (ColumnWrapper column : columns) {
            con.addArgument(new JavaArgument(column.getSimpleName(), column.getVariableName()));
        }

        return PrintJavaUtil.getConstructor(con);
    }

}
