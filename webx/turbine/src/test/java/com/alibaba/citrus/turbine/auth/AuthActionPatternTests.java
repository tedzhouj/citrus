package com.alibaba.citrus.turbine.auth;

import static org.junit.Assert.*;

import org.junit.Test;

import com.alibaba.citrus.turbine.auth.impl.AuthActionPattern;
import com.alibaba.citrus.turbine.auth.impl.AuthPattern;

public class AuthActionPatternTests {
    private AuthActionPattern pattern;

    @Test(expected = IllegalArgumentException.class)
    public void create_noname1() {
        new AuthActionPattern(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_noname2() {
        new AuthActionPattern(" ");
    }

    @Test
    public void getPatternName() {
        pattern = new AuthActionPattern("test");
        assertEquals("test", pattern.getPatternName());
    }

    @Test
    public void getPattern() {
        // relative path
        pattern = new AuthActionPattern("test");

        assertMatches(false, "a.b.test");
        assertMatches(false, "a.test.");
        assertMatches(false, "a.test.b");
        assertMatches(true, "test");
        assertMatches(true, "test.");
        assertMatches(true, "test.b");

        assertMatches(false, "atest");
        assertMatches(false, "testb");
        assertMatches(false, "atestb");

        // abs path
        pattern = new AuthActionPattern("t.est");

        assertMatches(false, "a.b.t.est");
        assertMatches(false, "a.t.est.");
        assertMatches(false, "a.t.est.b");
        assertMatches(true, "t.est");
        assertMatches(true, "t.est.");
        assertMatches(true, "t.est.b");

        assertMatches(false, "at.est");
        assertMatches(false, "t.estb");
        assertMatches(false, "at.estb");
    }

    private void assertMatches(boolean matches, String s) {
        assertEquals(s, matches, pattern.matcher(s).find());
        assertEquals(s, matches, pattern.getPattern().matcher(s).find());
    }

    @Test
    public void equalsAndHashCode() {
        AuthPattern p1 = new AuthActionPattern("test");
        AuthPattern p2 = new AuthActionPattern("test");
        AuthPattern p3 = new AuthActionPattern("*.test");

        assertEquals(p1, p1);
        assertEquals(p1, p2);
        assertFalse(p1.equals(p3));
        assertFalse(p1.equals(null));
        assertFalse(p1.equals("test"));

        assertEquals(p1.hashCode(), p2.hashCode());
        assertFalse(p1.hashCode() == p3.hashCode());
    }

    @Test
    public void toString_() {
        assertEquals("test", new AuthActionPattern(" test ").toString());
    }
}