package ca.mikegabelmann.codegen.java.lang.classbody;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JavaReturnTypeTest {

    @Test
    void test1_constructor() {
        JavaReturnType o = new JavaReturnType("type", "name");

        Assertions.assertEquals("type", o.getType());
        Assertions.assertEquals("name", o.getName());
    }

    @Test
    void test1_toString() {
        JavaReturnType o = new JavaReturnType("type", "name");

        Assertions.assertEquals("type", o.toString());
    }
}