package ca.mikegabelmann.codegen.java.lang.classbody;

import ca.mikegabelmann.codegen.java.lang.JavaPrimitive;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author mgabe
 */
public class JavaArgument extends AbstractJavaTypeAnnotated {
    /** Is the argument final. */
    private final boolean required;


    /**
     * Constructor.
     * @param type
     * @param name
     * @param required
     */
    public JavaArgument(@NotNull final JavaPrimitive type, @NotNull final String name, final boolean required) {
        this(type.getCanonicalName(), name, required);
    }

    /**
     * Constructor.
     * @param clazz
     * @param name
     * @param required
     */
    public JavaArgument(@NotNull final Class<?> clazz, @NotNull final String name, final boolean required) {
        this(clazz.getCanonicalName(), name, required);
    }

    /**
     * Constructor.
     * @param type
     * @param name
     * @param required
     */
    public JavaArgument(@NotNull final String type, @NotNull final String name, final boolean required) {
        super(type, name);
        this.required = required;
    }

    public boolean isRequired() {
        return required;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JavaArgument{");
        sb.append("type='").append(type).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", annotations=").append(annotations);
        sb.append(", required=").append(required);
        sb.append('}');
        return sb.toString();
    }

}
