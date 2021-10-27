package ca.mikegabelmann.db.freemarker;

import org.apache.torque.ColumnType;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author mgabe
 */
public class ColumnWrapper {

    private ColumnType columnType;
    private String javaType;


    /**
     * Constructor.
     * @param columnType
     * @param javaType
     */
    public ColumnWrapper(@NotNull final ColumnType columnType, @NotNull final String javaType) {
        this.columnType = columnType;
        this.javaType = javaType;
    }

    public ColumnType getColumnType() {
        return columnType;
    }

    public String getJavaType() {
        return javaType;
    }

}
