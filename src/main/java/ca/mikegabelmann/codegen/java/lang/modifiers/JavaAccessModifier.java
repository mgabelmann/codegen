package ca.mikegabelmann.codegen.java.lang.modifiers;

import ca.mikegabelmann.codegen.java.lang.JavaKeywords;

/**
 * Class, Method and Variable accessibility.
 * @author mgabelmann
 */
public enum JavaAccessModifier implements JavaModifier {
    PUBLIC(JavaKeywords.PUBLIC, 1),
    PROTECTED(JavaKeywords.PROTECTED, 2),
    PACKAGE("", 5),
    PRIVATE(JavaKeywords.PRIVATE, 3),
    ;

    JavaAccessModifier(final String modifier, final int order) {
        this.modifier = modifier;
        this.order = order;
    }

    private final String modifier;
    private final int order;

    @Override
    public String getModifier() {
        return modifier;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return modifier;
    }

}
