package org.informea.odata.config;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
        cfg.setMapping(LDAPConfiguration.LDAP_MAPPING_ID, "userIdAttribute");
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
        obj.setMapping(LDAPConfiguration.LDAP_MAPPING_ID, "userIdAttribute");

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
        obj.setMapping(LDAPConfiguration.LDAP_MAPPING_ID, "userIdAttribute");
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
            put(LDAPConfiguration.LDAP_MAPPING_ID, "userIdAttribute");
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

    @Test
    public void testFromRequest() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        assertNotNull(request);
        when(request.getParameter(LDAPConfiguration.LDAP_HOST)).thenReturn("localhost");
        when(request.getParameter(LDAPConfiguration.LDAP_PORT)).thenReturn("134");
        when(request.getParameter(LDAPConfiguration.LDAP_BIND_DN)).thenReturn("bindDN");
        when(request.getParameter(LDAPConfiguration.LDAP_PASSWORD)).thenReturn("password");
        when(request.getParameter(LDAPConfiguration.LDAP_USER_BASE_DN)).thenReturn("userBaseDN");
        when(request.getParameter(LDAPConfiguration.LDAP_USER_QUERY_FILTER)).thenReturn("userQueryFilter");
        when(request.getParameter(LDAPConfiguration.LDAP_USERS_BASE_DN)).thenReturn("usersBaseDN");
        when(request.getParameter(LDAPConfiguration.LDAP_USERS_QUERY_FILTER)).thenReturn("usersQueryFilter");
        when(request.getParameter(LDAPConfiguration.LDAP_USE_SSL)).thenReturn("ON");
        when(request.getParameter(LDAPConfiguration.LDAP_USE_TLS)).thenReturn("ON");
        when(request.getParameter(LDAPConfiguration.LDAP_MAX_PAGE_SIZE)).thenReturn("1344");

        when(request.getParameter(LDAPConfiguration.LDAP_MAPPING_ADDRESS)).thenReturn("address");
        when(request.getParameter(LDAPConfiguration.LDAP_MAPPING_COUNTRY)).thenReturn("country");
        when(request.getParameter(LDAPConfiguration.LDAP_MAPPING_DEPARTMENT)).thenReturn("department");
        when(request.getParameter(LDAPConfiguration.LDAP_MAPPING_EMAIL)).thenReturn("email");
        when(request.getParameter(LDAPConfiguration.LDAP_MAPPING_FAX)).thenReturn("fax");
        when(request.getParameter(LDAPConfiguration.LDAP_MAPPING_FIRST_NAME)).thenReturn("firstName");
        when(request.getParameter(LDAPConfiguration.LDAP_MAPPING_ID)).thenReturn("uid");
        when(request.getParameter(LDAPConfiguration.LDAP_MAPPING_INSTITUTION)).thenReturn("institution");
        when(request.getParameter(LDAPConfiguration.LDAP_MAPPING_LAST_NAME)).thenReturn("lastName");
        when(request.getParameter(LDAPConfiguration.LDAP_MAPPING_PHONE)).thenReturn("phone");
        when(request.getParameter(LDAPConfiguration.LDAP_MAPPING_POSITION)).thenReturn("position");
        when(request.getParameter(LDAPConfiguration.LDAP_MAPPING_PREFIX)).thenReturn("prefix");
        when(request.getParameter(LDAPConfiguration.LDAP_MAPPING_PRIMARY_NFP)).thenReturn("primary");
        when(request.getParameter(LDAPConfiguration.LDAP_MAPPING_TREATIES)).thenReturn("treaties");
        when(request.getParameter(LDAPConfiguration.LDAP_MAPPING_UPDATED)).thenReturn("updated");


        LDAPConfiguration s = LDAPConfiguration.fromHttpRequest(request);
        assertEquals("localhost", s.getHost());
        assertEquals(134, s.getPort());
        assertEquals("bindDN", s.getBindDN());
        assertEquals("password", s.getPassword());
        assertEquals("userBaseDN", s.getUserBaseDN());
        assertEquals("userQueryFilter", s.getUserQueryFilter());
        assertEquals("usersBaseDN", s.getUsersBaseDN());
        assertEquals("usersQueryFilter", s.getUsersQueryFilter());
        assertTrue(s.isUseSSL());
        assertTrue(s.isUseTLS());
        assertEquals(1344, s.getMaxPageSize());

        assertEquals("address", s.getMapping(LDAPConfiguration.LDAP_MAPPING_ADDRESS));
        assertEquals("country", s.getMapping(LDAPConfiguration.LDAP_MAPPING_COUNTRY));
        assertEquals("department", s.getMapping(LDAPConfiguration.LDAP_MAPPING_DEPARTMENT));
        assertEquals("email", s.getMapping(LDAPConfiguration.LDAP_MAPPING_EMAIL));
        assertEquals("fax", s.getMapping(LDAPConfiguration.LDAP_MAPPING_FAX));
        assertEquals("firstName", s.getMapping(LDAPConfiguration.LDAP_MAPPING_FIRST_NAME));
        assertEquals("uid", s.getMapping(LDAPConfiguration.LDAP_MAPPING_ID));
        assertEquals("institution", s.getMapping(LDAPConfiguration.LDAP_MAPPING_INSTITUTION));
        assertEquals("lastName", s.getMapping(LDAPConfiguration.LDAP_MAPPING_LAST_NAME));
        assertEquals("phone", s.getMapping(LDAPConfiguration.LDAP_MAPPING_PHONE));
        assertEquals("position", s.getMapping(LDAPConfiguration.LDAP_MAPPING_POSITION));
        assertEquals("prefix", s.getMapping(LDAPConfiguration.LDAP_MAPPING_PREFIX));
        assertEquals("primary", s.getMapping(LDAPConfiguration.LDAP_MAPPING_PRIMARY_NFP));
        assertEquals("treaties", s.getMapping(LDAPConfiguration.LDAP_MAPPING_TREATIES));
        assertEquals("updated", s.getMapping(LDAPConfiguration.LDAP_MAPPING_UPDATED));
    }
}
