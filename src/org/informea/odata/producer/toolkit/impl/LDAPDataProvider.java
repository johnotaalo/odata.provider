package org.informea.odata.producer.toolkit.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.criterion.Order;
import org.informea.odata.Configuration;
import org.informea.odata.IContact;
import org.informea.odata.constants.Treaty;
import org.informea.odata.producer.toolkit.IDataProvider;
import org.odata4j.producer.QueryInfo;

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

    protected List<Treaty> parseTreaties(String treatyString) {
        List<Treaty> ret = new ArrayList<Treaty>();
        if(treatyString != null && !"".equalsIgnoreCase(treatyString)) {
            String items[] = treatyString.split(",");
            for(String item: items) {
                try {
                    Treaty t = Treaty.getTreaty(item.trim().replace("'", "").replace("\"", ""));
                    ret.add(t);
                } catch(Exception ex) {
                    log.log(Level.WARNING, String.format("Cannot decode treaty value %s", item), ex);
                }
            }
        }
        return ret;
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
        LDAPContact ret = new LDAPContact();
        if(ob.hasAttribute("uid")) {
            ret.setId(ob.getAttributeValue("uid"));
        }
        if(ob.hasAttribute("personalTitle")) {
            ret.setPrefix(ob.getAttributeValue("personalTitle"));
        }
        if(ob.hasAttribute("givenName")) {
            ret.setFirstName(ob.getAttributeValue("givenName"));
        }
        if(ob.hasAttribute("sn")) {
            ret.setLastName("sn");
        }
        if(ob.hasAttribute("registeredAddress")) {
            ret.setAddress("registeredAddress");
        }
        if(ob.hasAttribute("c")) {
            ret.setCountry(ob.getAttributeValue("c"));
        }
        if(ob.hasAttribute("ou")) {
            ret.setDepartment(ob.getAttributeValue("ou"));
        }
        if(ob.hasAttribute("mail")) {
            ret.setEmail("mail");
        }
        if(ob.hasAttribute("facsimileTelephoneNumber")) {
            ret.setFax(ob.getAttributeValue("facsimileTelephoneNumber"));
        }
        if(ob.hasAttribute("o")) {
            ret.setInstitution(ob.getAttributeValue("o"));
        }
        if(ob.hasAttribute("telephoneNumber")) {
            ret.setPhoneNumber(ob.getAttributeValue("telephoneNumber"));
        }
        if(ob.hasAttribute("title")) {
            ret.setPosition(ob.getAttributeValue("title"));
        }
        ret.setPrimary(false);
        if(ob.hasAttribute("lastModifiedTime")) {
            ret.setUpdated(ob.getAttributeValueAsDate("lastModifiedTime"));
        }
        if(ob.hasAttribute("carLicense")) {
            String prop = ob.getAttributeValue("carLicense");
            List<Treaty> treaties = parseTreaties(prop);
            ret.setTreaties(treaties);
        }
        return ret;
    }
}
