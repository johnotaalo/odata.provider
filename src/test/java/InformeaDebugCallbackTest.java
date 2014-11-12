import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import edw.olingo.service.InformeaDebugCallback;

@RunWith(BlockJUnit4ClassRunner.class)
public class InformeaDebugCallbackTest {

	@Test
	public void testIsDebugEnabled() throws Exception {
		InformeaDebugCallback ob = new InformeaDebugCallback();
		assertTrue(ob.isDebugEnabled());
		
		System.setProperty("odata.debug", "false");
		assertFalse(ob.isDebugEnabled());
	}
}
