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

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author mgabe
 */
class ForeignKeyWrapperTest {
    /** Logger. */
    private static final Logger LOG = LogManager.getLogger(ForeignKeyWrapperTest.class);

    private Map<String, String> sqlMappings;
    private ColumnType ct1;
    private ColumnWrapper cw1;
    private ReferenceType rt1;
    private ForeignKeyType fkt1;
    private ForeignKeyWrapper fkw1;


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

        cw1 = new ColumnWrapper(sqlMappings, ct1);

        rt1 = new ReferenceType();
        rt1.setForeign("CONTACT_ID");
        rt1.setLocal("CONTACT_ID");

        fkt1 = new ForeignKeyType();
        fkt1.setForeignTable("CONTACT");
        fkt1.setName("CNTCT_FK");
        fkt1.getReference().add(rt1);

        fkw1 = new ForeignKeyWrapper(sqlMappings, fkt1);
        fkw1.addColumn(cw1);
    }

    @Test
    @DisplayName("getForeignKeyType")
    void test1_getForeignKeyType() {
        Assertions.assertEquals(fkt1, fkw1.getForeignKeyType());
    }

    @Test
    @DisplayName("getColumns")
    void test1_getColumns() {
        Assertions.assertEquals(1, fkw1.getColumns().size());
        Assertions.assertEquals(cw1, fkw1.getColumns().get(0));
    }

    @Test
    @DisplayName("addColumn")
    void test1_addColumn() {
        fkw1.addColumn(new ColumnWrapper(sqlMappings, new ColumnType()));
        Assertions.assertEquals(2, fkw1.getColumns().size());
    }

    @Test
    @DisplayName("removeColumn")
    void test1_removeColumn() {
        fkw1.removeColumn(cw1);
        Assertions.assertEquals(0, fkw1.getColumns().size());
    }

    @Test
    @DisplayName("getCanonicalName")
    void test1_getCanonicalName() {
        Assertions.assertEquals("Contact", fkw1.getCanonicalName());
    }

    @Test
    @DisplayName("getSimpleName")
    void test1_getSimpleName() {
        Assertions.assertEquals("Contact", fkw1.getSimpleName());
    }

    @Test
    @DisplayName("getVariableName")
    void test1_getVariableName() {
        Assertions.assertEquals("contact", fkw1.getVariableName());
    }

    @Test
    @DisplayName("getName")
    void test1_getName() {
        Assertions.assertEquals("CNTCT_FK", fkw1.getName());
    }

    @Test
    @DisplayName("getId")
    void test1_getId() {
        Assertions.assertEquals("CONTACT", fkw1.getId());
    }

    @Test
    @DisplayName("isRequired")
    void test1_isRequired() {
        Assertions.assertTrue(fkw1.isRequired());
    }

}