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

import edw.olingo.model.Contact;
import edw.olingo.model.CountryReport;
import edw.olingo.model.CountryReportTitle;

@RunWith(BlockJUnit4ClassRunner.class)
public class CountryReportsTest {

	private static final String PERSISTENCE_UNIT_NAME = "persistence_unit";
	private EntityManagerFactory factory;

	@Before
	public void setUp() throws Exception {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	@Test
	public void testGetCountryReports() throws Exception {
		EntityManager em = factory.createEntityManager();
		CriteriaBuilder qb = em.getCriteriaBuilder();
		List<Contact> rows = em.createQuery(qb.createQuery(Contact.class))
				.getResultList();
		assertEquals(1, rows.size());
		em.close();
	}

	@Test
	public void testGetSingleCountryReport() throws Exception {
		EntityManager em = factory.createEntityManager();
		CountryReport row = em.find(CountryReport.class, "3");

		assertEquals("KEN", row.getCountry());
		assertEquals("test", row.getTreaty());
		assertEquals("http://www.cbd.int/doc/world/ke/ke-nr-04-en.pdf",
				row.getUrl());

		List<CountryReportTitle> titles = row.getTitles();
		CountryReportTitle title = titles.get(0);
		assertEquals("Fourth National Report", title.getTitle());
		assertEquals("en", title.getLanguage());

		Calendar c = new GregorianCalendar(2009, 02, 30);
		assertEquals(c.getTime(), row.getSubmission());

		c = new GregorianCalendar(2013, 10, 2);
		assertEquals(c.getTime(), row.getUpdated());

		em.close();
	}
}
