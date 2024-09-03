package ca.mikegabelmann.codegen.java;

import ca.mikegabelmann.codegen.java.lang.JavaMethodNamePrefix;
import ca.mikegabelmann.codegen.java.lang.JavaPrimitive;
import ca.mikegabelmann.codegen.java.lang.JavaTokens;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaAnnotation;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaArgument;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaClass;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaConstructor;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaField;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaImport;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaMethod;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaPackage;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaReturnType;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaClassModifier;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaConstructorModifier;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaFieldModifier;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaMethodModifier;
import ca.mikegabelmann.codegen.util.StringUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


/**
 *
 * @author mgabe
 */
class JavaClassPrintFactoryTest {

    private AbstractJavaPrintFactory printFactory;

    @BeforeEach
    void setUp() {
        this.printFactory = new JavaClassPrintFactory();
    }

    @Test
    void test1_getAnnotation() {
        JavaAnnotation a = new JavaAnnotation("A");

        Assertions.assertEquals("@A", printFactory.printAnnotation(a));
    }

    @Test
    void test2_getAnnotation() {
        JavaAnnotation a = new JavaAnnotation("A");
        a.add("b", Boolean.FALSE);

        Assertions.assertEquals("@A(b = false)", printFactory.printAnnotation(a));
    }

    @Test
    void test3_getAnnotation() {
        JavaAnnotation a = new JavaAnnotation("A");
        a.add("b", Arrays.asList("c", "d"));

        Assertions.assertEquals("@A(b = {\"c\", \"d\"})", printFactory.printAnnotation(a));
    }

    @Test
    void test4_getAnnotation() {
        JavaAnnotation b = new JavaAnnotation("B");
        b.add("name1", "value1");
        b.add("name2", "value2");

        JavaAnnotation a = new JavaAnnotation("A");
        a.add("value", b);

        Assertions.assertEquals("@A(value = @B(name1 = \"value1\", name2 = \"value2\"))", printFactory.printAnnotation(a));
    }

    @Test
    void test5_getAnnotation() {
        JavaAnnotation b = new JavaAnnotation("B");
        b.add("name1", "value1");

        JavaAnnotation c = new JavaAnnotation("C");
        c.add("name2", "value2");

        JavaAnnotation a = new JavaAnnotation("A");
        a.add("", List.of(b, c));

        Assertions.assertEquals("@A({@B(name1 = \"value1\"), @C(name2 = \"value2\")})", printFactory.printAnnotation(a));
    }

    @Test
    void test1_getArgument() {
        JavaArgument a = new JavaArgument("String", "str", true);

        Assertions.assertEquals("final String str", printFactory.printArgument(a));
    }

    @Test
    void test2_getArgument() {
        JavaArgument a = new JavaArgument("String", "str", false);

        Assertions.assertEquals("String str", printFactory.printArgument(a));
    }

    @Test
    void test3_getArgument() {
        JavaArgument a = new JavaArgument("String", "str", true);
        a.addAnnotation(new JavaAnnotation("A"));

        Assertions.assertEquals("@A final String str", printFactory.printArgument(a));
    }

    @Test
    void test4_getArgument() {
        JavaArgument a = new JavaArgument("String", "str", false);
        a.addAnnotation(new JavaAnnotation("A"));

        Assertions.assertEquals("@A String str", printFactory.printArgument(a));
    }

    @Test
    @DisplayName("no arguments constructor")
    void test1_getConstructor() {
        JavaConstructor a = new JavaConstructor("Person");
        a.addModifier(JavaConstructorModifier.PUBLIC);

        Assertions.assertEquals("public Person() {}", printFactory.printConstructor(a));
    }

    @Test
    @DisplayName("constructor with 2 arguments")
    void test2_getConstructor() {
        JavaConstructor a = new JavaConstructor("Person");
        a.addModifier(JavaConstructorModifier.PUBLIC);
        a.addArgument(new JavaArgument(JavaPrimitive.INT, "age", true));
        a.addArgument(new JavaArgument("String", "firstName", false));

        Assertions.assertEquals("public Person(final int age, String firstName) {this.age = age;this.firstName = firstName;}", printFactory.printConstructor(a));
    }

    @Test
    @DisplayName("constructor with throws")
    void test3_getConstructor() {
        JavaConstructor a = new JavaConstructor("Person");
        a.addModifier(JavaConstructorModifier.PUBLIC);
        a.addThrows("IOException");

        Assertions.assertEquals("public Person() throws IOException {}", printFactory.printConstructor(a));
    }

    @Test
    @DisplayName("get field without annotation")
    void test1_getField() {
        JavaField field = new JavaField("Integer", "age", false);
        field.addModifier(JavaFieldModifier.PUBLIC);

        Assertions.assertEquals("public Integer age;", printFactory.printField(field));
    }

    @Test
    @DisplayName("get field with modifiers")
    void test2_getField() {
        JavaField field = new JavaField("Integer", "age", false);
        field.addModifier(JavaFieldModifier.PUBLIC);
        field.addModifier(JavaFieldModifier.FINAL);

        Assertions.assertEquals("public final Integer age;", printFactory.printField(field));
    }

    @Test
    @DisplayName("get field with special modifiers ordered")
    void test3_getField() {
        JavaField field = new JavaField("Integer", "age", false);
        field.addModifier(JavaFieldModifier.PRIVATE);
        field.addModifier(JavaFieldModifier.FINAL);
        field.addModifier(JavaFieldModifier.STATIC);

        Assertions.assertEquals("private static final Integer age;", printFactory.printField(field));
    }

