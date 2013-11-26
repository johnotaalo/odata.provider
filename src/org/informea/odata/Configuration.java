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
package org.informea.odata;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.servlet.http.HttpSession;

import org.informea.odata.constants.EntityType;

/**
 * Configuration service for application. Wrapper around Java Preferences API.
 * <br />
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 1.3.3
 */
public class Configuration {

    private static final Logger log = Logger.getLogger(Configuration.class.getName());

    private static Configuration _instance;

    /**
     * Constant, value: informea.documents.usePathPrefix
     */
    public static final String USE_PATH_PREFIX = "informea.documents.usePathPrefix";

    /**
     * Constant, value: informea.documents.pathPrefix
     */
    public static final String PATH_PREFIX = "informea.documents.pathPrefix";

    /**
     * Constant, value: informea.installed
     */
    public static final String INSTALLED = "informea.installed";

    /**
     * Constant, value: informea.db_type
     */
    public static final String DB_TYPE = "informea.db_type";

    /**
     * Constant, value: informea.db_host
     */
    public static final String DB_HOST = "informea.db_host";

    /**
     * Constant, value: informea.db_port
     */
    public static final String DB_PORT = "informea.db_port";

    /**
     * Constant, value: informea.db_user
     */
    public static final String DB_USER = "informea.db_user";

    /**
     * Constant, value: informea.db_pass
     */
    public static final String DB_PASS = "informea.db_pass";

    /**
     * Constant, value: informea.db_database
     */
    public static final String DB_DATABASE = "informea.db_database";


    /**
     * Constant, value: informea.use_decisions
     */
    public static final String USE_DECISIONS = "informea.use_decisions";

    /**
     * Constant, value: informea.use_meetings
     */
    public static final String USE_MEETINGS = "informea.use_meetings";

    /**
     * Constant, value: informea.use_contacts
     */
    public static final String USE_CONTACTS = "informea.use_contacts";

    /**
     * Constant, value: informea.use_country_reports
     */
    public static final String USE_COUNTRY_REPORTS = "informea.use_country_reports";

    /**
     * Constant, value: informea.use_country_profiles
     */
    public static final String USE_COUNTRY_PROFILES = "informea.use_country_profiles";

    /**
     * Constant, value: informea.use_national_plans
     */
    public static final String USE_NATIONAL_PLANS = "informea.use_national_plans";

    /**
     * Constant, value: informea.use_sites
     */
    public static final String USE_SITES = "informea.use_sites";

    /**
     * Constant, value: informea.ldap.host
     */
    public static final String LDAP_HOST = "informea.ldap.host";

    /**
     * Constant, value: informea.ldap.host
     */
    public static final String LDAP_PORT = "informea.ldap.port";

    /**
     * Constant, value: informea.ldap.bind_dn
     */
    public static final String LDAP_BIND_DN = "informea.ldap.bind_dn";

    /**
     * Constant, value: informea.ldap.password
     */
    public static final String LDAP_PASSWORD = "informea.ldap.password";

    /**
     * Constant, value: informea.ldap.users.query
     */
    public static final String LDAP_USERS_BASE_DN = "informea.ldap.users.baseDN";

    /**
     * Constant, value: informea.ldap.users.query
     */
    public static final String LDAP_USERS_FILTER = "informea.ldap.users.filter";

    /**
     * Constant, value: informea.ldap.user.query
     */
    public static final String LDAP_USER_BASE_DN = "informea.ldap.user.baseDN";

    /**
     * Constant, value: informea.ldap.maxPageSize
     */
    public static final String LDAP_MAX_PAGE_SIZE = "informea.ldap.maxPageSize";

    /**
     * Constant, value: informea.ldap.user.filter
     */
    public static final String LDAP_USER_FILTER = "informea.ldap.user.filter";

    private Preferences prefs;
    private String prefix = "";
    private Properties api = new Properties();

    private Configuration() {
        prefs = Preferences.userRoot();

        log.info("Loading configuration from informea.api.properties");
        try {
            api.load(this.getClass().getClassLoader().getResourceAsStream("informea.api.properties"));
            prefix = api.getProperty("informea.preferences-api.prefix");
            log.info(String.format("Using Java Preferences API prefix : %s", prefix));
        } catch(IOException ex) {
            log.log(Level.WARNING, "Error loading informea.api.properties", ex);
        }
    }

    /**
     * Access to singleton object
     * @return Class instance
     */
    public static Configuration getInstance() {
        if(_instance == null) {
            _instance = new Configuration();
        }
        return _instance;
    }

    /**
     * Is toolkit installed?
     * @return true if installed
     */
    public boolean isInstalled() {
        return getBoolean(INSTALLED);
    }

