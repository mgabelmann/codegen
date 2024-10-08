package ca.mikegabelmann.db.freemarker;

import ca.mikegabelmann.codegen.NamingType;
import ca.mikegabelmann.codegen.util.NameUtil;
import org.apache.torque.ForeignKeyType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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
     * @param foreignKeyType
     */
    public ForeignKeyWrapper(
        @NotNull final ForeignKeyType foreignKeyType) {

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
        return NameUtil.getJavaName(NamingType.UPPER_CAMEL_CASE, foreignKeyType.getForeignTable());
    }

    @Override
    public String getSimpleName() {
        return NameUtil.getJavaName(NamingType.UPPER_CAMEL_CASE, foreignKeyType.getForeignTable());
    }

    @Override
    public String getVariableName() {
        return NameUtil.getJavaName(NamingType.LOWER_CAMEL_CASE, foreignKeyType.getForeignTable());
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
        return isCompositeKey() || columns.get(0).isRequired();
    }

    @Override
    public Set<String> getAllImports() {
        this.imports.addAll(columns.stream().map(ColumnWrapper::getAllImports).flatMap(Collection::stream).toList());
        return imports;
    }

    @Override
    public String toString() {
        return "ForeignKeyWrapper{" +
                "name=" + foreignKeyType.getForeignTable() +
                ", columns=" + columns +
                '}';
    }
}
