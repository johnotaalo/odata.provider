package org.informea.odata.producer.toolkit.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.criterion.Order;
import org.informea.odata.Configuration;
import org.informea.odata.IContact;
import org.informea.odata.constants.Treaty;
import org.informea.odata.producer.toolkit.IDataProvider;
import org.odata4j.producer.QueryInfo;

import sun.util.calendar.BaseCalendar.Date;

import com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPSearchException;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchScope;

public class LDAPDataProvider implements IDataProvider {

    private static final Logger log = Logger.getLogger(LDAPDataProvider.class.getName());

    LDAPConnection conn = null;

    @Override
    public void openResources() {
        if(conn == null) {
            Configuration cfg = Configuration.getInstance();
            try {
                conn = new LDAPConnection(
                        cfg.getString(Configuration.LDAP_HOST),
                        cfg.getInt(Configuration.LDAP_PORT));
                conn.bind(cfg.getString(Configuration.LDAP_BIND_DN),
                        cfg.getString(Configuration.LDAP_PASSWORD)
                        );
            } catch(LDAPException ex) {
                log.log(Level.SEVERE, "Cannot connect to LDAP server", ex);
            }
        }
    }

    @Override
    public void closeResources() {
        if(conn != null) {
            conn.close();
        }
    }

    @Override
    public Integer countPrimaryEntities(Class entityClass, QueryInfo q) {
        int ret = 0;
        Configuration cfg = Configuration.getInstance();
        try {
            if(conn == null || !conn.isConnected()) {
                openResources();
            }
            SearchResult results = conn.search(
                    cfg.getString(Configuration.LDAP_USERS_BASE_DN), SearchScope.SUB,
                    cfg.getString(Configuration.LDAP_USERS_FILTER),
                    "ldapentrycount"
                    );
            ret = results.getEntryCount();
        } catch(LDAPSearchException ex) {
            log.log(Level.WARNING, "countPrimaryEntities(): Failed to retrieve LDAP search results", ex);
        }
        return ret;
    }

    @Override
    public List getPrimaryEntities(Class entityClass, QueryInfo query,
            int startResult, Integer pageSize, Order orderBy) {
        List<IContact> ret = new ArrayList<IContact>();
        Configuration cfg = Configuration.getInstance();
        int actualPageSize = getPageSize(pageSize);
        try {
            if(conn == null || !conn.isConnected()) {
                openResources();
            }
            SearchResult results = conn.search(
                    cfg.getString(Configuration.LDAP_USERS_BASE_DN), SearchScope.SUB,
                    cfg.getString(Configuration.LDAP_USERS_FILTER)
                    );
            List<IContact> items = fromSearchResult(results);
            // Sanity checks
            int start = startResult;
            if(start < 0) {
                start = 0;
            }

            int end = start + actualPageSize;
            if(end > items.size()) {
                end = items.size();
            }
            log.info(String.format("Start result: %s", start));
            log.info(String.format("End result: %s", end));
            ret = items.subList(start, end);
        } catch(LDAPSearchException ex) {
            log.log(Level.WARNING, "countPrimaryEntities(): Failed to retrieve LDAP search results", ex);
        }
        return ret;
    }

    @Override
    public Object getPrimaryEntity(Class entityClass, String id) {
        IContact ret = null;
        Configuration cfg = Configuration.getInstance();
        try {
            if(conn == null || !conn.isConnected()) {
                openResources();
            }
            SearchResultEntry item = conn.searchForEntry(
                    cfg.getString(Configuration.LDAP_USER_BASE_DN),
                    SearchScope.SUB,
                    String.format(cfg.getString(Configuration.LDAP_USER_FILTER), id)
                    );
            if(item != null) {
                ret = fromSearchResultEntry(item);
            }
        } catch(LDAPSearchException ex) {
            log.log(Level.WARNING, "countPrimaryEntities(): Failed to retrieve LDAP search results", ex);
        }
        return ret;
    }

    @Override
    public Object getEntity(Class entityClass, Object id) {
        log.info("getEntity(): Calling this method on LDAP data provider has no effect");
        return null;
    }

    /**
     * This method is used by test framework
     * @return
     */
    protected LDAPConnection getConnection() {
        return conn;
    }

    /**
     * Sanitize the request pageSize based on defaults to prevent server overload by retrieving a large result set.
     * @param requestedPageSize The requested pageSize
     * @return Sanitized pageSize
     */
    protected int getPageSize(int requestedPageSize) {
        int ret = 100;
        int maxPageSize = Configuration.getInstance().getInt(Configuration.LDAP_MAX_PAGE_SIZE);
        if(maxPageSize > 0) {
            ret = maxPageSize;
        }
        if(requestedPageSize > 0 && requestedPageSize < ret) {
            ret = requestedPageSize;
        }
        return ret;
    }

