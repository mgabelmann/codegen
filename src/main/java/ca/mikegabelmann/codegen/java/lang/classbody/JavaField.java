package ca.mikegabelmann.codegen.java.lang.classbody;

import ca.mikegabelmann.codegen.java.lang.modifiers.JavaFieldModifier;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaOrderedModifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author mgabe
 */
public class JavaField extends AbstractJavaTypeAnnotated implements JavaOrderedModifier<JavaFieldModifier> {

    /** Field modifiers. */
    private final Set<JavaFieldModifier> modifiers;

    private String initializationValue;

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
        super(type, name);
        this.modifiers = new LinkedHashSet<>();

        if (required) {
            this.modifiers.add(JavaFieldModifier.FINAL);
        }
    }

    public String getInitializationValue() {
        return initializationValue;
    }

    public void setInitializationValue(String initializationValue) {
        this.initializationValue = initializationValue;
    }

    public boolean isRequired() {
        return modifiers.contains(JavaFieldModifier.FINAL);
    }

    @Override
    public Set<JavaFieldModifier> getModifiers() {
        return modifiers;
    }

    @Override
    public void addModifier(@NotNull JavaFieldModifier modifier) {
        this.modifiers.add(modifier);
    }

    @Override
    public void addModifiers(@NotNull JavaFieldModifier... modifiers) {
        this.modifiers.addAll(Arrays.asList(modifiers));
    }

    @Override
    public boolean removeModifier(@NotNull JavaFieldModifier modifier) {
        return this.modifiers.remove(modifier);
    }

    @Override
    public List<JavaFieldModifier> getOrderedModifiers() {
        List<JavaFieldModifier> ordered = new ArrayList<>(modifiers);
        ordered.sort(Comparator.comparingInt(JavaFieldModifier::getOrder));
        return ordered;
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
