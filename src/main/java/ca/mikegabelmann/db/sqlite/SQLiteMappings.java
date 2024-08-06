package ca.mikegabelmann.db.sqlite;


import java.util.Map;
import java.util.TreeMap;

@Deprecated
public class SQLiteMappings {
    public static Map<String, String> mappings = new TreeMap<>();

    static {
        mappings.put("TEXT", "VARCHAR");
    }

}
