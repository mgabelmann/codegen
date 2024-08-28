package ca.mikegabelmann.codegen.java.lang.classbody;

import ca.mikegabelmann.codegen.Printable;
import org.jetbrains.annotations.NotNull;


public class JavaPackage implements Printable {

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
