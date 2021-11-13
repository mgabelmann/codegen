package ca.mikegabelmann.db.mapper;

import ca.mikegabelmann.codegen.java.lang.classbody.JavaAnnotation;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author mgabe
 */
public class ColumnAnnotation extends JavaAnnotation {
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
        this.add(NAME, value);
    }

    public void setUnique(final boolean unique) {
        this.add(UNIQUE, unique);
    }

    public void setNullable(final boolean nullable) {
        this.add(NULLABLE, nullable);
    }

    public void setInsertable(final boolean insertable) {
        this.add(INSERTABLE, insertable);
    }

    public void setUpdatable(final boolean updatable) {
        this.add(UPDATABLE, updatable);
    }

    public void setColumnDefinition(@NotNull final String columnDefinition) {
        this.add(COLUMN_DEFINITION, columnDefinition);
    }

    public void setTable(@NotNull final String table) {
        this.add(TABLE, table);
    }

    public void setLength(final int length) {
        this.add(LENGTH, length);
    }

    public void setPrecision(final int precision) {
        this.add(PRECISION, precision);
    }

    public void setScale(final int scale) {
        this.add(SCALE, scale);
    }

}
