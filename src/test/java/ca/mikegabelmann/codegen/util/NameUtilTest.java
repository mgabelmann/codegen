package ca.mikegabelmann.codegen.util;

import ca.mikegabelmann.codegen.java.JavaNamingType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 
 * @author mgabelmann
 */
public class NameUtilTest {

	
	
	@Test
	public void test1_getClassName() {
		this.testJavaName(null, null, JavaNamingType.NO_CHANGE);
		this.testJavaName("", "", JavaNamingType.NO_CHANGE);
		this.testJavaName("G", "G", JavaNamingType.NO_CHANGE);
		this.testJavaName("g", "g", JavaNamingType.NO_CHANGE);
		this.testJavaName("good_vs_evil", "good_vs_evil", JavaNamingType.NO_CHANGE);
		this.testJavaName("GOOD_VS_EVIL", "GOOD_VS_EVIL", JavaNamingType.NO_CHANGE);
		this.testJavaName("GoodVsEvil", "GoodVsEvil", JavaNamingType.NO_CHANGE);
		this.testJavaName("gOODvSeVIL", "gOODvSeVIL", JavaNamingType.NO_CHANGE);
		this.testJavaName("GOODVSEVIL", "GOODVSEVIL", JavaNamingType.NO_CHANGE);
		this.testJavaName("goodvsevil", "goodvsevil", JavaNamingType.NO_CHANGE);
	}
	
	@Test
	public void test2_getClassName() {
		this.testJavaName(null, null, JavaNamingType.UNDERSCORE);
		this.testJavaName("", "", JavaNamingType.UNDERSCORE);
		this.testJavaName("G", "g", JavaNamingType.UNDERSCORE);
		this.testJavaName("g", "g", JavaNamingType.UNDERSCORE);
		this.testJavaName("good_vs_evil", "good_vs_evil", JavaNamingType.UNDERSCORE);
		this.testJavaName("GOOD_VS_EVIL", "good_vs_evil", JavaNamingType.UNDERSCORE);
		this.testJavaName("GoodVsEvil", "goodvsevil", JavaNamingType.UNDERSCORE);
		this.testJavaName("gOODvSeVIL", "goodvsevil", JavaNamingType.UNDERSCORE);
		this.testJavaName("GOODVSEVIL", "goodvsevil", JavaNamingType.UNDERSCORE);
		this.testJavaName("goodvsevil", "goodvsevil", JavaNamingType.UNDERSCORE);
	}

	@Test
	public void test3_getClassName() {
		this.testJavaName(null, null, JavaNamingType.LOWER_CAMEL_CASE);
		this.testJavaName("", "", JavaNamingType.LOWER_CAMEL_CASE);
		this.testJavaName("G", "g", JavaNamingType.LOWER_CAMEL_CASE);
		this.testJavaName("g", "g", JavaNamingType.LOWER_CAMEL_CASE);
		this.testJavaName("good_vs_evil", "goodVsEvil", JavaNamingType.LOWER_CAMEL_CASE);
		this.testJavaName("GOOD_VS_EVIL", "goodVsEvil", JavaNamingType.LOWER_CAMEL_CASE);
		this.testJavaName("GoodVsEvil", "goodvsevil", JavaNamingType.LOWER_CAMEL_CASE);
		this.testJavaName("gOODvSeVIL", "goodvsevil", JavaNamingType.LOWER_CAMEL_CASE);
		this.testJavaName("GOODVSEVIL", "goodvsevil", JavaNamingType.LOWER_CAMEL_CASE);
		this.testJavaName("goodvsevil", "goodvsevil", JavaNamingType.LOWER_CAMEL_CASE);
	}

	@Test
	public void test4_getClassName() {
		this.testJavaName(null, null, JavaNamingType.UPPER_CAMEL_CASE);
		this.testJavaName("", "", JavaNamingType.UPPER_CAMEL_CASE);
		this.testJavaName("G", "G", JavaNamingType.UPPER_CAMEL_CASE);
		this.testJavaName("g", "G", JavaNamingType.UPPER_CAMEL_CASE);
		this.testJavaName("good_vs_evil", "GoodVsEvil", JavaNamingType.UPPER_CAMEL_CASE);
		this.testJavaName("GOOD_VS_EVIL", "GoodVsEvil", JavaNamingType.UPPER_CAMEL_CASE);
		this.testJavaName("GoodVsEvil", "Goodvsevil", JavaNamingType.UPPER_CAMEL_CASE);
		this.testJavaName("gOODvSeVIL", "Goodvsevil", JavaNamingType.UPPER_CAMEL_CASE);
		this.testJavaName("GOODVSEVIL", "Goodvsevil", JavaNamingType.UPPER_CAMEL_CASE);
		this.testJavaName("goodvsevil", "Goodvsevil", JavaNamingType.UPPER_CAMEL_CASE);
	}

	@Test
	public void test3_addKeyAndValue() {
		SortedMap<String, List<Object>> values = new TreeMap<>();
		NameUtil.addKeyAndValue(values, "key", new Object());
		Assertions.assertEquals(1, values.size());
	}
	
	@Test
	public void test1_quoteString() {
		Assertions.assertNull(NameUtil.quoteString(null));
		Assertions.assertEquals("\"\"", NameUtil.quoteString(""));
		Assertions.assertEquals("\"a\"", NameUtil.quoteString("a"));
	}


	//HELPER METHODS


	private void testJavaName(String given, String expected, JavaNamingType type) {
		Assertions.assertEquals(expected, NameUtil.getJavaName(type, given));
	}

}
