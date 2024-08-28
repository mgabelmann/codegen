package ca.mikegabelmann.codegen.java.lang.classbody;

import ca.mikegabelmann.codegen.Printable;
import org.jetbrains.annotations.NotNull;


public class JavaImport implements JavaType, Printable {

    private final String type;

    /**
     * Constructor.
     * @param clazz
     */
    public JavaImport(@NotNull Class<?> clazz) {
        this(clazz.getSimpleName());
    }

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
