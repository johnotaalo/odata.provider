/* Copyright 2011 UNEP (http://www.unep.org)
 * This file is part of InforMEA Toolkit project.
 * InforMEA Toolkit is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * InforMEA Toolkit is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * You should have received a copy of the GNU General Public License along with
 * InforMEA Toolkit. If not, see http://www.gnu.org/licenses/.
 */
package org.informea.odata.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.servlet.http.HttpServletRequest;

import org.informea.odata.IContact;
import org.informea.odata.ICountryProfile;
import org.informea.odata.ICountryReport;
import org.informea.odata.IDecision;
import org.informea.odata.IMeeting;
import org.informea.odata.INationalPlan;
import org.informea.odata.ISite;
import org.informea.odata.constants.EntityType;
import org.informea.odata.data.db.DatabaseDataProvider;
import org.informea.odata.data.ldap.LDAPDataProvider;
import org.informea.odata.util.ToolkitUtil;

import com.google.gson.Gson;

/**
 * Configuration service for application. Wrapper around Java Preferences API.
 * <br />
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 1.3.3
 */
public class Configuration {

    public static String USE_DATABASE = "use_database";
    public static String USE_LDAP = "use_ldap";

    private static transient final Logger log = Logger.getLogger(Configuration.class.getName());
    private static transient Configuration instance;
    private transient String prefix;
    private transient Preferences prefs;

    private boolean useDatabase;
    private boolean useLDAP;

    private boolean installed;
    private boolean usePathPrefix;
    private String pathPrefix;

    private boolean useDecisions;
    private boolean useMeetings;
    private boolean useContacts;
    private boolean useCountryReports;
    private boolean useCountryProfiles;
    private boolean useNationalPlans;
    private boolean useSites;

    private LDAPConfiguration ldap = null;
    private DatabaseConfiguration database = null;
    private Map<String, String> dataProviders;


    public Configuration() {
        prefs = Preferences.userRoot();
        ldap = new LDAPConfiguration();
        database = new DatabaseConfiguration();
        dataProviders = new HashMap<String, String>();
    }

    /**
     * Access to singleton object
     * @return Class instance
     */
    public static Configuration getInstance() {
        if(instance == null) {
            Properties api = Configuration.getAPIProperties();

            String prefix = api.getProperty("preferences.prefix");
            log.info(String.format("Using Java Preferences API prefix : %s", prefix));
            Preferences prefs = Preferences.userRoot();
            Gson json = new Gson();
            String key = String.format("informea.toolkit.%s.config", prefix);
            String jsonCode = prefs.get(key, null);
            if(jsonCode != null && !"".equals(jsonCode)) {
                instance =  json.fromJson(prefs.get(key, null), Configuration.class);
            } else {
                log.info(String.format("No toolkit configuration (%s) in persistent storage. Creating new instance.", key));
                instance = new Configuration();
                instance.setPrefix(prefix);
            }
        }
        return instance;
    }

    public static void reloadConfiguration() {
        instance = null;
        instance = Configuration.getInstance();
    }

    public String getPreferencesKey() {
        return String.format("informea.toolkit.%s.config", prefix);
    }

    public static Properties getAPIProperties() {
        Properties ret = new Properties();
        try {
            log.info("Loading configuration from informea.api.properties");
            ret.load(Configuration.class.getClassLoader().getResourceAsStream("informea.api.properties"));
        } catch(Exception ex) {
            log.log(Level.SEVERE, "Cannot load configuration file informea.api.properties", ex);
            throw new RuntimeException(ex);
        }
        return ret;
    }

    /**
     * Retrieve the current database configuration
     * @return Database information
     */
    public DatabaseConfiguration getDatabaseConfiguration() {
        return database;
    }

    public void setDatabaseConfiguration(DatabaseConfiguration config) {
        database = config;
    }

    public LDAPConfiguration getLDAPConfiguration() {
        return ldap;
    }

    public void setLDAPConfiguration(LDAPConfiguration config) {
        ldap = config;
    }

    /**
     * Is toolkit installed?
     * @return true if installed
     */
    public boolean isInstalled() {
        return installed;
    }

    /**
     * Set toolkit configured/not configured
     * @param value true if configured
     */
    public void setInstalled(boolean value) {
        installed = value;
    }


    /**
     * Are decision documents use a path prefix to locate them on disk?
     * @return true if use path prefix
     */
    public boolean isUsePathPrefix() {
        return usePathPrefix;
    }


    /**
     * Set decision documents to use path prefix.
     * @param value Use path prefix
     */
    public void setUsePathPrefix(boolean value) {
        usePathPrefix = value;
    }


    /**
     * Get path prefix
     * @return Path prefix, ex: "/var/www"
     */
    public String getPathPrefix() {
        return pathPrefix;
    }

    /**
     * Set the path prefix
     * @param value Prefix
     */
    public void setPathPrefix(String value) {
        pathPrefix = value;
    }

    /**
     * @return Is toolkit exposes decisions?
     */
    public boolean isUseDecisions() {
        return useDecisions;
    }


    public void setUseDecisions(boolean value) {
        useDecisions = value;
    }


    /**
     * @return Is toolkit exposes meetings?
     */
    public boolean isUseMeetings() {
        return useMeetings;
    }


