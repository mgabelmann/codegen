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
    private JavaConstructorModifier modifier;

    /** Constructor arguments. */
    private final Set<JavaArgument> javaArguments;

    /** Constructor exceptions (if any). */
    private final Set<String> javaThrows;


    /**
     * Constructor.
     * @param name name
     */
    public JavaConstructor(@NotNull final String name) {
        this(name, JavaConstructorModifier.PACKAGE);
    }

    /**
     *
     * @param name
     * @param modifier
     */
    public JavaConstructor(@NotNull final String name, @NotNull final JavaConstructorModifier modifier) {
        super("", name);
        this.modifier = modifier;
        this.javaArguments = new LinkedHashSet<>();
        this.javaThrows = new LinkedHashSet<>();
    }

    public Set<JavaArgument> getArguments() {
        return javaArguments;
    }

    public void addArgument(@NotNull final JavaArgument argument) {
        javaArguments.add(argument);
    }

    public boolean removeArgument(@NotNull final JavaArgument argument) {
        return javaArguments.remove(argument);
    }

    @Override
    public Set<JavaConstructorModifier> getModifiers() {
        return Set.of(modifier);
    }

    @Override
    public void addModifier(@NotNull final JavaConstructorModifier modifier) {
        this.modifier = modifier;
    }

    @Override
    public void addModifiers(@NotNull JavaConstructorModifier... modifiers) {
        if (modifiers.length != 1) {
            throw new IllegalArgumentException("Constructor only accepts 1 modifier");
        }

        this.modifier = modifiers[0];
    }

    @Override
    public boolean removeModifier(@NotNull JavaConstructorModifier modifier) {
        this.modifier = JavaConstructorModifier.PACKAGE;
        return true;
    }

    @Override
    public List<JavaConstructorModifier> getOrderedModifiers() {
        List<JavaConstructorModifier> ordered = new ArrayList<>();
        ordered.add(modifier);
        return ordered;
    }

    public Set<String> getThrows() {
        return javaThrows;
    }

    public void addThrows(@NotNull final String value) {
        javaThrows.add(value);
    }

    public boolean removeThrows(@NotNull final String value) {
        return javaThrows.remove(value);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JavaConstructor{");
        sb.append("type='").append(type).append('\'');
        sb.append(", javaArguments=").append(javaArguments);
        sb.append(", javaThrows=").append(javaThrows);
        sb.append(", modifiers=").append(modifier);
        sb.append(", annotations=").append(annotations);
        sb.append('}');
        return sb.toString();
    }

}