    @Test
    @DisplayName("get field with annotation")
    void test4_getField() {
        JavaAnnotation annotation = new JavaAnnotation("A");

        JavaField field = new JavaField("Integer", "age", false);
        field.addModifier(JavaFieldModifier.PUBLIC);
        field.addAnnotation(annotation);

        Assertions.assertEquals("@A" + JavaTokens.NEWLINE + "public Integer age;", printFactory.printField(field));
    }



    @Test
    @DisplayName("get method")
    void test1_getMethod() {
        JavaMethod method = new JavaMethod("Age");
        method.addModifier(JavaMethodModifier.PUBLIC);
        method.setJavaReturnType(new JavaReturnType("Integer"));
        method.setNamePrefix(JavaMethodNamePrefix.GET);
        method.getBody().append(JavaClassPrintFactory.printFieldReturnValue("age", true));

        Assertions.assertEquals("public Integer getAge() {return this.age;}", printFactory.printMethod(method));
    }

    @Test
    @DisplayName("method with annotation")
    void test2_getMethod() {
        JavaAnnotation annotation = new JavaAnnotation("A");

        JavaMethod method = new JavaMethod("Age");
        method.addModifier(JavaMethodModifier.PUBLIC);
        method.setJavaReturnType(new JavaReturnType("Integer"));
        method.setNamePrefix(JavaMethodNamePrefix.GET);
        method.addAnnotation(annotation);
        method.getBody().append(JavaClassPrintFactory.printFieldReturnValue("age", true));

        String expected = StringUtil.replaceLinefeeds("@A\npublic Integer getAge() {return this.age;}");
        Assertions.assertEquals(expected, printFactory.printMethod(method));
    }

    @Test
    @DisplayName("method with multiple annotations")
    void test3_getMethod() {
        JavaAnnotation annotation = new JavaAnnotation("A");
        JavaAnnotation annotation2 = new JavaAnnotation("B");
        annotation2.add("name", "value");

        JavaMethod method = new JavaMethod("Age");
        method.addModifier(JavaMethodModifier.PUBLIC);
        method.setJavaReturnType(new JavaReturnType("Integer"));
        method.setNamePrefix(JavaMethodNamePrefix.GET);
        method.addAnnotation(annotation);
        method.addAnnotation(annotation2);
        method.getBody().append(JavaClassPrintFactory.printFieldReturnValue("age", true));

        String expected = StringUtil.replaceLinefeeds("@A\n@B(name = \"value\")\npublic Integer getAge() {return this.age;}");
        Assertions.assertEquals(expected, printFactory.printMethod(method));
    }

    @Test
    @DisplayName("set method")
    void test1_setMethod() {
        JavaArgument argument = new JavaArgument("Integer", "age", true);

        JavaMethod method = new JavaMethod("Age");
        method.addModifier(JavaMethodModifier.PUBLIC);
        method.setNamePrefix(JavaMethodNamePrefix.SET);
        method.addArgument(argument);
        method.addThrows("IOException");
        method.getBody().append(JavaClassPrintFactory.printFieldAssignment("age", true));

        Assertions.assertEquals("public void setAge(final Integer age) throws IOException {this.age = age;}", printFactory.printMethod(method));
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
        JavaReturnType r = new JavaReturnType("Person");

        Assertions.assertEquals("Person ", printFactory.printReturn(r));
    }

    @Test
    void test2_getReturn() {
        Assertions.assertEquals("void ", printFactory.printReturn(null));
    }

    @Test
    void getImport() {
        Assertions.assertEquals("import java.io.IOException;", printFactory.printImport(new JavaImport("java.io.IOException")));
    }

    @Test
    void test1_getPackage() {
        Assertions.assertEquals("package a.b.c;", printFactory.printPackage(new JavaPackage("a.b.c")));
    }

    @Test
    void test2_getPackage() {
        Assertions.assertEquals("", printFactory.printPackage(new JavaPackage("")));
    }

    @Test
    @DisplayName("print a class")
    void test1_printClass() {
        JavaArgument a1 = new JavaArgument(String.class.getCanonicalName(), "name", true);

        JavaConstructor con = new JavaConstructor("Person");
        con.addModifier(JavaConstructorModifier.PUBLIC);
        con.addArgument(a1);

        JavaMethod m1 = JavaMethod.getGetter("FIRST_NAME", String.class.getCanonicalName(), JavaMethodModifier.FINAL, JavaMethodModifier.PUBLIC);

        JavaField f1 = new JavaField(String.class.getCanonicalName(), "name", false);
        f1.addModifier(JavaFieldModifier.FINAL);
        f1.addModifier(JavaFieldModifier.PUBLIC);

        JavaClass clazz = new JavaClass("Person", "person", new JavaPackage("a.b.c"));
        clazz.addJavaImport("java.io.IOException");
        clazz.addJavaClassModifier(JavaClassModifier.PUBLIC);
        clazz.addConstructor(con);
        clazz.addAnnotation(new JavaAnnotation("A"));
        clazz.addMethod(m1);
        clazz.addJavaField(f1);
        Assertions.assertEquals(StringUtil.replaceLinefeeds("package a.b.c;\nimport java.io.IOException;\nimport java.lang.String;\n@A\npublic class Person {\npublic final String name;\npublic Person(final String name) {this.name = name;}\npublic  final String getFirstName() {return this.firstName;}\n}"), printFactory.printClass(clazz));
    }

}
