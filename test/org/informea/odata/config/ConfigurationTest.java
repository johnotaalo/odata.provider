package org.informea.odata.config;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.prefs.Preferences;

import org.informea.odata.IContact;
import org.informea.odata.config.Configuration;
import org.informea.odata.constants.EntityType;
import org.informea.odata.data.db.DatabaseDataProvider;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class ConfigurationTest {

    private String key = "informea.toolkit.junit.config";

    private static void clearPreferences() throws Exception {
        Properties p = new Properties();
        p.put("preferences.prefix", "junit");
        String key = "informea.toolkit.junit.config";
        // Reset the preferences
        Preferences prefs = Preferences.userRoot();
        prefs.remove(key);
        prefs.flush();
        prefs.sync();
    }

    @Before
    public void setUp() throws Exception {
        clearPreferences();
    }

    @AfterClass
    public static void afterTearDown() throws Exception {
        clearPreferences();
    }

    private DatabaseConfiguration getSampleDatabaseConfiguration() {
        DatabaseConfiguration db = new DatabaseConfiguration();
        db.setDatabase("database");
        db.setHost("localhost");
        db.setPassword("password");
        db.setPort(3306);
        db.setType("com.mysql.jdbc.Driver");
        db.setUser("cristiroma");
        return db;
    }

    private LDAPConfiguration getSampleLDAPConfiguration() {
        LDAPConfiguration ldap = new LDAPConfiguration();
        ldap.setUsersBaseDN("usersBaseDN");
        ldap.setBindDN("bindDN");
        ldap.setHost("localhost");
        ldap.setMaxPageSize(139);
        ldap.setPassword("password");
        ldap.setPort(389);
        ldap.setUserBaseDN("userBaseDN");
        ldap.setUserQueryFilter("q1");
        ldap.setUsersQueryFilter("q2");
        ldap.setUseSSL(true);
        ldap.setUseTLS(true);
        return ldap;
    }

    private void setFullConfiguration() {
        Configuration cfg = Configuration.getInstance();
        // Full configuration
        DatabaseConfiguration db = getSampleDatabaseConfiguration();
        cfg.setDatabaseConfiguration(db);

        cfg.setDataProvider(IContact.class.getName(), DatabaseDataProvider.class.getName());

        cfg.setInstalled(true);

        LDAPConfiguration ldap = getSampleLDAPConfiguration();
        cfg.setLDAPConfiguration(ldap);

        cfg.setPathPrefix("PATH PREFIX");

        cfg.setUseContacts(true);
        cfg.setUseCountryProfiles(true);
        cfg.setUseCountryReports(true);
        cfg.setUseDecisions(true);
        cfg.setUseMeetings(true);
        cfg.setUseNationalPlans(true);
        cfg.setUseSites(true);

        cfg.setUsePathPrefix(true);

        cfg.save();
    }

    @Test
    public void testGetInstance() throws Exception {
        // System.setProperty("informea.api.properties", "etc/informea-test.api.properties");

        Configuration cfg = Configuration.getInstance();
        assertNotNull(cfg);
        assertEquals(key, cfg.getPreferencesKey());
        assertNotNull(cfg.getDatabaseConfiguration());
        assertNotNull(cfg.getLDAPConfiguration());
        assertNotNull(cfg.getDataProviders());
        assertFalse(cfg.isInstalled());
        assertNull(cfg.getPathPrefix());
        assertFalse(cfg.isUsePathPrefix());
        assertFalse(cfg.isUseDecisions());
        assertFalse(cfg.isUseMeetings());
        assertFalse(cfg.isUseContacts());
        assertFalse(cfg.isUseCountryReports());
        assertFalse(cfg.isUseNationalPlans());
        assertFalse(cfg.isUseSites());

        setFullConfiguration();

        Configuration.reloadConfiguration();

        cfg = Configuration.getInstance();

        DatabaseConfiguration db = getSampleDatabaseConfiguration();
        assertEquals(db, cfg.getDatabaseConfiguration());

        @SuppressWarnings("serial")
        Map<String, String> dpm = new HashMap<String, String>() {{
            put(IContact.class.getName(), DatabaseDataProvider.class.getName());
        }};

        assertEquals(dpm, cfg.getDataProviders());

        assertTrue(cfg.isInstalled());

        LDAPConfiguration ldap = getSampleLDAPConfiguration();
        assertEquals(ldap, cfg.getLDAPConfiguration());
        assertEquals("PATH PREFIX", cfg.getPathPrefix());
        assertTrue(cfg.isUseContacts());
        assertTrue(cfg.isUseCountryProfiles());
        assertTrue(cfg.isUseCountryReports());
        assertTrue(cfg.isUseDecisions());
        assertTrue(cfg.isUseMeetings());
        assertTrue(cfg.isUseNationalPlans());
        assertTrue(cfg.isUseSites());

        assertTrue(cfg.isUsePathPrefix());
    }

    @Test
    public void testIsInstalled() {
        Configuration cfg = Configuration.getInstance();
        cfg.setInstalled(false);
        assertFalse(cfg.isInstalled());
        cfg.setInstalled(true);
        assertTrue(cfg.isInstalled());
    }

    @Test
    public void testGetPreferencesKey() {
        Configuration cfg = Configuration.getInstance();
        assertEquals(key, cfg.getPreferencesKey());
    }

    @Test
    public void testIsUse() {
        Configuration cfg = Configuration.getInstance();

        cfg.setUseContacts(true);
        cfg.setUseCountryProfiles(true);
        cfg.setUseCountryReports(true);
        cfg.setUseDecisions(true);
        cfg.setUseMeetings(true);
        cfg.setUseNationalPlans(true);
        cfg.setUseSites(true);

        assertTrue(cfg.isUse(EntityType.CONTACTS));
        assertTrue(cfg.isUse(EntityType.COUNTRY_PROFILES));
        assertTrue(cfg.isUse(EntityType.COUNTRY_REPORTS));
        assertTrue(cfg.isUse(EntityType.DECISIONS));
        assertTrue(cfg.isUse(EntityType.MEETINGS));
        assertTrue(cfg.isUse(EntityType.NATIONAL_PLANS));
        assertTrue(cfg.isUse(EntityType.SITES));
        assertFalse(cfg.isUse((EntityType)null));
    }

    @Test
    public void testReset() {
        Configuration cfg = Configuration.getInstance();
        setFullConfiguration();
        cfg.reset();

        assertFalse(cfg.isInstalled());
    }

    @Test
    public void testClearDataProviders() {
        Configuration cfg = Configuration.getInstance();
        setFullConfiguration();
        cfg.clearDataProviders();

        Map<String, String> dpm = cfg.getDataProviders();
        assertEquals(0, dpm.size());
    }

    @Test
    public void getPrefix() {
        Configuration cfg = Configuration.getInstance();
        assertEquals("junit", cfg.getPrefix());
    }
}
