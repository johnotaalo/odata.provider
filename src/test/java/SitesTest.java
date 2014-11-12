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
		List<Site> rows = em.createQuery(qb.createQuery(Site.class))
				.getResultList();
		assertEquals(1, rows.size());
		em.close();
	}

	@Test
	public void testGetSingleSite() throws Exception {
		EntityManager em = factory.createEntityManager();
		Site row = em.find(Site.class, "2029");

		assertEquals("2029", row.getId());
		assertEquals("FIN", row.getCountry());
		assertEquals("test", row.getTreaty());
		assertEquals("ramsar", row.getType());
		assertEquals("http://www.wetlands.org/reports/spec.cfm?site_id=860",
				row.getUrl());
		assertEquals(new Double(60.2666666667), row.getLatitude());
		assertEquals(new Double(26.4166666667), row.getLongitude());

		List<SiteName> names = row.getNames();
		assertEquals(1, names.size());
		SiteName name = names.get(0);
		assertEquals("Aspskär Islands", name.getName());
		assertEquals("en", name.getLanguage());

		Calendar c = new GregorianCalendar(2014, 10, 12, 15, 55, 41);
		assertEquals(c.getTime(), row.getUpdated());

		em.close();
	}
}
