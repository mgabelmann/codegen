package ca.mikegabelmann.db.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author mgabe
 */
class CustomAnnotationTest {

    private CustomAnnotation ann;

    @BeforeEach
    void beforeEach() {
        this.ann = new CustomAnnotation("Custom");
    }

    @Test
    @DisplayName("add an Object property")
    void test1_addPropertyObject() {
        ann.addProperty("key", "value");

        List<Object> results = ann.getValues("key");

        Assertions.assertNotNull(results);
        Assertions.assertEquals(1, results.size());
    }

    @Test
    @DisplayName("add another property to an existing key")
    void test2_addPropertyObject() {
        ann.addProperty("key", "value1");
        ann.addProperty("key", "value2");

        List<Object> results = ann.getValues("key");

        Assertions.assertNotNull(results);
        Assertions.assertEquals(2, results.size());
    }

    @Test
    @DisplayName("add a List<Object> property")
    void test1_addPropertyList() {
        ann.addProperty("key", List.of("A", "B"));

        List<Object> results = ann.getValues("key");

        Assertions.assertNotNull(results);
        Assertions.assertEquals(2, results.size());
    }

    @Test
    @DisplayName("toString without value")
    void test1_toString() {
        Assertions.assertEquals("@Custom", ann.toString());
    }

    @Test
    @DisplayName("toString with value")
    void test2_toString() {
        ann.addProperty("key", "value");

        Assertions.assertEquals("@Custom(key = \"value\")", ann.toString());
    }

    @Test
    @DisplayName("remove")
    void test1_remove() {
        ann.addProperty("key", "value");
        ann.remove("key");

        Assertions.assertNull(ann.getValues("key"));
    }

}