package ca.mikegabelmann.db.freemarker;

import ca.mikegabelmann.codegen.java.lang.JavaPrimitive;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.torque.SqlDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author mgabe
 */
public abstract class AbstractWrapper implements JavaClass {
    /** Logger. */
    private static final Logger LOG = LogManager.getLogger(AbstractWrapper.class);

    /** Java classes to import. */
    protected final Set<String> imports;

    /** Java package, default unless specified. */
    protected String packageName;


    /** Constructor. */
    public AbstractWrapper() {
        this.imports = new TreeSet<>();
        this.packageName = "";
    }

    public final Set<String> getImports() {
        return imports;
    }

    public final String getPackageName() {
        return packageName;
    }

    public final void setPackageName(@NotNull final String packageName) {
        this.packageName = packageName;
    }

    /**
     * Add fully qualified import.
     * @param importString
     */
    public final void addImport(@NotNull final String importString) {
        //do not add classes from java.lang.* since they are available globally, also avoid primitive types
        if (importString.startsWith("java.lang") || JavaPrimitive.isPrimitiveType(importString)) {
            LOG.debug("{} - import {} - ignored", this.getId(), importString);
            return;
        }

        //TODO: ignore items in the same package

        boolean added = imports.add(importString);

        if (LOG.isTraceEnabled()) {
            LOG.trace("{} - import {} - {}", this.getId(), importString, added ? "added" : "duplicate");
        }
    }

    /**
     *
     * @param importString
     * @return
     */
    public final String addTypedImport(@NotNull String importString) {
        this.addImport(importString);
        return importString.substring(importString.lastIndexOf('.') + 1);
    }

    /**
     * Get name of object. Unique for a Table, Unique for a Column within a Table, Unique for a Foreign Key.
     * or Unique Constraint.
     * @return name
     */
    public abstract String getName();

    /**
     * Get name used for hashing, varies for tables, columns and keys. Value may be the same as getName().
     * @return id for hashing
     * @see AbstractWrapper#getName()
     */
    public abstract String getId();

    /**
     * Is the object required.
     * @return
     */
    public abstract boolean isRequired();

    /**
     * Gather imports locally or from child classes.
     */
    public abstract Set<String> getAllImports();

    /**
     *
     * @param sqlDataType type to find
     * @return class
     */
    protected Class<?> getClass(@NotNull final SqlDataType sqlDataType) {
        return getClass(sqlDataType.name());
    }

    /**
     *
     * @param name name of class to find
     * @return class
     * @throws ClassNotFoundException error
     */
    protected Class<?> getClass(@NotNull final String name) {
        Class<?> o;

        try {
            o = Class.forName(name);

        } catch (ClassNotFoundException cnfe) {
            o = Object.class;

            LOG.error("{} is not a valid Java class, defaulting to {}", name, o);
        }

        return o;
    }

}
