package ca.mikegabelmann.db;


import ca.mikegabelmann.codegen.util.StringUtil;
import ca.mikegabelmann.db.mapping.Mapping;

import java.util.ArrayList;
import java.util.List;


public final class ColumnMatcher {
    private final List<Mapping> mappings;


    /** Constructor. */
    public ColumnMatcher() {
        this.mappings = new ArrayList<>();
    }

    /**
     *
     * @param mapping
     */
    public void addMapping(final Mapping mapping) {
        this.mappings.add(mapping);
    }

    /**
     *
     * @param mappings
     */
    public void addMappings(final List<Mapping> mappings) {
        this.mappings.addAll(mappings);
    }

    /**
     *
     * @param mapping
     */
    public void removeMapping(final Mapping mapping) {
        this.mappings.remove(mapping);
    }

    /**
     *
     * @param databaseType
     * @param length
     * @param precision
     * @param scale
     * @param name
     * @return
     */
    public Mapping matchMapping(final String databaseType, final Integer length, final Integer precision, final Integer scale, final String name) {
        for (Mapping mapping : mappings) {
            boolean databaseTypeMatch = false;
            boolean lengthMatch = false;
            boolean precisionMatch = false;
            boolean scaleMatch = false;
            boolean regexMatch = false;

            if (StringUtil.isNotBlankOrNull(mapping.getDatabaseType())) {
                if (databaseType != null && databaseType.equals(mapping.getDatabaseType())) {
                    databaseTypeMatch = true;
                }
            } else {
                databaseTypeMatch = true;
            }

            if (mapping.getLength() != null) {
                if (length != null && length.equals(mapping.getLength())) {
                    lengthMatch = true;
                }
            } else {
                lengthMatch = true;
            }

            if (mapping.getPrecision() != null) {
                if (precision != null && precision.equals(mapping.getPrecision())) {
                    precisionMatch = true;
                }
            } else {
                precisionMatch = true;
            }

//            if (mapping.getScale() != null) {
//                if (scale != null && scale.equals(mapping.getScale())) {
//                    scaleMatch = true;
//                }
//            } else {
//                scaleMatch = true;
//            }

            if (mapping.getScale() == null || (mapping.getScale() != null && scale != null && scale.equals(mapping.getScale()))) {
                scaleMatch = true;
            }

            if (StringUtil.isNotBlankOrNull(mapping.getName())) {
                if (name != null && name.toUpperCase().matches(mapping.getName().toUpperCase())) {
                    regexMatch = true;
                }
            } else {
                regexMatch = true;
            }

            if (databaseTypeMatch && lengthMatch && precisionMatch && scaleMatch && regexMatch) {
                return mapping;
            }
        }

        return null;
    }

}
