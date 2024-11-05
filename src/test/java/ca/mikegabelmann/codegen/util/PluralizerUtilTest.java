package ca.mikegabelmann.codegen.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class PluralizerUtilTest {

    @Test
    void pluralize_null() {
        Assertions.assertEquals(null, PluralizerUtil.pluralize(null));
    }

    @Test
    void pluralize_short() {
        Assertions.assertEquals("T", PluralizerUtil.pluralize("T"));
    }

    @Test
    void pluralize_trim() {
        Assertions.assertEquals(" T ", PluralizerUtil.pluralize(" T "));
    }

    @ParameterizedTest
    @CsvSource({"roof,roofs","belief,beliefs","chef,chefs","chief,chiefs"})
    void pluralize_EF_or_F(String input, String expected) {
        Assertions.assertEquals(expected.toUpperCase(), PluralizerUtil.pluralize(input));
    }

    @ParameterizedTest
    @CsvSource({"wolf,wolves"})
    void pluralize_F_special_case1(String input, String expected) {
        Assertions.assertEquals(expected.toUpperCase(), PluralizerUtil.pluralize(input));
    }

    @ParameterizedTest
    @CsvSource({"city,cities","puppy,puppies"})
    void pluralize_Y_plus_consonant(String input, String expected) {
        Assertions.assertEquals(expected.toUpperCase(), PluralizerUtil.pluralize(input));
    }

    @ParameterizedTest
    @CsvSource({"ray,rays","boy,boys"})
    void pluralize_Y_plus_vowel(String input, String expected) {
        Assertions.assertEquals(expected.toUpperCase(), PluralizerUtil.pluralize(input));
    }

    @ParameterizedTest
    @CsvSource({"potato,potatoes","tomato,tomatoes"})
    void pluralize_O(String input, String expected) {
        Assertions.assertEquals(expected.toUpperCase(), PluralizerUtil.pluralize(input));
    }

    @ParameterizedTest
    @CsvSource({"photo,photos","piano,pianos","halo,halos"})
    void pluralize_O_special_cases(String input, String expected) {
        Assertions.assertEquals(expected.toUpperCase(), PluralizerUtil.pluralize(input));
    }

    @ParameterizedTest
    @CsvSource({"cactus,cacti", "focus,foci"})
    void pluralize_US(String input, String expected) {
        Assertions.assertEquals(expected.toUpperCase(), PluralizerUtil.pluralize(input));
    }

    @ParameterizedTest
    @CsvSource({"analysis,analyses", "ellipsis,ellipses"})
    void pluralize_IS(String input, String expected) {
        Assertions.assertEquals(expected.toUpperCase(), PluralizerUtil.pluralize(input));
    }

    @ParameterizedTest
    @CsvSource({"phenomenon,phenomena", "criterion,criteria"})
    void pluralize_ON(String input, String expected) {
        Assertions.assertEquals(expected.toUpperCase(), PluralizerUtil.pluralize(input));
    }

    @ParameterizedTest
    @CsvSource({"child,children", "goose,geese", "man,men", "woman,women", "tooth,teeth", "foot,feet", "mouse,mice", "person,people"})
    void pluralize_Irregular(String input, String expected) {
        Assertions.assertEquals(expected.toUpperCase(), PluralizerUtil.pluralize(input));
    }

    @ParameterizedTest
    @CsvSource({"bus,busses", "fez,fezzes"})
    void pluralize_S_or_Z(String input, String expected) {
        Assertions.assertEquals(expected.toUpperCase(), PluralizerUtil.pluralize(input));
    }

    @ParameterizedTest
    @CsvSource({"iris,irises","truss,trusses","marsh,marshes","lunch,lunches","tax,taxes","blitz,blitzes"})
    void pluralize_S_SS_SH_CH_X_Z(String input, String expected) {
        Assertions.assertEquals(expected.toUpperCase(), PluralizerUtil.pluralize(input));
    }

    @ParameterizedTest
    @CsvSource({"wife,wives"})
    void pluralize_exceptions(String input, String expected) {
        Assertions.assertEquals(expected.toUpperCase(), PluralizerUtil.pluralize(input));
    }

    @ParameterizedTest
    @CsvSource({"cat,cats","house,houses","movie,movies","test,tests"})
    void pluralize_regular_nouns(String input, String expected) {
        Assertions.assertEquals(expected.toUpperCase(), PluralizerUtil.pluralize(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"sheep", "series", "species", "deer", "moose", "fish"})
    void pluralize_NoChange(String input) {
        Assertions.assertEquals(input.toUpperCase(), PluralizerUtil.pluralize(input));
    }

}