package ca.mikegabelmann.codegen.util;

public final class SimpleAnnotation {
    /** Expected value when testing. */
    public static final String EXPECTED = "@SimpleAnnotation(name = \"Name\")";

    @Override
    public String toString() {
        return EXPECTED;
    }

}