    /**
     * Set toolkit configured/not configured
     * @param value true if configured
     */
    public void setInstalled(boolean value) {
        setBoolean(INSTALLED, value);
    }


    /**
     * Are decision documents use a path prefix to locate them on disk?
     * @return true if use path prefix
     */
    public boolean isUsePathPrefix() {
        return getBoolean(USE_PATH_PREFIX);
    }


    /**
     * Set decision documents to use path prefix.
     * @param value Use path prefix
     */
    public void setUsePathPrefix(boolean value) {
        setBoolean(USE_PATH_PREFIX, value);
    }


    /**
     * Get path prefix
     * @return Path prefix, ex: "/var/www"
     */
    public String getPathPrefix() {
        return getString(PATH_PREFIX);
    }


    /**
     * Set the path prefix
     * @param value Prefix
     */
    public void setPathPrefix(String value) {
        setString(PATH_PREFIX, value);
    }


    /**
     * Type of database to connect to
     * @param value database type. Currently supported is "mysql"
     */
    public void setDBType(String value) {
        setString(DB_TYPE, value);
    }


    /**
     * Get the type of database.
     * @return Currently supported is "mysql"
     */
    public String getDBType() {
        return getString(DB_TYPE);
    }


    /**
     * Set database server host
     * @param value Hostname or IP
     */
    public void setDBHost(String value) {
        setString(DB_HOST, value);
    }


    /**
     * Retrieve database server hostname or IP
     * @return String
     */
    public String getDBHost() {
        String ret = getString(DB_HOST);
        if(ret == null) {
            ret = "localhost";
        }
        return ret;
    }


    /**
     * Set the database port
     * @param value Valid TCP/IP port number
     */
    public void setDBPort(int value) {
        setInt(DB_PORT, value);
    }


    /**
     * @return Database server TCP/IP port number
     */
    public int getDBPort() {
        return getInt(DB_PORT);
    }


    /**
     * Database user
     * @param value Valid username to connect to DB server
     */
    public void setDBUser(String value) {
        setString(DB_USER, value);
    }


    /**
     * Get the database username
     * @return String
     */
    public String getDBUser() {
        return getString(DB_USER);
    }


    /**
     * Database password
     * @param value Database password
     */
    public void setDBPassword(String value) {
        setString(DB_PASS, value);
    }


    /**
     * Get password for database user
     * @return String
     */
    public String getDBPassword() {
        return getString(DB_PASS);
    }


    /**
     * Name of the database
     * @param value String
     */
    public void setDBName(String value) {
        setString(DB_DATABASE, value);
    }


    /**
     * @return Database name
     */
    public String getDBName() {
        return getString(DB_DATABASE);
    }


    /**
     * @return Is toolkit exposes decisions?
     */
    public boolean isUseDecisions() {
        return getBoolean(USE_DECISIONS);
    }


    public void setUseDecisions(boolean value) {
        setBoolean(USE_DECISIONS, value);
    }


    /**
     * @return Is toolkit exposes meetings?
     */
    public boolean isUseMeetings() {
        return getBoolean(USE_MEETINGS);
    }


    public void setUseMeetings(boolean value) {
        setBoolean(USE_MEETINGS, value);
    }


    /**
     * @return Is toolkit exposes contacts?
     */
    public boolean isUseContacts() {
        return getBoolean(USE_CONTACTS);
    }


    public void setUseContacts(boolean value) {
        setBoolean(USE_CONTACTS, value);
    }


    /**
     * @return Is toolkit exposes country reports?
     */
    public boolean isUseCountryReports() {
        return getBoolean(USE_COUNTRY_REPORTS);
    }


    public void setUseCountryReports(boolean value) {
        setBoolean(USE_COUNTRY_REPORTS, value);
    }


    /**
     * @return Is toolkit exposes country profiles?
     */
    public boolean isUseCountryProfiles() {
        return getBoolean(USE_COUNTRY_PROFILES);
    }


    public void setUseCountryProfiles(boolean value) {
        setBoolean(USE_COUNTRY_PROFILES, value);
    }


    /**
     * @return Is toolkit exposes national plans?
     */
    public boolean isUseNationalPlans() {
        return getBoolean(USE_NATIONAL_PLANS);
    }


    public void setUseNationalPlans(boolean value) {
        setBoolean(USE_NATIONAL_PLANS, value);
    }


    /**
     * @return Is toolkit exposes sites?
     */
    public boolean isUseSites() {
        return getBoolean(USE_SITES);
    }


    public void setUseSites(boolean value) {
        setBoolean(USE_SITES, value);
    }

    /**
     * @return JDBC driver name depending on db_type. Currently MySQL is supported
     */
    public String getJDBCDriver() {
        if("mysql".equals(this.getDBType())) {
            return "com.mysql.jdbc.Driver";
        }
        return null;
    }

