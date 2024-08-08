package ca.mikegabelmann.codegen.java.lang.classbody;

import ca.mikegabelmann.codegen.java.lang.modifiers.JavaConstructorModifier;
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
public class JavaConstructor extends AbstractJavaTypeAnnotated implements JavaName {
    /** Constructor modifiers. */
    private final Set<JavaConstructorModifier> modifiers;

    /** Constructor arguments. */
    private final Set<JavaArgument> javaArguments;

    /** Constructor exceptions (if any). */
    private final Set<String> javaThrows;

    private final String name;

    /**
     * Constructor.
     * @param name name
     */
    public JavaConstructor(@NotNull final String name) {
        super();
        this.modifiers = new LinkedHashSet<>();
        this.javaArguments = new LinkedHashSet<>();
        this.javaThrows = new LinkedHashSet<>();
        this.name = name;
    }

    public Set<JavaArgument> getJavaArguments() {
        return javaArguments;
    }

    public Set<String> getJavaThrows() {
        return javaThrows;
    }

    public Set<JavaConstructorModifier> getModifiers() {
        return modifiers;
    }

    public void addModifier(@NotNull final JavaConstructorModifier modifier) {
        modifiers.add(modifier);
    }

    public void addArgument(@NotNull final JavaArgument argument) {
        javaArguments.add(argument);
    }

    public void addThrows(@NotNull final String value) {
        javaThrows.add(value);
    }

    public List<JavaConstructorModifier> getOrderedModifiers() {
        List<JavaConstructorModifier> ordered = new ArrayList<>(modifiers);
        ordered.sort(Comparator.comparingInt(JavaConstructorModifier::getOrder));
        return ordered;
    }

    @Override
    public String getName() {
        return name;
    }

}
