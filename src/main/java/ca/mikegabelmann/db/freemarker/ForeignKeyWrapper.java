package ca.mikegabelmann.db.freemarker;

import org.apache.torque.ForeignKeyType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;


/**
 *
 * @author mgabe
 */
public class ForeignKeyWrapper extends AbstractWrapper {

    /**  */
    private final ForeignKeyType foreignKeyType;

    /**  */
    private final ColumnWrapper columnWrapper;


    /**
     *
     * @param sqlMappings
     * @param foreignKeyType
     * @param columnWrapper
     */
    public ForeignKeyWrapper(
        @NotNull final Map<String, String> sqlMappings,
        @NotNull final ForeignKeyType foreignKeyType,
        @NotNull final ColumnWrapper columnWrapper) {

        super(sqlMappings);
        this.foreignKeyType = foreignKeyType;
        this.columnWrapper = columnWrapper;
    }

    public ForeignKeyType getForeignKeyType() {
        return foreignKeyType;
    }

    public ColumnWrapper getColumnWrapper() {
        return columnWrapper;
    }

    @Override
    public String getCanonicalName() {
        return columnWrapper.getCanonicalName();
    }

    @Override
    public String getSimpleName() {
        return null;
    }

    @Override
    public String getVariableName() {
        return null;
    }

}
