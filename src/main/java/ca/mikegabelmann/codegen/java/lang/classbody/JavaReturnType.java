package ca.mikegabelmann.codegen.java.lang.classbody;

import ca.mikegabelmann.codegen.java.lang.JavaPrimitive;
import org.jetbrains.annotations.NotNull;


public class JavaReturnType implements JavaType, JavaName {

    private final String type;
    private final String name;


    public JavaReturnType(@NotNull JavaPrimitive primitive, @NotNull String name) {
        this(primitive.getCanonicalName(), name);
    }

    public JavaReturnType(@NotNull String type, @NotNull String name) {
        this.type = type;
        this.name = name;
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
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "JavaReturnType{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
