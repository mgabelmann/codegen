package ca.mikegabelmann.codegen.java.lang.classbody;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author mgabe
 */
public class JavaAnnotation implements JavaType {
    /** Logger. */
    private static final Logger logger = LogManager.getLogger(JavaAnnotation.class);

    private final String type;

    /** Properties of annotation. */
    private final SortedMap<String, List<Object>> properties;


    public JavaAnnotation(@NotNull final String type) {
        this.type = type;
        this.properties = new TreeMap<>();
    }

    /**
     * Get annotation properties.
     * @return properties
     */
    public SortedMap<String, List<Object>> getProperties() {
        return properties;
    }

    /**
     * Add a new value or values.
     * @param key key
     * @param values one or more values to add
     */
    public void add(@NotNull String key, Object... values) {
        if (properties.containsKey(key)) {
            properties.get(key).addAll(Arrays.stream(values).toList());

        } else {
            properties.put(key, new ArrayList<>(Arrays.stream(values).toList()));
        }
    }

    /**
     * Remove a key and set of properties.
     * @param key key to remove
     * @return set of properties
     */
    public List<Object> remove(@NotNull String key) {
        return properties.remove(key);
    }

    @Override
    public String getCanonicalName() {
        return type;
    }

    @Override
    public String getSimpleName() {
        return !type.contains(".") ? type : type.substring(type.lastIndexOf(".") + 1);
    }

    @Override
    public String toString() {
        return "JavaAnnotation{" +
                "type='" + type + '\'' +
                ", properties=" + properties +
                '}';
    }

}
