package ca.mikegabelmann.db.freemarker;

import ca.mikegabelmann.codegen.java.JavaNamingType;
import ca.mikegabelmann.codegen.util.NameUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.torque.ForeignKeyType;
import org.apache.torque.ReferenceType;
import org.apache.torque.TableType;
import org.jetbrains.annotations.NotNull;

import java.util.*;
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
    private final Set<String> imports;

    /**  */
    private final Map<String, ColumnWrapper> columnsNonKey;

    /**  */
    private final Map<String, ForeignKeyWrapper> columnsForeignKey;

    /**  */
    private final LocalKeyWrapper localKey;


    /**
     * Constructor.
     * @param sqlMappings
     * @param tableType
     */
    public TableWrapper(
        @NotNull Map<String, String> sqlMappings,
        @NotNull TableType tableType) {

        super(sqlMappings);
        this.tableType = tableType;
        this.imports = new TreeSet<>();

        //collection of all columns by column name
        Map<String, ColumnWrapper> columns = tableType.getColumn().stream()
                .map(c -> new ColumnWrapper(sqlMappings, c))
                .collect(Collectors.toMap(c -> c.getName(), Function.identity()));

        //collection of all keys (primary or composite) that identify this table
        Map<String, ColumnWrapper> keys = columns.values().stream()
                .filter(c -> c.getColumnType().isPrimaryKey())
                .collect(Collectors.toMap(c -> c.getName(), Function.identity()));

        //collection of foreign keys
        Map<String, ForeignKeyWrapper> foreignKeys = tableType.getForeignKeyOrIndexOrUnique().stream()
                .filter(c -> c instanceof ForeignKeyType)
                .map(c -> (ForeignKeyType) c)
                .map(c -> new ForeignKeyWrapper(sqlMappings, c))
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
        this.localKey = new LocalKeyWrapper(sqlMappings, this.getName());

        for (ColumnWrapper column : keys.values()) {
            localKey.addColumn(column);
        }
    }

    /**
     * Return underlying object.
     * @return table
     */
    public final TableType getTableType() {
        return tableType;
    }

    public Set<String> getImports() {
        return imports;
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
        columns.addAll(columnsForeignKey.values());
        columns.addAll(columnsNonKey.values());

        return columns;
    }

    public final boolean addImport(@NotNull String importString) {
        boolean added = false;

        if (!imports.contains(importString)) {
            added = imports.add(importString);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("import {} - {}", importString, added);
        }

        return added;
    }

    /**
     * Does this table have a composite key or a single primary key?
     * @return true if composite, false otherwise
     */
/*    public final boolean isCompositeKey() {
        return localKey.isCompositeKey();
    }*/

    @Override
    public final String getCanonicalName() {
        return NameUtil.getJavaName(JavaNamingType.UPPER_CAMEL_CASE, tableType.getName());
    }

    @Override
    public final String getSimpleName() {
        return NameUtil.getJavaName(JavaNamingType.UPPER_CAMEL_CASE, tableType.getName());
    }

    @Override
    public final String getVariableName() {
        return NameUtil.getJavaName(JavaNamingType.LOWER_CAMEL_CASE, tableType.getName());
    }

    @Override
    public final String getName() {
        return tableType.getName();
    }

    @Override
    public boolean isRequired() {
        return false;
    }

}
