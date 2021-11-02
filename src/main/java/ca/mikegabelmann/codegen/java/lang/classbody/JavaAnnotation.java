package ca.mikegabelmann.codegen.java.lang.classbody;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.*;


/**
 *
 * @author mgabe
 */
public class JavaAnnotation extends AbstractJavaType {
    /** Logger. */
    private static final Logger logger = LogManager.getLogger(JavaAnnotation.class);

    /** Properties of annotation. */
    private final SortedMap<String, List<Object>> properties;


    /**
     * Constructor.
     * @param type type
     */
    public JavaAnnotation(@NotNull String type) {
        super(type, "");
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
    public String toString() {
        final StringBuilder sb = new StringBuilder("JavaAnnotation{");
        sb.append("type='").append(type).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", properties=").append(properties);
        sb.append('}');

        return sb.toString();
    }

}
