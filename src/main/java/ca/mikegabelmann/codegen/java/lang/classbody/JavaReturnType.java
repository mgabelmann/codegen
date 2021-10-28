package ca.mikegabelmann.codegen.java.lang.classbody;

import org.jetbrains.annotations.NotNull;

/**
 *
 * @author mgabelmann
 */
public class JavaReturnType extends AbstractJavaType {

    /**
     * Constructor.
     * @param type object/primitive type
     * @param name variable name
     */
    public JavaReturnType(@NotNull final String type, @NotNull final String name) {
        super(type, name);
    }

    @Override
    public String toString() {
        return type;
    }

}
