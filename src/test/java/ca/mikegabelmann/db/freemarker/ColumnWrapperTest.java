package ca.mikegabelmann.db.freemarker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.torque.ColumnType;
import org.apache.torque.SqlDataType;
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
class ColumnWrapperTest {
    /** Logger. */
    private static final Logger LOG = LogManager.getLogger(ColumnWrapperTest.class);

    private Map<String, String> sqlMappings;
    private ColumnType ct1;
    private ColumnType ct2;
    private ColumnWrapper cw1;
    private ColumnWrapper cw2;

    @BeforeEach
    void beforeEach() {
        sqlMappings = new TreeMap<>();
        sqlMappings.put("DATE", "java.time.LocalDate");
        sqlMappings.put("INTEGER", "java.lang.Integer");

        //date column
        ct1 = new ColumnType();

        ct1.setName("BIRTH_DT");
        ct1.setType(SqlDataType.DATE);
        ct1.setPrimaryKey(Boolean.FALSE);
        ct1.setAutoIncrement(Boolean.FALSE);
        ct1.setRequired(Boolean.FALSE);
        ct1.setJavaName("birthDt");

        cw1 = new ColumnWrapper(sqlMappings, ct1);

        //primary key column
        ct2 = new ColumnType();

        ct2.setName("CONTACT_ID");
        ct2.setType(SqlDataType.INTEGER);
        ct2.setPrimaryKey(Boolean.TRUE);
        ct2.setAutoIncrement(Boolean.FALSE);
        ct2.setRequired(Boolean.TRUE);
        ct2.setJavaName("contactId");

        cw2 = new ColumnWrapper(sqlMappings, ct2);
    }

    @Test
    @DisplayName("isTemoral")
    void test2_isTemporal() {
        for (SqlDataType sdt : SqlDataType.values()) {
            ct1.setType(sdt);

            if (SqlDataType.DATE.equals(sdt) || SqlDataType.TIME.equals(sdt) || SqlDataType.TIMESTAMP.equals(sdt)) {
                Assertions.assertTrue(cw1.isTemporal(), sdt.name() + " expected true");

            } else {
                Assertions.assertFalse(cw1.isTemporal(), sdt.name() + " expected false");
            }
        }
    }

    @Test
    @DisplayName("getColumnType")
    void test1_getColumnType() {
        Assertions.assertEquals(ct1, cw1.getColumnType());
        Assertions.assertEquals(ct2, cw2.getColumnType());
    }

    @Test
    @DisplayName("getCanonicalName")
    void test1_getCanonicalName() {
        Assertions.assertEquals("java.time.LocalDate", cw1.getCanonicalName());
        Assertions.assertEquals("java.lang.Integer", cw2.getCanonicalName());
    }

    @Test
    @DisplayName("getSimpleName")
    void test1_getSimpleName() {
        Assertions.assertEquals("LocalDate", cw1.getSimpleName());
        Assertions.assertEquals("Integer", cw2.getSimpleName());
    }

    @Test
    @DisplayName("getVariableName")
    void test1_getVariableName() {
        Assertions.assertEquals("birthDt", cw1.getVariableName());
        Assertions.assertEquals("contactId", cw2.getVariableName());
    }

    @Test
    @DisplayName("getName")
    void test1_getName() {
        Assertions.assertEquals("BIRTH_DT", cw1.getName());
        Assertions.assertEquals("CONTACT_ID", cw2.getName());
    }

    @Test
    @DisplayName("getId")
    void test1_getId() {
        Assertions.assertEquals("BIRTH_DT", cw1.getId());
        Assertions.assertEquals("CONTACT_ID", cw2.getId());
    }

    @Test
    @DisplayName("isRequired")
    void test1_isRequired() {
        Assertions.assertFalse(cw1.isRequired());
        Assertions.assertTrue(cw2.isRequired());
    }

}