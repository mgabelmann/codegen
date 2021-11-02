package ca.mikegabelmann.db.freemarker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.torque.SqlDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 *
 * @author mgabe
 */
public abstract class AbstractWrapper implements JavaClass {
    /** Logger. */
    private static final Logger LOG = LogManager.getLogger(AbstractWrapper.class);

    /** SQL mappings. */
    protected final Map<String, String> sqlMappings;


    /**
     * Constructor.
     * @param sqlMappings mappings
     */
    public AbstractWrapper(@NotNull Map<String, String> sqlMappings) {
        this.sqlMappings = sqlMappings;
    }

    /**
     * Get SQL mappings.
     * @return
     */
    public final Map<String, String> getSqlMappings() {
        return sqlMappings;
    }


    /**
     *
     * @param type
     * @return
     */
    public final String getSqlMapping(@NotNull String type) {
        return sqlMappings.get(type);
    }

    /**
     *
     * @param sqlDataType
     * @return
     */
    public final String getSqlMapping(@NotNull final SqlDataType sqlDataType) {
        return sqlMappings.get(sqlDataType.name());
    }

    /**
     * Get name of object, used for hashing.
     * @return name
     */
    public abstract String getName();

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
     * @param name
     * @return
     * @throws ClassNotFoundException
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
