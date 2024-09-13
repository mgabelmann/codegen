package ca.mikegabelmann.codegen.java;

import ca.mikegabelmann.codegen.java.lang.JavaKeywords;
import ca.mikegabelmann.codegen.java.lang.JavaMethodNamePrefix;
import ca.mikegabelmann.codegen.java.lang.JavaTokens;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaAnnotation;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaArgument;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaClass;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaConstructor;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaField;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaImport;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaMethod;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaPackage;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaReturnType;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaFieldModifier;
import ca.mikegabelmann.codegen.util.AnnotationUtil;
import ca.mikegabelmann.codegen.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.stream.Collectors;

/**
 * Used to format various objects for printing to stream, file, etc.
 *
 * @author mgabe
 */
public class JavaClassPrintFactory extends AbstractJavaPrintFactory {
    /** Logger. */
    private static final Logger logger = LogManager.getLogger(JavaClassPrintFactory.class);

    /**
     * Get Java annotation.
     * @param annotation
     * @return
     */
    String printAnnotation(@NotNull final JavaAnnotation annotation) {
        StringBuilder sb = new StringBuilder();
        sb.append(JavaTokens.ANNOTATION);
        sb.append(annotation.getSimpleName());

        SortedMap<String, List<Object>> map = annotation.getProperties();

        if (map != null && !map.isEmpty()) {
            sb.append(JavaTokens.BRACKET_LEFT);
            sb.append(AnnotationUtil.getString(map));
            sb.append(JavaTokens.BRACKET_RIGHT);
        }

        return sb.toString();
    }

    /**
     * Get Java argument.
     * @param argument argument
     * @return
     */
    String printArgument(@NotNull final JavaArgument argument) {
        StringBuilder sb = new StringBuilder();

        for (JavaAnnotation annotation : argument.getAnnotations()) {
            sb.append(this.printAnnotation(annotation));
            sb.append(JavaTokens.SPACE);
        }

        if (argument.isRequired()) {
            sb.append(JavaKeywords.FINAL);
        }

        sb.append(argument.getSimpleName());
        sb.append(JavaTokens.SPACE);
        sb.append(argument.getName());

        return sb.toString();
    }

    String printClass(@NotNull final JavaClass javaClass) {
        //TODO: implement me
        //throw new RuntimeException("not implemented yet");
        StringBuilder sb = new StringBuilder();

        sb.append(printPackage(javaClass.getJavaPackage()));
        sb.append(JavaTokens.NEWLINE);

        sb.append(javaClass.getAllImports().stream().map(a-> this.printImport(new JavaImport(a))).collect(Collectors.joining(JavaTokens.NEWLINE)));
        sb.append(JavaTokens.NEWLINE);

        Set<JavaAnnotation> classAnnotations = javaClass.getAnnotations();
        sb.append(classAnnotations.stream().map(this::printAnnotation).collect(Collectors.joining(JavaTokens.NEWLINE)));
        if (!classAnnotations.isEmpty()) {
            sb.append(JavaTokens.NEWLINE);
        }

        String modifiersList = javaClass.getOrderedModifiers().stream().map(Object::toString).collect(Collectors.joining(JavaTokens.SPACE));
        sb.append(modifiersList);
        sb.append(JavaKeywords.CLASS);
        sb.append(javaClass.getSimpleName());
        sb.append(JavaTokens.SPACE);
        //TODO: add implements
        //TODO: add extends
        sb.append(JavaTokens.BRACE_LEFT);
        sb.append(JavaTokens.NEWLINE);

        Set<JavaField> fields = javaClass.getJavaFields();
        sb.append(fields.stream().map(this::printField).collect(Collectors.joining(JavaTokens.NEWLINE)));

        if (!fields.isEmpty()) {
            sb.append(JavaTokens.NEWLINE);
        }

        Set<JavaConstructor> constructors = javaClass.getConstructors();
        sb.append(constructors.stream().map(this::printConstructor).collect(Collectors.joining(JavaTokens.NEWLINE)));

        if (!fields.isEmpty()) {
            sb.append(JavaTokens.NEWLINE);
        }

        Set<JavaMethod> methods = javaClass.getMethods();
        sb.append(methods.stream().map(this::printMethod).collect(Collectors.joining(JavaTokens.NEWLINE + JavaTokens.NEWLINE)));

        sb.append(JavaTokens.NEWLINE);
        sb.append(JavaTokens.BRACE_RIGHT);

        return sb.toString();
    }

    /**
     * Get Java constructor.
     * @param constructor constructor
     * @return
     */
    String printConstructor(@NotNull final JavaConstructor constructor) {
        StringBuilder sb = new StringBuilder();

        //UML
        //  +Triangle ()
        //  +Triangle (v1 : Point, v2 : Point)
        //  +plot()
        //  +move(x : int, y : int)

        String annotationList = constructor.getAnnotations().stream().map(this::printAnnotation).collect(Collectors.joining(JavaTokens.NEWLINE));
        sb.append(annotationList);

        String modifiersList = constructor.getModifiers().stream().map(Object::toString).collect(Collectors.joining(JavaTokens.SPACE));
        sb.append(modifiersList);

        sb.append(constructor.getName());
        sb.append(JavaTokens.BRACKET_LEFT);

        Set<JavaArgument> arguments = constructor.getJavaArguments();
        String argumentList = arguments.stream().map(this::printArgument).collect(Collectors.joining(JavaTokens.DELIMITER));
        sb.append(argumentList);

        sb.append(JavaTokens.BRACKET_RIGHT);
        sb.append(JavaTokens.SPACE);

        Set<String> javaThrows = constructor.getJavaThrows();
        if (!javaThrows.isEmpty()) {
            sb.append(JavaKeywords.THROWS);
            String exceptions = javaThrows.stream().map(Object::toString).collect(Collectors.joining(JavaTokens.DELIMITER));
            sb.append(exceptions);
            sb.append(JavaTokens.SPACE);
        }

        sb.append(JavaTokens.BRACE_LEFT);

        for (JavaArgument argument : arguments) {
            sb.append(JavaClassPrintFactory.printFieldAssignment(argument.getName(), true));
        }

        sb.append(JavaTokens.BRACE_RIGHT);

        return sb.toString();
    }

