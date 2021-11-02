package ca.mikegabelmann.codegen.java.lang.classbody;

import org.jetbrains.annotations.NotNull;

/**
 *
 * @author mgabelmann
 */
public class JavaReturnType extends AbstractJavaType {

    /**
     * Constructor.
     * @param type object/primitive type
     * @param name variable name
     */
    public JavaReturnType(@NotNull String type, @NotNull String name) {
        super(type, name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JavaReturnType{");
        sb.append("type='").append(type).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
