package ca.mikegabelmann.db.freemarker;

import org.apache.torque.ColumnType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 *
 * @author mgabe
 */
public class ColumnWrapper implements JavaClass {

    private ColumnType columnType;
    private Map<String, String> sqlMappings;

    /**
     * Constructor.
     * @param columnType column
     * @param sqlMappings SQL mappings
     */
    public ColumnWrapper(
            @NotNull final ColumnType columnType,
            @NotNull final Map<String, String> sqlMappings) {

        this.columnType = columnType;
        this.sqlMappings = sqlMappings;
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
        return sqlMappings.get(columnType.getType().name());
    }

    @Override
    public String getSimpleName() {
        String type = sqlMappings.get(columnType.getType().name());
        int index = type.lastIndexOf(".");

        return index >= 0 ? type.substring(index + 1) : type;
    }

    @Override
    public String getVariableName() {
        return columnType.getJavaName();
    }

    //TODO: add import?

}
