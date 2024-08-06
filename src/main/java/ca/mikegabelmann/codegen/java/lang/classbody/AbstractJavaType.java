package ca.mikegabelmann.codegen.java.lang.classbody;

import org.jetbrains.annotations.NotNull;

/**
 *
 * @author mgabe
 */
abstract class AbstractJavaType {
    /** Class or primitive type. */
    protected final String type;

    /** field or variable name. */
    protected final String name;


    /**
     * Constructor.
     * @param type class or primitive type
     * @param name field or variable name
     */
    AbstractJavaType(@NotNull final String type, @NotNull final String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * Get Java type.
     * @return Java type
     */
    public final String getType() {
        return type;
    }

    /**
     * Get Java name.
     * @return Java name
     */
    public final String getName() {
        return name;
    }

}
