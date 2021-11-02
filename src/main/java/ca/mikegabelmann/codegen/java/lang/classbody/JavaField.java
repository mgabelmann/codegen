package ca.mikegabelmann.codegen.java.lang.classbody;

import ca.mikegabelmann.codegen.java.lang.modifiers.JavaFieldModifier;
import ca.mikegabelmann.codegen.java.lang.JavaTokens;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author mgabe
 */
public class JavaField extends AbstractJavaTypeAnnotated {
    /** Field modifiers. */
    private final Set<JavaFieldModifier> modifiers;


    /**
     * Constructor.
     * @param type class or primitive type
     * @param name field or variable name
     */
    public JavaField(@NotNull String type, @NotNull String name) {
        super(type, name);
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
