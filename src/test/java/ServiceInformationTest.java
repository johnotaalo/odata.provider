import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import edw.olingo.model.Meeting;
import edw.olingo.service.ServiceInformation;


@RunWith(BlockJUnit4ClassRunner.class)
public class ServiceInformationTest {

	@Test
	public void testCountEntities() {
		assertEquals(1, ServiceInformation.countEntities(Meeting.class));
	}
	
	@Test
	public void testCheckProductUpdates() {
		String baseDir = "file://" + System.getProperty("user.dir") + "/src/main/webapp/";
		System.out.println(baseDir);
		Map<String, Object> data = ServiceInformation.checkProductUpdates(baseDir + "test.needsupdate.api.properties");
		assertTrue((boolean)data.get("needsUpdate"));
		assertTrue((boolean)data.get("success"));
		assertEquals("2.0.1 beta", (String)data.get("remoteVersion"));
		assertTrue(((String)data.get("changes")).length() > 0);

		data = ServiceInformation.checkProductUpdates(baseDir + "test.updated.api.properties");
		assertFalse((boolean)data.get("needsUpdate"));
		assertTrue((boolean)data.get("success"));
		assertEquals("1.5.3", (String)data.get("remoteVersion"));
		assertTrue(((String)data.get("changes")).length() > 0);
		
		data = ServiceInformation.checkProductUpdates("http://malformed.url/");
		assertFalse((boolean)data.get("success"));
	}
}
