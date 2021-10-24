package ca.mikegabelmann.codegen.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import ca.mikegabelmann.codegen.lang.JavaTokens;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author mgabelmann
 */
public final class AnnotationUtil {
	/** Logger. */
	private static final Logger logger = LogManager.getLogger(AnnotationUtil.class);
	
	/** Do not instantiate this class. */
	private AnnotationUtil() {

	}

	
	/**
	 * Get Java annotation.
	 * <pre>
	 * 
	 * </pre>
	 * @param name annotation name
	 * @param values annotation key/value pairs
	 * @return annotation
	 */
	public static String getAnnotation(final String name, final SortedMap<String, List<Object>> values) {
		StringBuilder sb = new StringBuilder();
		sb.append(JavaTokens.ANNOTATION);
		sb.append(name);
		
		if (values != null && ! values.isEmpty()) {
			sb.append(JavaTokens.BRACKET_LEFT);
			sb.append(AnnotationUtil.getString(values));
			sb.append(JavaTokens.BRACKET_RIGHT);
		}
		
		return sb.toString();
	}
	
	/**
	 * Get key/value pairs for use in annotations.
	 * <pre>
	 * a="a"
	 * a="a", b="b"
	 * a="a", b={"b", "c", "d"}
	 * a="a", b={@B, @C, @D}
	 * </pre>
	 * @param values key/value pairs
	 * @return string
	 */
	public static String getString(final SortedMap<String, List<Object>> values) {
		StringBuilder sb = new StringBuilder();
		
		Iterator<Map.Entry<String, List<Object>>> it = values.entrySet().iterator();
		
		while (it.hasNext()) {
			Map.Entry<String, List<Object>> rec = it.next();
			String key = rec.getKey();
			
			if (StringUtil.isNotBlankOrNull(key)) {
				sb.append(key);
				sb.append(JavaTokens.EQUALS_WITH_SPACES);
			}
			sb.append(AnnotationUtil.getString(rec.getValue()));
			
			if (it.hasNext()) {
				sb.append(JavaTokens.DELIMITER);
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * Get delimited string.
	 * <pre>
	 * "a"
	 * {"a", "b"}
	 * @A
	 * {@A, @A}
	 * </pre>
	 * @param values values to delimit
	 * @return delimited string
	 */
	public static String getString(final List<Object> values) {
		StringBuilder sb = new StringBuilder();
		int size = values.size();
		
		if (size > 1) {
			sb.append(JavaTokens.BRACE_LEFT);
		}
		
		Iterator<Object> it = values.iterator();
		while (it.hasNext()) {
			sb.append(escapeValue(it.next()));
			
			if (it.hasNext()) {
				sb.append(JavaTokens.DELIMITER);
			}
		}
		
		if (size > 1) {
			sb.append(JavaTokens.BRACE_RIGHT);
		}
		
		return sb.toString();
	}
	
	/**
	 * Escape given value based on type given.
	 * @param o value to escape
	 * @return escaped value
	 */
	public static String escapeValue(final Object o) {
		String value;
		
		if (o instanceof Boolean) {
			Boolean b = (Boolean) o;
			value = "" + Boolean.TRUE.equals(b);
			
		} else if (o instanceof Integer) {
			Integer i = (Integer) o;
			value = "" + i.intValue();
			
		} else if (o instanceof String) {
			String s = (String) o;
			
			if (StringUtil.isNotBlankOrNull(s)) {
				value = JavaTokens.QUOTE + s + JavaTokens.QUOTE;
			} else {
				//throw new IllegalArgumentException("value cannot be blank or null");
				value = "";
			}
			
		} else if (o instanceof String[]) {
			List<String> recs = Arrays.asList((String[]) o);
			value = NameUtil.getDelimitedString(recs, true, true);
		
		} else if (o instanceof List<?>) {
			//change lists to correct type (downcast)
			value = getString(new ArrayList<Object>((List<?>) o));
			
		} else if (o != null && o.getClass().isEnum()) {
			//handle simple enums (may not work for all)
			value = o.getClass().getSimpleName() + "." + o;
			
		} else if (o instanceof Class) {
			value = ((Class<?>) o).getCanonicalName() + ".class";
			
		} else if (o instanceof Object) {
			value = o.toString();	
			
		} else {
			value = "";	
			logger.warn("invalid escape value=" + o + ", using \"\"");
		}
		
		return value;
	}
	
}
