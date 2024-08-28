package ca.mikegabelmann.codegen.java.lang;


import ca.mikegabelmann.codegen.java.lang.classbody.JavaType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;


public enum JavaPrimitive implements JavaType {
    BOOLEAN(boolean.class, JavaKeywords.BOOLEAN),
    BYTE(byte.class, JavaKeywords.BYTE),
    CHAR(char.class, JavaKeywords.CHAR),
    SHORT(short.class, JavaKeywords.SHORT),
    INT(int.class, JavaKeywords.INT),
    LONG(long.class, JavaKeywords.LONG),
    FLOAT(float.class, JavaKeywords.FLOAT),
    DOUBLE(double.class, JavaKeywords.DOUBLE),
    ;

    /**
     * Constructor.
     * @param clazz class
     * @param type primitive type
     */
    JavaPrimitive(@NotNull final Class<?> clazz, @NotNull final String type) {
        this.clazz = clazz;
        this.type = type;
    }

    private final Class<?> clazz;
    private final String type;

    public Class<?> getClazz() {
        return clazz;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getCanonicalName() {
        return clazz.getCanonicalName();
    }

    @Override
    public String getSimpleName() {
        return clazz.getSimpleName();
    }

    @Override
    public String toString() {
        return type;
    }

    /**
     *
     * @param type
     * @return
     */
    public static boolean isPrimitiveType(@NotNull final String type) {
        return Arrays.stream(JavaPrimitive.values()).anyMatch(a -> a.name().equalsIgnoreCase(type));
    }

}
