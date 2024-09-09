package ca.mikegabelmann.db.freemarker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.torque.ColumnType;
import org.apache.torque.SqlDataType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * @author mgabe
 */
class LocalKeyWrapperTest {
    /** Logger. */
    private static final Logger LOG = LogManager.getLogger(LocalKeyWrapperTest.class);

    private ColumnType ct1;
    private ColumnWrapper cw1;

    private LocalKeyWrapper lkw;


    @BeforeEach
    void beforeEach() {
        //primary key column
        ct1 = new ColumnType();

        ct1.setName("CONTACT_ID");
        ct1.setType(SqlDataType.INTEGER);
        ct1.setPrimaryKey(Boolean.TRUE);
        ct1.setAutoIncrement(Boolean.FALSE);
        ct1.setRequired(Boolean.TRUE);
        ct1.setJavaName("contactId");
        ct1.setJavaSqlType(Integer.class.getCanonicalName());

        cw1 = new ColumnWrapper(ct1);

        lkw = new LocalKeyWrapper("PERSON");
        lkw.addColumn("CONTACT_ID", cw1);
    }

    @Test
    @DisplayName("getColumns")
    void test1_getColumns() {
        Assertions.assertNotNull(lkw.getColumns());
        Assertions.assertEquals(1, lkw.getColumns().size());
    }

    @Test
    @DisplayName("addColumn")
    void test1_addColumn() {
        lkw.addColumn("TEST", new ColumnWrapper(new ColumnType()));
        Assertions.assertEquals(2, lkw.getColumns().size());
    }

    @Test
    @DisplayName("removeColumn")
    void test1_removeColumn() {
        lkw.removeColumn("CONTACT_ID");
        Assertions.assertEquals(0, lkw.getColumns().size());
    }

    @Test
    @DisplayName("isCompositeKey - 1 column")
    void test1_isCompositeKey() {
        Assertions.assertFalse(lkw.isCompositeKey());
    }

    @Test
    @DisplayName("isCompositeKey - 2 columns")
    void test2_isCompositeKey() {
        lkw.addColumn("TEST", new ColumnWrapper(new ColumnType()));
        Assertions.assertTrue(lkw.isCompositeKey());
    }

    @Test
    @DisplayName("getName - 1 column")
    void test1_getName() {
        Assertions.assertEquals("CONTACT_ID", lkw.getName());
    }

    @Test
    @DisplayName("getName - 2 columns")
    void test2_getName() {
        lkw.addColumn("TEST", new ColumnWrapper(new ColumnType()));
        Assertions.assertEquals("PERSON_ID", lkw.getName());
    }

    @Test
    @DisplayName("getId - 1 column")
    void test1_getId() {
        Assertions.assertEquals("CONTACT_ID", lkw.getId());
    }

    @Test
    @DisplayName("getId - 2 column")
    void test2_getId() {
        lkw.addColumn("TEST", new ColumnWrapper(new ColumnType()));
        Assertions.assertEquals("PERSON_ID", lkw.getId());
    }

    @Test
    @DisplayName("getCanonicalName - 1 column")
    void test1_getCanonicalName() {
        Assertions.assertEquals("java.lang.Integer", lkw.getCanonicalName());
    }

    @Test
    @DisplayName("getCanonicalName - 2 columns")
    void test2_getCanonicalName() {
        lkw.addColumn("TEST", new ColumnWrapper(new ColumnType()));
        Assertions.assertEquals("PersonId", lkw.getCanonicalName());
    }

    @Test
    @DisplayName("getSimpleName - 1 column")
    void test1_getSimpleName() {
        Assertions.assertEquals("Integer", lkw.getSimpleName());
    }

    @Test
    @DisplayName("getSimpleName - 2 columns")
    void test2_getSimpleName() {
        lkw.addColumn("TEST", new ColumnWrapper(new ColumnType()));
        Assertions.assertEquals("PersonId", lkw.getSimpleName());
    }

    @Test
    @DisplayName("getVariableName - 1 column")
    void test1_getVariableName() {
        Assertions.assertEquals("contactId", lkw.getVariableName());
    }

    @Test
    @DisplayName("getVariableName - 2 columns")
    void test2_getVariableName() {
        lkw.addColumn("TEST", new ColumnWrapper(new ColumnType()));
        Assertions.assertEquals("personId", lkw.getVariableName());
    }

    @Test
    @DisplayName("isRequired - 1 column")
    void test1_isRequired() {
        Assertions.assertTrue(lkw.isRequired());
    }

    @Test
    @DisplayName("isRequired - 2 columns")
    void test2_isRequired() {
        lkw.addColumn("TEST", new ColumnWrapper(new ColumnType()));
        Assertions.assertTrue(lkw.isRequired());
    }

}