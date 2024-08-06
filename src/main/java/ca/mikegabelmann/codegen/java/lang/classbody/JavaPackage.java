package ca.mikegabelmann.codegen.java.lang.classbody;

import org.jetbrains.annotations.NotNull;

public class JavaPackage extends AbstractJavaType {


    public JavaPackage(@NotNull String type) {
        super(type, "");
    }

    @Override
    public String toString() {
        return "JavaPackage{" +
                "name='" + name + '\'' +
                '}';
    }
}
