package ca.mikegabelmann.codegen.util;

import ca.mikegabelmann.codegen.java.lang.JavaMethodNamePrefix;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaAnnotation;
import org.apache.torque.SqlDataType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author mgabe
 */
public class AnnotationUtilTest {
	private List<Object> list;
	private SortedMap<String, List<Object>> map;
	
	@BeforeEach
	public void setup() {
		list = new ArrayList<>();
		map = new TreeMap<>();
	}

	@Test
	@DisplayName("getStringUsingMap - no values")
	void test1_getString_Map() {
		Assertions.assertEquals("", AnnotationUtil.getString(map));
	}

	@Test
	@DisplayName("getStringUsingMap - no key, single String in list")
	void test2_getString_Map() {
		map.put("", List.of("a"));
		Assertions.assertEquals("\"a\"", AnnotationUtil.getString(map));
	}

	@Test
	@DisplayName("getStringUsingMap - no key, two Strings in list")
	void test3_getString_Map() {
		map.put("", List.of("a", "b"));
		Assertions.assertEquals("{\"a\", \"b\"}", AnnotationUtil.getString(map));
	}

	@Test
	@DisplayName("getStringUsingMap - no key, two Integers in list")
	void test4_getString_Map() {
		map.put("", List.of(1, 2));
		Assertions.assertEquals("{1, 2}", AnnotationUtil.getString(map));
	}

	@Test
	@DisplayName("getStringUsingMap - key, single String in list")
	void test5_getString_Map() {
		map.put("a", List.of("b"));
		Assertions.assertEquals("a = \"b\"", AnnotationUtil.getString(map));
	}

	@Test
	@DisplayName("getStringUsingMap - key, two Strings in list")
	void test6_getString_Map() {
		map.put("a", List.of("b", "c"));
		Assertions.assertEquals("a = {\"b\", \"c\"}", AnnotationUtil.getString(map));
	}

	@Test
	@DisplayName("getStringUsingMap - multiple keys and values")
	void test7_getString_Map() {
		map.put("a", List.of("b", "c"));
		map.put("d", List.of(true, false));
		Assertions.assertEquals("a = {\"b\", \"c\"}, d = {true, false}", AnnotationUtil.getString(map));
	}

	@Test
	@DisplayName("getStringUsingMap - no key, single annotation in list")
	void test8_getString_Map() {
		map.put("", List.of(new JavaAnnotation("A")));
		Assertions.assertEquals("@A", AnnotationUtil.getString(map));
	}

	@Test
	@DisplayName("getStringUsingMap - no key, two annotations in list")
	void test9_getString_Map() {
		map.put("", List.of(new JavaAnnotation("A"), new JavaAnnotation("B")));
		Assertions.assertEquals("{@A, @B}", AnnotationUtil.getString(map));
	}

	@Test
	@DisplayName("getStringUsingMap - key, two annotations in list")
	void test10_getString_Map() {
		map.put("a", List.of(new JavaAnnotation("A"), new JavaAnnotation("B")));
		Assertions.assertEquals("a = {@A, @B}", AnnotationUtil.getString(map));
	}

	@Test
	@DisplayName("escapeValue - boolean")
	void test1_escapeValue() {
		Assertions.assertEquals("true", AnnotationUtil.escapeValue(Boolean.TRUE));
		Assertions.assertEquals("true", AnnotationUtil.escapeValue(true));
	}

	@Test
	@DisplayName("escapeValue - int")
	void test2_escapeValue() {
		Assertions.assertEquals("1", AnnotationUtil.escapeValue(1));
	}

	@Test
	@DisplayName("escapeValue - String")
	void test3_escapeValue() {
		Assertions.assertEquals("\"string\"", AnnotationUtil.escapeValue("string"));
	}

	@Test
	@DisplayName("escapeValue - empty String")
	void test3a_escapeValue() {
		Assertions.assertEquals("", AnnotationUtil.escapeValue(""));
	}

	@Test
	@DisplayName("escapeValue - String[]")
	void test4_escapeValue() {
		Assertions.assertEquals("{\"str1\", \"str2\"}", AnnotationUtil.escapeValue(new String[] {"str1", "str2"}));
	}

	@Test
	@DisplayName("escapeValue - List<String>")
	void test5_escapeValue() {
		Assertions.assertEquals("{\"a\", \"b\"}", AnnotationUtil.escapeValue(List.of("a", "b")));
	}

	@Test
	@DisplayName("escapeValue - Enum")
	void test6_escapeValue() {
		Assertions.assertEquals("SqlDataType.DATE", AnnotationUtil.escapeValue(SqlDataType.DATE));
		Assertions.assertEquals("JavaMethodNamePrefix.GET", AnnotationUtil.escapeValue(JavaMethodNamePrefix.GET));
	}

	@Test
	@DisplayName("escapeValue - Class")
	void test7_escapeValue() {
		Assertions.assertEquals("java.lang.String.class", AnnotationUtil.escapeValue(String.class));
	}

	@Test
	@DisplayName("escapeValue - Class")
	void test8_escapeValue() {
		Assertions.assertEquals("YesNoConverter.class", AnnotationUtil.escapeValue(new StringAsClass("YesNoConverter")));
	}

	@Test
	@DisplayName("escapeValue - Object")
	void test9_escapeValue() {
		Assertions.assertEquals("my name is Tim", AnnotationUtil.escapeValue(new TestObject("Tim")));
	}

	@Test
	@DisplayName("escapeValue - null")
	void test10_escapeValue() {
		Assertions.assertEquals("", AnnotationUtil.escapeValue(null));
	}

	@Test
	@DisplayName("escapeValue - JavaAnnotation")
	void test11_escapeValue() {
		Assertions.assertEquals("@A", AnnotationUtil.escapeValue(new JavaAnnotation("A")));
	}

	@Test
	@DisplayName("escapeValue - List<JavaAnnotation>")
	void test12_escapeValue() {
		Assertions.assertEquals("{@A, @B}", AnnotationUtil.escapeValue(List.of(new JavaAnnotation("A"), new JavaAnnotation("B"))));
	}


	//HELPER OBJECTS


	static class TestObject {
		final String name;

		TestObject(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "my name is " + name;
		}
	}

}
