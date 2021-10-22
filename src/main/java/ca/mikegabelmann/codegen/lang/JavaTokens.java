package ca.mikegabelmann.codegen.lang;

/**
 * 
 * @author mgabelmann
 */
public final class JavaTokens {
	public static final String NEWLINE = System.getProperty("line.separator");
	
	public static final String QUOTE = "\"";
	public static final String QUOTES = "\"\"";
	
	public static final String DELIMITER = ", ";
	public static final String SPACE = " ";
	
	public static final String SEMICOLON = ";";
	public static final String DOT = ".";
	
	public static final String EQUALS = "=";
	public static final String EQUALS_WITH_SPACES = " = ";
	
	public static final String BRACKET_LEFT = "(";
	public static final String BRACKET_RIGHT = ")";
	public static final String BRACKETS = "()";
	
	public static final String BRACE_LEFT = "{";
	public static final String BRACE_RIGHT = "}";
	
	public static final String ANNOTATION = "@";
	
	public static final String UNDERSCORE = "_";
	
	public static final String DIAMOND_LEFT = "<";
	public static final String DIAMOND_RIGHT = ">";
	public static final String DIAMOND = "<>";
	
	public static final String JAVADOC_OPEN = "/**";
	public static final String JAVADOC_CLOSE = "*/";
	
	public static final String COMMENT_OPEN = "/*";
	public static final String COMMENT_CLOSE = "*/";
	
	public static final String ASTERISK = "*";
	
	public static final String COMMENT = "//";
	
	public static final String VARARGS = "...";
	public static final String VARARGS_PRE = SPACE + VARARGS;
	public static final String VARARGS_POST = VARARGS + SPACE;
	public static final String VARARGS_BOTH = SPACE + VARARGS + SPACE;
	
	
	/** Do not instantiate this class. */
	private JavaTokens() {
		//do not instantiate this class
	}
	
}
