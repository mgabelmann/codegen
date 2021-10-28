package ca.mikegabelmann.codegen.java.lang.classbody;

import ca.mikegabelmann.codegen.java.lang.modifiers.JavaConstructorModifier;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author mgabe
 */
public class JavaConstructor extends AbstractJavaTypeAnnotated {
    /** Constructor modifiers. */
    private final Set<JavaConstructorModifier> modifiers;

    //FIXME: add arguments, throws, etc.

    /**
     * Constructor.
     * @param type class or primitive type
     * @param name field or variable name
     */
    public JavaConstructor(@NotNull String type, @NotNull String name) {
        super(type, name);
        this.modifiers = new LinkedHashSet<>();
    }


    public Set<JavaConstructorModifier> getModifiers() {
        return modifiers;
    }

    public void addModifier(@NotNull JavaConstructorModifier modifier) {
        this.modifiers.add(modifier);
    }

    public boolean removeModifier(@NotNull JavaConstructorModifier modifier) {
        return this.modifiers.remove(modifier);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        /*
        for (JavaAnnotation annotation : annotations) {
            sb.append(annotation.toString());
            sb.append(JavaTokens.SPACE);
        }

        for (JavaConstructorModifier modifier : modifiers) {
            sb.append(modifier.toString());
        }

        sb.append(super.toString());
        sb.append(JavaTokens.SEMICOLON);
         */

        return sb.toString();
    }

}
