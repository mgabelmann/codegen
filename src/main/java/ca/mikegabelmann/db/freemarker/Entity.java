package ca.mikegabelmann.db.freemarker;

import ca.mikegabelmann.codegen.java.JavaNamingType;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaArgument;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaField;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaReturnType;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaFieldModifier;
import ca.mikegabelmann.codegen.util.NameUtil;
import ca.mikegabelmann.codegen.util.ObjectUtil;
import org.jetbrains.annotations.NotNull;

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

        return field.toString();
    }

    /**
     *
     * @param column
     * @return
     */
    public static String getter(@NotNull final ColumnWrapper column) {
        JavaReturnType returnType = new JavaReturnType(column.getSimpleName(), column.getVariableName());

        //TODO: determine annotations to add

        String methodName = NameUtil.getJavaName(JavaNamingType.UPPER_CAMEL_CASE, column.getColumnType().getName());
        return ObjectUtil.getMethod(methodName, returnType);
    }

    /**
     *
     * @param column
     * @return
     */
    public static String setter(@NotNull final ColumnWrapper column) {
        JavaArgument argument = new JavaArgument(column.getSimpleName(), column.getVariableName());

        //TODO: determine annotations to add

        String methodName = NameUtil.getJavaName(JavaNamingType.UPPER_CAMEL_CASE, column.getColumnType().getName());
        return ObjectUtil.setMethod(methodName, new JavaArgument[] { argument });
    }

}
