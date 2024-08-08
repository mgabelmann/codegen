package ca.mikegabelmann.codegen.java.lang.classbody;

import ca.mikegabelmann.codegen.java.lang.JavaPrimitive;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaFieldModifier;
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
public class JavaField extends AbstractJavaTypeAnnotated implements JavaType, JavaName {

    private final String type;
    private final String name;

    /** Field modifiers. */
    private final Set<JavaFieldModifier> modifiers;

    /**
     * Constructor.
     * @param type class or primitive type
     * @param name field or variable name
     */
    public JavaField(@NotNull final String type, @NotNull final String name) {
        super();
        this.type = type;
        this.name = name;
        this.modifiers = new LinkedHashSet<>();
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JavaField{");
        sb.append("type='").append(type).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", annotations=").append(annotations);
        sb.append(", modifiers=").append(modifiers);
        sb.append('}');
        return sb.toString();
    }

}
