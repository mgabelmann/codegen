package ca.mikegabelmann.codegen.java.lang.classbody;


public interface JavaType {

    /**
     * Return type.
     * @return type
     */
    String getType();

    /**
     * Returns name.
     * @return name
     */
    String getName();

    /**
     * Returns the class name (if applicable) along with the package path (if given). Based on type.
     * @return [package.]class name or ""
     * @see Class#getCanonicalName()
     */
    String getCanonicalName();

    /**
     * Returns just the Class name. Based on type.
     * @return class name or ""
     * @see Class#getSimpleName()
     */
    String getSimpleName();

}
