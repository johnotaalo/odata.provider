import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import edw.olingo.model.Meeting;
import edw.olingo.model.MeetingDescription;
import edw.olingo.model.MeetingTitle;

@RunWith(BlockJUnit4ClassRunner.class)
public class MeetingsTest {

	private static final String PERSISTENCE_UNIT_NAME = "persistence_unit";
	private EntityManagerFactory factory;

	@Before
	public void setUp() throws Exception {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	@Test
	public void testGetMeetings() throws Exception {
		EntityManager em = factory.createEntityManager();
		CriteriaBuilder qb = em.getCriteriaBuilder();
		List<Meeting> rows = em.createQuery(qb.createQuery(Meeting.class)).getResultList();
		org.junit.Assert.assertTrue(1 <= rows.size());
		em.close();
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetSingleMeeting() throws Exception {
		EntityManager em = factory.createEntityManager();
		Meeting row = em.find(Meeting.class, "e6c7a14f-abfc-46e9-9041-7f25c90dd95b");

		assertEquals("cbd", row.getTreaty());
		assertEquals("http://www.cbd.int/doc/meetings/tk/wg8j-01/", row.getUrl());

		Calendar c = new GregorianCalendar(2000, 02, 26, 22, 0, 0);
		assertEquals(c.getTime(), row.getStart());

		c = new GregorianCalendar(2000, 02, 26, 22, 0, 0);
		assertEquals(c.getTime(), row.getEnd());

		// assertEquals("yearly", row.getRepetition());
		assertEquals("official", row.getKind());
		assertEquals("working", row.getType());
		// assertEquals("invitation", row.getAccess());
		assertEquals("nodate", row.getStatus());

		// assertEquals("http://placehold.it/128x128.png", row.getImageUrl());
		// assertEquals("image copyright text", row.getImageCopyright());

		assertEquals("Seville, Spain", row.getLocation());
		assertEquals("Seville", row.getCity());
		assertEquals("ES", row.getCountry());
		assertEquals(new Double(37.382640000), row.getLatitude());
		assertEquals(new Double(-5.996295000), row.getLongitude());

		c = new GregorianCalendar();
		assertEquals(c.getTime().getYear(), row.getUpdated().getYear());
		assertEquals(c.getTime().getMonth(), row.getUpdated().getMonth());
		assertEquals(c.getTime().getDay(), row.getUpdated().getDay());
		assertEquals(c.getTime().getHours(), row.getUpdated().getHours());

		/* @todo
		MeetingDescription description = row.getDescriptions().get(0);
		assertEquals("Sample description of COP 5", description.getDescription());
		assertEquals("en", description.getLanguage());
		*/

		MeetingTitle title = row.getTitles().get(0);
		assertEquals("First Meeting of the Ad Hoc Open-Ended Inter-Sessional Working Group on Article 8(j)", title.getTitle());
		assertEquals("en", title.getLanguage());

		em.close();
	}
}
