package ca.mikegabelmann.codegen.java.lang.classbody;


public interface JavaType {
    /**
     * Returns the class name (if applicable) along with the package path (if given).
     * @return [package.]class name or ""
     * @see Class#getCanonicalName()
     */
    String getCanonicalName();

    /**
     * Returns just the Class name.
     * @return class name or ""
     * @see Class#getSimpleName()
     */
    String getSimpleName();

}
