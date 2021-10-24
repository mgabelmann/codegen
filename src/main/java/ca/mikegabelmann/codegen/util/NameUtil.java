package ca.mikegabelmann.codegen.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;

import ca.mikegabelmann.codegen.lang.JavaNamingType;
import ca.mikegabelmann.codegen.lang.JavaTokens;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;


/**
 * 
 * @author mgabelmann
 */
public final class NameUtil {
	/** Logger. */
	private static final Logger logger = LogManager.getLogger(NameUtil.class);
	
	/** No not instantiate this class. */
	private NameUtil() {}


	/**
	 * Get class name.
	 * @param namingMethodType naming scheme
	 * @param value value to proces
	 * @return field
	 */
	public static String getClassName(
		final JavaNamingType namingMethodType,
		final String value) {
		
		if (JavaNamingType.NOCHANGE.equals(namingMethodType) || value == null) {
			return value;
			
		} else if (JavaNamingType.CAMELCASE.equals(namingMethodType)) {
			StringBuilder sb = new StringBuilder();
			String[] tokens = value.split(JavaTokens.UNDERSCORE);
			
			for (String token : tokens) {
				if (token.length() <= 1) {
					sb.append(token.toUpperCase());
					
				} else {
					sb.append(token.substring(0, 1).toUpperCase() + token.substring(1).toLowerCase());
				}
			}
			
			return sb.toString();
			
		} else if (JavaNamingType.UNDERSCORE.equals(namingMethodType)) {
			return value.toLowerCase();
			
		} else {
			throw new IllegalArgumentException("unsupported type " + namingMethodType.name());
		}
	}
	
	/**
	 * Get field name.
	 * @param namingMethodType naming scheme
	 * @param value value to process
	 * @return field
	 */
	public static String getFieldName(
		final JavaNamingType namingMethodType, 
		final String value) { 
		
		if (JavaNamingType.NOCHANGE.equals(namingMethodType) || value == null) {
			return value;
			
		} else if (JavaNamingType.CAMELCASE.equals(namingMethodType)) {
			StringBuilder sb = new StringBuilder();
			String[] tokens = value.split(JavaTokens.UNDERSCORE);
			
			for (int i = 0; i < tokens.length; i++) {
				if (i == 0) {
					sb.append(tokens[i].toLowerCase());
					
				} else if (tokens[i].length() <= 1) {
					sb.append(tokens[i].toUpperCase());
					
				} else {
					sb.append(tokens[i].substring(0, 1).toUpperCase() + tokens[i].substring(1).toLowerCase());
				}
			}
			
			return sb.toString();
			
		} else if (JavaNamingType.UNDERSCORE.equals(namingMethodType)) {
			return value.toLowerCase();
			
		} else {
			throw new IllegalArgumentException("unsupported type " + namingMethodType.name());
		}
	}
	
	/**
	 * Add key/value to collection.
	 * @param values collection
	 * @param key key to add
	 * @param o value to add
	 */
	public static void addKeyAndValue(
		@NotNull final SortedMap<String, List<Object>> values,
		@NotNull final String key,
		final Object o) {
		
		if (o != null) {
			values.put(key, Arrays.asList(o));
			
		} else {
			logger.info("key=" + key + " object is null, skipping addition");
		}
	}
	
	/**
	 * Get quoted string.
	 * <pre>
	 * "A"
	 * </pre>
	 * @param value value to quote
	 * @return quoted string
	 */
	public static String quoteString(final String value) {
		if (value != null) {
			return JavaTokens.QUOTE + value + JavaTokens.QUOTE;
			
		} else {
			return value;
		}
	}
	
	/**
	 * Get delimited string.
	 * <pre>
	 * A,B,C
	 * "A","B","C"
	 * {A,B,C}
	 * {"A","B","C"}
	 * </pre>
	 * @param values values to delimit
	 * @param quoted quote values
	 * @param braces add braces
	 * @return delimited string
	 */
	public static String getDelimitedString(final List<String> values, final boolean quoted, final boolean braces) {
		StringBuilder sb = new StringBuilder();
		if (braces) sb.append(JavaTokens.BRACE_LEFT);
		
		if (values != null) {
			Iterator<String> it = values.iterator();
			
			while (it.hasNext()) {
				if (quoted) sb.append(JavaTokens.QUOTE);
				sb.append(it.next());
				if (quoted) sb.append(JavaTokens.QUOTE);
				
				if (it.hasNext()) sb.append(JavaTokens.DELIMITER);
			}
		}
		
		if (braces) sb.append(JavaTokens.BRACE_RIGHT);
		
		return sb.toString();
	}

}
