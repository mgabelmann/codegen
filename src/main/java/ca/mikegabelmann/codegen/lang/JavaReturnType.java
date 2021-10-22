package ca.mikegabelmann.codegen.lang;

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
    public JavaReturnType(final String type, final String name) {
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
