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
	public void testGetContacts() throws Exception {
		EntityManager em = factory.createEntityManager();
		CriteriaBuilder qb = em.getCriteriaBuilder(); 
		List<NationalPlan> rows = em.createQuery(qb.createQuery(NationalPlan.class)).getResultList();
		assertEquals(1, rows.size());
		em.close();
	}

	@Test
	public void testGetSingleContact() throws Exception {
		EntityManager em = factory.createEntityManager();
		NationalPlan row = em.find(NationalPlan.class, "1");

		assertEquals("CHE", row.getCountry());
		assertEquals("test", row.getTreaty());
		assertEquals("napa", row.getType());
		assertEquals("http://ch.chm-cbd.net/view/list/folder?idHierarchy=13377233&fullId=13329573.13377233.&baseurl=home", row.getUrl());
		
		List<NationalPlanTitle> titles = row.getTitles();
		NationalPlanTitle title = titles.get(0);
		assertEquals("National Biodiversity Strategy and Action Plan", title.getTitle());
		assertEquals("en", title.getLanguage());

		Calendar c = new GregorianCalendar(1989, 01, 13);
		assertEquals(c.getTime(), row.getSubmission());

		c = new GregorianCalendar(2014, 10, 12, 16, 47, 07);
		assertEquals(c.getTime(), row.getUpdated());

		em.close();
	}
}
