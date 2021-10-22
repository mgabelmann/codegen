package ca.mikegabelmann.codegen.util;

import org.jetbrains.annotations.Contract;

/**
 *
 * @author mgabe
 */
public class StringUtil {

    /** Do not instantiate this class. */
    private StringUtil() {}

    /**
     * Determine if the given String is blank or null.
     * @param arg value to check
     * @return true if blank or null
     */
    @Contract("null -> true")
    public static boolean isBlankOrNull(final String arg) {
        return (arg == null || arg.replaceAll("\\s", "").length() == 0);
    }

    public static boolean isNotBlankOrNull(final String arg) {
        return ! StringUtil.isBlankOrNull(arg);
    }

    /**
     *
     * @param value
     * @return
     */
    public static String initCap(final String value) {
        if (value == null) {
            return null;

        } else if (value.length() == 1) {
            return value.toUpperCase();

        } else {
            return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
        }
    }

    /**
     *
     * @param value
     * @return
     */
    public static String initLower(final String value) {
        if (value == null) {
            return null;

        } else if (value.length() <= 1) {
            return value.toLowerCase();

        } else {
            return value.substring(0, 1).toLowerCase() + value.substring(1);
        }
    }
}
