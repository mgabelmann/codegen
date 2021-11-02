package ca.mikegabelmann.db.freemarker;

import ca.mikegabelmann.codegen.java.JavaNamingType;
import ca.mikegabelmann.codegen.util.NameUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.torque.TableType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
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

    private final List<ColumnWrapper> allColumns;
    private final List<ColumnWrapper> primaryKeys;

    //private final List<ColumnWrapper> requiredColumns;


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

        //collection of all columns
        this.allColumns = tableType.getColumn().stream().map(c -> new ColumnWrapper(sqlMappings, c)).collect(Collectors.toList());

        //collection of all columns marked as primary key (typically 1, but could be composite key in some cases)
        this.primaryKeys = allColumns.stream().filter(c -> c.getColumnType().isPrimaryKey()).collect(Collectors.toList());

        //collection of all required columns
        //this.requiredColumns = allColumns.stream().filter(c -> c.getColumnType().isRequired()).collect(Collectors.toList());

    }

    /**
     * Return underlying object.
     * @return table
     */
    public final TableType getTableType() {
        return tableType;
    }

    public final List<ColumnWrapper> getAllColumns() {
        return allColumns;
    }

    public final List<ColumnWrapper> getPrimaryKeys() {
        return primaryKeys;
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
    public final boolean isCompositeKey() {
        return this.primaryKeys.size() > 1;
    }


    public String getPrimaryKeySimpleName() {
        if (primaryKeys.size() == 1) {
            return primaryKeys.get(0).getSimpleName();

        } else {
            return this.getSimpleName() + "Id";
        }
    }

    @Override
    public String getCanonicalName() {
        //we don't know the package name, so just return the class name
        return NameUtil.getJavaName(JavaNamingType.UPPER_CAMEL_CASE, tableType.getName());
    }

    @Override
    public String getSimpleName() {
        return NameUtil.getJavaName(JavaNamingType.UPPER_CAMEL_CASE, tableType.getName());
    }

    @Override
    public String getVariableName() {
        return NameUtil.getJavaName(JavaNamingType.LOWER_CAMEL_CASE, tableType.getName());
    }


}
