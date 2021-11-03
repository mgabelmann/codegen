package ca.mikegabelmann.db.freemarker;

import ca.mikegabelmann.codegen.java.JavaNamingType;
import ca.mikegabelmann.codegen.util.NameUtil;
import org.apache.torque.ForeignKeyType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mgabe
 */
public class ForeignKeyWrapper extends AbstractWrapper {

    /**  */
    private final ForeignKeyType foreignKeyType;

    /**  */
    private final List<ColumnWrapper> columns;


    /**
     *
     * @param sqlMappings
     * @param foreignKeyType
     */
    public ForeignKeyWrapper(
        @NotNull final Map<String, String> sqlMappings,
        @NotNull final ForeignKeyType foreignKeyType) {

        super(sqlMappings);
        this.foreignKeyType = foreignKeyType;
        this.columns = new ArrayList<>();
    }

    public ForeignKeyType getForeignKeyType() {
        return foreignKeyType;
    }

    public List<ColumnWrapper> getColumns() {
        return columns;
    }

    public boolean addColumn(@NotNull ColumnWrapper column) {
        return columns.add(column);
    }

    public boolean removeColumn(@NotNull ColumnWrapper column) {
        return columns.remove(column);
    }

    @Override
    public String getCanonicalName() {
        return NameUtil.getJavaName(JavaNamingType.UPPER_CAMEL_CASE, foreignKeyType.getForeignTable());
    }

    @Override
    public String getSimpleName() {
        return NameUtil.getJavaName(JavaNamingType.UPPER_CAMEL_CASE, foreignKeyType.getForeignTable());
    }

    @Override
    public String getVariableName() {
        return NameUtil.getJavaName(JavaNamingType.LOWER_CAMEL_CASE, foreignKeyType.getForeignTable());
    }

    @Override
    public String getName() {
        return foreignKeyType.getForeignTable();
    }

    @Override
    public boolean isRequired() {
        return true;
    }

}
