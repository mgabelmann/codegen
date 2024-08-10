package ca.mikegabelmann.codegen.java.lang.classbody;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author mgabe
 */
class JavaAnnotationTest {

    @Test
    void test1_constructor() {
        JavaAnnotation ann = new JavaAnnotation("Id");

        Assertions.assertEquals("Id", ann.getCanonicalName());
        Assertions.assertEquals("Id", ann.getSimpleName());
    }

    @Test
    void test1_add() {
        JavaAnnotation ann = new JavaAnnotation("Id");
        ann.add("a", Boolean.TRUE);

        Assertions.assertEquals(1, ann.getProperties().size());
        Assertions.assertEquals("a", ann.getProperties().firstKey());
    }

    @Test
    void test1_remove() {
        JavaAnnotation ann = new JavaAnnotation("Id");
        ann.add("a", "A", "B");
        ann.add("b", Boolean.FALSE);
        ann.remove("a");

        Assertions.assertEquals(1, ann.getProperties().size());
        Assertions.assertEquals("b", ann.getProperties().firstKey());
    }

}