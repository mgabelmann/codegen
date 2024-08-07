package ca.mikegabelmann.codegen.java.lang.modifiers;

import ca.mikegabelmann.codegen.java.lang.JavaKeywords;

/**
 *
 * @author mgabe
 */
public enum JavaClassModifier implements JavaModifier {
    //accessability
    PUBLIC(JavaKeywords.PUBLIC, 1),
    PROTECTED(JavaKeywords.PROTECTED, 2),
    PACKAGE("", 5),
    PRIVATE(JavaKeywords.PRIVATE, 3),

    //modifiers
    ABSTRACT(JavaKeywords.ABSTRACT, 4),
    STATIC(JavaKeywords.STATIC, 6),
    //SEALED(JavaKeywords.SEALED, 7),
    //NON_SEALED(JavaKeywords.NON_SEALED, 8),
    STRICTFP(JavaKeywords.STRICTFP, 14),
    ;

    JavaClassModifier(final String modifier, final int order) {
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
