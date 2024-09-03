package ca.mikegabelmann.codegen.java.lang.classbody;

import org.jetbrains.annotations.NotNull;


public class JavaImport extends AbstractJavaType {

    /**
     * Constructor.
     * @param clazz
     */
    public JavaImport(@NotNull Class<?> clazz) {
        this(clazz.getCanonicalName());
    }

    /**
     * Constructor.
     * @param type canonical name [package.]class
     */
    public JavaImport(@NotNull final String type) {
        super(type, "");
    }

    @Override
    public String toString() {
        return "JavaImport{" +
                "importName='" + type + '\'' +
                '}';
    }

}
