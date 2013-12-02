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
package org.informea.odata.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.informea.odata.config.DatabaseConfiguration;


/**
 * Execute database operations through direct JDBC access
 * <br />
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 1.3.3
 */
public class JDBCHelper {
    private static final Logger log = Logger.getLogger(JDBCHelper.class.getName());

    /**
     * Constant, value: "mysql"
     */
    public static final String DB_TYPE_MYSQL = "com.mysql.jdbc.Driver";

    /**
     * Constant, value "postgresql"
     */
    public static final String DB_TYPE_POSTGRESQL = "org.postgresql.Driver";

    private String db_type;
    private String db_host;
    private int db_port;
    private String db_user;
    private String db_pass;
    private String db_database;

    @SuppressWarnings("rawtypes")
    private List cache_db_tables = null;


    /**
     * Constructor
     * @param db_type Database type
     * @param db_host Database server hostname or IP address
     * @param db_port Database port
     * @param db_user Database username
     * @param db_pass Database password
     * @param db_database Database name
     */
    public JDBCHelper(String db_type, String db_host, int db_port, String db_user, String db_pass, String db_database) {
        this.db_type = db_type;
        this.db_host = db_host;
        this.db_port = db_port;
        this.db_user = db_user;
        this.db_pass = db_pass;
        this.db_database = db_database;
    }

    public JDBCHelper(DatabaseConfiguration db) {
        this(db.getType(), db.getHost(), db.getPort(), db.getUser(), db.getPassword(), db.getDatabase());
    }

    /**
     * Check if the database connectivity is OK
     * @return true if connection is working
     * @throws Exception If cannot initialize connection
     */
    public boolean validateDBConnection() throws Exception {
        boolean ret;
        Connection conn;
        log.info("Testing database connection");
        if("".equalsIgnoreCase(db_type)) {
            throw new Exception("Database type not specified");
        }
        if("".equalsIgnoreCase(db_database)) {
            throw new Exception("Database name not specified");
        }
        conn = getDBConnection();
        /* TODO: This is only valid for MySQL.
        conn.prepareStatement(
                String.format("USE %s", db_database)
                ).execute();
         */
        conn.prepareStatement(
                String.format("SELECT 1", db_database)
                ).execute();
        ret = true;
        log.info("Successfully connected to database");
        close(conn);
        return ret;
    }


