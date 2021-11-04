package ca.mikegabelmann.codegen.util;

import ca.mikegabelmann.codegen.java.lang.classbody.JavaAnnotation;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaArgument;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaConstructor;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaReturnType;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaConstructorModifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author mgabe
 */
class PrintJavaUtilTest {

    @Test
    void test1_getAnnotation() {
        JavaAnnotation a = new JavaAnnotation("A");

        Assertions.assertEquals("@A", PrintJavaUtil.getAnnotation(a));
    }

    @Test
    void test2_getAnnotation() {
        JavaAnnotation a = new JavaAnnotation("A");
        a.add("b", Boolean.FALSE);

        Assertions.assertEquals("@A(b = false)", PrintJavaUtil.getAnnotation(a));
    }

    @Test
    void test3_getAnnotation() {
        JavaAnnotation a = new JavaAnnotation("A");
        a.add("b", Arrays.asList("c", "d"));

        Assertions.assertEquals("@A(b = {\"c\", \"d\"})", PrintJavaUtil.getAnnotation(a));
    }

    @Test
    void test4_getAnnotation() {
        JavaAnnotation b = new JavaAnnotation("B");
        b.add("name1", "value1");
        b.add("name2", "value2");

        JavaAnnotation a = new JavaAnnotation("A");
        a.add("value", b);

        Assertions.assertEquals("@A(value = @B(name1 = \"value1\", name2 = \"value2\"))", PrintJavaUtil.getAnnotation(a));
    }

    @Test
    void test5_getAnnotation() {
        JavaAnnotation b = new JavaAnnotation("B");
        b.add("name1", "value1");

        JavaAnnotation c = new JavaAnnotation("C");
        c.add("name2", "value2");

        JavaAnnotation a = new JavaAnnotation("A");
        a.add("", List.of(b, c));

        Assertions.assertEquals("@A({@B(name1 = \"value1\"), @C(name2 = \"value2\")})", PrintJavaUtil.getAnnotation(a));
    }

    @Test
    void test1_getArgument() {
        JavaArgument a = new JavaArgument("java.lang.String", "str");

        Assertions.assertEquals("final String str", PrintJavaUtil.getArgument(a));
    }

    @Test
    void test2_getArgument() {
        JavaArgument a = new JavaArgument("java.lang.String", "str", false);

        Assertions.assertEquals("String str", PrintJavaUtil.getArgument(a));
    }

    @Test
    void test3_getArgument() {
        JavaArgument a = new JavaArgument("java.lang.String", "str");
        a.addAnnotation(new JavaAnnotation("A"));

        Assertions.assertEquals("@A final String str", PrintJavaUtil.getArgument(a));
    }

    @Test
    void test4_getArgument() {
        JavaArgument a = new JavaArgument("java.lang.String", "str", false);
        a.addAnnotation(new JavaAnnotation("A"));

        Assertions.assertEquals("@A String str", PrintJavaUtil.getArgument(a));
    }

    @Test
    @DisplayName("no arguments constructor")
    void test1_getConstructor() {
        JavaConstructor a = new JavaConstructor("a.b.Person", "person");
        a.addModifier(JavaConstructorModifier.PUBLIC);

        Assertions.assertEquals("public Person() {}", PrintJavaUtil.getConstructor(a));
    }

    @Test
    @DisplayName("constructor with 2 arguments")
    void test2_getConstructor() {
        JavaConstructor a = new JavaConstructor("a.b.Person", "person");
        a.addModifier(JavaConstructorModifier.PUBLIC);
        a.addArgument(new JavaArgument("int", "age"));
        a.addArgument(new JavaArgument("String", "firstName", false));

        Assertions.assertEquals("public Person(final int age, String firstName) {this.age = age;this.firstName = firstName;}", PrintJavaUtil.getConstructor(a));
    }

    @Test
    @DisplayName("constructor with throws")
    void test3_getConstructor() {
        JavaConstructor a = new JavaConstructor("a.b.Person", "person");
        a.addModifier(JavaConstructorModifier.PUBLIC);
        a.addThrows("java.io.IOException");

        Assertions.assertEquals("public Person() throws IOException {}", PrintJavaUtil.getConstructor(a));
    }

    @Test
    void getField() {
        Assertions.fail("TODO");
    }

    @Test
    void getMethod() {
        Assertions.fail("TODO");
    }

    @Test
    void test1_getReturn() {
        JavaReturnType r = new JavaReturnType("a.b.Person", "person");

        Assertions.assertEquals("Person", PrintJavaUtil.getReturn(r));
    }

    @Test
    void test2_getReturn() {
        JavaReturnType r = null;

        Assertions.assertEquals("void ", PrintJavaUtil.getReturn(r));
    }

    @Test
    void getImport() {
        Assertions.fail("TODO");
    }

    @Test
    void getPackage() {
        Assertions.fail("TODO");
    }

}