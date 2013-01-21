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
package org.informea.odata.producer.toolkit;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.servlet.http.HttpSession;

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
     * Constant, value: informea.use_project
     */
    public static final String USE_PROJECTS = "informea.use_project";

    /**
     * Constant, value: informea.use_best_practice
     */
    public static final String USE_BEST_PRACTICES = "informea.use_best_practice";

    /**
     * Constant, value: informea.use_technical_report
     */
    public static final String USE_TECHNICAL_REPORTS = "informea.use_technical_report";

    public static final String URL_PEBLDS_FILES = "informea.url_pebld_files";
    public static final String PATH_PEBLDS_FILES = "informea.path_pebld_files";

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
        return prefs.getBoolean(prefix + INSTALLED, false);
    }


    /**
     * Set toolkit installed/uninstalled
     * @param value Status
     */
    public void setInstalled(boolean value) {
        prefs.putBoolean(prefix + INSTALLED, value);
        sync();
    }


    /**
     * Are decision documents use a path prefix to locate them on disk?
     * @return true if use path prefix
     */
    public boolean isUsePathPrefix() {
        return prefs.getBoolean(prefix + USE_PATH_PREFIX, false);
    }


    /**
     * Set decision documents to use path prefix.
     * @param value Use path prefix
     */
    public void setUsePathPrefix(boolean value) {
        prefs.putBoolean(prefix + USE_PATH_PREFIX, value);
        sync();
    }


    /**
     * Get path prefix
     * @return Path prefix, ex: "/var/www"
     */
    public String getPathPrefix() {
        return prefs.get(prefix + PATH_PREFIX, "");
    }


    /**
     * Set the path prefix
     * @param value Prefix
     */
    public void setPathPrefix(String value) {
        prefs.put(prefix + PATH_PREFIX, value);
        sync();
    }


    /**
     * Type of database to connect to
     * @param value database type. Currently supported is "mysql"
     */
    public void setDBType(String value) {
        prefs.put(prefix + DB_TYPE, value);
    }


    /**
     * Get the type of database.
     * @return Currently supported is "mysql"
     */
    public String getDBType() {
        return prefs.get(prefix + DB_TYPE, null);
    }


    /**
     * Set database server host
     * @param value Hostname or IP
     */
    public void setDBHost(String value) {
        prefs.put(prefix + DB_HOST, value);
    }


    /**
     * Retrieve database server hostname or Ip
     * @return String
     */
    public String getDBHost() {
        return prefs.get(prefix + DB_HOST, "localhost");
    }


    /**
     * Set the database port
     * @param value Valid TCP/IP port number
     */
    public void setDBPort(int value) {
        prefs.putInt(prefix + DB_PORT, value);
    }


    /**
     * @return Database server TCP/IP port number
     */
    public int getDBPort() {
        return prefs.getInt(prefix + DB_PORT, 0);
    }


    /**
     * Database user
     * @param value Valid username to connect to Db server
     */
    public void setDBUser(String value) {
        prefs.put(prefix + DB_USER, value);
    }


    /**
     * Get the database username
     * @return String
     */
    public String getDBUser() {
        return prefs.get(prefix + DB_USER, null);
    }


    /**
     * Database password
     * @param value Database password
     */
    public void setDBPassword(String value) {
        prefs.put(prefix + DB_PASS, value);
    }


    /**
     * Get password for database user
     * @return String
     */
    public String getDBPassword() {
        return prefs.get(prefix + DB_PASS, null);
    }


    /**
     * Name of the database
     * @param value String
     */
    public void setDBName(String value) {
        prefs.put(prefix + DB_DATABASE, value);
    }


    /**
     * @return Database name
     */
    public String getDBName() {
        return prefs.get(prefix + DB_DATABASE, null);
    }


    /**
     * @return Is toolkit exposes decisions?
     */
    public boolean isUseDecisions() {
        return prefs.getBoolean(prefix + USE_DECISIONS, false);
    }


    public void setUseDecisions(boolean value) {
        prefs.putBoolean(prefix + USE_DECISIONS, value);
        sync();
    }


    /**
     * @return Is toolkit exposes meetings?
     */
    public boolean isUseMeetings() {
        return prefs.getBoolean(prefix + USE_MEETINGS, false);
    }


    public void setUseMeetings(boolean value) {
        prefs.putBoolean(prefix + USE_MEETINGS, value);
        sync();
    }


    /**
     * @return Is toolkit exposes contacts?
     */
    public boolean isUseContacts() {
        return prefs.getBoolean(prefix + USE_CONTACTS, false);
    }


    public void setUseContacts(boolean value) {
        prefs.putBoolean(prefix + USE_CONTACTS, value);
        sync();
    }


    /**
     * @return Is toolkit exposes country reports?
     */
    public boolean isUseCountryReports() {
        return prefs.getBoolean(prefix + USE_COUNTRY_REPORTS, false);
    }


    public void setUseCountryReports(boolean value) {
        prefs.putBoolean(prefix + USE_COUNTRY_REPORTS, value);
        sync();
    }


    /**
     * @return Is toolkit exposes country profiles?
     */
    public boolean isUseCountryProfiles() {
        return prefs.getBoolean(prefix + USE_COUNTRY_PROFILES, false);
    }


    public void setUseCountryProfiles(boolean value) {
        prefs.putBoolean(prefix + USE_COUNTRY_PROFILES, value);
        sync();
    }


    /**
     * @return Is toolkit exposes national plans?
     */
    public boolean isUseNationalPlans() {
        return prefs.getBoolean(prefix + USE_NATIONAL_PLANS, false);
    }


    public void setUseNationalPlans(boolean value) {
        prefs.putBoolean(prefix + USE_NATIONAL_PLANS, value);
        sync();
    }


    /**
     * @return Is toolkit exposes sites?
     */
    public boolean isUseSites() {
        return prefs.getBoolean(prefix + USE_SITES, false);
    }


    public void setUseSites(boolean value) {
        prefs.putBoolean(prefix + USE_SITES, value);
        sync();
    }

    /**
     * @return Is toolkit exposes projects?
     */
    public boolean isUseProjects() {
        return prefs.getBoolean(prefix + USE_PROJECTS, false);
    }


    public void setUseProjects(boolean value) {
        prefs.putBoolean(prefix + USE_PROJECTS, value);
        sync();
    }

    /**
     * @return Is toolkit exposes projects?
     */
    public boolean isUseBestPractices() {
        return prefs.getBoolean(prefix + USE_BEST_PRACTICES, false);
    }


    public void setUseBestPractices(boolean value) {
        prefs.putBoolean(prefix + USE_BEST_PRACTICES, value);
        sync();
    }

    /**
     * @return Is toolkit exposes projects?
     */
    public boolean isUseTechnicalReports() {
        return prefs.getBoolean(prefix + USE_TECHNICAL_REPORTS, false);
    }


    public void setUseTechnicalReports(boolean value) {
        prefs.putBoolean(prefix + USE_TECHNICAL_REPORTS, value);
        sync();
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


    public String getTemplateHeadUrl() {
        if(api.containsKey("informea.template.url.head")) {
            return api.getProperty("informea.template.url.head");
        }
        return "http://www.informea.org/templates/get?part=head";
    }

    public String getTemplateHeaderUrl() {
        if(api.containsKey("informea.template.url.header")) {
            return api.getProperty("informea.template.url.header");
        }
        return "http://www.informea.org/templates/get?part=header";
    }

    public String getTemplateFooterUrl() {
        if(api.containsKey("informea.template.url.footer")) {
            return api.getProperty("informea.template.url.footer");
        }
        return "http://www.informea.org/templates/get?part=footer";
    }


    public File getTemplateCacheDirectory() {
        if(api.containsKey("informea.template.url.footer")) {
            String prop = api.getProperty("informea.template.cache.dir");
            File f = new File(prop);
            if(!f.exists()) {
                log.warning(String.format("Cache directory %s does not exists! Caching of remote resources is disabled (see informea.api.properties file)", prop));
                return null;
            }
            if(!f.isDirectory()) {
                log.warning(String.format("Cache directory %s is not a directory! Caching of remote resources is disabled (see informea.api.properties file)", prop));
                return null;
            }
            if(!f.canWrite()) {
                log.warning(String.format("Cache directory %s is not writable! Caching of remote resources is disabled (see informea.api.properties file)", prop));
                return null;
            }
            log.info(String.format("Using %s as cached directory", f.getAbsolutePath()));
            return f;
        }
        return null;
    }


    /**
     * @return JDBC url depending on db_type. Currently MySQL is supported
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
        prefs.put(prefix + DB_TYPE, (String)session.getAttribute(DB_TYPE));
        prefs.put(prefix + DB_HOST, (String)session.getAttribute(DB_HOST));
        prefs.put(prefix + DB_USER, (String)session.getAttribute(DB_USER));
        prefs.put(prefix + DB_PASS, (String)session.getAttribute(DB_PASS));
        prefs.put(prefix + DB_DATABASE, (String)session.getAttribute(DB_DATABASE));
        prefs.putInt(prefix + DB_PORT, (Integer)session.getAttribute(DB_PORT));

        if(session.getAttribute(USE_DECISIONS) != null) {
            prefs.putBoolean(prefix + USE_DECISIONS, (Boolean)session.getAttribute(USE_DECISIONS));
        }

        if(session.getAttribute(USE_MEETINGS) != null) {
            prefs.putBoolean(prefix + USE_MEETINGS, (Boolean)session.getAttribute(USE_MEETINGS));
        }

        if(session.getAttribute(USE_CONTACTS) != null) {
            prefs.putBoolean(prefix + USE_CONTACTS, (Boolean)session.getAttribute(USE_CONTACTS));
        }

        if(session.getAttribute(USE_COUNTRY_REPORTS) != null) {
            prefs.putBoolean(prefix + USE_COUNTRY_REPORTS, (Boolean)session.getAttribute(USE_COUNTRY_REPORTS));
        }

        if(session.getAttribute(USE_COUNTRY_PROFILES) != null) {
            prefs.putBoolean(prefix + USE_COUNTRY_PROFILES, (Boolean)session.getAttribute(USE_COUNTRY_PROFILES));
        }

        if(session.getAttribute(USE_NATIONAL_PLANS) != null) {
            prefs.putBoolean(prefix + USE_NATIONAL_PLANS, (Boolean)session.getAttribute(USE_NATIONAL_PLANS));
        }

        if(session.getAttribute(USE_SITES) != null) {
            prefs.putBoolean(prefix + USE_SITES, (Boolean)session.getAttribute(USE_SITES));
        }

        if(session.getAttribute(USE_PROJECTS) != null) {
            prefs.putBoolean(prefix + USE_PROJECTS, (Boolean)session.getAttribute(USE_PROJECTS));
        }

        if(session.getAttribute(USE_BEST_PRACTICES) != null) {
            prefs.putBoolean(prefix + USE_BEST_PRACTICES, (Boolean)session.getAttribute(USE_BEST_PRACTICES));
        }

        if(session.getAttribute(USE_TECHNICAL_REPORTS) != null) {
            prefs.putBoolean(prefix + USE_TECHNICAL_REPORTS, (Boolean)session.getAttribute(USE_TECHNICAL_REPORTS));
        }

        if(session.getAttribute(URL_PEBLDS_FILES) != null){
            prefs.put(prefix + URL_PEBLDS_FILES, session.getAttribute(URL_PEBLDS_FILES).toString());
        }

        if(session.getAttribute(PATH_PEBLDS_FILES) != null){
            prefs.put(prefix + PATH_PEBLDS_FILES, session.getAttribute(PATH_PEBLDS_FILES).toString());
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
        prefs.remove(prefix + USE_PROJECTS);
        prefs.remove(prefix + USE_TECHNICAL_REPORTS);
        prefs.remove(prefix + USE_BEST_PRACTICES);
        prefs.remove(prefix + PATH_PEBLDS_FILES);
        prefs.remove(prefix + URL_PEBLDS_FILES);
        sync();
    }

    public String getURLPebldsFiles(){
        return prefs.get(prefix + URL_PEBLDS_FILES, null);
    }

    public String getPathPebldsFiles(){
        return prefs.get(prefix + PATH_PEBLDS_FILES, null);
    }
}
