package org.informea.odata.data.ldap;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.informea.odata.IContact;
import org.informea.odata.config.Configuration;
import org.informea.odata.constants.Treaty;
import org.informea.odata.data.ldap.LDAPDataProvider;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchScope;
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

        Configuration cfg = org.informea.odata.config.Configuration.getInstance();
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
        assertEquals("cn=Barbara Jensen,ou=People,dc=example,dc=com", first.getId());
        assertEquals("uid=alangdon,ou=People,dc=example,dc=com", last.getId());
    }

    @Test
    public void testGetPrimaryEntitiesBiggerPage() {
        LDAPDataProvider dp = new LDAPDataProvider();
        users = dp.getPrimaryEntities(null, null, 0, 20, null);
        assertEquals(12, users.size());
        first = users.get(0);
        last = users.get(users.size() - 1);
        assertEquals("cn=Barbara Jensen,ou=People,dc=example,dc=com", first.getId());
        assertEquals("uid=ashelton,ou=People,dc=example,dc=com", last.getId());
    }

    @Test
    public void testGetPrimaryEntitiesNegativeStart() {
        LDAPDataProvider dp = new LDAPDataProvider();
        users = dp.getPrimaryEntities(null, null, -1, 5, null);
        assertEquals(5, users.size());
        first = users.get(0);
        last = users.get(users.size() - 1);
        assertEquals("cn=Barbara Jensen,ou=People,dc=example,dc=com", first.getId());
        assertEquals("uid=ahall,ou=People,dc=example,dc=com", last.getId());
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
        assertEquals("uid=alutz,ou=People,dc=example,dc=com", first.getId());
        assertEquals("uid=ashelton,ou=People,dc=example,dc=com", last.getId());
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
        assertEquals("cn=Barbara Jensen,ou=People,dc=example,dc=com", first.getId());
        assertEquals("uid=ajensen,ou=People,dc=example,dc=com", last.getId());
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
    public void testFromSearchResultEntry() throws Exception {
        LDAPDataProvider dp = new LDAPDataProvider();
        Configuration cfg = Configuration.getInstance();

        LDAPConnection conn = ldapServer.getConnection();
        SearchResultEntry item = conn.searchForEntry(
                cfg.getString(Configuration.LDAP_USER_BASE_DN),
                SearchScope.SUB,
                "uid=bjensen");

        List<Treaty> t = new ArrayList<Treaty>() {{
            add(Treaty.CMS);
            add(Treaty.AEWA);
        }};

        cfg.setString(Configuration.LDAP_MAPPING_PREFIX, "personalTitle");
        cfg.setString(Configuration.LDAP_MAPPING_COUNTRY, "c");
        cfg.setString(Configuration.LDAP_MAPPING_FIRST_NAME, "givenName");
        cfg.setString(Configuration.LDAP_MAPPING_LAST_NAME, "sn");
        cfg.setString(Configuration.LDAP_MAPPING_ADDRESS, "Address: ${registeredAddress} ${postalCode}");
        cfg.setString(Configuration.LDAP_MAPPING_DEPARTMENT, "ou");
        cfg.setString(Configuration.LDAP_MAPPING_EMAIL, "mail");
        cfg.setString(Configuration.LDAP_MAPPING_FAX, "facsimileTelephoneNumber");
        cfg.setString(Configuration.LDAP_MAPPING_INSTITUTION, "o");
        cfg.setString(Configuration.LDAP_MAPPING_PHONE, "telephoneNumber");
        cfg.setString(Configuration.LDAP_MAPPING_POSITION, "title");
        cfg.setString(Configuration.LDAP_MAPPING_UPDATED, "roomNumber");
        cfg.setString(Configuration.LDAP_MAPPING_TREATIES, "carLicense");

        IContact c = dp.fromSearchResultEntry(item);

        assertEquals("Address: 5th Avenue, NY 12345", c.getAddress());
        assertEquals("cn=Barbara Jensen,ou=People,dc=example,dc=com", c.getId());
        assertEquals("RO", c.getCountry());
        assertEquals("Software Development", c.getDepartment());
        assertEquals("bjensen@siroe.com", c.getEmail());
        assertEquals("+1 408 555 1992", c.getFax());
        assertEquals("Barbara", c.getFirstName());
        assertEquals("Eau de Web", c.getInstitution());
        assertEquals("Jensen", c.getLastName());
        assertEquals("+1 408 555 1862", c.getPhoneNumber());
        assertEquals("Web Developer", c.getPosition());
        assertEquals("H.E.", c.getPrefix());
        assertEquals(new Short((short) 1), c.getProtocolVersion());
        assertEquals(t, c.getTreaties());

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+2"));
        cal.set(2006, 11, 25, 16, 45, 22);
        cal.set(Calendar.MILLISECOND, 0);
        Date d = cal.getTime();
        Date updated = c.getUpdated();
        assertEquals(updated, d);
    }

    @Test
    public void testGetFieldValue() throws Exception {
        LDAPConnection conn = ldapServer.getConnection();
        SearchResultEntry item = conn.searchForEntry(
                "ou=People,dc=example,dc=com",
                SearchScope.SUB,
                "uid=bjensen");
        assertNotNull(item);

        LDAPDataProvider dp = new LDAPDataProvider();
        assertEquals("12345", dp.getFieldValue(item, "postalCode"));

        assertEquals("valid12345", dp.getFieldValue(item, "valid${postalCode}"));

        assertEquals("invalidAttribute", dp.getFieldValue(item, "invalidAttribute"));

        assertEquals("BJ", dp.getFieldValue(item, "${initials}"));
        assertEquals(" BJ ", dp.getFieldValue(item, " ${initials} "));
        assertEquals(" BJ arbitrary string", dp.getFieldValue(item, " ${initials} arbitrary string"));

        assertEquals(" BJ Web Developer arbitrary string", dp.getFieldValue(item, " ${initials} ${title} arbitrary string"));
        assertEquals(" BJWeb Developerarbitrary string", dp.getFieldValue(item, " ${initials}${title}arbitrary string"));
    }
}
