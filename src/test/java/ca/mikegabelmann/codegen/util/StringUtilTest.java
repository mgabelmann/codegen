package ca.mikegabelmann.codegen.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class StringUtilTest {

    @Test
    public void test1_initCap() {
        Assertions.assertNull(StringUtil.initCap(null));
    }

    @Test
    public void test2_initCap() {
        Assertions.assertEquals("G", StringUtil.initCap("g"));
    }

    @Test
    public void test3_initCap() {
        Assertions.assertEquals("Goodvsevil", StringUtil.initCap("goodVsEvil"));
    }

    @Test
    public void test4_initCap() {
        Assertions.assertEquals("Good Vs Evil", StringUtil.initCap("good vs evil"));
    }

    @Test
    public void test5_initCap() {
        Assertions.assertEquals("Good_vs_evil", StringUtil.initCap("good_VS_evil"));
    }

    @Test
    public void test1_initLower() {
        Assertions.assertNull(StringUtil.initLower(null));
        Assertions.assertEquals("", StringUtil.initLower(""));
        Assertions.assertEquals("a", StringUtil.initLower("a"));
        Assertions.assertEquals("a", StringUtil.initLower("A"));
        Assertions.assertEquals("aA", StringUtil.initLower("AA"));
    }

    @Test
    public void test1_isBlankOrNull() {
        Assertions.assertTrue(StringUtil.isBlankOrNull(null));
        Assertions.assertTrue(StringUtil.isBlankOrNull(""));
        Assertions.assertTrue(StringUtil.isBlankOrNull(" "));
        Assertions.assertFalse(StringUtil.isBlankOrNull("a"));
        Assertions.assertFalse(StringUtil.isBlankOrNull(" a "));
    }

    @Test
    public void test1_isNotBlankOrNull() {
        Assertions.assertFalse(StringUtil.isNotBlankOrNull(null));
        Assertions.assertFalse(StringUtil.isNotBlankOrNull(""));
        Assertions.assertFalse(StringUtil.isNotBlankOrNull(" "));
        Assertions.assertTrue(StringUtil.isNotBlankOrNull("a"));
        Assertions.assertTrue(StringUtil.isNotBlankOrNull(" a "));
    }
}