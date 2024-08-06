package ca.mikegabelmann.codegen.java.lang.classbody;

import org.jetbrains.annotations.NotNull;


public class JavaImport extends AbstractJavaType {

    /**
     * Constructor.
     * @param type
     */
    public JavaImport(@NotNull String type) {
        super(type, "");
    }

//    public String getImport() {
//        return JavaKeywords.IMPORT + this.getType() + JavaTokens.SEMICOLON;
//    }

    @Override
    public String toString() {
        return "JavaImport{" +
                "name='" + name + '\'' +
                '}';
    }
}
