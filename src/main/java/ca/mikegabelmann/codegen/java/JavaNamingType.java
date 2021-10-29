package ca.mikegabelmann.codegen.java;

/**
 * Java Naming schemes.
 * @author mgabelmann
 */
public enum JavaNamingType {
	NO_CHANGE,
	LOWER_CAMEL_CASE, //used for variables
	UPPER_CAMEL_CASE, //used for classes, methods, etc
	UNDERSCORE //also referred to snakecase
}
