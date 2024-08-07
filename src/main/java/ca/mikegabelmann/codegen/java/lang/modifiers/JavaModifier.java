package ca.mikegabelmann.codegen.java.lang.modifiers;


public interface JavaModifier {

    /**
     * Get Java modifier.
     * @return modifier
     */
    String getModifier();

    /**
     * Get preferred order.
     * @return order
     */
    int getOrder();

}
