package ca.mikegabelmann.codegen.lang;

import ca.mikegabelmann.codegen.util.AnnotationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 *
 * @author mgabe
 */
public class JavaAnnotation {
    /** Logger. */
    private static final Logger logger = LogManager.getLogger(JavaAnnotation.class);

    private String name;
    private SortedMap<String, List<Object>> properties;


    /**
     * Constructor.
     * @param name annotation name
     */
    public JavaAnnotation(@NotNull final String name) {
        this.name = name;
        this.properties = new TreeMap<>();
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
    public String toString() {
        return AnnotationUtil.getAnnotation(name, properties);
    }

}
