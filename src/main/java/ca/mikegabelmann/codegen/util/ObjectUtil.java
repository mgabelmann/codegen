package ca.mikegabelmann.codegen.util;

import ca.mikegabelmann.codegen.java.lang.JavaKeywords;
import ca.mikegabelmann.codegen.java.lang.JavaTokens;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaArgument;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaMethodNamePrefix;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaMethod;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaReturnType;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaConstructorModifier;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaMethodModifier;
import org.intellij.lang.annotations.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.Version;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 
 * @author mgabelmann
 */
public final class ObjectUtil {


	/** Do not instantiate this class. */
	private ObjectUtil() {}

	/**
	 * Get 'getter' method.
	 * @param methodName method name
	 * @param returnType return type
	 * @return getter
	 */
	public static String getMethod(
		@NotNull String methodName,
		@NotNull JavaReturnType returnType) {

		return ObjectUtil.getMethod(
			new JavaMethodModifier[] { JavaMethodModifier.PUBLIC },
			returnType,
			JavaMethodNamePrefix.GET,
			methodName,
			new String[0]);
	}

	/**
	 * Get 'getter' method.
	 * @param methodModifiers method modifiers
	 * @param returnType return type
	 * @param javaMethodNamePrefix method name prefix
	 * @param methodName method name
	 * @param methodExceptions exceptions
	 * @return getter
	 */
	public static String getMethod(
		@NotNull JavaMethodModifier[] methodModifiers,
		@NotNull JavaReturnType returnType,
		@NotNull JavaMethodNamePrefix javaMethodNamePrefix,
		@NotNull String methodName,
		@NotNull String[] methodExceptions) {
		
		StringBuilder sb = new StringBuilder();

		String modifiers = Arrays.stream(methodModifiers).map(Object::toString).collect(Collectors.joining(JavaTokens.SPACE));
		sb.append(modifiers);

		sb.append(returnType.getType());
		sb.append(JavaTokens.SPACE);

		sb.append(javaMethodNamePrefix);
		sb.append(methodName);
		sb.append(JavaTokens.BRACKET_LEFT);

		sb.append(JavaTokens.BRACKET_RIGHT);
		sb.append(JavaTokens.SPACE);

		if (methodExceptions.length > 0) {
			sb.append(JavaKeywords.THROWS);
			String argumentList = Arrays.stream(methodExceptions).map(Object::toString).collect(Collectors.joining(JavaTokens.DELIMITER));
			sb.append(argumentList);
			sb.append(JavaTokens.SPACE);
		}

		sb.append(JavaTokens.BRACE_LEFT);

		sb.append(JavaKeywords.RETURN);
		sb.append(JavaKeywords.THIS_DOT);
		sb.append(returnType.getName());
		sb.append(JavaTokens.SEMICOLON);

		sb.append(JavaTokens.BRACE_RIGHT);

		return sb.toString();
	}

	/**
	 * Get 'setter' method.
	 * @param methodName method name
	 * @param arguments method arguments
	 * @return setter
	 */
	public static String setMethod(
		@NotNull String methodName,
		@NotNull JavaArgument[] arguments) {

		return ObjectUtil.setMethod(
			new JavaMethodModifier[] { JavaMethodModifier.PUBLIC },
			JavaMethodNamePrefix.SET,
			methodName,
			arguments,
			new String[0]);
	}

	/**
	 * Get 'setter' method.
	 * @param methodModifiers method modifiers
	 * @param javaMethodNamePrefix method name prefix
	 * @param methodName method name
	 * @param arguments method arguments
	 * @param methodExceptions exceptions
	 * @return setter
	 */
	public static String setMethod(
		@NotNull JavaMethodModifier[] methodModifiers,
		@NotNull JavaMethodNamePrefix javaMethodNamePrefix,
		@NotNull String methodName,
		@NotNull JavaArgument[] arguments,
		@NotNull String[] methodExceptions) {

		StringBuilder sb = new StringBuilder();

		String modifiers = Arrays.stream(methodModifiers).map(Object::toString).collect(Collectors.joining(JavaTokens.SPACE));
		sb.append(modifiers);

		sb.append(JavaKeywords.VOID);

		sb.append(javaMethodNamePrefix);
		sb.append(methodName);
		sb.append(JavaTokens.BRACKET_LEFT);

		String argumentList = Arrays.stream(arguments).map(Object::toString).collect(Collectors.joining(JavaTokens.DELIMITER));
		sb.append(argumentList);

		sb.append(JavaTokens.BRACKET_RIGHT);
		sb.append(JavaTokens.SPACE);

		if (methodExceptions.length > 0) {
			sb.append(JavaKeywords.THROWS);
			String exceptions = Arrays.stream(methodExceptions).map(Object::toString).collect(Collectors.joining(JavaTokens.DELIMITER));
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
	 * Get constructor.
	 * @param constructorModifiers constructor modifiers
	 * @param constructorName constructor name
	 * @param constructorExceptions exceptions
	 * @return constructor
	 */
	public static String constructorNoArgsMethod(
		@NotNull JavaConstructorModifier[] constructorModifiers,
		@NotNull String constructorName,
		@NotNull String[] constructorExceptions) {

		return ObjectUtil.constructorMethod(
			constructorModifiers,
			constructorName,
			new JavaArgument[0],
			constructorExceptions);
	}

	/**
	 * Get constructor.
	 * @param constructorModifiers constructor modifiers
	 * @param constructorName constructor name
	 * @param arguments constructor arguments
	 * @param constructorExceptions exceptions
	 * @return constructor
	 */
	public static String constructorMethod(
		@NotNull JavaConstructorModifier[] constructorModifiers,
		@NotNull String constructorName,
		@NotNull JavaArgument[] arguments,
		@NotNull String[] constructorExceptions) {

		StringBuilder sb = new StringBuilder();

		String modifiers = Arrays.stream(constructorModifiers).map(Object::toString).collect(Collectors.joining(JavaTokens.SPACE));
		sb.append(modifiers);

		sb.append(constructorName);
		sb.append(JavaTokens.BRACKET_LEFT);

		String argumentList = Arrays.stream(arguments).map(Object::toString).collect(Collectors.joining(JavaTokens.DELIMITER));
		sb.append(argumentList);

		sb.append(JavaTokens.BRACKET_RIGHT);
		sb.append(JavaTokens.SPACE);


		if (constructorExceptions.length > 0) {
			sb.append(JavaKeywords.THROWS);
			String exceptions = Arrays.stream(constructorExceptions).map(Object::toString).collect(Collectors.joining(JavaTokens.DELIMITER));
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
	 * Get Java import statement.
	 * @param value package/class to import
	 * @return import statement
	 */
	public static String getImport(final String value) {
		StringBuilder sb = new StringBuilder();
		sb.append(JavaKeywords.IMPORT);
		sb.append(value);
		sb.append(JavaTokens.SEMICOLON);
		
		return sb.toString();
	}
	
	/**
	 * Get Java package statement.
	 * @param value package space
	 * @return package statement
	 */
	public static String getPackage(final String value) {
		StringBuilder sb = new StringBuilder();
		sb.append(JavaKeywords.PACKAGE);
		sb.append(value);
		sb.append(JavaTokens.SEMICOLON);
		
		return sb.toString();
	}
	
}
