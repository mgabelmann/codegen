package ca.mikegabelmann.codegen.java.lang.classbody;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
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
    AbstractJavaTypeAnnotated(@NotNull final String type, @NotNull final String name) {
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
     * Add one or more annotations.
     * @param annotations annotations to add
     */
    public final void addAnnotations(@NotNull final JavaAnnotation... annotations) {
        this.annotations.addAll(Arrays.asList(annotations));
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
