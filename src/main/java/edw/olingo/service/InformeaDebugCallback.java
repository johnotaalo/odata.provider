package edw.olingo.service;

import org.apache.olingo.odata2.api.ODataDebugCallback;

public class InformeaDebugCallback implements ODataDebugCallback {

	@Override
	public boolean isDebugEnabled() {
		boolean debug = true;
		try {
			String v = System.getProperty("odata.debug.disabled");
			Boolean disabled = Boolean.parseBoolean(v);
			if (disabled == true) {
				debug = false;
			}
		} catch (Exception e) {
		}
		return debug;
	}
}
