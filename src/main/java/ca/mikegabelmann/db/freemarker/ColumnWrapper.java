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


    /**
     * Constructor.
     * @param columnType column
     */
    public ColumnWrapper(
        @NotNull final ColumnType columnType) {

        this.columnType = columnType;
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

    /**
     * Return underlying object.
     * @return column
     */
    public ColumnType getColumnType() {
        return columnType;
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

}
