package ca.mikegabelmann.codegen.util;

import ca.mikegabelmann.codegen.java.lang.JavaKeywords;
import ca.mikegabelmann.codegen.java.lang.JavaTokens;
import ca.mikegabelmann.codegen.java.lang.classbody.*;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaFieldModifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.stream.Collectors;

/**
 * Used to format various objects for printing to stream, file, etc.
 * @author mgabe
 */
public class PrintJavaUtil {
    /** Logger. */
    private static final Logger logger = LogManager.getLogger(PrintJavaUtil.class);

    /** Do not instantiate this class */
    private PrintJavaUtil() {}


    /**
     * Get Java annotation.
     * @param annotation
     * @return
     */
    public static String getAnnotation(@NotNull final JavaAnnotation annotation) {
        StringBuilder sb = new StringBuilder();
        sb.append(JavaTokens.ANNOTATION);
        sb.append(annotation.getType());

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
    public static String getArgument(@NotNull final JavaArgument argument) {
        StringBuilder sb = new StringBuilder();

        for (JavaAnnotation annotation : argument.getAnnotations()) {
            sb.append(PrintJavaUtil.getAnnotation(annotation));
            sb.append(JavaTokens.SPACE);
        }

        if (argument.isFinal()) {
            sb.append(JavaKeywords.FINAL);
        }

        sb.append(argument.getType());
        sb.append(JavaTokens.SPACE);
        sb.append(argument.getName());

        return sb.toString();
    }

    /**
     * Get Java constructor.
     * @param constructor constructor
     * @return
     */
    public static String getConstructor(@NotNull final JavaConstructor constructor) {
        StringBuilder sb = new StringBuilder();

        //UML
        //  +Triangle ()
        //  +Triangle (v1 : Point, v2 : Point)
        //  +plot()
        //  +move(x : int, y : int)

        String annotationList = constructor.getAnnotations().stream().map(PrintJavaUtil::getAnnotation).collect(Collectors.joining());
        sb.append(annotationList);

        String modifiersList = constructor.getModifiers().stream().map(Object::toString).collect(Collectors.joining(JavaTokens.SPACE));
        sb.append(modifiersList);

        sb.append(constructor.getType());
        sb.append(JavaTokens.BRACKET_LEFT);

        Set<JavaArgument> arguments = constructor.getArguments();
        String argumentList = arguments.stream().map(PrintJavaUtil::getArgument).collect(Collectors.joining(JavaTokens.DELIMITER));
        sb.append(argumentList);

        sb.append(JavaTokens.BRACKET_RIGHT);
        sb.append(JavaTokens.SPACE);

        Set<String> javaThrows = constructor.getThrows();
        if (!javaThrows.isEmpty()) {
            sb.append(JavaKeywords.THROWS);
            String exceptions = javaThrows.stream().map(Object::toString).collect(Collectors.joining(JavaTokens.DELIMITER));
            sb.append(exceptions);
            sb.append(JavaTokens.SPACE);
        }

        sb.append(JavaTokens.BRACE_LEFT);

        for (JavaArgument argument : arguments) {
            sb.append(JavaKeywords.THIS_DOT);
            sb.append(argument.getName());
            sb.append(JavaTokens.EQUALS_WITH_SPACES);
            sb.append(argument.getName());
            sb.append(JavaTokens.SEMICOLON);
        }

        sb.append(JavaTokens.BRACE_RIGHT);

        return sb.toString();
    }

    /**
     * Get Java field.
     * @param field field
     * @return
     */
    public static String getField(@NotNull final JavaField field) {
        StringBuilder sb = new StringBuilder();

        Set<JavaAnnotation> annotations = field.getAnnotations();
        for (JavaAnnotation annotation : annotations) {
            sb.append(PrintJavaUtil.getAnnotation(annotation));
            sb.append(JavaTokens.NEWLINE);
        }

        List<JavaFieldModifier> modifiers = field.getOrderedModifiers();
        for (JavaFieldModifier modifier : modifiers) {
            sb.append(modifier.toString());
        }

        sb.append(field.getType());
        sb.append(JavaTokens.SPACE);
        sb.append(field.getName());

        sb.append(JavaTokens.SEMICOLON);

        return sb.toString();
    }

    /**
     * Get Java method.
     * @param method method
     * @return
     */
    public static String getMethod(@NotNull final JavaMethod method) {
        StringBuilder sb = new StringBuilder();

        String annotationList = method.getAnnotations().stream().map(PrintJavaUtil::getAnnotation).collect(Collectors.joining());
        sb.append(annotationList);

        String modifiers = method.getModifiers().stream().map(Object::toString).collect(Collectors.joining(JavaTokens.SPACE));
        sb.append(modifiers);

        JavaReturnType returnType = method.getJavaReturnType();

        sb.append(PrintJavaUtil.getReturn(returnType));
        sb.append(JavaTokens.SPACE);

        sb.append(method.getNamePrefix());
        sb.append(method.getName());
        sb.append(JavaTokens.BRACKET_LEFT);

        Set<JavaArgument> arguments = method.getArguments();
        String argumentList = arguments.stream().map(PrintJavaUtil::getArgument).collect(Collectors.joining(JavaTokens.DELIMITER));
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

        for (JavaArgument argument : arguments) {
            sb.append(JavaKeywords.THIS_DOT);
            sb.append(argument.getName());
            sb.append(JavaTokens.EQUALS_WITH_SPACES);
            sb.append(argument.getName());
            sb.append(JavaTokens.SEMICOLON);
        }

        if (returnType != null) {
            sb.append(JavaKeywords.RETURN);
            sb.append(JavaKeywords.THIS_DOT);
            sb.append(returnType.getName());
            sb.append(JavaTokens.SEMICOLON);
        }

        sb.append(JavaTokens.BRACE_RIGHT);

        return sb.toString();
    }

    /**
     * Get Java return type.
     * @param returnType return type
     * @return
     */
    public static String getReturn(final JavaReturnType returnType) {
        if (returnType != null) {
            return returnType.getType();

        } else {
            return JavaKeywords.VOID;
        }
    }

    /**
     * Get Java import statement.
     * @param value package/class to import
     * @return import statement
     */
    public static String getImport(@NotNull final JavaImport value) {
        return JavaKeywords.IMPORT + value.getType() + JavaTokens.SEMICOLON;
    }

    /**
     * Get Java package statement.
     * @param value package space
     * @return package statement
     */
    public static String getPackage(@NotNull final JavaPackage value) {
        return JavaKeywords.PACKAGE + value.getType() + JavaTokens.SEMICOLON;
    }

}
