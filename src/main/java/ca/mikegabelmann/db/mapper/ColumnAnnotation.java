package ca.mikegabelmann.db.mapper;


import org.jetbrains.annotations.NotNull;

/**
 *
 * @author mgabe
 */
public class ColumnAnnotation extends AbstractAnnotation {
    public static final String NAME = "name";
    public static final String UNIQUE = "unique";
    public static final String NULLABLE = "nullable";
    public static final String INSERTABLE = "insertable";
    public static final String UPDATABLE = "updatable";
    public static final String COLUMN_DEFINITION = "columnDefinition";
    public static final String TABLE = "table";
    public static final String LENGTH = "length";
    public static final String PRECISION = "precision";
    public static final String SCALE = "scale";


    /** Constructor. */
    public ColumnAnnotation() {
        super("Column");
    }

    public void setName(@NotNull final String value) {
        this.addProperty(NAME, value);
    }

    public void setUnique(final boolean unique) {
        this.addProperty(UNIQUE, unique);
    }

    public void setNullable(final boolean nullable) {
        this.addProperty(NULLABLE, nullable);
    }

    public void setInsertable(final boolean insertable) {
        this.addProperty(INSERTABLE, insertable);
    }

    public void setUpdatable(final boolean updatable) {
        this.addProperty(UPDATABLE, updatable);
    }

    public void setColumnDefinition(@NotNull final String columnDefinition) {
        this.addProperty(COLUMN_DEFINITION, columnDefinition);
    }

    public void setTable(@NotNull final String table) {
        this.addProperty(TABLE, table);
    }

    public void setLength(final int length) {
        this.addProperty(LENGTH, length);
    }

    public void setPrecision(final int precision) {
        this.addProperty(PRECISION, precision);
    }

    public void setScale(final int scale) {
        this.addProperty(SCALE, scale);
    }

}
