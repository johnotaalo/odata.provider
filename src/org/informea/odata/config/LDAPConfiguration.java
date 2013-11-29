package org.informea.odata.config;

import java.util.HashMap;
import java.util.Map;

import org.informea.odata.util.ToolkitUtil;

public class LDAPConfiguration {

    public static final String LDAP_MAPPING_PREFIX = "informea.ldap.mapping.personalTitle";
    public static final String LDAP_MAPPING_FIRST_NAME = "informea.ldap.mapping.firstName";
    public static final String LDAP_MAPPING_LAST_NAME = "informea.ldap.mapping.lastName";
    public static final String LDAP_MAPPING_ADDRESS = "informea.ldap.mapping.address";
    public static final String LDAP_MAPPING_COUNTRY = "informea.ldap.mapping.country";
    public static final String LDAP_MAPPING_DEPARTMENT = "informea.ldap.mapping.department";
    public static final String LDAP_MAPPING_EMAIL = "informea.ldap.mapping.email";
    public static final String LDAP_MAPPING_FAX = "informea.ldap.mapping.fax";
    public static final String LDAP_MAPPING_INSTITUTION = "informea.ldap.mapping.institution";
    public static final String LDAP_MAPPING_PHONE = "informea.ldap.mapping.phone";
    public static final String LDAP_MAPPING_POSITION = "informea.ldap.mapping.position";
    public static final String LDAP_MAPPING_UPDATED = "informea.ldap.mapping.updated";
    public static final String LDAP_MAPPING_TREATIES = "informea.ldap.mapping.treaties";
    public static final String LDAP_MAPPING_PRIMARY_NFP = "informea.ldap.mapping.primaryNFP";

    private String host;
    private int port;
    private String bindDN;
    private String password;

    private String baseDN;
    private String usersQueryFilter;
    private String userBaseDN;
    private String userQueryFilter;

    private Map<String, String> mappings;
    private int maxPageSize;

    private boolean useSSL;
    private boolean useTLS;

    public LDAPConfiguration() {
        mappings = new HashMap<String, String>();
        // no parameter constructor
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(obj == null || !(obj instanceof LDAPConfiguration)) {
            return false;
        }

        LDAPConfiguration o = (LDAPConfiguration)obj;
        if(!ToolkitUtil.compareStrings(host, o.getHost())) {
            return false;
        }
        if(port != o.getPort()) {
            return false;
        }
        if(!ToolkitUtil.compareStrings(bindDN, o.getBindDN())) {
            return false;
        }
        if(!ToolkitUtil.compareStrings(password, o.getPassword())) {
            return false;
        }
        if(!ToolkitUtil.compareStrings(baseDN, o.getBaseDN())) {
            return false;
        }
        if(!ToolkitUtil.compareStrings(usersQueryFilter, o.getUsersQueryFilter())) {
            return false;
        }
        if(!ToolkitUtil.compareStrings(userBaseDN, o.getUserBaseDN())) {
            return false;
        }
        if(!ToolkitUtil.compareStrings(userQueryFilter, o.getUserQueryFilter())) {
            return false;
        }
        if(mappings != null && o.getMappings() != null) {
            if(!mappings.equals(o.getMappings())) {
                return false;
            }
        } else {
            if(mappings == null && o.getMappings() != null) {
                return false;
            }
        }
        if(maxPageSize != o.getMaxPageSize()) {
            return false;
        }
        if(useSSL != o.isUseSSL()) {
            return false;
        }
        if(useTLS != o.useTLS) {
            return false;
        }
        return true;
    }



    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getBindDN() {
        return bindDN;
    }

    public String getPassword() {
        return password;
    }

    public String getBaseDN() {
        return baseDN;
    }

    public String getUsersQueryFilter() {
        return usersQueryFilter;
    }

    public String getUserBaseDN() {
        return userBaseDN;
    }

    public String getUserQueryFilter() {
        return userQueryFilter;
    }

    public Map<String, String> getMappings() {
        return mappings;
    }

    public String getMapping(String key) {
        if(mappings != null && mappings.containsKey(key)) {
            return mappings.get(key);
        }
        return null;
    }

    public int getMaxPageSize() {
        return maxPageSize;
    }

    public boolean isUseSSL() {
        return useSSL;
    }

    public boolean isUseTLS() {
        return useTLS;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setBindDN(String bindDN) {
        this.bindDN = bindDN;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBaseDN(String baseDN) {
        this.baseDN = baseDN;
    }

    public void setUsersQueryFilter(String usersQueryFilter) {
        this.usersQueryFilter = usersQueryFilter;
    }

    public void setUserBaseDN(String userBaseDN) {
        this.userBaseDN = userBaseDN;
    }

    public void setUserQueryFilter(String userQueryFilter) {
        this.userQueryFilter = userQueryFilter;
    }

    public void setMapping(String key, String value) {
        mappings.put(key, value);
    }

    public void clearMappings() {
        mappings.clear();
    }

    public void setMaxPageSize(int maxPageSize) {
        this.maxPageSize = maxPageSize;
    }

    public void setUseSSL(boolean useSSL) {
        this.useSSL = useSSL;
    }

    public void setUseTLS(boolean useTLS) {
        this.useTLS = useTLS;
    }
}
