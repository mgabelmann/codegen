package ca.mikegabelmann.codegen.java.lang.classbody;

import ca.mikegabelmann.codegen.java.lang.JavaKeywords;
import ca.mikegabelmann.codegen.java.lang.JavaPrimitive;
import org.jetbrains.annotations.NotNull;


public class JavaReturnType extends AbstractJavaType {

    public JavaReturnType(@NotNull final JavaPrimitive primitive) {
        this(primitive.getCanonicalName());
    }

    public JavaReturnType(@NotNull final Class<?> clazz) {
        this(clazz.getCanonicalName());
    }

    public JavaReturnType(@NotNull final String type) {
        super(type, "");
    }

    public JavaReturnType() {
        super(JavaKeywords.VOID.trim(), "");
    }

    @Override
    public String toString() {
        return "JavaReturnType{" +
                "type='" + type + '\'' +
                '}';
    }

}
