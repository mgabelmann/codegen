package ca.mikegabelmann.codegen.util;


public class StringAsClass {
    private final String string;

    public StringAsClass(String string) {
        this.string = string;
    }

    public StringAsClass(final Class clazz, boolean simple) {
        this.string = simple ? clazz.getSimpleName() : clazz.getCanonicalName();
    }

    public String getString() {
        return string;
    }

    @Override
    public String toString() {
        return "StringAsClass{" +
                "string='" + string + '\'' +
                '}';
    }

}
