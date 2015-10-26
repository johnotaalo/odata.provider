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

import edw.olingo.model.Site;
import edw.olingo.model.SiteName;

@RunWith(BlockJUnit4ClassRunner.class)
public class SitesTest {

	private static final String PERSISTENCE_UNIT_NAME = "persistence_unit";
	private EntityManagerFactory factory;

	@Before
	public void setUp() throws Exception {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	@Test
	public void testGetSites() throws Exception {
		EntityManager em = factory.createEntityManager();
		CriteriaBuilder qb = em.getCriteriaBuilder();
		List<Site> rows = em.createQuery(qb.createQuery(Site.class)).getResultList();
		org.junit.Assert.assertTrue(1 <= rows.size());
		em.close();
	}

	@Test
	public void testGetSingleSite() throws Exception {
		EntityManager em = factory.createEntityManager();
		Site row = em.find(Site.class, "c81755d2-8f69-4f09-b448-5d62de9bf005");

		assertEquals("IT", row.getCountry());
		assertEquals("whc", row.getTreaty());
		assertEquals("whc", row.getType());
		assertEquals("http://whc.unesco.org/en/list/1487", row.getUrl());
		assertEquals(new Double(38.110800000), row.getLatitude());
		assertEquals(new Double(13.353100000), row.getLongitude());

		List<SiteName> names = row.getNames();
		assertEquals(2, names.size());
		SiteName name = names.get(0);
		assertEquals("Arab-Norman Palermo and the Cathedral Churches of Cefalú and Monreale", name.getName());
		assertEquals("en", name.getLanguage());

		GregorianCalendar c = new GregorianCalendar(2015, 7, 31, 8, 43, 16);
		assertEquals(c.getTime(), row.getUpdated());

		em.close();
	}
}
