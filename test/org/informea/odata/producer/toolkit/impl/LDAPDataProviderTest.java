package org.informea.odata.producer.toolkit.impl;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.informea.odata.Configuration;
import org.informea.odata.IContact;
import org.informea.odata.constants.Treaty;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.Entry;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.schema.Schema;
import com.unboundid.util.LDAPTestUtils;

public class LDAPDataProviderTest {

    private static String BIND_DN = "cn=test";
    private static String BIND_PASSWORD = "test";
    private static InMemoryDirectoryServer ldapServer = null;

    private IContact first = null;
    private IContact last = null;
    private List<IContact> users = null;


    /**
     * Set-up an in-memory LDAP server
     * @see https://www.unboundid.com/products/ldap-sdk/docs/javadoc/com/unboundid/ldap/listener/InMemoryDirectoryServer.html
     * @throws Exception
     */
    @BeforeClass
    public static void setUp() throws Exception {
        String baseDir = String.format("%s/etc/ldap", System.getProperty("user.dir"));
        String ldifTestFile = String.format("%s/test.ldif", baseDir);
        InMemoryDirectoryServerConfig config =
                new InMemoryDirectoryServerConfig("dc=example,dc=com");
        config.addAdditionalBindCredentials(BIND_DN, BIND_PASSWORD);

        // StartTLS factory
        InMemoryListenerConfig ldapFactory = InMemoryListenerConfig.createLDAPConfig(
                "LDAP", // Listener name
                InetAddress.getLocalHost(),
                0, // Listen port (0 = automatically choose an available port)
                null
                );
        config.setListenerConfigs(ldapFactory);

        Schema ns = Schema.getSchema(String.format("%s/test.schema.ldif", baseDir));
        config.setSchema(ns);

        ldapServer = new InMemoryDirectoryServer(config);
        ldapServer.importFromLDIF(true, ldifTestFile);
        ldapServer.startListening();

        Configuration cfg = org.informea.odata.Configuration.getInstance();
        cfg.setString(Configuration.LDAP_HOST, ldapServer.getListenAddress().getHostAddress());
        cfg.setInt(Configuration.LDAP_PORT, ldapServer.getListenPort());
        cfg.setString(Configuration.LDAP_BIND_DN, BIND_DN);
        cfg.setString(Configuration.LDAP_PASSWORD, BIND_PASSWORD);
        cfg.setString(Configuration.LDAP_USERS_BASE_DN, "ou=People,dc=example,dc=com");
        cfg.setString(Configuration.LDAP_USERS_FILTER, "uid=*");
    }

    @Before
    public void beforeEachTest() {
        first = null;
        last = null;
        users = null;
    }

    @AfterClass
    public static void tearDown() throws Exception {
        if(ldapServer != null) {
            ldapServer.shutDown(true);
        }
    }

    @Test
    public void testInMemoryLDAPSetup() throws Exception {
        // Get a plain connection (unencrypted) to the server's LDAP listener
        LDAPConnection conn = ldapServer.getConnection("LDAP");
        LDAPTestUtils.assertEntryExists(conn, "cn=Barbara Jensen,ou=People,dc=example,dc=com");
        conn.close();
    }

    @Test
    public void testOpenResources() {
        LDAPDataProvider dp = new LDAPDataProvider();
        dp.openResources();
        LDAPConnection conn = dp.getConnection();
        assertNotNull(conn);
        assertTrue(conn.isConnected());
        dp.closeResources();
    }

    @Test
    public void testCloseResources() {
        LDAPDataProvider dp = new LDAPDataProvider();
        dp.openResources();
        LDAPConnection conn = dp.getConnection();
        assertNotNull(conn);
        assertTrue(conn.isConnected());
        dp.closeResources();
        assertFalse(conn.isConnected());
    }

    @Test
    public void testCountPrimaryEntities() {
        LDAPDataProvider dp = new LDAPDataProvider();
        int c = dp.countPrimaryEntities(null, null);
        assertEquals(12, c);
        dp.closeResources();
    }

    @Test
    public void testGetPrimaryEntities() {
        LDAPDataProvider dp = new LDAPDataProvider();
        users = dp.getPrimaryEntities(null, null, 0, 10, null);
        assertEquals(10, users.size());
        first = users.get(0);
        last = users.get(users.size() - 1);
        assertEquals("bjensen", first.getId());
        assertEquals("alangdon", last.getId());
    }

