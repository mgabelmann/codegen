package ca.mikegabelmann.codegen.java.lang.classbody;

import ca.mikegabelmann.codegen.java.lang.modifiers.JavaMethodModifier;
import ca.mikegabelmann.codegen.java.lang.JavaTokens;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author mgabelmann
 */
public class JavaMethod extends AbstractJavaTypeAnnotated {
    /** Method modifiers. */
    private Set<JavaMethodModifier> modifiers;


    /**
     * Constructor.
     * @param type class or primitive type
     * @param name field or variable name
     */
    public JavaMethod(@NotNull String type, @NotNull String name) {
        super(type, name);
        this.modifiers = new LinkedHashSet<>();
    }


    public Set<JavaMethodModifier> getModifiers() {
        return modifiers;
    }

    public void addModifier(@NotNull JavaMethodModifier modifier) {
        this.modifiers.add(modifier);
    }

    public boolean removeModifier(@NotNull JavaMethodModifier modifier) {
        return this.modifiers.remove(modifier);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (JavaMethodModifier modifier : modifiers) {
            sb.append(modifier.toString());
        }

        sb.append(super.toString());
        sb.append(JavaTokens.SEMICOLON);

        return sb.toString();
    }

}