    /**
     * Transform an array from LDAP SearchResultEntry to IContact items
     * @param entries List of entries
     * @return Corresponding IContact entities
     */
    public List<IContact> fromSearchResult(SearchResult entries) {
        List<IContact> ret = new ArrayList<IContact>();
        for(SearchResultEntry row: entries.getSearchEntries()) {
            ret.add(this.fromSearchResultEntry(row));
        }
        return ret;
    }

    /**
     * Transform a single entity from LDAP SearchResultEntry into IContact entity.
     * It is based on <code>Configuration</code> defined mappings (<code>Configuration.LDAP_MAPPING_*</code>)
     * @param ob The LDAP entry
     * @return Corresponding IContact object
     */
    public IContact fromSearchResultEntry(SearchResultEntry ob) {
        if(ob == null) {
            return null;
        }
        Configuration cfg = Configuration.getInstance();
        LDAPContact ret = new LDAPContact();
        ret.setId(ob.getDN());

        String key = cfg.getString(Configuration.LDAP_MAPPING_PREFIX);
        if(key != null && ob.hasAttribute(key)) {
            ret.setPrefix(ob.getAttributeValue(key));
        }

        key = cfg.getString(Configuration.LDAP_MAPPING_FIRST_NAME);
        if(key != null && ob.hasAttribute(key)) {
            ret.setFirstName(ob.getAttributeValue(key));
        }

        key = cfg.getString(Configuration.LDAP_MAPPING_LAST_NAME);
        if(key != null && ob.hasAttribute(key)) {
            ret.setLastName(ob.getAttributeValue(key));
        }

        key = cfg.getString(Configuration.LDAP_MAPPING_ADDRESS);
        if(key != null && ob.hasAttribute(key)) {
            ret.setAddress(ob.getAttributeValue(key));
        }

        key = cfg.getString(Configuration.LDAP_MAPPING_COUNTRY);
        if(key != null && ob.hasAttribute(key)) {
            ret.setCountry(ob.getAttributeValue(key));
        }

        key = cfg.getString(Configuration.LDAP_MAPPING_DEPARTMENT);
        if(ob.hasAttribute(key)) {
            ret.setDepartment(ob.getAttributeValue(key));
        }

        key = cfg.getString(Configuration.LDAP_MAPPING_EMAIL);
        if(key != null && ob.hasAttribute(key)) {
            ret.setEmail(ob.getAttributeValue(key));
        }

        key = cfg.getString(Configuration.LDAP_MAPPING_FAX);
        if(key != null && ob.hasAttribute(key)) {
            ret.setFax(ob.getAttributeValue(key));
        }

        key = cfg.getString(Configuration.LDAP_MAPPING_INSTITUTION);
        if(key != null && ob.hasAttribute(key)) {
            ret.setInstitution(ob.getAttributeValue(key));
        }

        key = cfg.getString(Configuration.LDAP_MAPPING_PHONE);
        if(key != null && ob.hasAttribute(key)) {
            ret.setPhoneNumber(ob.getAttributeValue(key));
        }

        key = cfg.getString(Configuration.LDAP_MAPPING_POSITION);
        if(key != null && ob.hasAttribute(key)) {
            ret.setPosition(ob.getAttributeValue(key));
        }

        //TODO:
        ret.setPrimary(false);
        key = cfg.getString(Configuration.LDAP_MAPPING_UPDATED);
        if(key != null && ob.hasAttribute(key)) {
            java.util.Date d = ob.getAttributeValueAsDate(key);
            ret.setUpdated(d);
        }

        key = cfg.getString(Configuration.LDAP_MAPPING_TREATIES);
        List<Treaty> treaties = new ArrayList<Treaty>();
        if(key != null && ob.hasAttribute(key)) {
            String[] items = ob.getAttributeValues(key);
            for(String row: items) {
                try {
                    Treaty t = Treaty.getTreaty(row);
                    treaties.add(t);
                } catch(Exception ex) {
                    log.log(Level.WARNING,
                            String.format("Unable to parse %s property with value %s", key, row),
                            ex);
                }
            }
            if(treaties.size() == 0) {
                log.warning(
                        String.format("Entry with DN:%s does not have any treaty set!", ret.getId()));
            }
            ret.setTreaties(treaties);
        }
        return ret;
    }
}
