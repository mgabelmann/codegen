package ca.mikegabelmann.codegen.java.lang.classbody;

import ca.mikegabelmann.codegen.java.lang.modifiers.JavaConstructorModifier;
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
public class JavaConstructor extends AbstractJavaTypeAnnotated implements JavaOrderedModifier<JavaConstructorModifier> {
    /** Constructor modifiers. */
    private final Set<JavaConstructorModifier> modifiers;

    /** Constructor arguments. */
    private final Set<JavaArgument> javaArguments;

    /** Constructor exceptions (if any). */
    private final Set<String> javaThrows;


    /**
     * Constructor.
     * @param name name
     */
    public JavaConstructor(@NotNull final String name) {
        super("", name);
        this.modifiers = new LinkedHashSet<>();
        this.javaArguments = new LinkedHashSet<>();
        this.javaThrows = new LinkedHashSet<>();
    }

    public Set<JavaArgument> getJavaArguments() {
        return javaArguments;
    }

    public Set<String> getJavaThrows() {
        return javaThrows;
    }

    @Override
    public Set<JavaConstructorModifier> getModifiers() {
        return modifiers;
    }

    @Override
    public void addModifier(@NotNull final JavaConstructorModifier modifier) {
        modifiers.add(modifier);
    }

    @Override
    public void addModifiers(@NotNull JavaConstructorModifier... modifiers) {
        this.modifiers.addAll(Arrays.asList(modifiers));
    }

    @Override
    public boolean removeModifier(@NotNull JavaConstructorModifier modifier) {
        return this.modifiers.remove(modifier);
    }

    @Override
    public List<JavaConstructorModifier> getOrderedModifiers() {
        List<JavaConstructorModifier> ordered = new ArrayList<>(modifiers);
        ordered.sort(Comparator.comparingInt(JavaConstructorModifier::getOrder));
        return ordered;
    }

    public void addArgument(@NotNull final JavaArgument argument) {
        javaArguments.add(argument);
    }

    public void addThrows(@NotNull final String value) {
        javaThrows.add(value);
    }

}
