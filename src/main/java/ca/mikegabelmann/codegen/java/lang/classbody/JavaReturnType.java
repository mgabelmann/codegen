package ca.mikegabelmann.codegen.java.lang.classbody;

import ca.mikegabelmann.codegen.java.lang.JavaKeywords;
import ca.mikegabelmann.codegen.java.lang.JavaPrimitive;
import org.jetbrains.annotations.NotNull;


public class JavaReturnType implements JavaType {
    private final String type;


    public JavaReturnType(@NotNull final JavaPrimitive primitive) {
        this(primitive.getCanonicalName());
    }

    public JavaReturnType(@NotNull final String type) {
        this.type = type;
    }

    public JavaReturnType() {
        this.type = JavaKeywords.VOID.trim();
    }

    @Override
    public String getCanonicalName() {
        return type;
    }

    @Override
    public String getSimpleName() {
        return !type.contains(".") ? type : type.substring(type.lastIndexOf(".") + 1);
    }

    @Override
    public String toString() {
        return "JavaReturnType{" +
                "type='" + type + '\'' +
                '}';
    }

}
