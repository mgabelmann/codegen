package ca.mikegabelmann.codegen.lang;

/**
 *
 * @author mgabelmann
 */
public enum JavaFieldModifier {
    //accessability
    PUBLIC(JavaKeywords.PUBLIC),
    PROTECTED(JavaKeywords.PROTECTED),
    PACKAGE(""),
    PRIVATE(JavaKeywords.PRIVATE),

    //modifiers
    STATIC(JavaKeywords.STATIC),
    FINAL(JavaKeywords.FINAL),
    TRANSIENT(JavaKeywords.TRANSIENT),
    VOLATILE(JavaKeywords.VOLATILE),
    ;

    JavaFieldModifier(final String modifier) {
        this.modifier = modifier;
    }

    private final String modifier;

    @Override
    public String toString() {
        return modifier;
    }
}
