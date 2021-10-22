package ca.mikegabelmann.codegen.lang;

import org.jetbrains.annotations.NotNull;

/**
 *
 * @author mgabelmann
 */
public class JavaArgument {

    //FIXME: need to add Annotations here.

    private final boolean isFinal;
    private final String type;
    private final String name;


    /**
     * Constructor.
     * @param type object/primitive type
     * @param name type name
     */
    public JavaArgument(
        @NotNull final String type,
        @NotNull final String name) {

        this(true, type, name);
    }

    /**
     * All args constructor.
     * @param isFinal whether the type is final
     * @param type object/primitive type
     * @param name type name
     */
    public JavaArgument(
        final boolean isFinal,
        @NotNull final String type,
        @NotNull final String name) {

        this.isFinal = isFinal;
        this.type = type;
        this.name = name;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (isFinal) {
            sb.append(JavaKeywords.FINAL);
        }

        sb.append(type);
        sb.append(JavaTokens.SPACE);
        sb.append(name);

        return sb.toString();
    }
}
