package org.informea.odata.config;

import org.informea.odata.util.ToolkitUtil;

public class DatabaseConfiguration {

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
}
