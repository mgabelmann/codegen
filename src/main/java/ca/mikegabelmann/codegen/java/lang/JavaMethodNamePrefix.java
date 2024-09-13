package ca.mikegabelmann.codegen.java.lang;

import org.jetbrains.annotations.NotNull;


/**
 *
 * @author mgabe
 */
public enum JavaMethodNamePrefix {
    IS("is"),
    HAS("has"),
    GET("get"),
    SET("set"),
    NONE(""),
    ;


    /**
     * Constructor.
     * @param prefix method prefix
     */
    JavaMethodNamePrefix(@NotNull final String prefix) {
        this.prefix = prefix;
    }

    /** Prefix name. */
    private final String prefix;

    @Override
    public String toString() {
        return prefix;
    }
}
