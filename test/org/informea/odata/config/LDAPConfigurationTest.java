package org.informea.odata.config;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class LDAPConfigurationTest {


    LDAPConfiguration cfg;

    @Before
    public void before() {
        cfg = new LDAPConfiguration();
        cfg.setBaseDN("baseDN");
        cfg.setBindDN("bindDN");
        cfg.setHost("localhost");
        cfg.setMapping("one", "one-x");
        cfg.setMaxPageSize(233);
        cfg.setPassword("password");
        cfg.setPort(389);
        cfg.setUserBaseDN("userBaseDN");
        cfg.setUserQueryFilter("uid=*");
        cfg.setUsersQueryFilter("cn=*");
        cfg.setUseSSL(true);
        cfg.setUseTLS(true);
    }

    @Test
    public void testLDAPConfiguration() {
        assertNotNull(cfg);
    }

    @Test
    public void testEqualsObject() {
        LDAPConfiguration obj = new LDAPConfiguration();
        assertTrue(cfg.equals(cfg));
        assertFalse(cfg.equals(""));
        assertFalse(cfg.equals(null));
        assertFalse(cfg.equals(obj));

        obj.setBaseDN("baseDN");
        assertFalse(cfg.equals(obj));

        obj.setBindDN("bindDN");
        assertFalse(cfg.equals(obj));

        obj.setHost("localhost");
        assertFalse(cfg.equals(obj));

        obj.setMapping("one", "one-x");
        assertFalse(cfg.equals(obj));

        obj.setMaxPageSize(233);
        assertFalse(cfg.equals(obj));

        obj.setPassword("password");
        assertFalse(cfg.equals(obj));

        obj.setPort(389);
        assertFalse(cfg.equals(obj));

        obj.setUserBaseDN("userBaseDN");
        assertFalse(cfg.equals(obj));

        obj.setUserQueryFilter("uid=*");
        assertFalse(cfg.equals(obj));

        obj.setUsersQueryFilter("cn=*");
        assertFalse(cfg.equals(obj));

        obj.setUseSSL(true);
        assertFalse(cfg.equals(obj));

        obj.setUseTLS(true);
        assertTrue(cfg.equals(obj));

        obj.setBindDN("1");
        assertFalse(cfg.equals(obj));
        obj.setBindDN("bindDN");

        obj.setPassword("1");
        assertFalse(cfg.equals(obj));
        obj.setPassword("password");

        obj.setBaseDN("1");
        assertFalse(cfg.equals(obj));
        obj.setBaseDN("baseDN");

        obj.setUserBaseDN("1");
        assertFalse(cfg.equals(obj));
        obj.setUserBaseDN("userBaseDN");

        obj.setUserQueryFilter("1");
        assertFalse(cfg.equals(obj));
        obj.setUserQueryFilter("uid=*");

        obj.setMaxPageSize(1);
        assertFalse(cfg.equals(obj));
        obj.setMaxPageSize(233);

    }

    @Test
    public void testGetHost() {
        assertEquals("localhost", cfg.getHost());
    }

    @Test
    public void testGetPort() {
        assertEquals(389, cfg.getPort());
    }

    @Test
    public void testGetBindDN() {
        assertEquals("bindDN", cfg.getBindDN());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password", cfg.getPassword());
    }

    @Test
    public void testGetBaseDN() {
        assertEquals("baseDN", cfg.getBaseDN());
    }

    @Test
    public void testGetUsersQueryFilter() {
        assertEquals("cn=*", cfg.getUsersQueryFilter());
    }

    @Test
    public void testGetUserBaseDN() {
        assertEquals("userBaseDN", cfg.getUserBaseDN());
    }

    @Test
    public void testGetUserQueryFilter() {
        assertEquals("uid=*", cfg.getUserQueryFilter());
    }

    @Test
    public void testGetMappings() {
        @SuppressWarnings("serial")
        Map<String, String> m = new HashMap<String, String>() {{
            put("one", "one-x");
        }};
        assertEquals(m, cfg.getMappings());
    }

    @Test
    public void testGetMapping() {
        assertEquals("one-x", cfg.getMapping("one"));
        assertNull(cfg.getMapping("invalid"));
    }

    @Test
    public void testGetMaxPageSize() {
        assertEquals(233, cfg.getMaxPageSize());
    }

    @Test
    public void testIsUseSSL() {
        assertTrue(cfg.isUseSSL());
    }

    @Test
    public void testIsUseTLS() {
        assertTrue(cfg.isUseTLS());
    }

    @Test
    public void testClearMappings() {
        cfg.clearMappings();
        assertNull(cfg.getMapping("one"));
        Map<String, String> m = new HashMap<String, String>();
        assertEquals(m, cfg.getMappings());
    }
}
