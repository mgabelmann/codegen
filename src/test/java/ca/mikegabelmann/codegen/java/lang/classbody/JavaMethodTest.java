package ca.mikegabelmann.codegen.java.lang.classbody;


import ca.mikegabelmann.codegen.java.lang.JavaMethodNamePrefix;
import ca.mikegabelmann.codegen.java.lang.JavaPrimitive;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaMethodModifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.ArrayList;
import java.util.List;

class JavaMethodTest {

    @DisplayName("constructor - name")
    @Test
    void test1_constructor() {
        JavaMethod jm = new JavaMethod("name");
        Assertions.assertEquals("", jm.getType());
        Assertions.assertEquals("name", jm.getName());
        Assertions.assertEquals(0, jm.getModifiers().size());
        Assertions.assertEquals(0, jm.getArguments().size());
        Assertions.assertEquals(0, jm.getThrows().size());
        Assertions.assertEquals("", jm.getBody().toString());
        Assertions.assertEquals(JavaMethodNamePrefix.NONE, jm.getNamePrefix());
        Assertions.assertNotNull(jm.getJavaReturnType());
    }

    @DisplayName("returnType")
    @Test
    void test1_returnType() {
        JavaMethod jm = new JavaMethod("name");
        JavaReturnType jrt = new JavaReturnType(JavaPrimitive.INT);
        jm.setJavaReturnType(jrt);

        Assertions.assertEquals(jrt, jm.getJavaReturnType());
    }

    @DisplayName("namePrefix")
    @ParameterizedTest
    @EnumSource
    void test1_namePrefix(JavaMethodNamePrefix prefix) {
        JavaMethod jm = new JavaMethod("name");

        jm.setNamePrefix(prefix);
        Assertions.assertEquals(prefix, jm.getNamePrefix());
    }

    @DisplayName("arguments")
    @Test
    void test1_arguments() {
        JavaMethod jm = new JavaMethod("name");
        JavaArgument ja1 = new JavaArgument("type", "name", true);
        jm.addArgument(ja1);

        Assertions.assertEquals(1, jm.getArguments().size());
        Assertions.assertTrue(jm.getArguments().contains(ja1));
        Assertions.assertTrue(jm.removeArgument(ja1));
        Assertions.assertEquals(0, jm.getArguments().size());
    }

    @DisplayName("throws")
    @Test
    void test1_throws() {
        JavaMethod jm = new JavaMethod("name");
        String th = "java.lang.Exception";
        jm.addThrows(th);
        Assertions.assertEquals(1, jm.getThrows().size());
        Assertions.assertTrue(jm.removeThrows(th));
        Assertions.assertEquals(0, jm.getThrows().size());
    }

    @DisplayName("body")
    @Test
    void test1_body() {
        JavaMethod jm = new JavaMethod("name");
        jm.getBody().append("return 1;");
        Assertions.assertEquals("return 1;", jm.getBody().toString());
    }

    @DisplayName("modifiers")
    @ParameterizedTest
    @EnumSource
    void test1_modifiers(JavaMethodModifier modifier) {
        JavaMethod jm = new JavaMethod("name");
        jm.addModifier(modifier);
        Assertions.assertEquals(1, jm.getModifiers().size());
        Assertions.assertTrue(jm.getModifiers().contains(modifier));
        Assertions.assertTrue(jm.removeModifier(modifier));
        Assertions.assertEquals(0, jm.getModifiers().size());
    }

    @DisplayName("modifiers - addModifiers")
    @Test
    void test2_modifiers() {
        JavaMethod jm = new JavaMethod("name");
        jm.addModifiers(JavaMethodModifier.values());
        Assertions.assertEquals(JavaMethodModifier.values().length, jm.getModifiers().size());
    }

    @DisplayName("modifiers - ordered")
    @Test
    @Disabled("not complete")
    void test3_modifiers() {
        JavaMethod jm = new JavaMethod("name");

        List<JavaMethodModifier> modifiers = new ArrayList<>();
        for (int i = JavaMethodModifier.values().length - 1; i >= 0; i--) {
            modifiers.add(JavaMethodModifier.values()[i]);
        }

        jm.addModifiers(modifiers.toArray(new JavaMethodModifier[0]));

        Assertions.assertEquals(JavaMethodModifier.values().length, jm.getModifiers().size());

        List<JavaMethodModifier> modifiers2 = jm.getOrderedModifiers();

        //TODO

    }

    @DisplayName("toString - base constructor")
    @Test
    void test1_toString() {
        JavaMethod jm = new JavaMethod("name");

        Assertions.assertEquals("JavaMethod{name='name', body='', annotations=[], modifiers=[], javaReturnType=JavaReturnType{type='void'}, namePrefix=, javaArguments=[], javaThrows=[]}", jm.toString());
    }

    @DisplayName("getter")
    @Test
    void test1_getter() {
        JavaMethod jm = JavaMethod.getGetter("name", String.class.getCanonicalName(), JavaMethodModifier.PUBLIC);

        Assertions.assertEquals("JavaMethod{name='Name', body='return this.name;', annotations=[], modifiers=[public ], javaReturnType=JavaReturnType{type='java.lang.String'}, namePrefix=get, javaArguments=[], javaThrows=[]}", jm.toString());
    }

    @DisplayName("setter")
    @Test
    void test1_setter() {
        JavaMethod jm = JavaMethod.getSetter("name", String.class.getCanonicalName(), JavaMethodModifier.PUBLIC);
        Assertions.assertEquals("JavaMethod{name='Name', body='this.name = name;', annotations=[], modifiers=[public ], javaReturnType=JavaReturnType{type='void'}, namePrefix=set, javaArguments=[JavaArgument{type='java.lang.String', name='name', annotations=[], required=true}], javaThrows=[]}", jm.toString());
    }

}