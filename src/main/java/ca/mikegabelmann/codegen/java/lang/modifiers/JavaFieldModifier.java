package ca.mikegabelmann.codegen.java.lang.modifiers;

import ca.mikegabelmann.codegen.java.lang.JavaKeywords;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author mgabelmann
 */
public enum JavaFieldModifier implements JavaModifier {
    //accessability
    PUBLIC(JavaKeywords.PUBLIC, 1),
    PROTECTED(JavaKeywords.PROTECTED, 2),
    PACKAGE("", 5),
    PRIVATE(JavaKeywords.PRIVATE, 3),

    //modifiers
    STATIC(JavaKeywords.STATIC, 6),
    FINAL(JavaKeywords.FINAL, 9),
    TRANSIENT(JavaKeywords.TRANSIENT, 10),
    VOLATILE(JavaKeywords.VOLATILE, 11),
    ;

    JavaFieldModifier(final String modifier, final int order) {
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
