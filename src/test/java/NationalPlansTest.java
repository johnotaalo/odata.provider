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

import edw.olingo.model.NationalPlan;
import edw.olingo.model.NationalPlanTitle;

@RunWith(BlockJUnit4ClassRunner.class)
public class NationalPlansTest {

	private static final String PERSISTENCE_UNIT_NAME = "persistence_unit";
	private EntityManagerFactory factory;

	@Before
	public void setUp() throws Exception {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	@Test
	public void testGetNationalPlans() throws Exception {
		EntityManager em = factory.createEntityManager();
		CriteriaBuilder qb = em.getCriteriaBuilder();
		List<NationalPlan> rows = em.createQuery(qb.createQuery(NationalPlan.class)).getResultList();
		org.junit.Assert.assertTrue(1 <= rows.size());
		em.close();
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetSingleNationalPlan() throws Exception {
		EntityManager em = factory.createEntityManager();
		NationalPlan row = em.find(NationalPlan.class, "5e991a41-123e-47a1-bd02-3524435baf9c");

		assertEquals("cbd", row.getTreaty());
		assertEquals("VE", row.getCountry());
		assertEquals("nbsap", row.getType());
		assertEquals("http://www.cbd.int/doc/world/ve/ve-nbsap-01-es.pdf", row.getUrl());

		List<NationalPlanTitle> titles = row.getTitles();
		NationalPlanTitle title = titles.get(0);
		assertEquals("Venezuela (Bolivarian Republic of) - NBSAP v.1 (2001)", title.getTitle());
		assertEquals("en", title.getLanguage());

		Calendar c = new GregorianCalendar(2014, 05, 01, 22, 0, 0);
		assertEquals(c.getTime(), row.getSubmission());

		c = new GregorianCalendar();
		assertEquals(c.getTime().getYear(), row.getUpdated().getYear());
		assertEquals(c.getTime().getMonth(), row.getUpdated().getMonth());
		assertEquals(c.getTime().getDay(), row.getUpdated().getDay());
		assertEquals(c.getTime().getHours(), row.getUpdated().getHours());

		em.close();
	}
}
