package ca.mikegabelmann.db.freemarker;

import ca.mikegabelmann.codegen.java.lang.JavaPrimitive;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaClassModifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.torque.SqlDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author mgabe
 */
public abstract class AbstractWrapper implements JavaClass {
    /** Logger. */
    private static final Logger LOG = LogManager.getLogger(AbstractWrapper.class);

    /** SQL mappings. */
    protected final Map<String, String> sqlMappings;

    /** Java classes to import. */
    protected final Set<String> imports;

    /** Java package, default unless specified. */
    protected String packageName;


    /**
     * Constructor.
     * @param sqlMappings mappings
     */
    public AbstractWrapper(@NotNull Map<String, String> sqlMappings) {
        this.sqlMappings = sqlMappings;
        this.imports = new TreeSet<>();
        this.packageName = "";
    }

    public final Map<String, String> getSqlMappings() {
        return sqlMappings;
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

    public final String getSqlMapping(@NotNull String type) {
        return sqlMappings.get(type);
    }

    public final String getSqlMapping(@NotNull final SqlDataType sqlDataType) {
        return sqlMappings.get(sqlDataType.name());
    }

    /**
     * Add fully qualified import
     * @param importString
     */
    public final void addImport(@NotNull String importString) {
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
     *
     * @param sqlDataType
     * @return
     */
    protected Class<?> getClass(@NotNull final SqlDataType sqlDataType) {
        return getClass(getSqlMapping(sqlDataType));
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
