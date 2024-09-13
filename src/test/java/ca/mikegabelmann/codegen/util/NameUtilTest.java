package ca.mikegabelmann.codegen.util;

import ca.mikegabelmann.codegen.NamingType;
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
		this.testJavaName(null, null, NamingType.NO_CHANGE);
		this.testJavaName("", "", NamingType.NO_CHANGE);
		this.testJavaName("G", "G", NamingType.NO_CHANGE);
		this.testJavaName("g", "g", NamingType.NO_CHANGE);
		this.testJavaName("good_vs_evil", "good_vs_evil", NamingType.NO_CHANGE);
		this.testJavaName("GOOD_VS_EVIL", "GOOD_VS_EVIL", NamingType.NO_CHANGE);
		this.testJavaName("GoodVsEvil", "GoodVsEvil", NamingType.NO_CHANGE);
		this.testJavaName("gOODvSeVIL", "gOODvSeVIL", NamingType.NO_CHANGE);
		this.testJavaName("GOODVSEVIL", "GOODVSEVIL", NamingType.NO_CHANGE);
		this.testJavaName("goodvsevil", "goodvsevil", NamingType.NO_CHANGE);
	}
	
	@Test
	public void test2_getClassName() {
		this.testJavaName(null, null, NamingType.UNDERSCORE);
		this.testJavaName("", "", NamingType.UNDERSCORE);
		this.testJavaName("G", "g", NamingType.UNDERSCORE);
		this.testJavaName("g", "g", NamingType.UNDERSCORE);
		this.testJavaName("good_vs_evil", "good_vs_evil", NamingType.UNDERSCORE);
		this.testJavaName("GOOD_VS_EVIL", "good_vs_evil", NamingType.UNDERSCORE);
		this.testJavaName("GoodVsEvil", "goodvsevil", NamingType.UNDERSCORE);
		this.testJavaName("gOODvSeVIL", "goodvsevil", NamingType.UNDERSCORE);
		this.testJavaName("GOODVSEVIL", "goodvsevil", NamingType.UNDERSCORE);
		this.testJavaName("goodvsevil", "goodvsevil", NamingType.UNDERSCORE);
	}

	@Test
	public void test3_getClassName() {
		this.testJavaName(null, null, NamingType.LOWER_CAMEL_CASE);
		this.testJavaName("", "", NamingType.LOWER_CAMEL_CASE);
		this.testJavaName("G", "g", NamingType.LOWER_CAMEL_CASE);
		this.testJavaName("g", "g", NamingType.LOWER_CAMEL_CASE);
		this.testJavaName("good_vs_evil", "goodVsEvil", NamingType.LOWER_CAMEL_CASE);
		this.testJavaName("GOOD_VS_EVIL", "goodVsEvil", NamingType.LOWER_CAMEL_CASE);
		this.testJavaName("GoodVsEvil", "goodvsevil", NamingType.LOWER_CAMEL_CASE);
		this.testJavaName("gOODvSeVIL", "goodvsevil", NamingType.LOWER_CAMEL_CASE);
		this.testJavaName("GOODVSEVIL", "goodvsevil", NamingType.LOWER_CAMEL_CASE);
		this.testJavaName("goodvsevil", "goodvsevil", NamingType.LOWER_CAMEL_CASE);
	}

	@Test
	public void test4_getClassName() {
		this.testJavaName(null, null, NamingType.UPPER_CAMEL_CASE);
		this.testJavaName("", "", NamingType.UPPER_CAMEL_CASE);
		this.testJavaName("G", "G", NamingType.UPPER_CAMEL_CASE);
		this.testJavaName("g", "G", NamingType.UPPER_CAMEL_CASE);
		this.testJavaName("good_vs_evil", "GoodVsEvil", NamingType.UPPER_CAMEL_CASE);
		this.testJavaName("GOOD_VS_EVIL", "GoodVsEvil", NamingType.UPPER_CAMEL_CASE);
		this.testJavaName("GoodVsEvil", "Goodvsevil", NamingType.UPPER_CAMEL_CASE);
		this.testJavaName("gOODvSeVIL", "Goodvsevil", NamingType.UPPER_CAMEL_CASE);
		this.testJavaName("GOODVSEVIL", "Goodvsevil", NamingType.UPPER_CAMEL_CASE);
		this.testJavaName("goodvsevil", "Goodvsevil", NamingType.UPPER_CAMEL_CASE);
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


	private void testJavaName(String given, String expected, NamingType type) {
		Assertions.assertEquals(expected, NameUtil.getJavaName(type, given));
	}

}
