package ca.mikegabelmann.db.freemarker;

import org.apache.torque.ColumnType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 *
 * @author mgabe
 */
public class ColumnWrapper extends AbstractWrapper {
    /**  */
    private final ColumnType columnType;


    /**
     * Constructor.
     * @param sqlMappings SQL mappings
     * @param columnType column
     */
    public ColumnWrapper(
        @NotNull final Map<String, String> sqlMappings,
        @NotNull final ColumnType columnType) {

        super(sqlMappings);
        this.columnType = columnType;
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
        return super.getClass(columnType.getType()).getCanonicalName();
    }

    @Override
    public String getSimpleName() {
        return super.getClass(columnType.getType()).getSimpleName();
    }

    @Override
    public String getVariableName() {
        return columnType.getJavaName();
    }

    public boolean isTemporal() {
        boolean temporal;

        switch(columnType.getType()) {
            case TIMESTAMP:
            case TIME:
            case DATE:
                temporal = true;
                break;

            default:
                temporal = false;
        }

        return temporal;
    }

    //TODO: add import?

}
