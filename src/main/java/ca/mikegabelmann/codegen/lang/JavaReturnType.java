package ca.mikegabelmann.codegen.lang;

import org.jetbrains.annotations.NotNull;

/**
 *
 * @author mgabelmann
 */
public class JavaReturnType {
    private final String type;
    private final String name;


    /**
     * Constructor.
     * @param type object/primitive type
     * @param name variable name
     */
    public JavaReturnType(
        @NotNull final String type,
        @NotNull final String name) {

        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return type + JavaTokens.SPACE + name;
    }

}
