package ca.mikegabelmann.codegen.java.lang.modifiers;

import ca.mikegabelmann.codegen.java.lang.JavaKeywords;

/**
 * Class, Method and Variable accessibility.
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
