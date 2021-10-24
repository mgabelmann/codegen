package ca.mikegabelmann.codegen.lang;

/**
 *
 * @author mgabelmann
 */
public enum JavaAccessModifier {
    PUBLIC(JavaKeywords.PUBLIC),
    PROTECTED(JavaKeywords.PROTECTED),
    PACKAGE(""),
    PRIVATE(JavaKeywords.PRIVATE),
    ;


    JavaAccessModifier(final String modifier) {
        this.modifier = modifier;
    }

    private final String modifier;

    @Override
    public String toString() {
        return modifier;
    }
}
