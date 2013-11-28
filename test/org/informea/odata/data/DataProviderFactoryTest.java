package org.informea.odata.data;
import static org.junit.Assert.*;

import org.informea.odata.Configuration;
import org.informea.odata.IContact;
import org.informea.odata.IDecision;
import org.informea.odata.constants.EntityType;
import org.informea.odata.data.DataProviderFactory;
import org.informea.odata.pojo.AbstractContact;
import org.informea.odata.producer.InvalidValueException;
import org.informea.odata.producer.toolkit.IDataProvider;
import org.junit.Test;


public class DataProviderFactoryTest {

    @Test
    public void testGetDataProviderDefaults() {
        Configuration cfg = Configuration.getInstance();
        cfg.setString(Configuration.DATA_PROVIDERS, null);
        IDataProvider s = DataProviderFactory.getDataProvider(IDecision.class);
        assertNotNull(s);
        assertTrue(s instanceof DatabaseDataProvider);
    }

    @Test
    public void testGetDataProviderDatabase() {
        Configuration cfg = Configuration.getInstance();
        cfg.setString(Configuration.DATA_PROVIDERS, "{\"org.informea.odata.IContact\" : \"org.informea.odata.producer.toolkit.impl.LDAPDataProvider\"}");
        IDataProvider s = DataProviderFactory.getDataProvider(IContact.class);
        assertNotNull(s);
        assertTrue(s instanceof LDAPDataProvider);
    }

    @Test(expected=InvalidValueException.class)
    public void testGetDataProviderInvalidParam() throws InvalidValueException {
        Configuration cfg = Configuration.getInstance();
        cfg.setString(Configuration.DATA_PROVIDERS, "{\"org.informea.odata.IContact\" : \"java.lang.String\"}");
        DataProviderFactory.getDataProvider(IContact.class);
    }

    @Test
    public void testGetDataProviderStringParam() {
        Configuration cfg = Configuration.getInstance();
        cfg.setString(Configuration.DATA_PROVIDERS, "{\"org.informea.odata.IContact\" : \"org.informea.odata.producer.toolkit.impl.LDAPDataProvider\"}");
        IDataProvider s = DataProviderFactory.getDataProvider(AbstractContact.COLLECTION_NAME);
        assertNotNull(s);
        assertTrue(s instanceof LDAPDataProvider);
    }

    @Test(expected=InvalidValueException.class)
    public void testGetDataProviderStringParamInvalid() {
        DataProviderFactory.getDataProvider("Just breakin' things up");
    }

    @Test
    public void testGetDataProviderEntityTypeParam() {
        Configuration cfg = Configuration.getInstance();
        cfg.setString(Configuration.DATA_PROVIDERS, "{\"org.informea.odata.IContact\" : \"org.informea.odata.producer.toolkit.impl.LDAPDataProvider\"}");
        IDataProvider s = DataProviderFactory.getDataProvider(EntityType.CONTACTS);
        assertNotNull(s);
        assertTrue(s instanceof LDAPDataProvider);
    }

    @Test(expected=InvalidValueException.class)
    public void testGetDataProviderEntityTypeParamNull() {
        DataProviderFactory.getDataProvider((EntityType)null);
    }
}
