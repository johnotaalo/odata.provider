package org.informea.odata.config;

import javax.servlet.http.HttpServletRequest;

import org.informea.odata.util.ToolkitUtil;

public class DatabaseConfiguration {

    /**
     * Constant, value: informea.db_type
     */
    public static final String DB_TYPE = "db_type";

    /**
     * Constant, value: informea.db_host
     */
    public static final String DB_HOST = "db_host";

    /**
     * Constant, value: informea.db_port
     */
    public static final String DB_PORT = "db_port";

    /**
     * Constant, value: informea.db_user
     */
    public static final String DB_USER = "db_user";

    /**
     * Constant, value: informea.db_pass
     */
    public static final String DB_PASS = "db_pass";

    /**
     * Constant, value: informea.db_database
     */
    public static final String DB_DATABASE = "db_database";

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(obj == null || !(obj instanceof DatabaseConfiguration)) {
            return false;
        }

        DatabaseConfiguration o = (DatabaseConfiguration)obj;
        if(!ToolkitUtil.compareStrings(type, o.getType())) {
            return false;
        }
        if(!ToolkitUtil.compareStrings(host, o.getHost())) {
            return false;
        }
        if(port != o.getPort()) {
            return false;
        }
        if(!ToolkitUtil.compareStrings(user, o.getUser())) {
            return false;
        }
        if(!ToolkitUtil.compareStrings(password, o.getPassword())) {
            return false;
        }
        if(!ToolkitUtil.compareStrings(database, o.getDatabase())) {
            return false;
        }
        return true;
    }

    private String type;
    private String host;
    private int port;
    private String user;
    private String password;
    private String database;

    public DatabaseConfiguration() {
        // no arguments constructor
    }

    public String getType() {
        return type;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }

    /**
     * @return JDBC driver name depending on db_type. Currently MySQL is supported
     */
    public String getJDBCDriver() {
        return type;
    }

    /**
     * @return JDBC URL depending on db_type. Currently MySQL is supported
     */
    public String getJDBCUrl() {
        if("com.mysql.jdbc.Driver".equals(type)) {
            return String.format("jdbc:mysql://%s:%s/%s", host, port, database);
        }
        return null;
    }

    /**
     * @return Hibernate dialect depending on db_type. Currently MySQL is supported
     */
    public String getHibernateDialect() {
        if("com.mysql.jdbc.Driver".equals(type)) {
            return "org.hibernate.dialect.MySQLDialect";
        }
        return null;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public static DatabaseConfiguration fromRequest(HttpServletRequest request) {
        String db_type = ToolkitUtil.getRequestValue(DatabaseConfiguration.DB_TYPE, request);
        String db_host = ToolkitUtil.getRequestValue(DatabaseConfiguration.DB_HOST, request);
        int db_port = ToolkitUtil.getRequestInteger(DatabaseConfiguration.DB_PORT, request);
        String db_user = ToolkitUtil.getRequestValue(DatabaseConfiguration.DB_USER, request);
        String db_pass = ToolkitUtil.getRequestValue(DatabaseConfiguration.DB_PASS, request);
        String db_database = ToolkitUtil.getRequestValue(DatabaseConfiguration.DB_DATABASE, request);
        DatabaseConfiguration db = new DatabaseConfiguration();
        db.setDatabase(db_database);
        db.setHost(db_host);
        db.setPassword(db_pass);
        db.setPort(db_port);
        db.setType(db_type);
        db.setUser(db_user);
        return db;
    }
}
