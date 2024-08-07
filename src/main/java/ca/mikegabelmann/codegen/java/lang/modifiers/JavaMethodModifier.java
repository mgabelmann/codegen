package ca.mikegabelmann.codegen.java.lang.modifiers;

import ca.mikegabelmann.codegen.java.lang.JavaKeywords;

/**
 *
 * @author mgabe
 */
public enum JavaMethodModifier implements JavaModifier {
    //accessability
    PUBLIC(JavaKeywords.PUBLIC,1),
    PROTECTED(JavaKeywords.PROTECTED, 2),
    PACKAGE("", 5),
    PRIVATE(JavaKeywords.PRIVATE, 3),

    //modifiers
    ABSTRACT(JavaKeywords.ABSTRACT, 4),
    STATIC(JavaKeywords.STATIC, 6),
    FINAL(JavaKeywords.FINAL, 9),
    SYNCHRONIZED(JavaKeywords.SYNCHRONIZED, 12),
    NATIVE(JavaKeywords.NATIVE, 13),
    STRICTFP(JavaKeywords.STRICTFP, 14),
    ;


    JavaMethodModifier(final String modifier, final int order) {
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
