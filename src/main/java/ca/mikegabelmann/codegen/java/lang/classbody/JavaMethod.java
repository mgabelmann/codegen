package ca.mikegabelmann.codegen.java.lang.classbody;

import ca.mikegabelmann.codegen.NamingType;
import ca.mikegabelmann.codegen.java.lang.JavaMethodNamePrefix;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaMethodModifier;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaOrderedModifier;
import ca.mikegabelmann.codegen.util.NameUtil;
import ca.mikegabelmann.codegen.java.JavaClassPrintFactory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author mgabelmann
 */
public class JavaMethod extends AbstractJavaTypeAnnotated implements JavaOrderedModifier<JavaMethodModifier> {
    /** Method modifiers. */
    private final Set<JavaMethodModifier> modifiers;

    /**  */
    private final Set<JavaArgument> javaArguments;

    /**  */
    private final Set<String> javaThrows;

    /**  */
    private final StringBuilder body;

    /**  */
    private JavaMethodNamePrefix namePrefix;

    /**  */
    private JavaReturnType javaReturnType;


    /**
     * Constructor.
     * @param name field or variable name
     */
    public JavaMethod(@NotNull final String name) {
        super("", name);
        this.modifiers = new LinkedHashSet<>();
        this.javaArguments = new LinkedHashSet<>();
        this.javaThrows = new LinkedHashSet<>();
        this.body = new StringBuilder();
        this.namePrefix = JavaMethodNamePrefix.NONE;
        this.javaReturnType = new JavaReturnType();
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

    public StringBuilder getBody() {
        return body;
    }

    @Override
    public Set<JavaMethodModifier> getModifiers() {
        return modifiers;
    }

    @Override
    public void addModifier(@NotNull JavaMethodModifier modifier) {
        this.modifiers.add(modifier);
    }

    @Override
    public void addModifiers(@NotNull JavaMethodModifier... modifiers) {
        this.modifiers.addAll(Arrays.asList(modifiers));
    }

    @Override
    public boolean removeModifier(@NotNull JavaMethodModifier modifier) {
        return this.modifiers.remove(modifier);
    }

    @Override
    public List<JavaMethodModifier> getOrderedModifiers() {
        List<JavaMethodModifier> ordered = new ArrayList<>(modifiers);
        ordered.sort(Comparator.comparingInt(JavaMethodModifier::getOrder));
        return ordered;
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

    public static JavaMethod getGetter(
            @NotNull final String name,
            @NotNull final String canonicalName,
            @NotNull final JavaMethodModifier... modifiers) {

        String methName = NameUtil.getJavaName(NamingType.UPPER_CAMEL_CASE, name);
        String varName = NameUtil.getJavaName(NamingType.LOWER_CAMEL_CASE, name);

        JavaMethod jm = new JavaMethod(methName);
        jm.setNamePrefix(JavaMethodNamePrefix.GET);
        jm.setJavaReturnType(new JavaReturnType(canonicalName));
        jm.getBody().append(JavaClassPrintFactory.printFieldReturnValue(varName, true));

        Arrays.stream(modifiers).forEach(jm::addModifier);

        return jm;
    }

    public static JavaMethod getSetter(
            @NotNull final String name,
            @NotNull final String canonicalName,
            @NotNull final JavaMethodModifier... modifiers) {

        String methName = NameUtil.getJavaName(NamingType.UPPER_CAMEL_CASE, name);
        String varName = NameUtil.getJavaName(NamingType.LOWER_CAMEL_CASE, name);

        JavaMethod jm = new JavaMethod(methName);
        jm.setNamePrefix(JavaMethodNamePrefix.SET);
        jm.addArgument(new JavaArgument(canonicalName, varName, true));
        jm.getBody().append(JavaClassPrintFactory.printFieldAssignment(varName, true));

        Arrays.stream(modifiers).forEach(jm::addModifier);

        return jm;
    }

}
