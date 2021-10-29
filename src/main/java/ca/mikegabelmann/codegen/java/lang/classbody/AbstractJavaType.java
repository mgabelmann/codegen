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
    AbstractJavaType(@NotNull String type, @NotNull String name) {
        this.type = type;
        this.name = name;
    }

    public final String getType() {
        return type;
    }

    public final String getName() {
        return name;
    }

    /*@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(type);
        sb.append(JavaTokens.SPACE);
        sb.append(name);

        return sb.toString();
    }*/

}
