package ca.mikegabelmann.codegen.java.lang.classbody;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author mgabe
 */
abstract class AbstractJavaTypeAnnotated extends AbstractJavaType {
    /** Annotations. */
    protected final Set<JavaAnnotation> annotations;


    /**
     * Constructor.
     * @param type class or primitive type
     * @param name field or variable name
     */
    AbstractJavaTypeAnnotated(@NotNull String type, @NotNull String name) {
        super(type, name);
        this.annotations = new LinkedHashSet<>();

    }

    /**
     * Get Java annotations.
     * @return annotations
     */
    public final Set<JavaAnnotation> getAnnotations() {
        return annotations;
    }

    /**
     * Add Java annotation.
     * @param annotation annotation to add
     */
    public final void addAnnotation(@NotNull JavaAnnotation annotation) {
        this.annotations.add(annotation);
    }

    /**
     * Remove Java annotation.
     * @param annotation annotation to remove
     * @return true if removed, false otherwise
     */
    public final boolean removeAnnotation(@NotNull JavaAnnotation annotation) {
        return this.annotations.remove(annotation);
    }

}
