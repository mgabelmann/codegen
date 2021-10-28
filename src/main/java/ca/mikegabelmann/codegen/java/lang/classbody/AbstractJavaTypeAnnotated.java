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
    protected Set<JavaAnnotation> annotations;


    /**
     * Constructor.
     * @param type class or primitive type
     * @param name field or variable name
     */
    AbstractJavaTypeAnnotated(@NotNull String type, @NotNull String name) {
        super(type, name);
        this.annotations = new LinkedHashSet<>();

    }

    public final Set<JavaAnnotation> getAnnotations() {
        return annotations;
    }

    public final void addAnnotation(@NotNull JavaAnnotation annotation) {
        this.annotations.add(annotation);
    }

    public final boolean removeAnnotation(@NotNull JavaAnnotation annotation) {
        return this.annotations.remove(annotation);
    }

/*    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (JavaAnnotation annotation : annotations) {
            sb.append(annotation.toString());
        }

        sb.append(super.toString());

        return sb.toString();
    }*/

}
