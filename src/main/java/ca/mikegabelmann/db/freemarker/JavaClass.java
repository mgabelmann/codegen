package ca.mikegabelmann.db.freemarker;

/**
 *
 * @author mgabe
 */
public interface JavaClass {

    /**
     * Returns the class name along with the package path (if given).
     * @return [package.]class name
     * @see Class#getCanonicalName()
     */
    String getCanonicalName();

    /**
     * Returns just the Class name.
     * @return class name
     * @see Class#getSimpleName()
     */
    String getSimpleName();

    /**
     * Returns the variable name of the field/property.
     * @return variable name
     */
    String getVariableName();

}
