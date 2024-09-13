package ca.mikegabelmann.codegen.java.lang.classbody;

import ca.mikegabelmann.codegen.Printable;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author mgabe
 */
abstract class AbstractJavaType implements JavaType, Printable {
    /** Class or primitive type. */
    protected final String type;

    /** field, method, class or variable name. */
    protected final String name;


    /**
     * Constructor.
     * @param type class or primitive type
     * @param name field or variable name
     */
    AbstractJavaType(@NotNull final String type, @NotNull final String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public final String getType() {
        return type;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final String getCanonicalName() {
        return type;
    }

    @Override
    public final String getSimpleName() {
        return !type.contains(".") ? type : type.substring(type.lastIndexOf('.') + 1);
    }

}
