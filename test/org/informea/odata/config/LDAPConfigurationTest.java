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
        cfg.setUsersBaseDN("baseDN");
        cfg.setBindDN("bindDN");
        cfg.setHost("localhost");
        cfg.setMapping("one", "one-x");
        cfg.setMaxPageSize(233);
        cfg.setPassword("password");
        cfg.setPort(389);
        cfg.setUserBaseDN("userBaseDN");
        cfg.setUserQueryFilter("userQueryFilter");
        cfg.setUsersBaseDN("usersBaseDN");
        cfg.setUsersQueryFilter("usersQueryFilter");
        cfg.setUseSSL(true);
        cfg.setUseTLS(true);
        cfg.setUserIdAttribute("userIdAttribute");
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

        obj.setUsersBaseDN("usersBaseDN");
        obj.setBindDN("bindDN");
        obj.setHost("localhost");
        obj.setMapping("one", "one-x");
        obj.setMaxPageSize(233);
        obj.setPassword("password");
        obj.setPort(389);
        obj.setUserBaseDN("userBaseDN");
        obj.setUserQueryFilter("userQueryFilter");
        obj.setUsersBaseDN("usersBaseDN");
        obj.setUsersQueryFilter("usersQueryFilter");
        obj.setUseSSL(true);
        obj.setUseTLS(true);
        obj.setBindDN("bindDN");
        obj.setPassword("password");
        obj.setUserIdAttribute("userIdAttribute");

        assertTrue(cfg.equals(obj));

        obj.setUsersBaseDN("");
        assertFalse(cfg.equals(obj));
        obj.setUsersBaseDN("usersBaseDN");
        assertTrue(cfg.equals(obj));

        obj.setBindDN("");
        assertFalse(cfg.equals(obj));
        obj.setBindDN("bindDN");
        assertTrue(cfg.equals(obj));

        obj.setHost("");
        assertFalse(cfg.equals(obj));
        obj.setHost("localhost");
        assertTrue(cfg.equals(obj));

        obj.setMapping("two", "two");
        assertFalse(cfg.equals(obj));
        obj.clearMappings();
        obj.setMapping("one", "one-x");
        assertTrue(cfg.equals(obj));

        obj.setMaxPageSize(0);
        assertFalse(cfg.equals(obj));
        obj.setMaxPageSize(233);
        assertTrue(cfg.equals(obj));

        obj.setPassword("");
        assertFalse(cfg.equals(obj));
        obj.setPassword("password");
        assertTrue(cfg.equals(obj));

        obj.setPort(0);
        assertFalse(cfg.equals(obj));
        obj.setPort(389);
        assertTrue(cfg.equals(obj));

        obj.setUserBaseDN("");
        assertFalse(cfg.equals(obj));
        obj.setUserBaseDN("userBaseDN");
        assertTrue(cfg.equals(obj));

        obj.setUserQueryFilter("");
        assertFalse(cfg.equals(obj));
        obj.setUserQueryFilter("userQueryFilter");
        assertTrue(cfg.equals(obj));

        obj.setUsersBaseDN("");
        assertFalse(cfg.equals(obj));
        obj.setUsersBaseDN("usersBaseDN");
        assertTrue(cfg.equals(obj));

        obj.setUsersQueryFilter("");
        assertFalse(cfg.equals(obj));
        obj.setUsersQueryFilter("usersQueryFilter");
        assertTrue(cfg.equals(obj));

        obj.setUseSSL(false);
        assertFalse(cfg.equals(obj));
        obj.setUseSSL(true);
        assertTrue(cfg.equals(obj));

        obj.setUseTLS(false);
        assertFalse(cfg.equals(obj));
        obj.setUseTLS(true);
        assertTrue(cfg.equals(obj));

        obj.setBindDN("");
        assertFalse(cfg.equals(obj));
        obj.setBindDN("bindDN");
        assertTrue(cfg.equals(obj));

        obj.setPassword("");
        assertFalse(cfg.equals(obj));
        obj.setPassword("password");
        assertTrue(cfg.equals(obj));

        obj.setUserIdAttribute("");
        assertFalse(cfg.equals(obj));
        obj.setUserIdAttribute("userIdAttribute");
        assertTrue(cfg.equals(obj));
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
    public void testGetUsersBaseDN() {
        assertEquals("usersBaseDN", cfg.getUsersBaseDN());
    }

    @Test
    public void testGetUsersQueryFilter() {
        assertEquals("usersQueryFilter", cfg.getUsersQueryFilter());
    }

    @Test
    public void testGetUserBaseDN() {
        assertEquals("userBaseDN", cfg.getUserBaseDN());
    }

    @Test
    public void testGetUserQueryFilter() {
        assertEquals("userQueryFilter", cfg.getUserQueryFilter());
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
    public void testGetUserIdAttribute() {
        assertEquals("userIdAttribute", cfg.getUserIdAttribute());
    }

    @Test
    public void testClearMappings() {
        cfg.clearMappings();
        assertNull(cfg.getMapping("one"));
        Map<String, String> m = new HashMap<String, String>();
        assertEquals(m, cfg.getMappings());
    }
}
