package ca.mikegabelmann.codegen.util;

import ca.mikegabelmann.codegen.java.lang.JavaMethodNamePrefix;
import ca.mikegabelmann.codegen.java.lang.JavaPrimitive;
import ca.mikegabelmann.codegen.java.lang.JavaTokens;
import ca.mikegabelmann.codegen.java.lang.classbody.*;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaConstructorModifier;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaFieldModifier;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaMethodModifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


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
        JavaArgument a = new JavaArgument("String", "str", true);

        Assertions.assertEquals("final String str", PrintJavaUtil.getArgument(a));
    }

    @Test
    void test2_getArgument() {
        JavaArgument a = new JavaArgument("String", "str", false);

        Assertions.assertEquals("String str", PrintJavaUtil.getArgument(a));
    }

    @Test
    void test3_getArgument() {
        JavaArgument a = new JavaArgument("String", "str", true);
        a.addAnnotation(new JavaAnnotation("A"));

        Assertions.assertEquals("@A final String str", PrintJavaUtil.getArgument(a));
    }

    @Test
    void test4_getArgument() {
        JavaArgument a = new JavaArgument("String", "str", false);
        a.addAnnotation(new JavaAnnotation("A"));

        Assertions.assertEquals("@A String str", PrintJavaUtil.getArgument(a));
    }

    @Test
    @DisplayName("no arguments constructor")
    void test1_getConstructor() {
        JavaConstructor a = new JavaConstructor("Person");
        a.addModifier(JavaConstructorModifier.PUBLIC);

        Assertions.assertEquals("public Person() {}", PrintJavaUtil.getConstructor(a));
    }

    @Test
    @DisplayName("constructor with 2 arguments")
    void test2_getConstructor() {
        JavaConstructor a = new JavaConstructor("Person");
        a.addModifier(JavaConstructorModifier.PUBLIC);
        a.addArgument(new JavaArgument(JavaPrimitive.INT, "age", true));
        a.addArgument(new JavaArgument("String", "firstName", false));

        Assertions.assertEquals("public Person(final int age, String firstName) {this.age = age;this.firstName = firstName;}", PrintJavaUtil.getConstructor(a));
    }

    @Test
    @DisplayName("constructor with throws")
    void test3_getConstructor() {
        JavaConstructor a = new JavaConstructor("Person");
        a.addModifier(JavaConstructorModifier.PUBLIC);
        a.addThrows("IOException");

        Assertions.assertEquals("public Person() throws IOException {}", PrintJavaUtil.getConstructor(a));
    }

    @Test
    @DisplayName("get field without annotation")
    void test1_getField() {
        JavaField field = new JavaField("Integer", "age");
        field.addModifier(JavaFieldModifier.PUBLIC);

        Assertions.assertEquals("public Integer age;", PrintJavaUtil.getField(field));
    }

    @Test
    @DisplayName("get field with modifiers")
    void test2_getField() {
        JavaField field = new JavaField("Integer", "age");
        field.addModifier(JavaFieldModifier.PUBLIC);
        field.addModifier(JavaFieldModifier.FINAL);

        Assertions.assertEquals("public final Integer age;", PrintJavaUtil.getField(field));
    }

    @Test
    @DisplayName("get field with special modifiers ordered")
    void test3_getField() {
        JavaField field = new JavaField("Integer", "age");
        field.addModifier(JavaFieldModifier.PRIVATE);
        field.addModifier(JavaFieldModifier.FINAL);
        field.addModifier(JavaFieldModifier.STATIC);

        Assertions.assertEquals("private static final Integer age;", PrintJavaUtil.getField(field));
    }

    @Test
    @DisplayName("get field with annotation")
    void test4_getField() {
        JavaAnnotation annotation = new JavaAnnotation("A");

        JavaField field = new JavaField("Integer", "age");
        field.addModifier(JavaFieldModifier.PUBLIC);
        field.addAnnotation(annotation);

        Assertions.assertEquals("@A" + JavaTokens.NEWLINE + "public Integer age;", PrintJavaUtil.getField(field));
    }



    @Test
    @DisplayName("get method")
    void test1_getMethod() {
        JavaMethod method = new JavaMethod("Age");
        method.addModifier(JavaMethodModifier.PUBLIC);
        method.setJavaReturnType(new JavaReturnType("Integer", "age"));
        method.setNamePrefix(JavaMethodNamePrefix.GET);

        Assertions.assertEquals("public Integer getAge() {return this.age;}", PrintJavaUtil.getMethod(method));
    }

    @Test
    @DisplayName("set method")
    void test2_getMethod() {
        JavaArgument argument = new JavaArgument("Integer", "age", true);

        JavaMethod method = new JavaMethod("Age");
        method.addModifier(JavaMethodModifier.PUBLIC);
        method.setNamePrefix(JavaMethodNamePrefix.SET);
        method.addArgument(argument);
        method.addThrows("IOException");

        Assertions.assertEquals("public void setAge(final Integer age) throws IOException {this.age = age;}", PrintJavaUtil.getMethod(method));
    }

    @Test
    @DisplayName("method with annotation")
    void test3_getMethod() {
        JavaAnnotation annotation = new JavaAnnotation("A");

        JavaMethod method = new JavaMethod("Age");
        method.addModifier(JavaMethodModifier.PUBLIC);
        method.setJavaReturnType(new JavaReturnType("Integer", "age"));
        method.setNamePrefix(JavaMethodNamePrefix.GET);
        method.addAnnotation(annotation);

        String expected = StringUtil.replaceLinefeeds("@A\npublic Integer getAge() {return this.age;}");
        Assertions.assertEquals(expected, PrintJavaUtil.getMethod(method));
    }

    @Test
    @DisplayName("method with multiple annotations")
    void test4_getMethod() {
        JavaAnnotation annotation = new JavaAnnotation("A");
        JavaAnnotation annotation2 = new JavaAnnotation("B");
        annotation2.add("name", "value");

        JavaMethod method = new JavaMethod("Age");
        method.addModifier(JavaMethodModifier.PUBLIC);
        method.setJavaReturnType(new JavaReturnType("Integer", "age"));
        method.setNamePrefix(JavaMethodNamePrefix.GET);
        method.addAnnotation(annotation);
        method.addAnnotation(annotation2);

        String expected = StringUtil.replaceLinefeeds("@A\n@B(name = \"value\")\npublic Integer getAge() {return this.age;}");
        Assertions.assertEquals(expected, PrintJavaUtil.getMethod(method));
    }

    /*
    if (method.getThrows().size() > 0) {
            sb.append(JavaKeywords.THROWS);
            String throwsList = method.getThrows().stream().map(Object::toString).collect(Collectors.joining(JavaTokens.DELIMITER));
            sb.append(throwsList);
            sb.append(JavaTokens.SPACE);
        }

        sb.append(JavaTokens.BRACE_LEFT);

        for (JavaArgument argument : arguments) {
            sb.append(JavaKeywords.THIS_DOT);
            sb.append(argument.getName());
            sb.append(JavaTokens.EQUALS_WITH_SPACES);
            sb.append(argument.getName());
            sb.append(JavaTokens.SEMICOLON);
        }
     */

    @Test
    void test1_getReturn() {
        JavaReturnType r = new JavaReturnType("Person", "person");

        Assertions.assertEquals("Person ", PrintJavaUtil.getReturn(r));
    }

    @Test
    void test2_getReturn() {
        Assertions.assertEquals("void ", PrintJavaUtil.getReturn(null));
    }

    @Test
    void getImport() {
        Assertions.assertEquals("import java.io.IOException;", PrintJavaUtil.getImport(new JavaImport("java.io.IOException")));
    }

    @Test
    void getPackage() {
        Assertions.assertEquals("package a.b.c;", PrintJavaUtil.getPackage(new JavaPackage("a.b.c")));
    }

}