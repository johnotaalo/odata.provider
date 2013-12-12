package org.informea.odata.config;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;

import org.informea.odata.util.ToolkitUtil;
import org.odata4j.producer.QueryInfo;

import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchScope;

public class LDAPConfiguration {

    public static final String LDAP_HOST = "ldap_host";
    public static final String LDAP_PORT = "ldap_port";
    public static final String LDAP_BIND_DN = "ldap_bindDN";
    public static final String LDAP_PASSWORD = "ldap_password";
    public static final String LDAP_USER_BASE_DN = "ldap_userBaseDN";
    public static final String LDAP_USER_QUERY_FILTER = "ldap_userQueryFilter";
    public static final String LDAP_ID_ATTRIBUTE = "ldap_idAttribute";

    public static final String LDAP_MAX_PAGE_SIZE = "ldap_maxPageSize";

    public static final String LDAP_USERS_BASE_DN = "ldap_usersBaseDN";
    public static final String LDAP_USERS_QUERY_FILTER = "ldap_usersQueryFilter";

    public static final String LDAP_USE_TLS = "ldap_useTLS";
    public static final String LDAP_USE_SSL = "ldap_useSSL";

    public static final String LDAP_MAPPING_ID = "informea_ldap_mapping_id";
    public static final String LDAP_MAPPING_PREFIX = "informea_ldap_mapping_personalTitle";
    public static final String LDAP_MAPPING_FIRST_NAME = "informea_ldap_mapping_firstName";
    public static final String LDAP_MAPPING_LAST_NAME = "informea_ldap_mapping_lastName";
    public static final String LDAP_MAPPING_ADDRESS = "informea_ldap_mapping_address";
    public static final String LDAP_MAPPING_COUNTRY = "informea_ldap.mapping_country";
    public static final String LDAP_MAPPING_DEPARTMENT = "informea_ldap_mapping_department";
    public static final String LDAP_MAPPING_EMAIL = "informea_ldap_mapping_email";
    public static final String LDAP_MAPPING_FAX = "informea_ldap_mapping_fax";
    public static final String LDAP_MAPPING_INSTITUTION = "informea_ldap_mapping_institution";
    public static final String LDAP_MAPPING_PHONE = "informea_ldap_mapping_phone";
    public static final String LDAP_MAPPING_POSITION = "informea_ldap_mapping_position";
    public static final String LDAP_MAPPING_UPDATED = "informea_ldap_mapping_updated";
    public static final String LDAP_MAPPING_TREATIES = "informea_ldap_mapping_treaties";
    public static final String LDAP_MAPPING_PRIMARY_NFP = "informea_ldap_mapping_primaryNFP";

    private String host;
    private int port;
    private String bindDN;
    private String password;

    private String userBaseDN;
    private String userQueryFilter;

    private String usersBaseDN;
    private String usersQueryFilter;

    private Map<String, String> mappings = new HashMap<String, String>();
    private int maxPageSize;

    private boolean useSSL;
    private boolean useTLS;

