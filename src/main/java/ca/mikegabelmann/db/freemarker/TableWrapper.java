package ca.mikegabelmann.db.freemarker;

import ca.mikegabelmann.codegen.lang.JavaArgument;
import ca.mikegabelmann.codegen.lang.JavaConstructorModifier;
import ca.mikegabelmann.codegen.util.ObjectUtil;
import ca.mikegabelmann.db.App;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.torque.ColumnType;
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
public class TableWrapper {
    /** Logger. */
    private static final Logger LOG = LogManager.getLogger(TableWrapper.class);

    private TableType tableType;
    private Map<String, String> sqlMappings;
    private Set<String> imports;

    private List<ColumnWrapper> allColumns;
    private List<ColumnWrapper> requiredColumns;


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
        this.allColumns = tableType.getColumn().stream().map(p -> new ColumnWrapper(p, sqlMappings.get(p.getType().name()))).collect(Collectors.toList());

        //collection of all required columns
        this.requiredColumns = tableType.getColumn().stream().filter(c -> c.isRequired()).map(p -> new ColumnWrapper(p, sqlMappings.get(p.getType().name()))).collect(Collectors.toList());
    }

    public TableType getTableType() {
        return tableType;
    }

    public List<ColumnWrapper> getRequiredColumns() {
        return requiredColumns;
    }

    public List<ColumnWrapper> getAllColumns() {
        return allColumns;
    }

    public String getSqlMapping(SqlDataType type) {
        return this.sqlMappings.get(type.name());
    }

    public String getSqlMapping(String type) {
        return this.sqlMappings.get(type);
    }

    public String getConstructorNoArgs() {
        return ObjectUtil.constructorNoArgsMethod(
            new JavaConstructorModifier[] { JavaConstructorModifier.PUBLIC },
            tableType.getJavaName(),
            new String[0]);
    }

    public String getConstructorRequiredArgs() {
        JavaArgument[] parameters = new JavaArgument[requiredColumns.size()];

        for (int i = 0; i < requiredColumns.size(); i++) {
            ColumnWrapper ct = requiredColumns.get(i);
            String javaType = ct.getJavaType();
            String javaName = ct.getColumnType().getJavaName();

            if (javaType.contains(".")) {
                this.addImport(javaType);

                javaType = javaType.substring(javaType.lastIndexOf("."));
            }

            parameters[i] = new JavaArgument(javaType, javaName);
        }

        return ObjectUtil.constructorMethod(
            new JavaConstructorModifier[] { JavaConstructorModifier.PUBLIC },
            tableType.getJavaName(),
            parameters,
            new String[0]);
    }

    public String getConstructorAllArgs() {
        JavaArgument[] parameters = new JavaArgument[allColumns.size()];

        for (int i=0; i < allColumns.size(); i++) {
            ColumnWrapper ct = allColumns.get(i);
            String javaType = ct.getJavaType();
            String javaName = ct.getColumnType().getJavaName();

            if (javaType.contains(".")) {
                this.addImport(javaType);

                javaType = javaType.substring(javaType.lastIndexOf(".") + 1);
            }

            parameters[i] = new JavaArgument(javaType, javaName);
        }

        return ObjectUtil.constructorMethod(
                new JavaConstructorModifier[] { JavaConstructorModifier.PUBLIC },
                tableType.getJavaName(),
                parameters,
                new String[0]);
    }

    public boolean addImport(String importString) {
        if (!imports.contains(importString)) {
            LOG.debug("import {} - added", importString);
            return imports.add(importString);

        } else {
            LOG.debug("import {} - skipped", importString);
            return false;
        }
    }

}
