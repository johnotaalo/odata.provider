package org.informea.odata.data;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.informea.odata.IContact;
import org.informea.odata.ICountryProfile;
import org.informea.odata.ICountryReport;
import org.informea.odata.IDecision;
import org.informea.odata.IMeeting;
import org.informea.odata.INationalPlan;
import org.informea.odata.ISite;
import org.informea.odata.config.Configuration;
import org.informea.odata.constants.EntityType;
import org.informea.odata.data.db.DatabaseDataProvider;
import org.informea.odata.data.ldap.LDAPDataProvider;
import org.informea.odata.pojo.AbstractContact;
import org.informea.odata.pojo.AbstractCountryProfile;
import org.informea.odata.pojo.AbstractCountryReport;
import org.informea.odata.pojo.AbstractDecision;
import org.informea.odata.pojo.AbstractMeeting;
import org.informea.odata.pojo.AbstractNationalPlan;
import org.informea.odata.pojo.AbstractSite;
import org.informea.odata.producer.InvalidValueException;

import com.google.gson.Gson;

public class DataProviderFactory {

    private static final Logger log = Logger.getLogger(DataProviderFactory.class.getName());

    public static IDataProvider getDataProvider(Class entity) throws InvalidValueException {
        IDataProvider ret = null;
        String entityClassName = entity.getName();
        try {
            Configuration cfg = Configuration.getInstance();
            String settingsStr = cfg.getString(Configuration.DATA_PROVIDERS);
            Gson json = new Gson();
            Map<String, String> mappings = json.fromJson(settingsStr, new HashMap<String, String>().getClass());
            if(mappings == null) {
                log.warning(String.format("Could not decode data providers mappings."));
            } else if(mappings.containsKey(entityClassName)) {
                String value = mappings.get(entityClassName);
                if(DatabaseDataProvider.class.getName().equals(value)) {
                    ret = new DatabaseDataProvider();
                } else if(LDAPDataProvider.class.getName().equals(value)) {
                    ret = new LDAPDataProvider();
                } else {
                    throw new InvalidValueException(String.format("Data provider: %s is not supported. Please check your configuration", value));
                }
            } else {
                log.warning(String.format("Entity %s is not mapped to any provider", entityClassName));
            }
        } catch(InvalidValueException ex) {
            throw ex;
        } catch(Exception ex) {
            log.log(Level.WARNING, String.format("Exception while mapping the data providers"), ex);
        }
        if(ret == null) {
            // In case of an error, return the DatabaseDataProvider for backwards compatibility
            ret = new DatabaseDataProvider();
        }
        return ret;
    }


    public static IDataProvider getDataProvider(String entitySetName) throws InvalidValueException {
        Class entity = null;
        if(AbstractDecision.COLLECTION_NAME.contains(entitySetName)) {
            entity = IDecision.class;
        } else if (AbstractContact.COLLECTION_NAME.equals(entitySetName)) {
            entity = IContact.class;
        } else if (AbstractCountryProfile.COLLECTION_NAME.equals(entitySetName)) {
            entity = ICountryProfile.class;
        } else if (AbstractCountryReport.COLLECTION_NAME.equals(entitySetName)) {
            entity = ICountryReport.class;
        } else if (AbstractMeeting.COLLECTION_NAME.equals(entitySetName)) {
            entity = IMeeting.class;
        } else if (AbstractNationalPlan.COLLECTION_NAME.equals(entitySetName)) {
            entity = INationalPlan.class;
        } else if (AbstractSite.COLLECTION_NAME.equals(entitySetName)) {
            entity = ISite.class;
        } else {
            throw new InvalidValueException(String.format("Unsupported collection name: %s", entitySetName));
        }
        return getDataProvider(entity);
    }

    public static IDataProvider getDataProvider(EntityType entityType) throws InvalidValueException {
        Class entity = null;
        if(entityType == null) {
            throw new InvalidValueException("Unsupported collection name: null");
        }
        switch(entityType) {
            case DECISIONS:
                entity = IDecision.class;
                break;
            case CONTACTS:
                entity = IContact.class;
                break;
            case COUNTRY_PROFILES:
                entity = ICountryProfile.class;
                break;
            case COUNTRY_REPORTS:
                entity = ICountryReport.class;
                break;
            case MEETINGS:
                entity = IMeeting.class;
                break;
            case NATIONAL_PLANS:
                entity = INationalPlan.class;
                break;
            case SITES:
                entity = ISite.class;
                break;
            default:
                throw new InvalidValueException(String.format("Unsupported collection name: %s", entityType.toString()));
        }
        return getDataProvider(entity);
    }
}
