package ca.mikegabelmann.codegen.util;

import ca.mikegabelmann.codegen.java.lang.classbody.JavaArgument;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaMethodNamePrefix;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaMethod;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaReturnType;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaConstructorModifier;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaMethodModifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;


/**
 * 
 * @author mgabelmann
 */
public class ObjectUtilTest {
	
	@Test
	@DisplayName("get import declaration")
	public void test1_getImport() {
		Assertions.assertEquals("import ca.mgabelmann.ObjectUtilTest;", ObjectUtil.getImport("ca.mgabelmann.ObjectUtilTest"));
	}
	
	@Test
	@DisplayName("get package declaration")
	public void test1_getPackage() {
		Assertions.assertEquals("package ca.mgabelmann;", ObjectUtil.getPackage("ca.mgabelmann"));
	}

	@Test
	@DisplayName("getter - without throws")
	void test1_getMethod() {
		String expected = "public String getName() {return this.name;}";

		String result = ObjectUtil.getMethod(
				new JavaMethodModifier[] { JavaMethodModifier.PUBLIC },
				new JavaReturnType(String.class.getSimpleName(), "name"),
				JavaMethodNamePrefix.GET,
				"Name",
				new String[0]);

		Assertions.assertEquals(expected, result);
	}

	@Test
	@DisplayName("getter - with throws")
	void test2_getMethod() {
		String expected = "public String getName() throws Exception {return this.name;}";

		String result = ObjectUtil.getMethod(
				new JavaMethodModifier[] { JavaMethodModifier.PUBLIC },
				new JavaReturnType(String.class.getSimpleName(), "name"),
				JavaMethodNamePrefix.GET,
				"Name",
				new String[] { Exception.class.getSimpleName() });

		Assertions.assertEquals(expected, result);
	}

	@Test
	@DisplayName("getter - with multiple throws")
	void test3_getMethod() {
		String expected = "public String getName() throws IOException, IllegalArgumentException {return this.name;}";

		String result = ObjectUtil.getMethod(
				new JavaMethodModifier[] { JavaMethodModifier.PUBLIC },
				new JavaReturnType(String.class.getSimpleName(), "name"),
				JavaMethodNamePrefix.GET,
				"Name",
				new String[] { IOException.class.getSimpleName(), IllegalArgumentException.class.getSimpleName() });

		Assertions.assertEquals(expected, result);
	}

	@Test
	@DisplayName("getter - simple")
	void test4_getMethod() {
		String expected = "public String getName() {return this.name;}";

		String result = ObjectUtil.getMethod("Name",
				new JavaReturnType(String.class.getSimpleName(), "name"));

		Assertions.assertEquals(expected, result);
	}

	@Test
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
	}

	@Test
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
	}

}
