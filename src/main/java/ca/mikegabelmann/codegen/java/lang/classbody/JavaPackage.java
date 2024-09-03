package ca.mikegabelmann.codegen.java.lang.classbody;

import org.jetbrains.annotations.NotNull;


public class JavaPackage extends AbstractJavaType {


    public JavaPackage(@NotNull final String name) {
        super("", name);
    }

    @Override
    public String toString() {
        return "JavaPackage{" +
                "packageName='" + name + '\'' +
                '}';
    }

}
