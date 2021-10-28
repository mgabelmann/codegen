package ca.mikegabelmann.db.freemarker;

import ca.mikegabelmann.codegen.lang.JavaArgument;
import ca.mikegabelmann.codegen.lang.JavaReturnType;
import org.apache.torque.ColumnType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 *
 * @author mgabe
 */
public class ColumnWrapper {

    private ColumnType columnType;
    private Map<String, String> sqlMappings;

    /**
     * Constructor.
     * @param columnType
     * @param sqlMappings
     */
    public ColumnWrapper(
            @NotNull final ColumnType columnType,
            @NotNull final Map<String, String> sqlMappings) {

        this.columnType = columnType;
        this.sqlMappings = sqlMappings;
    }

    /**
     *
     * @return
     */
    public ColumnType getColumnType() {
        return columnType;
    }

    /**
     * Returns just the
     * @return
     * @see Class#getSimpleName()
     */
    public String getJavaSimpleName() {
        String type = sqlMappings.get(columnType.getType().name());
        int index = type.lastIndexOf(".");

        return index >= 0 ? type.substring(index + 1) : type;
    }

    /**
     *
     * @return
     * @see Class#getCanonicalName()
     */
    public String getJavaCanonicalName() {
        return sqlMappings.get(columnType.getType().name());
    }

    /**
     *
     * @return
     */
    public JavaArgument getJavaArgument() {
        return new JavaArgument(true, this.getJavaSimpleName(), columnType.getJavaName());
    }

    /**
     *
     * @return
     */
    public JavaReturnType getJavaReturnType() {
        return new JavaReturnType(this.getJavaSimpleName(), columnType.getJavaName());
    }

    public String getJavaProperty() {
        return columnType.getJavaName();
    }

    //TODO: add import?

}
