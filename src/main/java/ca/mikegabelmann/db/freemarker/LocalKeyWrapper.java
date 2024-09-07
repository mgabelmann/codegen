package ca.mikegabelmann.db.freemarker;

import ca.mikegabelmann.codegen.NamingType;
import ca.mikegabelmann.codegen.util.NameUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author mgabe
 */
public class LocalKeyWrapper extends AbstractWrapper {
    /**  */
    private final String tableName;

    /**  */
    private final Map<String, AbstractWrapper> columns;


    /**
     * Constructor.
     * @param tableWrapperName
     */
    public LocalKeyWrapper(@NotNull String tableWrapperName) {
        this.tableName = tableWrapperName;
        this.columns = new TreeMap<>();
    }

    public LocalKeyWrapper(@NotNull String tableName, @NotNull Map<String, AbstractWrapper> columns) {
        this.tableName = tableName;
        this.columns = columns;
    }

    public String getTableName() {
        return tableName;
    }

    public Map<String, AbstractWrapper> getColumns() {
        return columns;
    }

    public void addColumn(@NotNull final String key, @NotNull final AbstractWrapper wrapper) {
        columns.put(key, wrapper);
    }

    public boolean removeColumn(@NotNull final String key) {
        return columns.remove(key) != null;
    }

    public List<AbstractWrapper> getColumnValues() {
        return new ArrayList<>(columns.values());
    }

    /**
     * Does this table have a composite key or a single primary key?
     * @return true if composite, false otherwise
     */
    public final boolean isCompositeKey() {
        return columns.size() > 1;
    }

    @Override
    public String getCanonicalName() {
        return isCompositeKey() ? NameUtil.getJavaName(NamingType.UPPER_CAMEL_CASE, this.getName()) : columns.entrySet().iterator().next().getValue().getCanonicalName();
    }

    @Override
    public String getSimpleName() {
        return isCompositeKey() ? NameUtil.getJavaName(NamingType.UPPER_CAMEL_CASE, this.getName()) : columns.entrySet().iterator().next().getValue().getSimpleName();
    }

    @Override
    public String getVariableName() {
        return isCompositeKey() ? NameUtil.getJavaName(NamingType.LOWER_CAMEL_CASE, this.getName()) : columns.entrySet().iterator().next().getValue().getVariableName();
    }

    @Override
    public String getName() {
        return isCompositeKey() ? tableName + "_ID" : columns.entrySet().iterator().next().getValue().getName();
    }

    @Override
    public String getId() {
        return getName();
    }

    @Override
    public boolean isRequired() {
        return true;
    }

    @Override
    public Set<String> getAllImports() {
        this.imports.addAll(columns.values().stream().map(AbstractWrapper::getAllImports).flatMap(Collection::stream).toList());
        return imports;
    }

    @Override
    public String toString() {
        return "LocalKeyWrapper{" +
                "tableName=" + this.getName() +
                ", columns=" + columns +
                '}';
    }

}
