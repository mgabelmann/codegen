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
		this.testClassName(null, null, JavaNamingType.NOCHANGE);
		this.testClassName("", "", JavaNamingType.NOCHANGE);
		this.testClassName("G", "G", JavaNamingType.NOCHANGE);
		this.testClassName("g", "g", JavaNamingType.NOCHANGE);
		this.testClassName("good_vs_evil", "good_vs_evil", JavaNamingType.NOCHANGE);
		this.testClassName("GOOD_VS_EVIL", "GOOD_VS_EVIL", JavaNamingType.NOCHANGE);
		this.testClassName("GoodVsEvil", "GoodVsEvil", JavaNamingType.NOCHANGE);
		this.testClassName("gOODvSeVIL", "gOODvSeVIL", JavaNamingType.NOCHANGE);
		this.testClassName("GOODVSEVIL", "GOODVSEVIL", JavaNamingType.NOCHANGE);
		this.testClassName("goodvsevil", "goodvsevil", JavaNamingType.NOCHANGE);
	}
	
	@Test
	public void test2_getClassName() {
		this.testClassName(null, null, JavaNamingType.CAMELCASE);
		this.testClassName("", "", JavaNamingType.CAMELCASE);
		this.testClassName("G", "G", JavaNamingType.CAMELCASE);
		this.testClassName("g", "G", JavaNamingType.CAMELCASE);
		this.testClassName("good_vs_evil", "GoodVsEvil", JavaNamingType.CAMELCASE);
		this.testClassName("GOOD_VS_EVIL", "GoodVsEvil", JavaNamingType.CAMELCASE);
		this.testClassName("GoodVsEvil", "Goodvsevil", JavaNamingType.CAMELCASE);
		this.testClassName("gOODvSeVIL", "Goodvsevil", JavaNamingType.CAMELCASE);
		this.testClassName("GOODVSEVIL", "Goodvsevil", JavaNamingType.CAMELCASE);
		this.testClassName("goodvsevil", "Goodvsevil", JavaNamingType.CAMELCASE);
	}
	
	@Test
	public void test3_getClassName() {
		this.testClassName(null, null, JavaNamingType.UNDERSCORE);
		this.testClassName("", "", JavaNamingType.UNDERSCORE);
		this.testClassName("G", "g", JavaNamingType.UNDERSCORE);
		this.testClassName("g", "g", JavaNamingType.UNDERSCORE);
		this.testClassName("good_vs_evil", "good_vs_evil", JavaNamingType.UNDERSCORE);
		this.testClassName("GOOD_VS_EVIL", "good_vs_evil", JavaNamingType.UNDERSCORE);
		this.testClassName("GoodVsEvil", "goodvsevil", JavaNamingType.UNDERSCORE);
		this.testClassName("gOODvSeVIL", "goodvsevil", JavaNamingType.UNDERSCORE);
		this.testClassName("GOODVSEVIL", "goodvsevil", JavaNamingType.UNDERSCORE);
		this.testClassName("goodvsevil", "goodvsevil", JavaNamingType.UNDERSCORE);
	}
	
	
	@Test
	public void test1_getFieldName() {
		this.testFieldName(null, null, JavaNamingType.NOCHANGE);
		this.testFieldName("", "", JavaNamingType.NOCHANGE);
		this.testFieldName("G", "G", JavaNamingType.NOCHANGE);
		this.testFieldName("g", "g", JavaNamingType.NOCHANGE);
		this.testFieldName("good_vs_evil", "good_vs_evil", JavaNamingType.NOCHANGE);
		this.testFieldName("GOOD_VS_EVIL", "GOOD_VS_EVIL", JavaNamingType.NOCHANGE);
		this.testFieldName("GoodVsEvil", "GoodVsEvil", JavaNamingType.NOCHANGE);
		this.testFieldName("gOODvSeVIL", "gOODvSeVIL", JavaNamingType.NOCHANGE);
		this.testFieldName("GOODVSEVIL", "GOODVSEVIL", JavaNamingType.NOCHANGE);
		this.testFieldName("goodvsevil", "goodvsevil", JavaNamingType.NOCHANGE);
	}
	
	@Test
	public void test2_getFieldName() {
		this.testFieldName(null, null, JavaNamingType.CAMELCASE);
		this.testFieldName("", "", JavaNamingType.CAMELCASE);
		this.testFieldName("G", "g", JavaNamingType.CAMELCASE);
		this.testFieldName("g", "g", JavaNamingType.CAMELCASE);
		this.testFieldName("good_vs_evil", "goodVsEvil", JavaNamingType.CAMELCASE);
		this.testFieldName("GOOD_VS_EVIL", "goodVsEvil", JavaNamingType.CAMELCASE);
		this.testFieldName("GoodVsEvil", "goodvsevil", JavaNamingType.CAMELCASE);
		this.testFieldName("gOODvSeVIL", "goodvsevil", JavaNamingType.CAMELCASE);
		this.testFieldName("GOODVSEVIL", "goodvsevil", JavaNamingType.CAMELCASE);
		this.testFieldName("goodvsevil", "goodvsevil", JavaNamingType.CAMELCASE);
		
		this.testFieldName("GOOD_V_Evil", "goodVEvil", JavaNamingType.CAMELCASE);
	}
	
	@Test
	public void test3_getFieldName() {
		this.testFieldName(null, null, JavaNamingType.UNDERSCORE);
		this.testFieldName("", "", JavaNamingType.UNDERSCORE);
		this.testFieldName("G", "g", JavaNamingType.UNDERSCORE);
		this.testFieldName("g", "g", JavaNamingType.UNDERSCORE);
		this.testFieldName("good_vs_evil", "good_vs_evil", JavaNamingType.UNDERSCORE);
		this.testFieldName("GOOD_VS_EVIL", "good_vs_evil", JavaNamingType.UNDERSCORE);
		this.testFieldName("GoodVsEvil", "goodvsevil", JavaNamingType.UNDERSCORE);
		this.testFieldName("gOODvSeVIL", "goodvsevil", JavaNamingType.UNDERSCORE);
		this.testFieldName("GOODVSEVIL", "goodvsevil", JavaNamingType.UNDERSCORE);
		this.testFieldName("goodvsevil", "goodvsevil", JavaNamingType.UNDERSCORE);
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

	/*
	@Test(expected=IllegalArgumentException.class)
	public void test1_addKeyAndValue() {
		StringUtil.addKeyAndValue(null, "", new Object());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test2_addKeyAndValue() {
		StringUtil.addKeyAndValue(new TreeMap<String, List<Object>>(), null, new Object());
	}
	 */


	
	
	
	//-------------------------------------------------------------------------
	//Helper methods
	private void testClassName(String given, String expected, JavaNamingType type) {
		Assertions.assertEquals(expected, NameUtil.getClassName(type, given));
	}
	
	private void testFieldName(String given, String expected, JavaNamingType type) {
		Assertions.assertEquals(expected, NameUtil.getFieldName(type, given));
	}
}