    public void setUseMeetings(boolean value) {
        useMeetings = value;
    }


    /**
     * @return Is toolkit exposes contacts?
     */
    public boolean isUseContacts() {
        return useContacts;
    }


    public void setUseContacts(boolean value) {
        useContacts = value;
    }


    /**
     * @return Is toolkit exposes country reports?
     */
    public boolean isUseCountryReports() {
        return useCountryReports;
    }


    public void setUseCountryReports(boolean value) {
        useCountryReports = value;
    }


    /**
     * @return Is toolkit exposes country profiles?
     */
    public boolean isUseCountryProfiles() {
        return useCountryProfiles;
    }


    public void setUseCountryProfiles(boolean value) {
        useCountryProfiles = value;
    }


    /**
     * @return Is toolkit exposes national plans?
     */
    public boolean isUseNationalPlans() {
        return useNationalPlans;
    }


    public void setUseNationalPlans(boolean value) {
        useNationalPlans = value;
    }


    /**
     * @return Is toolkit exposes sites?
     */
    public boolean isUseSites() {
        return useSites;
    }


    public void setUseSites(boolean value) {
        useSites = value;
    }

    public Map<String, String> getDataProviders() {
        return dataProviders;
    }

    public void setDataProvider(String key, String value) {
        dataProviders.put(key, value);
    }

    public void clearDataProviders() {
        dataProviders.clear();
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String value) {
        prefix = value;
    }

    public void save() {
        try {
            log.info("Saving configuration to persistent storage");
            String key = String.format("informea.toolkit.%s.config", prefix);
            Gson json = new Gson();
            String data = json.toJson(instance);
            prefs.put(key, data);
            prefs.sync();
        } catch(BackingStoreException ex) {
            throw new RuntimeException("Cannot save preferences in backstore", ex);
        }
    }

    public boolean isUseDatabase() {
        return useDatabase;
    }

    public boolean isUseLDAP() {
        return useLDAP;
    }

    public void setUseDatabase(boolean value) {
        this.useDatabase = value;
    }

    public void setUseLDAP(boolean value) {
        this.useLDAP = value;
    }

    /**
     * Check if an entity is configured into the toolkit
     * @param type Type of entity
     * @return true if enabled/configured
     */
    public boolean isUse(EntityType type) {
        if(type != null) {
            switch(type) {
                case DECISIONS:
                    return this.isUseDecisions();
                case MEETINGS:
                    return this.isUseMeetings();
                case CONTACTS:
                    return this.isUseContacts();
                case COUNTRY_PROFILES:
                    return this.isUseCountryProfiles();
                case COUNTRY_REPORTS:
                    return this.isUseCountryReports();
                case NATIONAL_PLANS:
                    return this.isUseNationalPlans();
                case SITES:
                    return this.isUseSites();
            }
        }
        return false;
    }

    /**
     * Reset all preferences to their defaults
     */
    public void reset() {
        setDatabaseConfiguration(new DatabaseConfiguration());
        setLDAPConfiguration(new LDAPConfiguration());
        dataProviders = new HashMap<String, String>();
        setInstalled(false);
        setUseDecisions(false);
        setUseMeetings(false);
        setUseContacts(false);
        setUseCountryReports(false);
        setUseCountryProfiles(false);
        setUseNationalPlans(false);
        setUseSites(false);

        setPathPrefix(null);
        setUsePathPrefix(false);
    }


    public void loadFromRequest(HttpServletRequest request) {

        database = (DatabaseConfiguration)request.getSession().getAttribute("db");
        ldap = (LDAPConfiguration)request.getSession().getAttribute("ldap");

        useDatabase = (database != null);
        useLDAP = (ldap != null);

        useDecisions = ToolkitUtil.isOnRequest("useDecisions", request);
        useMeetings = ToolkitUtil.isOnRequest("useMeetings", request);
        useContacts = ToolkitUtil.isOnRequest("useContacts", request);
        useCountryReports = ToolkitUtil.isOnRequest("useCountryReports", request);
        useCountryProfiles = ToolkitUtil.isOnRequest("useCountryProfiles", request);
        useNationalPlans = ToolkitUtil.isOnRequest("useNationalPlans", request);
        useSites = ToolkitUtil.isOnRequest("useSites", request);

        if(useDecisions) {
            dataProviders.put(IDecision.class.getName(), DatabaseDataProvider.class.getName());
        }
        if(useMeetings) {
            dataProviders.put(IMeeting.class.getName(), DatabaseDataProvider.class.getName());
        }
        if(useContacts) {
            if(ldap != null) {
                dataProviders.put(IContact.class.getName(), LDAPDataProvider.class.getName());
            } else {
                dataProviders.put(IContact.class.getName(), DatabaseDataProvider.class.getName());
            }
        }
        if(useCountryReports) {
            dataProviders.put(ICountryReport.class.getName(), DatabaseDataProvider.class.getName());
        }
        if(useCountryProfiles) {
            dataProviders.put(ICountryProfile.class.getName(), DatabaseDataProvider.class.getName());
        }
        if(useNationalPlans) {
            dataProviders.put(INationalPlan.class.getName(), DatabaseDataProvider.class.getName());
        }
        if(useSites) {
            dataProviders.put(ISite.class.getName(), DatabaseDataProvider.class.getName());
        }
    }
}
