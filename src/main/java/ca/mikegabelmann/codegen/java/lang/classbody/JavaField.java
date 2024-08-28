package ca.mikegabelmann.codegen.java.lang.classbody;

import ca.mikegabelmann.codegen.Printable;
import ca.mikegabelmann.codegen.java.lang.JavaTokens;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaFieldModifier;
import ca.mikegabelmann.codegen.java.JavaClassPrintFactory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author mgabe
 */
public class JavaField extends AbstractJavaTypeAnnotated implements JavaType, JavaName, Printable {

    private final String type;

    private final String name;

    /** Field modifiers. */
    private final Set<JavaFieldModifier> modifiers;


    /**
     * Constructor.
     * @param clazz
     * @param name
     * @param required
     */
    public JavaField(@NotNull final Class<?> clazz, @NotNull final String name, final boolean required) {
        this(clazz.getCanonicalName(), name, required);
    }

    /**
     * Constructor.
     * @param type class or primitive type
     * @param name field or variable name
     * @param required is field required
     */
    public JavaField(@NotNull final String type, @NotNull final String name, final boolean required) {
        super();
        this.type = type;
        this.name = name;
        this.modifiers = new LinkedHashSet<>();

        if (required) {
            this.modifiers.add(JavaFieldModifier.FINAL);
        }
    }

    public Set<JavaFieldModifier> getModifiers() {
        return modifiers;
    }

    public void addModifier(@NotNull JavaFieldModifier modifier) {
        this.modifiers.add(modifier);
    }

    public boolean removeModifier(@NotNull JavaFieldModifier modifier) {
        return this.modifiers.remove(modifier);
    }

    public List<JavaFieldModifier> getOrderedModifiers() {
        List<JavaFieldModifier> ordered = new ArrayList<>(modifiers);
        ordered.sort(Comparator.comparingInt(JavaFieldModifier::getOrder));
        return ordered;
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
        return modifiers.contains(JavaFieldModifier.FINAL);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JavaField{");
        sb.append("type='").append(type).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", required=").append(this.isRequired());
        sb.append(", annotations=").append(annotations);
        sb.append(", modifiers=").append(modifiers);
        sb.append('}');

        return sb.toString();
    }

}
