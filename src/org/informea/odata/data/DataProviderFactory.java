package org.informea.odata.data;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.informea.odata.Configuration;
import org.informea.odata.producer.InvalidValueException;
import org.informea.odata.producer.toolkit.IDataProvider;
import org.informea.odata.producer.toolkit.impl.DatabaseDataProvider;
import org.informea.odata.producer.toolkit.impl.LDAPDataProvider;

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
                if(DatabaseDataProvider.class.getName().equalsIgnoreCase(value)) {
                    ret = new DatabaseDataProvider();
                } else if(LDAPDataProvider.class.getName().equalsIgnoreCase(value)) {
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
}