    @Test
    public void testGetPrimaryEntitiesBiggerPage() {
        LDAPDataProvider dp = new LDAPDataProvider();
        users = dp.getPrimaryEntities(null, null, 0, 20, null);
        assertEquals(12, users.size());
        first = users.get(0);
        last = users.get(users.size() - 1);
        assertEquals("bjensen", first.getId());
        assertEquals("ashelton", last.getId());
    }

    @Test
    public void testGetPrimaryEntitiesNegativeStart() {
        LDAPDataProvider dp = new LDAPDataProvider();
        users = dp.getPrimaryEntities(null, null, -1, 5, null);
        assertEquals(5, users.size());
        first = users.get(0);
        last = users.get(users.size() - 1);
        assertEquals("bjensen", first.getId());
        assertEquals("ahall", last.getId());
    }

    @Test
    public void testGetPrimaryEntitiesNegativePageSize() {
        Configuration cfg = Configuration.getInstance();
        cfg.setInt(Configuration.LDAP_MAX_PAGE_SIZE, 5);
        LDAPDataProvider dp = new LDAPDataProvider();
        users = dp.getPrimaryEntities(null, null, 10, -1, null);
        assertEquals(2, users.size());
        first = users.get(0);
        last = users.get(users.size() - 1);
        assertEquals("alutz", first.getId());
        assertEquals("ashelton", last.getId());
        // Reset to default
        cfg.setInt(Configuration.LDAP_MAX_PAGE_SIZE, 0);
    }

    @Test
    public void testGetPrimaryEntitiesMaxPageSize() {
        Configuration cfg = Configuration.getInstance();
        cfg.setInt(Configuration.LDAP_MAX_PAGE_SIZE, 8);
        LDAPDataProvider dp = new LDAPDataProvider();
        users = dp.getPrimaryEntities(null, null, 0, 10000, null);
        assertEquals(8, users.size());
        first = users.get(0);
        last = users.get(users.size() - 1);
        assertEquals("bjensen", first.getId());
        assertEquals("ajensen", last.getId());
        // Reset to default
        cfg.setInt(Configuration.LDAP_MAX_PAGE_SIZE, 0);
    }

    @Test
    public void testGetPageSize() {
        Configuration cfg = Configuration.getInstance();
        cfg.setInt(Configuration.LDAP_MAX_PAGE_SIZE, 0);
        LDAPDataProvider dp = new LDAPDataProvider();
        assertEquals(10, dp.getPageSize(10));
        assertEquals(100, dp.getPageSize(-1));
        assertEquals(100, dp.getPageSize(100));
        assertEquals(100, dp.getPageSize(10000));

        cfg.setInt(Configuration.LDAP_MAX_PAGE_SIZE, 200);
        assertEquals(10, dp.getPageSize(10));
        assertEquals(200, dp.getPageSize(-1));
        assertEquals(200, dp.getPageSize(200));
        assertEquals(200, dp.getPageSize(10000));
    }

    @Test
    public void testParseTreaties() {
        LDAPDataProvider dp = new LDAPDataProvider();
        String treaties = "a,aewa, ascobans, 'cms' ,,\"eurobats\" ,,";
        List<Treaty> l = dp.parseTreaties(treaties);
        assertEquals(4, l.size());
    }

    @Test
    public void testGetPrimaryEntity() {
        Configuration cfg = Configuration.getInstance();
        cfg.setString(Configuration.LDAP_USER_FILTER, "uid=%s");
        LDAPDataProvider dp = new LDAPDataProvider();
        IContact person = (IContact)dp.getPrimaryEntity(null, "bjensen");
        assertEquals("bjensen", person.getId());
    }

    @Test
    public void testGetEntity() {
        Configuration cfg = Configuration.getInstance();
        cfg.setString(Configuration.LDAP_USER_FILTER, "uid=%s");
        LDAPDataProvider dp = new LDAPDataProvider();
        IContact person = (IContact)dp.getPrimaryEntity(null, "bjensen");
        List<Treaty> c = new ArrayList<Treaty>() {{
            add(Treaty.CMS);
            add(Treaty.AEWA);
        }};
        assertEquals(c, person.getTreaties());
    }
}