    /**
     * Retrieve a connection to the database
     * @return Connection object ready for use
     * @throws Exception If cannot initialize connection
     */
    public Connection getDBConnection() throws Exception {
        Connection conn = null;
        if (DB_TYPE_MYSQL.equals(db_type)) {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                if(db_port == 0) {
                    db_port = 3306;
                }
                String sqlUrl = String.format("jdbc:mysql://%s:%s/%s?zeroDateTimeBehavior=convertToNull", db_host, db_port, db_database);
                log.fine(String.format("Using JDBC url: %s", sqlUrl));
                conn = DriverManager.getConnection(sqlUrl, db_user, db_pass);
            } catch (Exception e) {
                throw e;
            }
        } else if (DB_TYPE_POSTGRESQL.equals(db_type)) {
            try {
                Class.forName("org.postgresql.Driver").newInstance();
                if(db_port == 0) {
                    db_port = 5432;
                }
                String sqlUrl = String.format("jdbc:postgresql://%s:%s/%s", db_host, db_port, db_database);
                log.fine(String.format("Using JDBC url: %s", sqlUrl));
                conn = DriverManager.getConnection(sqlUrl, db_user, db_pass);
            } catch (Exception e) {
                throw e;
            }
        }
        return conn;
    }


    /**
     * Safely close a database connection object
     * @param conn
     */
    public static void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {} finally {

        }
    }


    /**
     * Check if the required tables for decisions primary entity are present
     * @return true if all tables are present
     */
    public boolean detectDecisions() {
        List<String> tables = getTables();
        return tables.contains("informea_decisions") && tables.contains("informea_decisions_content")
                && tables.contains("informea_decisions_documents") && tables.contains("informea_decisions_keywords")
                && tables.contains("informea_decisions_longtitle") && tables.contains("informea_decisions_summary")
                && tables.contains("informea_decisions_title");
    }


    /**
     * Check if the required tables for meetings primary entity are present
     * @return true if all tables are present
     */
    public boolean detectMeetings() {
        List<String> tables = getTables();
        return tables.contains("informea_meetings") && tables.contains("informea_meetings_description")
                && tables.contains("informea_meetings_title");
    }


    /**
     * Check if the required tables for contacts primary entity are present
     * @return true if all tables are present
     */
    public boolean detectContacts() {
        List<String> tables = getTables();
        return tables.contains("informea_contacts") && tables.contains("informea_contacts_treaties");
    }


    /**
     * Check if the required tables for country reports primary entity are present
     * @return true if all tables are present
     */
    public boolean detectCountryReports() {
        List<String> tables = getTables();
        return tables.contains("informea_country_reports") && tables.contains("informea_country_reports_title");
    }


    /**
     * Check if the required tables for country profiles primary entity are present
     * @return true if all tables are present
     */
    public boolean detectCountryProfiles() {
        List<String> tables = getTables();
        return tables.contains("informea_country_profiles");
    }


    /**
     * Check if the required tables for national plans primary entity are present
     * @return true if all tables are present
     */
    public boolean detectNationalPlans() {
        List<String> tables = getTables();
        return tables.contains("informea_national_plans") && tables.contains("informea_national_plans_title");
    }


    /**
     * Check if the required tables for sites primary entity are present
     * @return true if all tables are present
     */
    public boolean detectSites() {
        List<String> tables = getTables();
        return tables.contains("informea_sites");
    }


    /**
     * Check if all the database required for toolkit are missing
     * @return true if all tables are missing
     */
    public boolean isMissingAllEntities() {
        return !detectDecisions() && !detectMeetings() && !detectContacts() && !detectCountryReports()
                && !detectCountryProfiles() && !detectNationalPlans() && !detectSites();
    }


    /**
     * Retrieve the list of tables from database
     * @return List of tables. Uses internal cache to avoid DB overhead
     */
    @SuppressWarnings("unchecked")
    public List<String> getTables() {
        if(cache_db_tables != null && cache_db_tables.size() > 0) {
            return cache_db_tables;
        }
        List<String> ret = new ArrayList<String>(40);
        Connection conn = null;
        try {
            if (DB_TYPE_MYSQL.equals(db_type)) {
                conn = getDBConnection();
                PreparedStatement stmt = conn.prepareStatement("SHOW TABLES");
                ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    ret.add(rs.getString(1));
                }
            } else if (DB_TYPE_POSTGRESQL.equals(db_type)) {
                conn = getDBConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT viewname FROM pg_views");
                ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    ret.add(rs.getString(1));
                }
            }
            cache_db_tables = ret;
        } catch (Exception e) {
            // noop
        } finally {
            close(conn);
        }
        return ret;
    }


    /**
     * Execute SQL queries on current database. Data manipulatin statements are not possible with this method.
     * @param statement SQL statement
     * @return Table as list of lists
     */
    public List<List<String>> executeQuery(String statement) throws Exception {
        List<List<String>> ret = new ArrayList<List<String>>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getDBConnection();
            stmt = conn.prepareStatement(statement);
            rs = stmt.executeQuery(statement);
            ResultSetMetaData md = rs.getMetaData();
            int c = md.getColumnCount();
            int types[] = new int[c + 1];
            List<String> columns = new ArrayList<String>(c);
            for(int i = 1; i <= c; i++) {
                columns.add(md.getColumnName(i));
                types[i] = md.getColumnType(i);
            }
            ret.add(columns);
            while(rs.next()) {
                List<String> row = new ArrayList<String>();
                for(int i = 1; i <= c; i++) {
                    row.add(rs.getString(i));
                }
                ret.add(row);
            }
        } finally {
            if(rs != null) { try { rs.close(); } catch(Exception ex) {}}
            if(stmt != null) { try { stmt.close(); } catch(Exception ex) {}}
            close(conn);
        }
        close(conn);
        return ret;
    }
}
