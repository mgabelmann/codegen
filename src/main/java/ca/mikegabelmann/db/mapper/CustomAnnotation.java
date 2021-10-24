package ca.mikegabelmann.db.mapper;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 *
 * @author mgabe
 */
public final class CustomAnnotation extends AbstractAnnotation {

    /**
     * Constructor.
     * @param name annotation name
     */
    public CustomAnnotation(@NotNull final String name) {
        super(name);
    }

    @Override
    public void addProperty(@NotNull final String key, @NotNull final List<Object> values) {
        super.addProperty(key, values);
    }

    @Override
    public void addProperty(@NotNull final String key, @NotNull final Object value) {
        super.addProperty(key, value);
    }

}
