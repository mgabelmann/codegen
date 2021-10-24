package ca.mikegabelmann.db.mapper;

import ca.mikegabelmann.codegen.util.AnnotationUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 *
 * @author mgabe
 */
public abstract class AbstractAnnotation {
    /** Annotation name. */
    protected final String name;

    /** Annotation properties. */
    private final SortedMap<String, List<Object>> properties;


    /**
     * Constructor.
     * @param name annotation name
     */
    public AbstractAnnotation(@NotNull final String name) {
        this.name = name;
        this.properties = new TreeMap<>();
    }

    /**
     * Add an annotation property.
     * @param key property
     * @param values values
     */
    void addProperty(@NotNull final String key, @NotNull final List<Object> values) {
        this.properties.put(key, values);
    }

    /**
     * Add a value to an existing properties set of values.
     * @param key key
     * @param value value to add
     */
    void addProperty(@NotNull final String key, @NotNull final Object value) {
        if (properties.containsKey(key)) {
            properties.get(key).add(value);

        } else {
            ArrayList<Object> values = new ArrayList<>();
            values.add(value);
            properties.put(key, values);
        }
    }

    /**
     * Get set of values for a property.
     * @param key property
     * @return values
     */
    public final List<Object> getValues(@NotNull final String key) {
        return properties.get(key);
    }

    /**
     * Remove an entry.
     * @param key property
     * @return removed entry
     */
    public final List<Object> remove(@NotNull final String key) {
        return properties.remove(key);
    }

    @Override
    public String toString() {
        return AnnotationUtil.getAnnotation(name, properties);
    }

}
