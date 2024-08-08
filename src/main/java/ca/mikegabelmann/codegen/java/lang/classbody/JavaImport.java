package ca.mikegabelmann.codegen.java.lang.classbody;

import org.jetbrains.annotations.NotNull;


public class JavaImport implements JavaType {

    private final String type;

    /**
     * Constructor.
     * @param type canonical name [package.]class
     */
    public JavaImport(@NotNull final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getCanonicalName() {
        return type;
    }

    @Override
    public String getSimpleName() {
        return !type.contains(".") ? type : type.substring(type.lastIndexOf('.') + 1);
    }

    @Override
    public String toString() {
        return "JavaImport{" +
                "importName='" + type + '\'' +
                '}';
    }

}
