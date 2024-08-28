package ca.mikegabelmann.codegen.java.lang.classbody;

import ca.mikegabelmann.codegen.Printable;
import ca.mikegabelmann.codegen.java.lang.JavaKeywords;
import ca.mikegabelmann.codegen.java.lang.JavaPrimitive;
import ca.mikegabelmann.codegen.java.lang.JavaTokens;
import ca.mikegabelmann.codegen.java.JavaClassPrintFactory;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author mgabe
 */
public class JavaArgument extends AbstractJavaTypeAnnotated implements JavaType, JavaName, Printable {
    /** Is the argument final. */
    private final boolean required;

    private final String name;

    private final String type;


    /**
     *
     * @param type
     * @param name
     * @param required
     */
    public JavaArgument(@NotNull final JavaPrimitive type, @NotNull final String name, final boolean required) {
        this(type.getCanonicalName(), name, required);
    }

    /**
     *
     * @param type
     * @param name
     * @param required
     */
    public JavaArgument(@NotNull final String type, @NotNull final String name, final boolean required) {
        this.type = type;
        this.name = name;
        this.required = required;
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
