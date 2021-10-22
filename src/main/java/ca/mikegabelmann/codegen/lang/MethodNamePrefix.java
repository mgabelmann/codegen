package ca.mikegabelmann.codegen.lang;

/**
 *
 * @author mgabe
 */
public enum MethodNamePrefix {
    IS("is"),
    HAS("has"),
    GET("get"),
    SET("set"),
    NONE("")
    ;


    /**
     * Constructor.
     * @param prefix method prefix
     */
    MethodNamePrefix(final String prefix) {
        this.prefix = prefix;
    }

    /** Prefix name. */
    private String prefix;

    @Override
    public String toString() {
        return prefix;
    }
}
