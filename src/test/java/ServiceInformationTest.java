import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import edw.olingo.model.Meeting;
import edw.olingo.service.ServiceInformation;


@RunWith(BlockJUnit4ClassRunner.class)
public class ServiceInformationTest {

	@Test
	public void testCountEntities() throws Exception {
		assertEquals(1, ServiceInformation.countEntities(Meeting.class));
	}
}
