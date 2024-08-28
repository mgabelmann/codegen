package ca.mikegabelmann.db.freemarker;

import ca.mikegabelmann.codegen.NamingType;
import ca.mikegabelmann.codegen.util.NameUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.torque.ForeignKeyType;
import org.apache.torque.ReferenceType;
import org.apache.torque.TableType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author mgabe
 */
public class TableWrapper extends AbstractWrapper {
    /** Logger. */
    private static final Logger LOG = LogManager.getLogger(TableWrapper.class);

    /**  */
    private final TableType tableType;

    /**  */
    private final Map<String, ColumnWrapper> columnsNonKey;

    /**  */
    private final Map<String, ForeignKeyWrapper> columnsForeignKey;

    /**  */
    private final LocalKeyWrapper localKey;


    /**
     * Constructor.
     * @param tableType
     */
    public TableWrapper(
        @NotNull TableType tableType) {

        this.tableType = tableType;

        //collection of all columns by column name
        Map<String, ColumnWrapper> columns = tableType.getColumn().stream()
                .map(ColumnWrapper::new)
                .collect(Collectors.toMap(ColumnWrapper::getName, Function.identity()));

        //collection of all keys (primary or composite) that identify this table
        Map<String, ColumnWrapper> keys = columns.values().stream()
                .filter(c -> c.getColumnType().isPrimaryKey())
                .collect(Collectors.toMap(ColumnWrapper::getName, Function.identity()));

        //collection of foreign keys
        Map<String, ForeignKeyWrapper> foreignKeys = tableType.getForeignKeyOrIndexOrUnique().stream()
                .filter(c -> c instanceof ForeignKeyType)
                .map(c -> (ForeignKeyType) c)
                .map(ForeignKeyWrapper::new)
                .collect(Collectors.toMap(c -> c.getForeignKeyType().getForeignTable(), Function.identity()));

        {
            //cleanup
            for (ForeignKeyWrapper fkw : foreignKeys.values()) {
                for (ReferenceType rt : fkw.getForeignKeyType().getReference()) {
                    ColumnWrapper c = columns.remove(rt.getLocal());

                    if (c != null) {
                        //NOTE: should never be null
                        fkw.addColumn(c);
                    }
                }
            }

            for (String key : keys.keySet()) {
                columns.remove(key);
            }
        }

        //TODO: process index
        //TODO: process unique

        this.columnsNonKey = columns;
        this.columnsForeignKey = foreignKeys;
        this.localKey = new LocalKeyWrapper(this.getName());

        for (ColumnWrapper column : keys.values()) {
            localKey.addColumn(column);
        }


        if (localKey.isCompositeKey()) {
            //TODO:
        } else {
            //TODO:
        }

        if (!columnsNonKey.isEmpty()) {
            //TODO:
        }

        if (!foreignKeys.isEmpty()) {
            //TODO: detect OneToOne, OneToMany, ManyToOne, ManyToMany
        }

    }

    /**
     * Return underlying object.
     * @return table
     */
    public final TableType getTableType() {
        return tableType;
    }

    public Map<String, ColumnWrapper> getColumnsNonKey() {
        return columnsNonKey;
    }

    public Map<String, ForeignKeyWrapper> getColumnsForeignKey() {
        return columnsForeignKey;
    }

    public Collection<ColumnWrapper> getColumnsNonKeyList() {
        return columnsNonKey.values();
    }

    public Collection<ForeignKeyWrapper> getColumnsForeignKeyList() {
        return columnsForeignKey.values();
    }

    public LocalKeyWrapper getLocalKey() {
        return localKey;
    }

    public List<AbstractWrapper> getColumns() {
        List<AbstractWrapper> columns = new ArrayList<>();
        columns.add(localKey);
        columns.addAll(columnsNonKey.values());
        columns.addAll(columnsForeignKey.values());

        return columns;
    }

    //FIXME: move to AbstractWrapper?
    public final String addTypedImport(@NotNull String importString) {
        this.addImport(importString);
        return importString.substring(importString.lastIndexOf('.') + 1);
    }

    @Override
    public Set<String> getAllImports() {
        this.imports.addAll(columnsForeignKey.values().stream().map(ForeignKeyWrapper::getAllImports).flatMap(Collection::stream).toList());
        this.imports.addAll(columnsNonKey.values().stream().map(ColumnWrapper::getAllImports).flatMap(Collection::stream).toList());
        this.imports.addAll(localKey.getAllImports());
        return this.imports;
    }

    @Override
    public final String getCanonicalName() {
        return NameUtil.getJavaName(NamingType.UPPER_CAMEL_CASE, tableType.getName());
    }

    @Override
    public final String getSimpleName() {
        return NameUtil.getJavaName(NamingType.UPPER_CAMEL_CASE, tableType.getName());
    }

    @Override
    public final String getVariableName() {
        return NameUtil.getJavaName(NamingType.LOWER_CAMEL_CASE, tableType.getName());
    }

    @Override
    public final String getName() {
        return tableType.getName();
    }

    @Override
    public String getId() {
        return tableType.getName();
    }

    @Override
    public boolean isRequired() {
        return false;
    }

}
