package ca.mikegabelmann.codegen.java.lang.classbody;

import ca.mikegabelmann.codegen.java.lang.JavaKeywords;
import ca.mikegabelmann.codegen.java.lang.JavaTokens;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author mgabe
 */
public class JavaArgument extends AbstractJavaTypeAnnotated {

    private boolean isFinal;


    public JavaArgument(@NotNull String type, @NotNull String name) {
        this(type, name, true);
    }

    public JavaArgument(@NotNull String type, @NotNull String name, boolean isFinal) {
        super(type, name);
        this.isFinal = isFinal;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (JavaAnnotation annotation : annotations) {
            sb.append(annotation.toString());
            sb.append(JavaTokens.SPACE);
        }

        if (isFinal) {
            sb.append(JavaKeywords.FINAL.toString());
        }

        sb.append(type);
        sb.append(JavaTokens.SPACE);
        sb.append(name);

        return sb.toString();
    }
}
