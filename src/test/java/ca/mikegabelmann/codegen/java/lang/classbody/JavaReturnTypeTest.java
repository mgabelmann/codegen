package ca.mikegabelmann.codegen.java.lang.classbody;

import ca.mikegabelmann.codegen.java.lang.JavaPrimitive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class JavaReturnTypeTest {

    @DisplayName("constructor - String")
    @Test
    void test1_constructor() {
        JavaReturnType o = new JavaReturnType("Type");

        Assertions.assertEquals("JavaReturnType{type='Type'}", o.toString());
    }

    @DisplayName("constructor - JavaPrimitive")
    @ParameterizedTest
    @EnumSource
    void test3_constructor(final JavaPrimitive type) {
        JavaReturnType o = new JavaReturnType(type);
        Assertions.assertEquals("JavaReturnType{type='" + type.getType().trim() + "'}", o.toString());
    }

    @DisplayName("constructor - no args")
    @Test
    void test3_constructor() {
        JavaReturnType o = new JavaReturnType();

        Assertions.assertEquals("JavaReturnType{type='void'}", o.toString());
    }

    @DisplayName("constructor - Class")
    @Test
    void test4_constructor() {
        JavaReturnType o = new JavaReturnType(StringBuilder.class);

        Assertions.assertEquals("JavaReturnType{type='java.lang.StringBuilder'}", o.toString());
    }

}