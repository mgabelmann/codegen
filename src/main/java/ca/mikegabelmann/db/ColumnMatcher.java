package ca.mikegabelmann.db;

import ca.mikegabelmann.codegen.util.StringUtil;
import ca.mikegabelmann.db.mapping.Mapping;

import java.util.ArrayList;
import java.util.List;


/**
 * Helper class to contain and validate mappings against database values. Mappings must be ordered from most specific
 * to least specific since first match wins.
 * @author mgabe
 */
public final class ColumnMatcher {
    private final List<Mapping> mappings;


    /** Constructor. */
    public ColumnMatcher() {
        this.mappings = new ArrayList<>();
    }

    /**
     *  Add a mapping.
     * @param mapping value to add
     */
    public void addMapping(final Mapping mapping) {
        this.mappings.add(mapping);
    }

    /**
     * Add one or more mappings.
     * @param mappings values to add
     */
    public void addMappings(final List<Mapping> mappings) {
        this.mappings.addAll(mappings);
    }

    /**
     * Remove a mapping.
     * @param mapping value to remove
     */
    public void removeMapping(final Mapping mapping) {
        this.mappings.remove(mapping);
    }

    /**
     * Finds the first mapping that matches given values or returns null if none matched.
     * @param databaseType database SQL type
     * @param length length for non-numeric fields
     * @param precision precision for numeric fields
     * @param scale scale for numeric fields
     * @param name value or valid regular expression
     * @return null or mapping that matched
     */
    public Mapping matchMapping(final String databaseType, final Integer length, final Integer precision, final Integer scale, final String name) {
        for (Mapping mapping : mappings) {
            boolean databaseTypeMatch = false;
            boolean lengthMatch = false;
            boolean precisionMatch = false;
            boolean scaleMatch = false;
            boolean regexMatch = false;

            String mappingDatabaseType = mapping.getDatabaseType();
            if (StringUtil.isBlankOrNull(mappingDatabaseType) || (databaseType != null && databaseType.toUpperCase().equals(mappingDatabaseType))) {
                databaseTypeMatch = true;
            }

            Integer mappingLength = mapping.getLength();
            if (mappingLength == null || (length != null && length.equals(mappingLength))) {
                lengthMatch = true;
            }

            Integer mappingPrecision = mapping.getPrecision();
            if (mappingPrecision == null || (precision != null && precision.equals(mappingPrecision))) {
                precisionMatch = true;
            }

            Integer mappingScale = mapping.getScale();
            if (mappingScale == null || (scale != null && scale.equals(mappingScale))) {
                scaleMatch = true;
            }

            String mappingName = mapping.getName();
            if (StringUtil.isBlankOrNull(mappingName) || (name != null && name.toUpperCase().matches(mappingName.toUpperCase()))) {
                regexMatch = true;
            }

            if (databaseTypeMatch && lengthMatch && precisionMatch && scaleMatch && regexMatch) {
                return mapping;
            }
        }

        return null;
    }

}
