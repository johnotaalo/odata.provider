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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.servlet.http.HttpSession;

import org.informea.odata.constants.EntityType;

import com.google.gson.Gson;

/**
 * Configuration service for application. Wrapper around Java Preferences API.
 * <br />
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 1.3.3
 */
public class Configuration {

    private static transient final Logger log = Logger.getLogger(Configuration.class.getName());
    private static transient Configuration instance;

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

    private String prefix;

    private transient Preferences prefs;

    private Configuration() {
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
                instance.save();
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
            String filePath = System.getProperty("informea.api.properties");
            if(filePath != null) {
                log.info(String.format("Loading configuration from %s", filePath));
                ret.load(new FileInputStream(filePath));
            } else {
                log.info("Loading configuration from informea.api.properties");
                ret.load(Configuration.class.getClassLoader().getResourceAsStream("informea.api.properties"));
            }
        } catch(IOException ex) {
            log.log(Level.SEVERE, "Cannot load configuration file informea.api.properties", ex);
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
        save();
    }

    public LDAPConfiguration getLDAPConfiguration() {
        return ldap;
    }

    public void setLDAPConfiguration(LDAPConfiguration config) {
        ldap = config;
        save();
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
        save();
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
        save();
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
        save();
    }

    /**
     * @return Is toolkit exposes decisions?
     */
    public boolean isUseDecisions() {
        return useDecisions;
    }


    public void setUseDecisions(boolean value) {
        useDecisions = value;
        save();
    }


    /**
     * @return Is toolkit exposes meetings?
     */
    public boolean isUseMeetings() {
        return useMeetings;
    }


    public void setUseMeetings(boolean value) {
        useMeetings = value;
        save();
    }


    /**
     * @return Is toolkit exposes contacts?
     */
    public boolean isUseContacts() {
        return useContacts;
    }


    public void setUseContacts(boolean value) {
        useContacts = value;
        save();
    }


    /**
     * @return Is toolkit exposes country reports?
     */
    public boolean isUseCountryReports() {
        return useCountryReports;
    }


    public void setUseCountryReports(boolean value) {
        useCountryReports = value;
        save();
    }


    /**
     * @return Is toolkit exposes country profiles?
     */
    public boolean isUseCountryProfiles() {
        return useCountryProfiles;
    }


    public void setUseCountryProfiles(boolean value) {
        useCountryProfiles = value;
        save();
    }


    /**
     * @return Is toolkit exposes national plans?
     */
    public boolean isUseNationalPlans() {
        return useNationalPlans;
    }


    public void setUseNationalPlans(boolean value) {
        useNationalPlans = value;
        save();
    }


    /**
     * @return Is toolkit exposes sites?
     */
    public boolean isUseSites() {
        return useSites;
    }


    public void setUseSites(boolean value) {
        useSites = value;
        save();
    }

    public Map<String, String> getDataProviders() {
        return dataProviders;
    }

    public void setDataProvider(String key, String value) {
        dataProviders.put(key, value);
        save();
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
            String key = String.format("informea.toolkit.%s.config", prefix);
            Gson json = new Gson();
            String data = json.toJson(instance);
            prefs.put(key, data);
            prefs.sync();
        } catch(BackingStoreException ex) {
            throw new RuntimeException("Cannot save preferences in backstore", ex);
        }
    }

    /**
     * Set the preferences from HTTP session. Shortcut method.
     * @param session Valid HTTP session object
     */
    public void putFromSession(HttpSession session) {
        //TODO: setString(DB_TYPE, (String)session.getAttribute(DB_TYPE));
        //TODO: setString(DB_HOST, (String)session.getAttribute(DB_HOST));
        //TODO: setString(DB_USER, (String)session.getAttribute(DB_USER));
        //TODO: setString(DB_PASS, (String)session.getAttribute(DB_PASS));
        //TODO: setString(DB_DATABASE, (String)session.getAttribute(DB_DATABASE));
        //TODO: setInt(DB_PORT, (Integer)session.getAttribute(DB_PORT));

        //        if(session.getAttribute(USE_DECISIONS) != null) {
        //            setBoolean(USE_DECISIONS, (Boolean)session.getAttribute(USE_DECISIONS));
        //        }
        //
        //        if(session.getAttribute(USE_MEETINGS) != null) {
        //            setBoolean(USE_MEETINGS, (Boolean)session.getAttribute(USE_MEETINGS));
        //        }
        //
        //        if(session.getAttribute(USE_CONTACTS) != null) {
        //            setBoolean(USE_CONTACTS, (Boolean)session.getAttribute(USE_CONTACTS));
        //        }
        //
        //        if(session.getAttribute(USE_COUNTRY_REPORTS) != null) {
        //            setBoolean(USE_COUNTRY_REPORTS, (Boolean)session.getAttribute(USE_COUNTRY_REPORTS));
        //        }
        //
        //        if(session.getAttribute(USE_COUNTRY_PROFILES) != null) {
        //            setBoolean(USE_COUNTRY_PROFILES, (Boolean)session.getAttribute(USE_COUNTRY_PROFILES));
        //        }
        //
        //        if(session.getAttribute(USE_NATIONAL_PLANS) != null) {
        //            setBoolean(USE_NATIONAL_PLANS, (Boolean)session.getAttribute(USE_NATIONAL_PLANS));
        //        }
        //
        //        if(session.getAttribute(USE_SITES) != null) {
        //            setBoolean(USE_SITES, (Boolean)session.getAttribute(USE_SITES));
        //        }

        save();
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
     * Reset all preferences
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
        save();
    }
}