    /**
     * @return JDBC URL depending on db_type. Currently MySQL is supported
     */
    public String getJDBCUrl() {
        if("mysql".equals(this.getDBType())) {
            return String.format("jdbc:mysql://%s:%s/%s", getDBHost(), getDBPort(), getDBName());
        }
        return null;
    }


    private void sync() {
        try {
            prefs.sync();
        } catch(BackingStoreException ex) {
            throw new RuntimeException("Cannot save preferences in backstore", ex);
        }
    }


    /**
     * @return Hibernate dialect depending on db_type. Currently MySQL is supported
     */
    public String getHibernateDialect() {
        if("mysql".equals(this.getDBType())) {
            return "org.hibernate.dialect.MySQLDialect";
        }
        return null;
    }


    /**
     * Set the preferences from HTTP session. Shortcut method.
     * @param session Valid HTTP session object
     */
    public void putFromSession(HttpSession session) {
        setString(DB_TYPE, (String)session.getAttribute(DB_TYPE));
        setString(DB_HOST, (String)session.getAttribute(DB_HOST));
        setString(DB_USER, (String)session.getAttribute(DB_USER));
        setString(DB_PASS, (String)session.getAttribute(DB_PASS));
        setString(DB_DATABASE, (String)session.getAttribute(DB_DATABASE));
        setInt(DB_PORT, (Integer)session.getAttribute(DB_PORT));

        if(session.getAttribute(USE_DECISIONS) != null) {
            setBoolean(USE_DECISIONS, (Boolean)session.getAttribute(USE_DECISIONS));
        }

        if(session.getAttribute(USE_MEETINGS) != null) {
            setBoolean(USE_MEETINGS, (Boolean)session.getAttribute(USE_MEETINGS));
        }

        if(session.getAttribute(USE_CONTACTS) != null) {
            setBoolean(USE_CONTACTS, (Boolean)session.getAttribute(USE_CONTACTS));
        }

        if(session.getAttribute(USE_COUNTRY_REPORTS) != null) {
            setBoolean(USE_COUNTRY_REPORTS, (Boolean)session.getAttribute(USE_COUNTRY_REPORTS));
        }

        if(session.getAttribute(USE_COUNTRY_PROFILES) != null) {
            setBoolean(USE_COUNTRY_PROFILES, (Boolean)session.getAttribute(USE_COUNTRY_PROFILES));
        }

        if(session.getAttribute(USE_NATIONAL_PLANS) != null) {
            setBoolean(USE_NATIONAL_PLANS, (Boolean)session.getAttribute(USE_NATIONAL_PLANS));
        }

        if(session.getAttribute(USE_SITES) != null) {
            setBoolean(USE_SITES, (Boolean)session.getAttribute(USE_SITES));
        }

        sync();
    }

    /**
     * Check if an entity is configured into the toolkit
     * @param type Type of entity
     * @return true if enabled/configured
     */
    public boolean isUse(EntityType type) {
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
        return false;
    }


    public String getString(String key) {
        return prefs.get(prefix + key, "");
    }

    public void setString(String key, String value) {
        if(value != null) {
            prefs.put(prefix + key, value);
        } else {
            prefs.remove(prefix + key);
        }
        sync();
    }

    public boolean getBoolean(String key) {
        return prefs.getBoolean(prefix + key, false);
    }

    public void setBoolean(String key, Boolean value) {
        if(value != null) {
            prefs.putBoolean(prefix + key, value);
        } else {
            prefs.remove(prefix + key);
        }
        sync();
    }

    public int getInt(String key) {
        return prefs.getInt(prefix + key, 0);
    }

    public void setInt(String key, Integer value) {
        if(value != null) {
            prefs.putInt(prefix + key, value);
        } else {
            prefs.remove(prefix + key);
        }
        sync();
    }

    /**
     * Reset all preferences
     */
    public void reset() {
        prefs.remove(prefix + DB_TYPE);
        prefs.remove(prefix + DB_HOST);
        prefs.remove(prefix + DB_USER);
        prefs.remove(prefix + DB_PASS);
        prefs.remove(prefix + DB_DATABASE);
        prefs.remove(prefix + DB_PORT);

        prefs.remove(prefix + USE_DECISIONS);
        prefs.remove(prefix + USE_MEETINGS);
        prefs.remove(prefix + USE_CONTACTS);
        prefs.remove(prefix + USE_COUNTRY_REPORTS);
        prefs.remove(prefix + USE_COUNTRY_PROFILES);
        prefs.remove(prefix + USE_NATIONAL_PLANS);
        prefs.remove(prefix + USE_SITES);
        sync();
    }
}
