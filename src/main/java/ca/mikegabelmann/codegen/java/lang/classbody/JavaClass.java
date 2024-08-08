package ca.mikegabelmann.codegen.java.lang.classbody;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;


public class JavaClass extends AbstractJavaTypeAnnotated implements JavaType, JavaName {
    private final JavaPackage javaPackage;
    private final Set<String> javaImports;
    private final Set<JavaField> javaFields;
    private final Set<JavaConstructor> constructors;
    private final Set<JavaMethod> methods;

    private final String type;
    private final String name;

    /**
     * Constructor.
     * @param type
     * @param name
     * @param javaPackage
     */
    public JavaClass(@NotNull final String type, @NotNull final String name, @NotNull final JavaPackage javaPackage) {
        super();
        this.javaPackage = javaPackage;
        this.javaImports = new HashSet<>();
        this.javaFields = new HashSet<>();
        this.constructors = new HashSet<>();
        this.methods = new HashSet<>();
        this.type = type;
        this.name = name;
    }

    public Set<JavaConstructor> getConstructors() {
        return constructors;
    }

    public Set<JavaField> getJavaFields() {
        return javaFields;
    }

    public Set<String> getJavaImports() {
        return javaImports;
    }

    public JavaPackage getJavaPackage() {
        return javaPackage;
    }

    public Set<JavaMethod> getMethods() {
        return methods;
    }


    public void addJavaImport(@NotNull final String javaImport) {
        this.javaImports.add(javaImport);
    }

    public void addJavaField(@NotNull final JavaField javaField) {
        this.javaFields.add(javaField);
    }

    public void addConstructor(@NotNull final JavaConstructor javaConstructor) {
        this.constructors.add(javaConstructor);
    }

    public void addMethod(@NotNull final JavaMethod javaMethod) {
        this.methods.add(javaMethod);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCanonicalName() {
        return type;
    }

    @Override
    public String getSimpleName() {
        return !type.contains(".") ? type : type.substring(type.lastIndexOf(".") + 1);
    }

    public Set<String> getAllImports() {
        Set<String> imports = new TreeSet<>();

        imports.addAll(javaImports);

        imports.addAll(javaFields.stream().map(JavaField::getCanonicalName).toList());

        for (JavaConstructor javaConstructor : constructors) {
            imports.addAll(javaConstructor.getJavaArguments().stream().map(JavaArgument::getCanonicalName).toList());
            imports.addAll(javaConstructor.getJavaThrows().stream().toList());
        }

        for (JavaMethod javaMethod : methods) {
            imports.add(javaMethod.getJavaReturnType().getCanonicalName());
            imports.addAll(javaMethod.getArguments().stream().map(JavaArgument::getCanonicalName).toList());
            imports.addAll(javaMethod.getThrows().stream().toList());
        }

        return imports;
    }

}
