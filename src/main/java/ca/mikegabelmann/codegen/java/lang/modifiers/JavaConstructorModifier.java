package ca.mikegabelmann.codegen.java.lang.modifiers;

import ca.mikegabelmann.codegen.java.lang.JavaKeywords;

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
