package ca.mikegabelmann.codegen.lang;

/**
 *
 * @author mgabe
 */
public enum JavaClassModifier {
    //accessability
    PUBLIC(JavaKeywords.PUBLIC),
    PROTECTED(JavaKeywords.PROTECTED),
    PACKAGE(""),
    PRIVATE(JavaKeywords.PRIVATE),

    //modifiers
    ABSTRACT(JavaKeywords.ABSTRACT),
    STATIC(JavaKeywords.STATIC),
    //SEALED(JavaKeywords.SEALED),
    //NON_SEALED(JavaKeywords.NON_SEALED),
    STRICTFP(JavaKeywords.STRICTFP),
    ;

    JavaClassModifier(final String modifier) {
        this.modifier = modifier;
    }

    private final String modifier;

    @Override
    public String toString() {
        return modifier;
    }

}
