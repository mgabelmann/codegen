package ca.mikegabelmann.codegen.java.lang.classbody;

import ca.mikegabelmann.codegen.java.lang.JavaMethodNamePrefix;
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
public class JavaMethod extends AbstractJavaTypeAnnotated implements JavaName {
    /** Method modifiers. */
    private final Set<JavaMethodModifier> modifiers;

    /**  */
    private final Set<JavaArgument> javaArguments;

    /**  */
    private final Set<String> javaThrows;

    /**  */
    private final String name;

    /**  */
    private JavaMethodNamePrefix namePrefix;

    /**  */
    private String body;

    /**  */
    private JavaReturnType javaReturnType;


    /**
     * Constructor.
     * @param name field or variable name
     */
    public JavaMethod(@NotNull String name) {
        super();
        this.modifiers = new LinkedHashSet<>();
        this.javaArguments = new LinkedHashSet<>();
        this.javaThrows = new LinkedHashSet<>();

        this.name = name;
        this.namePrefix = JavaMethodNamePrefix.NONE;
        this.body = null;
        this.javaReturnType = null;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<JavaMethodModifier> getOrderedModifiers() {
        List<JavaMethodModifier> ordered = new ArrayList<>(modifiers);
        ordered.sort(Comparator.comparingInt(JavaMethodModifier::getOrder));
        return ordered;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JavaMethod{");
        sb.append("name='").append(name).append('\'');
        sb.append(", body='").append(body).append('\'');
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
