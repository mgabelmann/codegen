package ca.mikegabelmann.db.freemarker;

import ca.mikegabelmann.codegen.java.JavaNamingType;
import ca.mikegabelmann.codegen.util.NameUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mgabe
 */
public class LocalKeyWrapper extends AbstractWrapper {
    /**  */
    private final List<ColumnWrapper> columns;

    private String tableName;


    /**
     * Constructor.
     * @param sqlMappings
     * @param tableWrapperName
     */
    public LocalKeyWrapper(
        @NotNull Map<String, String> sqlMappings,
        @NotNull String tableWrapperName) {

        super(sqlMappings);
        this.columns = new ArrayList<>();
        this.tableName = tableWrapperName;
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

    /**
     * Does this table have a composite key or a single primary key?
     * @return true if composite, false otherwise
     */
    public final boolean isCompositeKey() {
        return columns.size() > 1;
    }

    @Override
    public String getName() {
        return isCompositeKey() ? tableName + "_ID" : columns.get(0).getName();
    }

    @Override
    public String getCanonicalName() {
        return isCompositeKey() ? NameUtil.getJavaName(JavaNamingType.UPPER_CAMEL_CASE, this.getName()) : columns.get(0).getCanonicalName();
    }

    @Override
    public String getSimpleName() {
        return isCompositeKey() ? NameUtil.getJavaName(JavaNamingType.UPPER_CAMEL_CASE, this.getName()) : columns.get(0).getSimpleName();
    }

    @Override
    public String getVariableName() {
        return isCompositeKey() ? NameUtil.getJavaName(JavaNamingType.LOWER_CAMEL_CASE, this.getName()) : columns.get(0).getVariableName();
    }

    @Override
    public boolean isRequired() {
        return true;
    }
}
