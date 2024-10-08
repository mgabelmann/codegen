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

        Assertions.assertEquals("type", argument.getCanonicalName());
        Assertions.assertEquals("name", argument.getName());
        Assertions.assertFalse(argument.isRequired());
    }

    @Test
    void test2_constructor1() {
        JavaArgument argument = new JavaArgument("type", "name", true);

        Assertions.assertEquals("type", argument.getCanonicalName());
        Assertions.assertEquals("name", argument.getName());
        Assertions.assertTrue(argument.isRequired());
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