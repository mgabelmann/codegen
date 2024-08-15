package ca.mikegabelmann.db;

import org.apache.torque.TableType;

import java.util.List;


public interface DatabaseParser {

    /**
     * Get all parsed tables.
     * @return tables
     */
    List<TableType> getTables();

    /**
     * Get a table by name.
     * @param tableName name of table
     * @return table or null
     */
    TableType getTable(String tableName);

}
