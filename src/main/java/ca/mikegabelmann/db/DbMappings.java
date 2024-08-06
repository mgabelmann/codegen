package ca.mikegabelmann.db;

import ca.mikegabelmann.codegen.util.StringUtil;
import org.apache.torque.SqlDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.TreeSet;

/**
 * <p>Not all databases have corresponding types. For example SQLite does not have a VARCHAR type,
 * instead it uses TEXT. So in all cases we want to map TEXT to VARCHAR.</p>
 *
 * @see ca.mikegabelmann.db.sqlite.SQLiteMappings
 */
public final class DbMappings {
    //TODO: add schema?

    /** String for matching table name. */
    private final String tableName;

    /** String for matching table type. */
    private final String dbDataType;

    /** Resulting SQL data type for use by Java. */
    private final SqlDataType sqlDataType;

    /** Strings for matching column name. */
    private final Set<String> names;

    /**
     *
     * @param dbDataType
     * @param sqlDataType
     */
    public DbMappings(@NotNull final String tableName, @NotNull final String dbDataType, @NotNull final SqlDataType sqlDataType) {
        if (StringUtil.isBlankOrNull(tableName)) {
            throw new IllegalArgumentException("tableName is null or empty");

        } else if (StringUtil.isBlankOrNull(dbDataType)) {
            throw new IllegalArgumentException("dbDataType is null or empty");

        } else if (sqlDataType == null) {
            throw new IllegalArgumentException("sqlDataType is null");
        }

        this.tableName = tableName;
        this.dbDataType = dbDataType;
        this.sqlDataType = sqlDataType;
        this.names = new TreeSet<>();
    }

    public String getTableName() {
        return tableName;
    }

    public String getDbDataType() {
        return dbDataType;
    }

    public SqlDataType getSqlDataType() {
        return sqlDataType;
    }

    public Set<String> getNames() {
        return names;
    }


    public void addName(final String name) {
        this.names.add(name);
    }

    public boolean matches(final String tableName, final String dbDataType, final String columnName) {
        if (tableName.matches(this.tableName) && dbDataType.matches(this.dbDataType)) {
            return names.stream().anyMatch(a -> a.matches(columnName));
        }

        return false;
    }

    @Override
    public String toString() {
        return "DbMappings{" +
                "dbDataType='" + dbDataType + '\'' +
                ", tableName='" + tableName + '\'' +
                ", sqlDataType=" + sqlDataType +
                ", names=" + names +
                '}';
    }
}
