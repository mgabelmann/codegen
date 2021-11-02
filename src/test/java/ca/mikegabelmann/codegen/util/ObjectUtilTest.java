package ca.mikegabelmann.codegen.util;

import ca.mikegabelmann.codegen.java.lang.classbody.JavaArgument;
import ca.mikegabelmann.codegen.java.lang.JavaMethodNamePrefix;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaReturnType;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaConstructorModifier;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaMethodModifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * 
 * @author mgabelmann
 */
public class ObjectUtilTest {


	/*@Test
	void test1_getAnnotation() {
		SortedMap<String, List<Object>> map = new TreeMap<>();
		map.put("a", Arrays.asList(new Object[] {"a"}));

		Assertions.assertEquals("@Test(a = \"a\")", ObjectUtil.getAnnotation("Test", map));
	}

	@Test
	void test2_getAnnotation() {
		SortedMap<String, List<Object>> map = new TreeMap<>();
		map.put("a", Arrays.asList(new Object[] {"a", "b"}));
		map.put("c", Arrays.asList(new Object[] {true}));

		Assertions.assertEquals("@Test(a = {\"a\", \"b\"}, c = true)", ObjectUtil.getAnnotation("Test", map));
	}*/

	/*@Test
	@DisplayName("getter - without throws")
	void test1_getMethod() {
		String expected = "public String getName() {return this.name;}";

		String result = ObjectUtil.getMethod(
				new JavaMethodModifier[] { JavaMethodModifier.PUBLIC },
				new JavaReturnType(String.class.getSimpleName()),
				JavaMethodNamePrefix.GET,
				"Name",
				new String[0]);

		Assertions.assertEquals(expected, result);
	}*/

	/*@Test
	@DisplayName("getter - with throws")
	void test2_getMethod() {
		String expected = "public String getName() throws Exception {return this.name;}";

		String result = ObjectUtil.getMethod(
				new JavaMethodModifier[] { JavaMethodModifier.PUBLIC },
				new JavaReturnType(String.class.getSimpleName()),
				JavaMethodNamePrefix.GET,
				"Name",
				new String[] { Exception.class.getSimpleName() });

		Assertions.assertEquals(expected, result);
	}*/

	/*@Test
	@DisplayName("getter - with multiple throws")
	void test3_getMethod() {
		String expected = "public String getName() throws IOException, IllegalArgumentException {return this.name;}";

		String result = ObjectUtil.getMethod(
				new JavaMethodModifier[] { JavaMethodModifier.PUBLIC },
				new JavaReturnType(String.class.getSimpleName()),
				JavaMethodNamePrefix.GET,
				"Name",
				new String[] { IOException.class.getSimpleName(), IllegalArgumentException.class.getSimpleName() });

		Assertions.assertEquals(expected, result);
	}*/

	/*@Test
	@DisplayName("getter - simple")
	void test4_getMethod() {
		String expected = "public String getName() {return this.name;}";

		String result = ObjectUtil.getMethod("Name",
				new JavaReturnType(String.class.getSimpleName()));

		Assertions.assertEquals(expected, result);
	}*/

	/*@Test
	@DisplayName("setter - without throws")
	void test1_setMethod() {
		String expected = "public void setName(final String name) {this.name = name;}";

		String result = ObjectUtil.setMethod(
			new JavaMethodModifier[] { JavaMethodModifier.PUBLIC },
			JavaMethodNamePrefix.SET,
			"Name",
			new JavaArgument[] { new JavaArgument(String.class.getSimpleName(), "name")},
			new String[0]);

		Assertions.assertEquals(expected, result);
	}*/

/*	@Test
	@DisplayName("setter - with throws")
	void test2_setMethod() {
		String expected = "public void setName(final String name) throws IOException {this.name = name;}";

		String result = ObjectUtil.setMethod(
				new JavaMethodModifier[] { JavaMethodModifier.PUBLIC },
				JavaMethodNamePrefix.SET,
				"Name",
				new JavaArgument[] { new JavaArgument(String.class.getSimpleName(), "name")},
				new String[] { IOException.class.getSimpleName() });

		Assertions.assertEquals(expected, result);
	}

	@Test
	@DisplayName("setter - simple")
	void test3_setMethod() {
		String expected = "public void setName(final String name) {this.name = name;}";

		String result = ObjectUtil.setMethod(
			"Name",
			new JavaArgument[] { new JavaArgument(String.class.getSimpleName(), "name")});

		Assertions.assertEquals(expected, result);
	}

	@Test
	@DisplayName("constructor - without throws")
	void test1_constructorMethod() {
		String expected = "public Test(final String name) {this.name = name;}";

		String result = ObjectUtil.constructorMethod(
			new JavaConstructorModifier[] { JavaConstructorModifier.PUBLIC },
			"Test",
			new JavaArgument[] { new JavaArgument(String.class.getSimpleName(), "name")},
			new String[0]);

		Assertions.assertEquals(expected, result);
	}

	@Test
	@DisplayName("constructor - with throws")
	void test2_constructorMethod() {
		String expected = "public Test(final String name) throws IOException {this.name = name;}";

		String result = ObjectUtil.constructorMethod(
				new JavaConstructorModifier[] { JavaConstructorModifier.PUBLIC },
				"Test",
				new JavaArgument[] { new JavaArgument(String.class.getSimpleName(), "name")},
				new String[] { IOException.class.getSimpleName() });

		Assertions.assertEquals(expected, result);
	}

	@Test
	@DisplayName("constructor - no arguments")
	void test3_constructorNoArgsMethod() {
		String expected = "public Test() {}";

		String result = ObjectUtil.constructorNoArgsMethod(
				new JavaConstructorModifier[] { JavaConstructorModifier.PUBLIC },
				"Test",
				new String[0]);

		Assertions.assertEquals(expected, result);
	}*/

}
