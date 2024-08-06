package ca.mikegabelmann.codegen.java.lang.classbody;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class AbstractJavaClass extends AbstractJavaTypeAnnotated {
    private final JavaPackage javaPackage;
    private final Set<JavaImport> javaImports;
    private final Set<JavaField> javaFields;
    private final Set<JavaConstructor> constructors;
    private final Set<JavaMethod> methods;

    /**
     * Constructor.
     * @param type
     * @param name
     * @param javaPackage
     */
    public AbstractJavaClass(@NotNull String type, @NotNull String name, @NotNull JavaPackage javaPackage) {
        super(type, name);
        this.javaPackage = javaPackage;
        this.javaImports = new HashSet<>();
        this.javaFields = new HashSet<>();
        this.constructors = new HashSet<>();
        this.methods = new HashSet<>();
    }

    public Set<JavaConstructor> getConstructors() {
        return constructors;
    }

    public Set<JavaField> getJavaFields() {
        return javaFields;
    }

    public Set<JavaImport> getJavaImports() {
        return javaImports;
    }

    public JavaPackage getJavaPackage() {
        return javaPackage;
    }

    public Set<JavaMethod> getMethods() {
        return methods;
    }


    public void addJavaImport(@NotNull JavaImport javaImport) {
        this.javaImports.add(javaImport);
    }

    public void addJavaField(@NotNull JavaField javaField) {
        this.javaFields.add(javaField);
    }

    public void addConstructor(@NotNull JavaConstructor javaConstructor) {
        this.constructors.add(javaConstructor);
    }

    public void addMethod(@NotNull JavaMethod javaMethod) {
        this.methods.add(javaMethod);
    }


}
