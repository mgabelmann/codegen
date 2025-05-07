package ca.mikegabelmann.codegen.java.lang.classbody;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JavaImportTest {

    @DisplayName("constructor - String")
    @Test
    void test1_constructor() {
        JavaImport o = new JavaImport("java.util.List");
        Assertions.assertEquals("java.util.List", o.getType());
    }

    @DisplayName("constructor - Class")
    @Test
    void test2_constructor() {
        JavaImport o = new JavaImport(java.util.List.class);
        Assertions.assertEquals("java.util.List", o.getType());
    }

    @Test
    void test1_toString() {
        JavaImport o = new JavaImport("java.util.List");
        Assertions.assertEquals("JavaImport{importName='java.util.List'}", o.toString());
    }

}