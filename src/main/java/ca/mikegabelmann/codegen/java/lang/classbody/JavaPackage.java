package ca.mikegabelmann.codegen.java.lang.classbody;

import org.jetbrains.annotations.NotNull;


public class JavaPackage {

    private final String packageName;

    public JavaPackage(@NotNull final String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    @Override
    public String toString() {
        return "JavaPackage{" +
                "packageName='" + packageName + '\'' +
                '}';
    }

}
