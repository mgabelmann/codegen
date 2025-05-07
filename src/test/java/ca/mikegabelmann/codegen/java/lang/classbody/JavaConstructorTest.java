package ca.mikegabelmann.codegen.java.lang.classbody;


import ca.mikegabelmann.codegen.java.lang.JavaPrimitive;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaConstructorModifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

class JavaConstructorTest {

    @DisplayName("constructor - name")
    @Test
    void test1_constructor() {
        JavaConstructor jc = new JavaConstructor("name");
        Assertions.assertEquals("name",  jc.getName());
        Assertions.assertEquals("", jc.getType());
        Assertions.assertTrue(jc.getModifiers().contains(JavaConstructorModifier.PACKAGE));
    }

    @DisplayName("constructor - name, modifier")
    @Test
    void test2_constructor() {
        JavaConstructor jc = new JavaConstructor("name", JavaConstructorModifier.PUBLIC);
        Assertions.assertEquals("name",  jc.getName());
        Assertions.assertEquals("", jc.getType());
        Assertions.assertTrue(jc.getModifiers().contains(JavaConstructorModifier.PUBLIC));
    }

    @DisplayName("test JavaArgument")
    @Test
    void test1_javaArguments() {
        JavaConstructor jc = new JavaConstructor("name");
        JavaArgument ja1 = new JavaArgument("type", "name", true);
        jc.addArgument(ja1);

        //can't add duplicates
        jc.addArgument(ja1);

        Assertions.assertEquals(1, jc.getArguments().size());
        Assertions.assertTrue(jc.getArguments().contains(ja1));

        jc.removeArgument(ja1);
        Assertions.assertEquals(0, jc.getArguments().size());
    }

    @DisplayName("constructor modifiers")
    @Test
    void test1_constructorModifiers() {
        JavaConstructor jc = new JavaConstructor("name");
        jc.addModifier(JavaConstructorModifier.PUBLIC);
        Assertions.assertTrue(jc.getModifiers().contains(JavaConstructorModifier.PUBLIC));
    }

    @DisplayName("addModifiers - only 1")
    @ParameterizedTest
    @EnumSource
    void test1_addModifiers(final JavaConstructorModifier modifier) {
        JavaConstructor jc = new JavaConstructor("name");
        jc.addModifiers(modifier);
        Assertions.assertEquals(1, jc.getModifiers().size());
        Assertions.assertTrue(jc.getModifiers().contains(modifier));
    }

    @DisplayName("addModifiers - can only add 1")
    @Test
    void test2_addModifiers() {
        JavaConstructor jc = new JavaConstructor("name");
        Assertions.assertThrows(IllegalArgumentException.class, () -> jc.addModifiers(JavaConstructorModifier.PUBLIC, JavaConstructorModifier.PROTECTED));
    }

    @DisplayName("removeModifier - defaults to package")
    @ParameterizedTest
    @EnumSource
    void test1_removeModifier(final JavaConstructorModifier modifier) {
        JavaConstructor jc = new JavaConstructor("name");
        Assertions.assertTrue(jc.removeModifier(modifier));
        Assertions.assertTrue(jc.getModifiers().contains(JavaConstructorModifier.PACKAGE));
    }

    @DisplayName("getOrderedModifiers - only 1")
    @Test
    void test1_getOrderedModifiers() {
        JavaConstructor jc = new JavaConstructor("name");
        jc.addModifier(JavaConstructorModifier.PRIVATE);
        List<JavaConstructorModifier> jcs = jc.getOrderedModifiers();
        Assertions.assertEquals(1, jcs.size());
    }

    @DisplayName("test throws")
    @Test
    void test1_throws() {
        JavaConstructor jc = new JavaConstructor("name");

        Assertions.assertEquals(0, jc.getThrows().size());

        jc.addThrows("java.lang.Exception");

        Assertions.assertEquals(1, jc.getThrows().size());

        jc.removeThrows("java.lang.NullPointerException");

        Assertions.assertEquals(1, jc.getThrows().size());

        jc.removeThrows("java.lang.Exception");
        Assertions.assertEquals(0, jc.getThrows().size());
    }

    @DisplayName("toString - all parameters")
    @Test
    void test1_toString() {
        JavaConstructor jc = new JavaConstructor("name");
        jc.addModifier(JavaConstructorModifier.PUBLIC);
        jc.addThrows("java.lang.Exception");
        jc.addArgument(new JavaArgument(JavaPrimitive.INT, "age", false));

        Assertions.assertEquals("JavaConstructor{type='', javaArguments=[JavaArgument{type='int', name='age', annotations=[], required=false}], javaThrows=[java.lang.Exception], modifiers=public , annotations=[]}", jc.toString());
    }

}
