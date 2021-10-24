package ca.mikegabelmann.codegen.lang;

/**
 *
 * @author mgabe
 */
public enum JavaMethodModifier {
    //accessability
    PUBLIC(JavaKeywords.PUBLIC),
    PROTECTED(JavaKeywords.PROTECTED),
    PACKAGE(""),
    PRIVATE(JavaKeywords.PRIVATE),

    //modifiers
    ABSTRACT(JavaKeywords.ABSTRACT),
    STATIC(JavaKeywords.STATIC),
    FINAL(JavaKeywords.FINAL),
    SYNCHRONIZED(JavaKeywords.SYNCHRONIZED),
    NATIVE(JavaKeywords.NATIVE),
    STRICTFP(JavaKeywords.STRICTFP),
    ;


    JavaMethodModifier(final String modifier) {
        this.modifier = modifier;
    }

    private final String modifier;

    @Override
    public String toString() {
        return modifier;
    }
}
