package org.informea.odata;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConfigurationTest {

    @Test
    public void testGetInstance() {
        Configuration cfg = Configuration.getInstance();
        assertNotNull(cfg);
    }

    @Test
    public void testGetSetString() {
        Configuration cfg = Configuration.getInstance();
        assertEquals("", cfg.getString("invalid.key"));
        cfg.setString("one", "one");
        assertEquals("one", cfg.getString("one"));
    }

    @Test
    public void testGetSetBoolean() {
        Configuration cfg = Configuration.getInstance();
        assertFalse(cfg.getBoolean("invalid.key"));
        cfg.setBoolean("true", true);
        assertTrue("true", cfg.getBoolean("true"));
    }

    @Test
    public void testGetSetInt() {
        Configuration cfg = Configuration.getInstance();
        assertEquals(0, cfg.getInt("invalid.key"));
        cfg.setInt("six-hundred-sixty-six", 666);
        assertEquals(666, cfg.getInt("six-hundred-sixty-six"));
    }
}