    /**
     * Get Java field.
     * @param field field
     * @return
     */
    String printField(@NotNull final JavaField field) {
        StringBuilder sb = new StringBuilder();

        Set<JavaAnnotation> annotations = field.getAnnotations();
        for (JavaAnnotation annotation : annotations) {
            sb.append(printAnnotation(annotation));
            sb.append(JavaTokens.NEWLINE);
        }

        List<JavaFieldModifier> modifiers = field.getOrderedModifiers();
        for (JavaFieldModifier modifier : modifiers) {
            sb.append(modifier.toString());
        }

        sb.append(field.getSimpleName());
        sb.append(JavaTokens.SPACE);
        sb.append(field.getName());

        if (StringUtil.isNotBlankOrNull(field.getInitializationValue())) {
            sb.append(JavaTokens.EQUALS_WITH_SPACES);
            sb.append(field.getInitializationValue());
        }

        sb.append(JavaTokens.SEMICOLON);

        return sb.toString();
    }

    /**
     * Get Java import statement.
     * @param value package/class to import
     * @return import statement
     */
    String printImport(@NotNull final JavaImport value) {
        if (value.getType().isEmpty()) {
            return "";
        }

        return JavaKeywords.IMPORT + value.getCanonicalName() + JavaTokens.SEMICOLON;
    }

    /**
     * Get Java method.
     * @param method method
     * @return
     */
    String printMethod(@NotNull final JavaMethod method) {
        StringBuilder sb = new StringBuilder();

        String annotationList = method.getAnnotations().stream().map(this::printAnnotation).collect(Collectors.joining(JavaTokens.NEWLINE));
        sb.append(annotationList);

        if (StringUtil.isNotBlankOrNull(annotationList)) {
            sb.append(JavaTokens.NEWLINE);
        }

        String modifiers = method.getOrderedModifiers().stream().map(Object::toString).collect(Collectors.joining(JavaTokens.SPACE));
        sb.append(modifiers);

        JavaReturnType returnType = method.getJavaReturnType();

        sb.append(this.printReturn(returnType));

        JavaMethodNamePrefix jmnp = method.getNamePrefix();
        if (jmnp != null) {
            sb.append(method.getNamePrefix());
        }
        sb.append(method.getName());
        sb.append(JavaTokens.BRACKET_LEFT);

        Set<JavaArgument> arguments = method.getArguments();
        String argumentList = arguments.stream().map(this::printArgument).collect(Collectors.joining(JavaTokens.DELIMITER));
        sb.append(argumentList);

        sb.append(JavaTokens.BRACKET_RIGHT);
        sb.append(JavaTokens.SPACE);

        if (!method.getThrows().isEmpty()) {
            sb.append(JavaKeywords.THROWS);
            String throwsList = method.getThrows().stream().map(Object::toString).collect(Collectors.joining(JavaTokens.DELIMITER));
            sb.append(throwsList);
            sb.append(JavaTokens.SPACE);
        }

        sb.append(JavaTokens.BRACE_LEFT);

        //add method body
        sb.append(method.getBody());

        sb.append(JavaTokens.BRACE_RIGHT);

        return sb.toString();
    }

    /**
     * Get Java package statement.
     * @param value package space
     * @return package statement
     */
    String printPackage(@NotNull final JavaPackage value) {
        if (value.getName().isEmpty()) {
            //default package
            return "";
        }

        return JavaKeywords.PACKAGE + value.getName() + JavaTokens.SEMICOLON;
    }

    /**
     * Get Java return type.
     * @param returnType return type
     * @return
     */
    String printReturn(final JavaReturnType returnType) {
        if (returnType != null) {
            return returnType.getSimpleName() + JavaTokens.SPACE;

        } else {
            return JavaKeywords.VOID;
        }
    }

    public static String printFieldAssignment(@NotNull final String name, boolean includeThis) {
        StringBuilder sb = new StringBuilder();

        if (includeThis) {
            sb.append(JavaKeywords.THIS_DOT);
        }

        sb.append(name);
        sb.append(JavaTokens.EQUALS_WITH_SPACES);
        sb.append(name);
        sb.append(JavaTokens.SEMICOLON);

        return sb.toString();
    }

    public static String printFieldReturnValue(@NotNull final String name, boolean includeThis) {
        StringBuilder sb = new StringBuilder(JavaKeywords.RETURN);

        if (includeThis) {
            sb.append(JavaKeywords.THIS_DOT);
        }

        sb.append(name);
        sb.append(JavaTokens.SEMICOLON);

        return sb.toString();
    }

}
