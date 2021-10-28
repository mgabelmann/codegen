package ca.mikegabelmann.codegen.java.lang.classbody;

/**
 *
 * @author mgabe
 */
public enum JavaMethodNamePrefix {
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
    JavaMethodNamePrefix(final String prefix) {
        this.prefix = prefix;
    }

    /** Prefix name. */
    private final String prefix;

    @Override
    public String toString() {
        return prefix;
    }
}
