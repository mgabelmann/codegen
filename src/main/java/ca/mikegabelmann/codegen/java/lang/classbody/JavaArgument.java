package ca.mikegabelmann.codegen.java.lang.classbody;

import org.jetbrains.annotations.NotNull;

/**
 *
 * @author mgabe
 */
public class JavaArgument extends AbstractJavaTypeAnnotated {
    /** Is the argument final. */
    private final boolean isFinal;


    /**
     * Constructor.
     * @param type
     * @param name
     */
    public JavaArgument(@NotNull String type, @NotNull String name) {
        this(type, name, true);
    }

    /**
     *
     * @param type
     * @param name
     * @param isFinal
     */
    public JavaArgument(@NotNull String type, @NotNull String name, boolean isFinal) {
        super(type, name);
        this.isFinal = isFinal;
    }

    /**
     *
     * @return
     */
    public boolean isFinal() {
        return isFinal;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JavaArgument{");
        sb.append("type='").append(type).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", annotations=").append(annotations);
        sb.append(", isFinal=").append(isFinal);
        sb.append('}');
        return sb.toString();
    }

}
