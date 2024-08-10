package ca.mikegabelmann.db.freemarker;

import ca.mikegabelmann.codegen.java.JavaNamingType;
import ca.mikegabelmann.codegen.util.NameUtil;
import org.apache.torque.ForeignKeyType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
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

    /**
     * Does this table have a composite key or a single primary key?
     * @return true if composite, false otherwise
     */
    public final boolean isCompositeKey() {
        return columns.size() > 1;
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
        return foreignKeyType.getName();
    }

    @Override
    public String getId() {
        return foreignKeyType.getForeignTable();
    }

    @Override
    public boolean isRequired() {
        return isCompositeKey() ? true : columns.get(0).isRequired();
    }

    @Override
    public void consolidateImports() {
        this.imports.addAll(columns.stream().map(ColumnWrapper::getImports).flatMap(Collection::stream).toList());
    }

}
