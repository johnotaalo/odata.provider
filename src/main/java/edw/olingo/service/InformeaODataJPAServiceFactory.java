package edw.olingo.service;

import java.util.TimeZone;

import org.apache.olingo.odata2.api.ODataCallback;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.ref.factory.JPAEntityManagerFactory;

public class InformeaODataJPAServiceFactory extends
		org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory {

	private static final String PUNIT_NAME = "persistence_unit";
	// private static final int PAGE_SIZE = 100;

	@Override
	public ODataJPAContext initializeODataJPAContext()
			throws ODataJPARuntimeException {
		// Assume that all dates received in InforMEA are given in UTC (OData
		// default?).
		TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
		ODataJPAContext oDataJPAContext = getODataJPAContext();
		oDataJPAContext.setEntityManagerFactory(JPAEntityManagerFactory
				.getEntityManagerFactory(PUNIT_NAME));
		oDataJPAContext.setPersistenceUnitName(PUNIT_NAME);
		// oDataJPAContext.setPageSize(PAGE_SIZE);
		// oDataJPAContext.setDefaultNaming(true);
		setDetailErrors(true);
		oDataJPAContext.setJPAEdmExtension(new InformeaJPAExtension());
		oDataJPAContext.setJPAEdmMappingModel("jpa_edm_mapping.xml");
		return oDataJPAContext;
	}

	@SuppressWarnings("unchecked")
	public <T extends ODataCallback> T getCallback(
			final Class<? extends ODataCallback> callbackInterface) {
		T callback;
		if (callbackInterface.isAssignableFrom(InformeaDebugCallback.class)) {
			callback = (T) new InformeaDebugCallback();
		} else {
			callback = (T) super.getCallback(callbackInterface);
		}
		return callback;
	}
}
