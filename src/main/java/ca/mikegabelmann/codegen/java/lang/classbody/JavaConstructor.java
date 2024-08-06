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

    /** Constructor arguments. */
    private final Set<JavaArgument> javaArguments;

    /** Constructor exceptions (if any). */
    private final Set<String> javaThrows;


    /**
     * Constructor.
     * @param type class or primitive type
     */
    public JavaConstructor(@NotNull String type) {
        super(type, "");
        this.modifiers = new LinkedHashSet<>();
        this.javaArguments = new LinkedHashSet<>();
        this.javaThrows = new LinkedHashSet<>();
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

    public Set<JavaArgument> getArguments() {
        return javaArguments;
    }

    public void addArgument(@NotNull JavaArgument argument) {
        this.javaArguments.add(argument);
    }

    public boolean removeArgument(@NotNull JavaArgument argument) {
        return this.javaArguments.remove(argument);
    }

    public Set<String> getThrows() {
        return javaThrows;
    }

    public void addThrows(@NotNull String javaThrows) {
        this.javaThrows.add(javaThrows);
    }

    public boolean removeThrows(@NotNull String javaThrows) {
        return this.javaThrows.remove(javaThrows);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JavaConstructor{");
        sb.append("type='").append(type).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", annotations=").append(annotations);
        sb.append(", modifiers=").append(modifiers);
        sb.append(", javaArguments=").append(javaArguments);
        sb.append(", javaThrows=").append(javaThrows);
        sb.append('}');
        return sb.toString();
    }
}
