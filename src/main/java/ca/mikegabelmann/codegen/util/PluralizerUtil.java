package ca.mikegabelmann.codegen.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class PluralizerUtil {
    private static final Logger LOG = LogManager.getLogger(PluralizerUtil.class);

    private static final HashMap<String, String> irregularNouns = new HashMap<>();
    static {
        irregularNouns.put("CHILD", "CHILDREN");
        irregularNouns.put("GOOSE", "GEESE");
        irregularNouns.put("MAN", "MEN");
        irregularNouns.put("WOMAN", "WOMEN");
        irregularNouns.put("TOOTH", "TEETH");
        irregularNouns.put("FOOT", "FEET");
        irregularNouns.put("MOUSE", "MICE");
        irregularNouns.put("PERSON", "PEOPLE");
    }

    /** These do not have plural versions and will break if used. */
    private static final HashMap<String, String> noChange = new HashMap<>();
    static {
        noChange.put("SHEEP", "SHEEP");
        noChange.put("SERIES", "SERIES");
        noChange.put("SPECIES", "SPECIES");
        noChange.put("DEER", "DEER");
        noChange.put("FISH", "FISH"); //plural can be FISHES in some cases, but sounds stupid
        noChange.put("MOOSE", "MOOSE");
    }

    private static final HashMap<String, String> exceptions = new HashMap<>();
    static {
        exceptions.put("WIFE", "WIVES");
    }

    private PluralizerUtil() {}

    public static String pluralize(String word) {
        if (word == null || word.trim().length() < 2) {
            return word;
        }

        String tmp = word.trim().toUpperCase();
        final int tmpLen = tmp.length();
        String last = tmp.substring(tmpLen - 1);
        String secondLast = tmp.substring(tmpLen - 2, tmpLen - 1);
        String lastTwo = tmp.substring(tmpLen - 2);

        if (irregularNouns.containsKey(tmp)) {
            //handle irregular nouns
            return irregularNouns.get(tmp);

        } else if (noChange.containsKey(tmp)) {
            //some nouns don't change
            return noChange.get(tmp);

        } else if (exceptions.containsKey(tmp)) {
            return exceptions.get(tmp);

        } else if (lastTwo.equals("ON")) {
            // phenomenon -> phenomena
            return tmp.substring(0, tmpLen - 2) + "A";

        } else if (lastTwo.equals("IS") && !tmp.equals("IRIS")) {
            //analysis -> analyses
            return tmp.substring(0, tmpLen - 2) + "ES";

        } else if (last.matches("[SZ]") && tmpLen <= 3) {
            //bus -> busses
            //fez -> fezzes
            return tmp + last + "ES";

        } else if (lastTwo.equals("US")) {
            //cactus -> cacti
            //focus -> foci
            return tmp.substring(0, tmpLen - 2) + "I";

        } else if (lastTwo.matches("(SS|SH|CH)") || (last.matches("[SXZ]") && tmpLen >= 3)) {
            //iris -> irises
            //truss -> trusses
            //marsh -> marshes
            //lunch -> lunches
            //tax -> taxes
            //blitz -> blitzes
            return tmp + "ES";

        } else if (last.equals("O")) {
            if (tmp.equals("PHOTO") || tmp.equals("PIANO") || tmp.equals("HALO")) {
                return tmp + "S";

            } else {
                //potato -> potatoes
                return tmp + "ES";
            }

        } else if (last.equals("Y") && secondLast.matches("[AEIOU]")) {
            //ray -> rays
            return tmp + "S";

        } else if (last.equals("Y") && secondLast.matches("[^AEIOU]")) {
            //city -> cities
            return tmp.substring(0, tmpLen - 1) + "IES";

        } else if (lastTwo.equals("EF") || last.equals("F")) {
            if (tmp.equals("WOLF")) {
                return "WOLVES";

            } else {
                //roof -> roofs
                return tmp + "S";
            }

        } else {
            //default rule
            LOG.debug("no plural match found for {}", tmp);
            return tmp + "S";
        }
    }

}
