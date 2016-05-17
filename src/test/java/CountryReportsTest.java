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

	private EntityManagerFactory factory;

	@Before
	public void setUp() throws Exception {
		factory = Persistence.createEntityManagerFactory(AllTests.getPersistenceUnitName());
	}

	@Test
	public void testGetCountryReports() throws Exception {
		EntityManager em = factory.createEntityManager();
		CriteriaBuilder qb = em.getCriteriaBuilder();
		List<Contact> rows = em.createQuery(qb.createQuery(Contact.class)).getResultList();
		org.junit.Assert.assertTrue(1 <= rows.size());
		em.close();
	}

	@Test
	public void testGetSingleCountryReport() throws Exception {
		EntityManager em = factory.createEntityManager();
		CountryReport row = em.find(CountryReport.class, "ca6654f2-fe8d-4daf-949f-96baf297e2e7");

		assertEquals("KE", row.getCountry());
		assertEquals("cartagena", row.getTreaty());
		assertEquals("http://www.cbd.int/doc/world/ke/ke-nr-cpb-01-en.pdf", row.getUrl());

		List<CountryReportTitle> titles = row.getTitles();
		CountryReportTitle title = titles.get(0);
		assertEquals("First National Report (2007)", title.getTitle());
		assertEquals("en", title.getLanguage());

		Calendar c = new GregorianCalendar(2007, 8, 10, 22, 0, 0);
		assertEquals(c.getTime(), row.getSubmission());

		c = new GregorianCalendar(2015, 05, 16, 20, 52, 22);
		assertEquals(c.getTime(), row.getUpdated());

		em.close();
	}
}
