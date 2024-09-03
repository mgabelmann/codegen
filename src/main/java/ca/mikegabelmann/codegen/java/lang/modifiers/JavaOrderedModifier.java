package ca.mikegabelmann.codegen.java.lang.modifiers;

import java.util.List;
import java.util.Set;


public interface JavaOrderedModifier<E extends JavaModifier> {

    /**
     * Get all modifiers.
     * @return modifiers.
     */
    Set<E> getModifiers();

    /**
     * Add a modifier.
     * @param modifier
     */
    void addModifier(E modifier);

    /**
     * Add one or more modifiers.
     * @param modifiers
     */
    void addModifiers(E... modifiers);

    /**
     * Remove a modifier.
     * @param modifier
     */
    boolean removeModifier(E modifier);

    /**
     * Get list of ordered modifiers.
     * @return ordered modifiers
     */
    List<E> getOrderedModifiers();

}
