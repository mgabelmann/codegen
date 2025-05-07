package ca.mikegabelmann.codegen.java.lang.classbody;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JavaPackageTest {

    @DisplayName("constructor - String")
    @Test
    void test1_constructor() {
        JavaPackage o = new JavaPackage("a.b.c.d");
        Assertions.assertEquals("a.b.c.d", o.getName());
    }

    @DisplayName("toString")
    @Test
    void test1_toString() {
        JavaPackage o = new JavaPackage("a.b.c.d");
        Assertions.assertEquals("JavaPackage{packageName='a.b.c.d'}", o.toString());
    }

}
