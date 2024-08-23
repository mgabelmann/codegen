package ca.mikegabelmann.codegen.java.lang.classbody;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JavaReturnTypeTest {

    @Test
    void test1_constructor() {
        JavaReturnType o = new JavaReturnType("Type");

        Assertions.assertEquals("JavaReturnType{type='Type'}", o.toString());
    }

}