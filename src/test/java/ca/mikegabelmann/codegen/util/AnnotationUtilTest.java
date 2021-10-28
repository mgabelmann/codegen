package ca.mikegabelmann.codegen.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;


public class AnnotationUtilTest {
	private List<Object> list;
	private SortedMap<String, List<Object>> map;
	
	@BeforeEach
	public void setup() {
		list = new ArrayList<>();
		map = new TreeMap<>();
	}
	
	/**
	 * <pre>
	 * @Annotation
	 * </pre>
	 */
	@Test
	public void getStringList1() {
		Assertions.assertEquals("", AnnotationUtil.getString(list));
	}
	
	/**
	 * <pre>
	 * @Annotation("a")
	 * </pre>
	 */
	@Test
	public void getStringList2() {
		list.add("a");
		
		Assertions.assertEquals("\"a\"", AnnotationUtil.getString(list));
	}

	/**
	 * <pre>
	 * @Annotation({"a", "b"})
	 * </pre>
	 */
	@Test
	public void getStringList3() {
		list.add("a");
		list.add("b");
		
		Assertions.assertEquals("{\"a\", \"b\"}", AnnotationUtil.getString(list));
	}
	
	/**
	 * <pre>
	 * @Annotation(@A)
	 * </pre>
	 */
	@Test
	public void getStringList4() {
		list.add(Integer.valueOf(1));
		
		Assertions.assertEquals("1", AnnotationUtil.getString(list));
	}
	
	/**
	 * <pre>
	 * @Annotation({@A, @A})
	 * </pre>
	 */
	@Test
	public void getStringList5() {
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		
		Assertions.assertEquals("{1, 2}", AnnotationUtil.getString(list));
	}
	
	/**
	 * <pre>
	 * @Annotation
	 * </pre>
	 */
	@Test
	public void getStringSortedMap1() {
		Assertions.assertEquals("", AnnotationUtil.getString(map));
	}
	
	/**
	 * <pre>
	 * @Annotation(a="a")
	 * </pre>
	 */
	@Test
	public void getStringSortedMap2() {
		map.put("a", Arrays.asList(new Object[] {"a"}));
		
		Assertions.assertEquals("a = \"a\"", AnnotationUtil.getString(map));
	}
	
	/**
	 * <pre>
	 * @Annotation(a={"a","b","c"})
	 * </pre>
	 */
	@Test
	public void getStringSortedMap3() {
		map.put("a", Arrays.asList(new Object[] {"a", "b", "c"}));
		
		Assertions.assertEquals("a = {\"a\", \"b\", \"c\"}", AnnotationUtil.getString(map));
	}
	
	/**
	 * <pre>
	 * @Annotation(a="a", b="b")
	 * </pre>
	 */
	@Test
	public void getStringSortedMap4() {
		map.put("a", Arrays.asList(new Object[] {"a"}));
		map.put("b", Arrays.asList(new Object[] {"b"}));
		
		Assertions.assertEquals("a = \"a\", b = \"b\"", AnnotationUtil.getString(map));
	}
	
	/**
	 * <pre>6
	 * @Annotation(a="a", b={"b", "c", "d"})
	 * </pre>
	 */
	@Test
	public void getStringSortedMap5() {
		map.put("a", Arrays.asList(new Object[] {"a"}));
		map.put("b", Arrays.asList(new Object[] {"b", "c", "d"}));
		
		Assertions.assertEquals("a = \"a\", b = {\"b\", \"c\", \"d\"}", AnnotationUtil.getString(map));
		
	}
	
	@Test
	public void getStringSortedMap6() {
		map.put("a", Arrays.asList(new Object[] {new SimpleAnnotation()}));
		
		String expected = "a = " + SimpleAnnotation.EXPECTED;
		Assertions.assertEquals(expected, AnnotationUtil.getString(map));
	}
	
	@Test
	public void getStringSortedMap7() {
		map.put("a", Arrays.asList(new Object[] {new SimpleAnnotation(), new SimpleAnnotation()}));
		
		String expected = "a = {" + SimpleAnnotation.EXPECTED + ", " + SimpleAnnotation.EXPECTED + "}";
		Assertions.assertEquals(expected, AnnotationUtil.getString(map));
	}

	@Test
	public void escapeValue() {
		Assertions.assertEquals("", AnnotationUtil.escapeValue(null));

		Assertions.assertEquals("true", AnnotationUtil.escapeValue(Boolean.TRUE));
		Assertions.assertEquals("1", AnnotationUtil.escapeValue(Integer.valueOf(1)));
		Assertions.assertEquals("\"a\"", AnnotationUtil.escapeValue("a"));
		Assertions.assertEquals("", AnnotationUtil.escapeValue(""));

		Assertions.assertEquals("", AnnotationUtil.escapeValue(list));

		list.add("a");
		Assertions.assertEquals("\"a\"", AnnotationUtil.escapeValue(list));

		list.add("b");
		Assertions.assertEquals("{\"a\", \"b\"}", AnnotationUtil.escapeValue(list));

		Assertions.assertEquals("", AnnotationUtil.escapeValue((String[]) null));
		Assertions.assertEquals("{\"a\"}", AnnotationUtil.escapeValue(new String[]{"a"}));
		Assertions.assertEquals("{\"a\", \"b\"}", AnnotationUtil.escapeValue(new String[]{"a", "b"}));

		//Assertions.assertEquals("CascadeType.PERSIST", AnnotationUtil.escapeValue(CascadeType.PERSIST));
		//Assertions.assertEquals("FetchType.EAGER", AnnotationUtil.escapeValue(FetchType.EAGER));
	}

	@Test
	void test1_getAnnotation() {
		map.put("a", Arrays.asList(new Object[] {"a"}));

		Assertions.assertEquals("@Test(a = \"a\")", AnnotationUtil.getAnnotation("Test", map));
	}

	@Test
	void test2_getAnnotation() {
		map.put("a", Arrays.asList(new Object[] {"a", "b"}));
		map.put("c", Arrays.asList(new Object[] {true}));

		Assertions.assertEquals("@Test(a = {\"a\", \"b\"}, c = true)", AnnotationUtil.getAnnotation("Test", map));
	}

}
