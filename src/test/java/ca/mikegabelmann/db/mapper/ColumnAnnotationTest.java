package ca.mikegabelmann.db.mapper;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author mgabe
 */
class ColumnAnnotationTest {
    private ColumnAnnotation ann;


    @BeforeEach
    void beforeEach() {
        this.ann = new ColumnAnnotation();
    }

    /**
     *
     * <pre>
     * @Column(name = "ACMA_RK", nullable = false, updatable = false, precision = 9, scale = 0)
     * </pre>
     */
    @Test
    void test1_toString() {
        ann.setName("PRSN_ID");
        ann.setNullable(Boolean.FALSE);
        ann.setUpdatable(Boolean.FALSE);
        ann.setPrecision(9);
        ann.setScale(0);

        Assertions.assertEquals("@Column(name = \"PRSN_ID\", nullable = false, precision = 9, scale = 0, updatable = false)", ann.toString());
    }

    @Test
    void test2_toString() {
        ann.setUnique(true);
        ann.setInsertable(true);
        ann.setColumnDefinition("columnDefinition");
        ann.setLength(50);
        ann.setTable("table");

        Assertions.assertEquals("@Column(columnDefinition = \"columnDefinition\", insertable = true, length = 50, table = \"table\", unique = true)", ann.toString());
    }

}