package ca.mikegabelmann.codegen.lang;

/**
 *
 * @author mgabelmann
 */
public enum JavaConstructorModifier {
    //accessability
    PUBLIC(JavaKeywords.PUBLIC),
    PROTECTED(JavaKeywords.PROTECTED),
    PACKAGE(""),
    PRIVATE(JavaKeywords.PRIVATE),
    ;

    JavaConstructorModifier(final String modifier) {
        this.modifier = modifier;
    }

    private final String modifier;

    @Override
    public String toString() {
        return modifier;
    }

}
