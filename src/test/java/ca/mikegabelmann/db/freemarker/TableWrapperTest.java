package ca.mikegabelmann.db.freemarker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.torque.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author mgabe
 */
class TableWrapperTest {
    /** Logger. */
    private static final Logger LOG = LogManager.getLogger(TableWrapperTest.class);

    private Map<String, String> sqlMappings;
    private ColumnType ct1;
    private ColumnType ct2;
    private ReferenceType rt1;
    private ForeignKeyType fkt1;

    private TableType tt1;
    private TableWrapper tw1;


    @BeforeEach
    void beforeEach() {
        sqlMappings = new TreeMap<>();
        sqlMappings.put("INTEGER", "java.lang.Integer");

        //primary key column
        ct1 = new ColumnType();
        ct1.setName("CONTACT_ID");
        ct1.setType(SqlDataType.INTEGER);
        ct1.setPrimaryKey(Boolean.TRUE);
        ct1.setAutoIncrement(Boolean.FALSE);
        ct1.setRequired(Boolean.TRUE);
        ct1.setJavaName("contactId");

        ct2 = new ColumnType();
        ct2.setName("ADDRESS_ID");
        ct2.setRequired(Boolean.FALSE);
        ct2.setJavaName("addressId");

        rt1 = new ReferenceType();
        rt1.setLocal("ADDRESS_ID");
        rt1.setForeign("ADDRESS_ID");

        fkt1 = new ForeignKeyType();
        fkt1.setName("ADR_FK");
        fkt1.setForeignTable("ADDRESS");
        fkt1.getReference().add(rt1);

        tt1 = new TableType();
        tt1.setName("CONTACT");
        tt1.setJavaName("Contact");
        tt1.getColumn().add(ct1);
        tt1.getColumn().add(ct2);
        tt1.getForeignKeyOrIndexOrUnique().add(fkt1);

        tw1 = new TableWrapper(sqlMappings, tt1);
    }

    @Test
    @DisplayName("getTableType")
    void test1_getTableType() {
        Assertions.assertNotNull(tw1.getTableType());
    }

    @Test
    @DisplayName("getImports - none")
    void test1_getImports() {
        Assertions.assertNotNull(tw1.getImports());
        Assertions.assertEquals(0, tw1.getImports().size());
    }

    @Test
    @DisplayName("getColumnsNonKey")
    void test1_getColumnsNonKey() {
        Assertions.assertNotNull(tw1.getColumnsNonKey());
        Assertions.assertEquals(0, tw1.getColumnsNonKey().size());
    }

    @Test
    @DisplayName("getColumnsForeignKey")
    void test1_getColumnsForeignKey() {
        Assertions.assertNotNull(tw1.getColumnsForeignKey());
        Assertions.assertEquals(1, tw1.getColumnsForeignKey().size());
    }

    @Test
    @DisplayName("getColumnsNonKeyList")
    void test1_getColumnsNonKeyList() {
        Assertions.assertNotNull(tw1.getColumnsNonKeyList());
        Assertions.assertEquals(0, tw1.getColumnsNonKeyList().size());
    }

    @Test
    @DisplayName("getColumnsForeignKeyList")
    void test1_getColumnsForeignKeyList() {
        Assertions.assertNotNull(tw1.getColumnsForeignKeyList());
        Assertions.assertEquals(1, tw1.getColumnsForeignKeyList().size());
    }

    @Test
    @DisplayName("getLocalKey")
    void test1_getLocalKey() {
        Assertions.assertNotNull(tw1.getLocalKey());
    }

    @Test
    @DisplayName("getColumns")
    void test1_getColumns() {
        Assertions.assertEquals(2, tw1.getColumns().size());
    }

    @Test
    @DisplayName("addImport")
    void test1_addImport() {
        tw1.addImport("javax.annotation.Generated");
        Assertions.assertEquals(1, tw1.getImports().size());
    }

    @Test
    @DisplayName("getCanonicalName")
    void test1_getCanonicalName() {
        Assertions.assertEquals("Contact", tw1.getCanonicalName());
    }

    @Test
    @DisplayName("getSimpleName")
    void test1_getSimpleName() {
        Assertions.assertEquals("Contact", tw1.getSimpleName());
    }

    @Test
    @DisplayName("getVariableName")
    void test1_getVariableName() {
        Assertions.assertEquals("contact", tw1.getVariableName());
    }

    @Test
    @DisplayName("getName")
    void test1_getName() {
        Assertions.assertEquals("CONTACT", tw1.getName());
    }

    @Test
    @DisplayName("getId")
    void test1_getId() {
        Assertions.assertEquals("CONTACT", tw1.getId());
    }

    @Test
    @DisplayName("isRequired")
    void test1_isRequired() {
        Assertions.assertFalse(tw1.isRequired());
    }

}