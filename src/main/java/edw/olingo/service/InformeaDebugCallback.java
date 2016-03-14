package edw.olingo.service;

import org.apache.olingo.odata2.api.ODataDebugCallback;

public class InformeaDebugCallback implements ODataDebugCallback {

	@Override
	public boolean isDebugEnabled() {
		boolean debug = false;
		try {
			String v = System.getProperty("odata.debug");
			debug = Boolean.parseBoolean(v);
		} catch (Exception e) {
		}
		return debug;
	}
}
