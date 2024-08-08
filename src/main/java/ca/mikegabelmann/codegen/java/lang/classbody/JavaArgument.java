package ca.mikegabelmann.codegen.java.lang.classbody;

import ca.mikegabelmann.codegen.java.lang.JavaPrimitive;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author mgabe
 */
public class JavaArgument extends AbstractJavaTypeAnnotated implements JavaType, JavaName {
    /** Is the argument final. */
    private final boolean isFinal;

    private final String name;

    private final String type;

    /**
     *
     * @param type
     * @param name
     * @param isFinal
     */
    public JavaArgument(@NotNull final JavaPrimitive type, @NotNull final String name, final boolean isFinal) {
        this(type.getCanonicalName(), name, isFinal);
    }

    /**
     *
     * @param type
     * @param name
     * @param isFinal
     */
    public JavaArgument(@NotNull final String type, @NotNull final String name, final boolean isFinal) {
        this.type = type;
        this.name = name;
        this.isFinal = isFinal;
    }

    @Override
    public String getCanonicalName() {
        return type;
    }

    @Override
    public String getSimpleName() {
        return !type.contains(".") ? type : type.substring(type.lastIndexOf(".") + 1);
    }

    @Override
    public String getName() {
        return name;
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
