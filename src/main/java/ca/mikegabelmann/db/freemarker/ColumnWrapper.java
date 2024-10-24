package ca.mikegabelmann.db.freemarker;

import org.apache.torque.ColumnType;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 *
 * @author mgabe
 */
public class ColumnWrapper extends AbstractWrapper {
    /**  */
    private final ColumnType columnType;

    /**  */
    private boolean unique;

    /**
     * Constructor.
     * @param columnType column
     */
    public ColumnWrapper(
        @NotNull final ColumnType columnType) {

        this.columnType = columnType;
        this.unique = false;
    }

    /**
     *
     * @return
     */
    public boolean isTemporal() {
        boolean temporal = false;

        switch (columnType.getType()) {
            case TIMESTAMP:
            case TIME:
            case DATE:
                temporal = true;
                break;
            default:
        }

        return temporal;
    }

    public boolean isBoolean() {
        return columnType.getJavaSqlType().equals("java.lang.Boolean");
    }

    /**
     * Return underlying object.
     * @return column
     */
    public ColumnType getColumnType() {
        return columnType;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    @Override
    public String getCanonicalName() {
        return columnType.getJavaSqlType();
    }

    @Override
    public String getSimpleName() {
        final String type = columnType.getJavaSqlType();
        return !type.contains(".") ? type : type.substring(type.lastIndexOf(".") + 1);
    }

    @Override
    public String getVariableName() {
        return columnType.getJavaName();
    }

    @Override
    public String getName() {
        return columnType.getName();
    }

    @Override
    public String getId() {
        return columnType.getName();
    }

    @Override
    public boolean isRequired() {
        return columnType.isRequired();
    }

    @Override
    public Set<String> getAllImports() {
        return imports;
    }

    @Override
    public String toString() {
        return "ColumnWrapper{" +
                "name=" + columnType.getName() +
                ", PK=" + columnType.isPrimaryKey() +
                ", type=" + columnType.getType() +
                ", required=" + columnType.isRequired() +
                ", unique=" + unique +
                '}';
    }
}