    public LDAPConfiguration() {
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
        if(!ToolkitUtil.compareStrings(usersBaseDN, o.getUsersBaseDN())) {
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
        if(!mappings.equals(o.getMappings())) {
            return false;
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

    public String getUsersBaseDN() {
        return usersBaseDN;
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
        if(mappings.containsKey(key)) {
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

    public String getUserIdAttribute() {
        return mappings.get(LDAP_MAPPING_ID);
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

    public void setUsersBaseDN(String usersBaseDN) {
        this.usersBaseDN = usersBaseDN;
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

    public static LDAPConfiguration fromHttpRequest(HttpServletRequest request) {
        LDAPConfiguration ret = new LDAPConfiguration();

        ret.setHost(ToolkitUtil.getRequestValue(LDAPConfiguration.LDAP_HOST, request));
        ret.setPort(ToolkitUtil.getRequestInteger(LDAPConfiguration.LDAP_PORT, request));
        ret.setBindDN(ToolkitUtil.getRequestValue(LDAPConfiguration.LDAP_BIND_DN, request));
        ret.setPassword(ToolkitUtil.getRequestValue(LDAPConfiguration.LDAP_PASSWORD, request));
        ret.setUserBaseDN(ToolkitUtil.getRequestValue(LDAPConfiguration.LDAP_USER_BASE_DN, request));
        ret.setUserQueryFilter(ToolkitUtil.getRequestValue(LDAPConfiguration.LDAP_USER_QUERY_FILTER, request));
        ret.setUsersBaseDN(ToolkitUtil.getRequestValue(LDAPConfiguration.LDAP_USERS_BASE_DN, request));
        ret.setUsersQueryFilter(ToolkitUtil.getRequestValue(LDAPConfiguration.LDAP_USERS_QUERY_FILTER, request));
        ret.setUseSSL(ToolkitUtil.getRequestCheckbox(LDAPConfiguration.LDAP_USE_SSL, request));
        ret.setUseTLS(ToolkitUtil.getRequestCheckbox(LDAPConfiguration.LDAP_USE_TLS, request));
        ret.setMaxPageSize(ToolkitUtil.getRequestInteger(LDAPConfiguration.LDAP_MAX_PAGE_SIZE, request));

        ret.setMapping(LDAPConfiguration.LDAP_MAPPING_ID,
                ToolkitUtil.getRequestValue(LDAPConfiguration.LDAP_MAPPING_ID, request));
        ret.setMapping(LDAPConfiguration.LDAP_MAPPING_ADDRESS,
                ToolkitUtil.getRequestValue(LDAPConfiguration.LDAP_MAPPING_ADDRESS, request));
        ret.setMapping(LDAPConfiguration.LDAP_MAPPING_COUNTRY,
                ToolkitUtil.getRequestValue(LDAPConfiguration.LDAP_MAPPING_COUNTRY, request));
        ret.setMapping(LDAPConfiguration.LDAP_MAPPING_DEPARTMENT,
                ToolkitUtil.getRequestValue(LDAPConfiguration.LDAP_MAPPING_DEPARTMENT, request));
        ret.setMapping(LDAPConfiguration.LDAP_MAPPING_EMAIL,
                ToolkitUtil.getRequestValue(LDAPConfiguration.LDAP_MAPPING_EMAIL, request));
        ret.setMapping(LDAPConfiguration.LDAP_MAPPING_FAX,
                ToolkitUtil.getRequestValue(LDAPConfiguration.LDAP_MAPPING_FAX, request));
        ret.setMapping(LDAPConfiguration.LDAP_MAPPING_FIRST_NAME,
                ToolkitUtil.getRequestValue(LDAPConfiguration.LDAP_MAPPING_FIRST_NAME, request));
        ret.setMapping(LDAPConfiguration.LDAP_MAPPING_INSTITUTION,
                ToolkitUtil.getRequestValue(LDAPConfiguration.LDAP_MAPPING_INSTITUTION, request));
        ret.setMapping(LDAPConfiguration.LDAP_MAPPING_LAST_NAME,
                ToolkitUtil.getRequestValue(LDAPConfiguration.LDAP_MAPPING_LAST_NAME, request));
        ret.setMapping(LDAPConfiguration.LDAP_MAPPING_PHONE,
                ToolkitUtil.getRequestValue(LDAPConfiguration.LDAP_MAPPING_PHONE, request));
        ret.setMapping(LDAPConfiguration.LDAP_MAPPING_POSITION,
                ToolkitUtil.getRequestValue(LDAPConfiguration.LDAP_MAPPING_POSITION, request));
        ret.setMapping(LDAPConfiguration.LDAP_MAPPING_PREFIX,
                ToolkitUtil.getRequestValue(LDAPConfiguration.LDAP_MAPPING_PREFIX, request));
        ret.setMapping(LDAPConfiguration.LDAP_MAPPING_PRIMARY_NFP,
                ToolkitUtil.getRequestValue(LDAPConfiguration.LDAP_MAPPING_PRIMARY_NFP, request));
        ret.setMapping(LDAPConfiguration.LDAP_MAPPING_TREATIES,
                ToolkitUtil.getRequestValue(LDAPConfiguration.LDAP_MAPPING_TREATIES, request));
        ret.setMapping(LDAPConfiguration.LDAP_MAPPING_UPDATED,
                ToolkitUtil.getRequestValue(LDAPConfiguration.LDAP_MAPPING_UPDATED, request));
        return ret;
    }

    public boolean testConnection() throws LDAPException {
        LDAPConnection conn = null;
        boolean ret = true;
        try {
            conn = new LDAPConnection(getHost(), getPort());
            conn.bind(getBindDN(), getPassword());

            SearchResult results = conn.search(
                    getUserBaseDN(), SearchScope.SUB,
                    String.format("%s=*", getMapping(LDAP_MAPPING_ID)),
                    "ldapentrycount");
            int c = results.getEntryCount();
            if(c <= 0) {
                throw new RuntimeException("The search query returned zero results");
            }
        } catch(LDAPException ex) {
            throw ex;
        } finally {
            if(conn != null) {
                conn.close();
            }
        }
        return ret;
    }
}