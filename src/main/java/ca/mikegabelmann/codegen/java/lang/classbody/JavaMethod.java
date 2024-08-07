package ca.mikegabelmann.codegen.java.lang.classbody;

import ca.mikegabelmann.codegen.java.lang.JavaMethodNamePrefix;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaConstructorModifier;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaMethodModifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author mgabelmann
 */
public class JavaMethod extends AbstractJavaTypeAnnotated {
    /** Method modifiers. */
    private final Set<JavaMethodModifier> modifiers;

    /**  */
    private JavaReturnType javaReturnType;

    /**  */
    private JavaMethodNamePrefix namePrefix;

    /**  */
    private final Set<JavaArgument> javaArguments;

    /**  */
    private final Set<String> javaThrows;


    /**
     * Constructor.
     * @param type class or primitive type
     * @param name field or variable name
     */
    public JavaMethod(@NotNull String type, @NotNull String name) {
        super(type, name);
        this.modifiers = new LinkedHashSet<>();
        this.javaArguments = new LinkedHashSet<>();
        this.javaThrows = new LinkedHashSet<>();
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

    public JavaReturnType getJavaReturnType() {
        return javaReturnType;
    }

    public void setJavaReturnType(JavaReturnType javaReturnType) {
        this.javaReturnType = javaReturnType;
    }

    public JavaMethodNamePrefix getNamePrefix() {
        return namePrefix;
    }

    public void setNamePrefix(JavaMethodNamePrefix namePrefix) {
        this.namePrefix = namePrefix;
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

    public List<JavaMethodModifier> getOrderedModifiers() {
        List<JavaMethodModifier> ordered = new ArrayList<>(modifiers);
        ordered.sort(Comparator.comparingInt(JavaMethodModifier::getOrder));
        return ordered;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JavaMethod{");
        sb.append("type='").append(type).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", annotations=").append(annotations);
        sb.append(", modifiers=").append(modifiers);
        sb.append(", javaReturnType=").append(javaReturnType);
        sb.append(", namePrefix=").append(namePrefix);
        sb.append(", javaArguments=").append(javaArguments);
        sb.append(", javaThrows=").append(javaThrows);
        sb.append('}');
        return sb.toString();
    }

}
