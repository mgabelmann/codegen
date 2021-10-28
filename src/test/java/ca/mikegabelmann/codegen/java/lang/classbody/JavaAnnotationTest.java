package ca.mikegabelmann.codegen.java.lang.classbody;


import ca.mikegabelmann.codegen.java.lang.classbody.JavaAnnotation;
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

        Assertions.assertEquals("@Id", ann.toString());
    }

    @Test
    void test1_add() {
        JavaAnnotation ann = new JavaAnnotation("Id");
        ann.add("a", Boolean.TRUE);

        Assertions.assertEquals("@Id(a = true)", ann.toString());
    }

    @Test
    void test2_add() {
        JavaAnnotation ann = new JavaAnnotation("Id");
        ann.add("a", "A", "B");

        Assertions.assertEquals("@Id(a = {\"A\", \"B\"})", ann.toString());
    }

    @Test
    void test3_add() {
        JavaAnnotation ann = new JavaAnnotation("Id");
        ann.add("a", "A");
        ann.add("a", "B");

        Assertions.assertEquals("@Id(a = {\"A\", \"B\"})", ann.toString());
    }

    @Test
    void test1_remove() {
        JavaAnnotation ann = new JavaAnnotation("Id");
        ann.add("a", "A", "B");
        ann.add("b", Boolean.FALSE);
        ann.remove("a");

        Assertions.assertEquals("@Id(b = false)", ann.toString());
    }

}