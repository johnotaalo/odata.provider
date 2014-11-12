import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import edw.olingo.model.Meeting;

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
		assertEquals(1, rows.size());
		em.close();
	}

	@Test
	public void testGetSingleMeeting() throws Exception {
		EntityManager em = factory.createEntityManager();
		Query q = em.createQuery("select m from Meeting m");
		Meeting row = (Meeting)q.getSingleResult();
		
		assertEquals("1596", row.getId());
		assertEquals("test", row.getTreaty());
		assertEquals("http://www.cbd.int/doc/meetings/cop/cop-05/", row.getUrl());
		assertEquals("official", row.getKind());
		assertEquals("cop", row.getType());
		assertEquals("invitation", row.getAccess());
		assertEquals("confirmed", row.getStatus());
		assertEquals("yearly", row.getRepetition());
		assertEquals("image url", row.getImageUrl());
		assertEquals("image copyright text", row.getImageCopyright()); 
		assertEquals("UNEP Headquarters", row.getLocation());
		assertEquals("Nairobi", row.getCity());
		assertEquals("KEN", row.getCountry());
		assertEquals(new Double(-1.274359), row.getLatitude());
		assertEquals(new Double(36.81311), row.getLongitude());
		
		Calendar c = new GregorianCalendar(2000, 04, 10, 14, 0, 0);
		assertEquals(c.getTime(), row.getStart());
		
		c = new GregorianCalendar(2000, 04, 15, 15, 0, 0);
		assertEquals(c.getTime(), row.getEnd());
		
		c = new GregorianCalendar(2014, 10, 10, 14, 9, 57);
		assertEquals(c.getTime(), row.getUpdated());

		em.close();
	}
}
