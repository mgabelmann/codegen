package ca.mikegabelmann.codegen.java.lang.classbody;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.SortedMap;

/**
 *
 * @author mgabe
 */
class JavaAnnotationTest {

    @DisplayName("constructor - String")
    @Test
    void test1_constructor() {
        JavaAnnotation ann = new JavaAnnotation("Id");

        Assertions.assertEquals("Id", ann.getCanonicalName());
        Assertions.assertEquals("Id", ann.getSimpleName());
    }

    @DisplayName("constructor - Class")
    @Test
    void test2_constructor() {
        JavaAnnotation ann = new JavaAnnotation(StringBuffer.class);

        Assertions.assertEquals("java.lang.StringBuffer", ann.getCanonicalName());
        Assertions.assertEquals("StringBuffer", ann.getSimpleName());
    }

    @Test
    void test1_add() {
        JavaAnnotation ann = new JavaAnnotation("Id");
        ann.add("a", Boolean.TRUE);

        Assertions.assertEquals(1, ann.getProperties().size());
        Assertions.assertEquals("a", ann.getProperties().firstKey());
    }

    @DisplayName("add - duplicate key")
    @Test
    void test2_add() {
        JavaAnnotation ann = new JavaAnnotation("Id");
        ann.add("a", "A", "C");
        ann.add("a", "A", "B");

        Assertions.assertEquals(1, ann.getProperties().size());
        SortedMap<String, List<Object>> x = ann.getProperties();

        Assertions.assertEquals("a", x.firstKey());
        Assertions.assertEquals(4, x.get("a").size());
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

    @DisplayName("toString")
    @Test
    void test1_toString() {
        JavaAnnotation ann = new JavaAnnotation("Id");
        ann.add("a", "A", "B");
        ann.add("b", Boolean.FALSE);
        Assertions.assertEquals("JavaAnnotation{type='Id', properties={a=[A, B], b=[false]}}", ann.toString());
    }

}