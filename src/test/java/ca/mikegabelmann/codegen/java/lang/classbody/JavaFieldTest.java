package ca.mikegabelmann.codegen.java.lang.classbody;

import ca.mikegabelmann.codegen.java.lang.modifiers.JavaFieldModifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JavaFieldTest {
    private JavaField field;


    @BeforeEach
    void beforeEach() {
        this.field = new JavaField("type", "name", false);
    }

    @Test
    @DisplayName("is modifier set empty by default")
    void test1_getModifiers() {
        Assertions.assertNotNull(field.getModifiers());
        Assertions.assertEquals(0, field.getModifiers().size());
    }

    @Test
    @DisplayName("can we add a modifer")
    void test1_addModifier() {
        field.addModifier(JavaFieldModifier.PUBLIC);

        Assertions.assertEquals(1, field.getModifiers().size());
    }

    @Test
    @DisplayName("can we remove a modifer")
    void test1_removeModifier() {
        field.addModifier(JavaFieldModifier.PUBLIC);

        Assertions.assertTrue(field.removeModifier(JavaFieldModifier.PUBLIC));
        Assertions.assertEquals(0, field.getModifiers().size());
    }

}