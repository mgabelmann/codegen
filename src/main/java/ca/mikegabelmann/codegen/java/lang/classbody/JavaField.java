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
        StringBuilder sb = new StringBuilder();

        for (JavaAnnotation annotation : annotations) {
            sb.append(annotation.toString());
            sb.append(JavaTokens.NEWLINE);
        }

        for (JavaFieldModifier modifier : modifiers) {
            sb.append(modifier.toString());
        }

        sb.append(this.getType());
        sb.append(JavaTokens.SPACE);
        sb.append(this.getName());

        sb.append(JavaTokens.SEMICOLON);

        return sb.toString();
    }

}
