package ca.mikegabelmann.codegen.java.lang.classbody;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author mgabe
 */
class JavaArgumentTest {

    @Test
    void test1_constructor1() {
        JavaArgument argument = new JavaArgument("type", "name", false);

        Assertions.assertEquals("type", argument.getType());
        Assertions.assertEquals("name", argument.getName());
        Assertions.assertFalse(argument.isFinal());
    }

    @Test
    void test2_constructor1() {
        JavaArgument argument = new JavaArgument("type", "name");

        Assertions.assertEquals("type", argument.getType());
        Assertions.assertEquals("name", argument.getName());
        Assertions.assertTrue(argument.isFinal());
    }

    @Test
    void test1_getAnnotations() {
        JavaArgument argument = new JavaArgument("type", "name");
        argument.addAnnotation(new JavaAnnotation("A"));

        Assertions.assertEquals(1, argument.getAnnotations().size());
    }

    @Test
    void test1_removeAnnotation() {
        JavaAnnotation ann = new JavaAnnotation("A");

        JavaArgument argument = new JavaArgument("type", "name");
        argument.addAnnotation(ann);

        Assertions.assertTrue(argument.removeAnnotation(ann));
        Assertions.assertEquals(0, argument.getAnnotations().size());
    }

    @Test
    void test1_toString() {
        JavaArgument argument = new JavaArgument("type", "name");

        Assertions.assertEquals("final type name", argument.toString());
    }

    @Test
    void test2_toString() {
        JavaArgument argument = new JavaArgument("type", "name", false);

        Assertions.assertEquals("type name", argument.toString());
    }

    @Test
    void test3_toString() {
        JavaArgument argument = new JavaArgument("type", "name");
        argument.addAnnotation(new JavaAnnotation("A"));

        Assertions.assertEquals("@A final type name", argument.toString());
    }

}