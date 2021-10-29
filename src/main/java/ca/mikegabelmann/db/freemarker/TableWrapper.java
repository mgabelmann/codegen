package ca.mikegabelmann.db.freemarker;

import ca.mikegabelmann.codegen.java.lang.classbody.JavaArgument;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaConstructorModifier;
import ca.mikegabelmann.codegen.java.JavaNamingType;
import ca.mikegabelmann.codegen.util.NameUtil;
import ca.mikegabelmann.codegen.util.ObjectUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.torque.SqlDataType;
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
public class TableWrapper implements JavaClass {
    /** Logger. */
    private static final Logger LOG = LogManager.getLogger(TableWrapper.class);

    private final TableType tableType;
    private final Map<String, String> sqlMappings;
    private final Set<String> imports;

    private final List<ColumnWrapper> allColumns;
    private final List<ColumnWrapper> requiredColumns;
    private final List<ColumnWrapper> primaryKeys;


    /**
     * Constructor.
     * @param tableType
     * @param sqlMappings
     */
    public TableWrapper(
            @NotNull TableType tableType,
            @NotNull Map<String, String> sqlMappings) {

        this.tableType = tableType;
        this.sqlMappings = sqlMappings;
        this.imports = new TreeSet<>();

        //collection of all columns
        this.allColumns = tableType.getColumn().stream().map(c -> new ColumnWrapper(c, sqlMappings)).collect(Collectors.toList());

        //collection of all required columns
        this.requiredColumns = allColumns.stream().filter(c -> c.getColumnType().isRequired()).collect(Collectors.toList());

        //collection of all columns marked as primary key (typically 1, but could be composite key in some cases)
        this.primaryKeys = allColumns.stream().filter(c -> c.getColumnType().isPrimaryKey()).collect(Collectors.toList());
    }

    /**
     * Return underlying object.
     * @return table
     */
    public TableType getTableType() {
        return tableType;
    }

    public List<ColumnWrapper> getRequiredColumns() {
        return requiredColumns;
    }

    public List<ColumnWrapper> getAllColumns() {
        return allColumns;
    }

    public List<ColumnWrapper> getPrimaryKeys() {
        return primaryKeys;
    }

    public String getSqlMapping(@NotNull SqlDataType type) {
        return this.sqlMappings.get(type.name());
    }

    public String getSqlMapping(@NotNull String type) {
        return this.sqlMappings.get(type);
    }

    public String getConstructorNoArgs() {
        return ObjectUtil.constructorNoArgsMethod(
            new JavaConstructorModifier[] { JavaConstructorModifier.PUBLIC },
            tableType.getJavaName(),
            new String[0]);
    }

    public String getConstructorRequiredArgs() {
        JavaArgument[] parameters = this.getJavaArguments(requiredColumns);

        return ObjectUtil.constructorMethod(
            new JavaConstructorModifier[] { JavaConstructorModifier.PUBLIC },
            tableType.getJavaName(),
            parameters,
            new String[0]);
    }

    public String getConstructorAllArgs() {
        JavaArgument[] parameters = this.getJavaArguments(allColumns);

        return ObjectUtil.constructorMethod(
                new JavaConstructorModifier[] { JavaConstructorModifier.PUBLIC },
                tableType.getJavaName(),
                parameters,
                new String[0]);
    }

    public boolean addImport(@NotNull String importString) {
        boolean added = false;

        if (!imports.contains(importString)) {
            added = imports.add(importString);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("import {} - {}", importString, added);
        }

        return added;
    }

    public String getPrimaryKeySimpleName() {
        if (primaryKeys.size() == 1) {
            return primaryKeys.get(0).getSimpleName();

        } else {
            //FIXME: determine class for composite key
            return "CompositeKey";
        }
    }

    @Override
    public String getCanonicalName() {
        //TODO: we don't know the package name, so just return the class name
        return tableType.getJavaName();
    }

    @Override
    public String getSimpleName() {
        return tableType.getJavaName();
    }

    @Override
    public String getVariableName() {
        return NameUtil.getJavaName(JavaNamingType.LOWER_CAMEL_CASE, tableType.getName());
    }

    private JavaArgument[] getJavaArguments(@NotNull List<ColumnWrapper> columns) {
        JavaArgument[] parameters = new JavaArgument[columns.size()];

        for (int i=0; i < columns.size(); i++) {
            ColumnWrapper column = columns.get(i);
            parameters[i] = new JavaArgument(column.getSimpleName(), column.getVariableName());
        }

        return parameters;
    }

}
