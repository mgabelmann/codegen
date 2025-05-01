package ca.mikegabelmann.codegen.java.lang.classbody;

import ca.mikegabelmann.codegen.java.lang.JavaPrimitive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

/**
 *
 * @author mgabe
 */
class JavaArgumentTest {

    @DisplayName("constructor - String, not required")
    @Test
    void test1_constructor1() {
        JavaArgument argument = new JavaArgument("type", "name", false);

        Assertions.assertEquals("type", argument.getCanonicalName());
        Assertions.assertEquals("name", argument.getName());
        Assertions.assertFalse(argument.isRequired());
    }

    @DisplayName("constructor - String, required")
    @Test
    void test2_constructor1() {
        JavaArgument argument = new JavaArgument("type", "name", true);

        Assertions.assertEquals("type", argument.getCanonicalName());
        Assertions.assertEquals("name", argument.getName());
        Assertions.assertTrue(argument.isRequired());
    }

    @DisplayName("constructor - JavaPrimitive")
    @ParameterizedTest
    @EnumSource
    void test3_constructor(final JavaPrimitive type) {
        JavaArgument o = new JavaArgument(type, "name", true);
        Assertions.assertEquals("JavaArgument{type='" + type.getType().trim() + "', name='name', annotations=[], required=true}", o.toString());
    }

    @DisplayName("constructor - Class")
    @Test
    void test4_constructor() {
        JavaArgument o = new JavaArgument(StringBuilder.class, "name", true);
        Assertions.assertEquals("JavaArgument{type='java.lang.StringBuilder', name='name', annotations=[], required=true}", o.toString());
    }


    @Test
    void test1_getAnnotations() {
        JavaArgument argument = new JavaArgument("type", "name", true);
        argument.addAnnotation(new JavaAnnotation("A"));

        Assertions.assertEquals(1, argument.getAnnotations().size());
    }

    @Test
    void test1_removeAnnotation() {
        JavaAnnotation ann = new JavaAnnotation("A");

        JavaArgument argument = new JavaArgument("type", "name", true);
        argument.addAnnotation(ann);

        Assertions.assertTrue(argument.removeAnnotation(ann));
        Assertions.assertEquals(0, argument.getAnnotations().size());
    }

}