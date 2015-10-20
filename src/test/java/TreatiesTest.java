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

import edw.olingo.model.Treaty;
import edw.olingo.model.TreatyTitle;
import edw.olingo.model.TreatyDescription;

@RunWith(BlockJUnit4ClassRunner.class)
public class TreatiesTest {

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
		List<Treaty> rows = em.createQuery(qb.createQuery(Treaty.class)).getResultList();
		org.junit.Assert.assertTrue(1 <= rows.size());
		em.close();
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetSingleSite() throws Exception {
		EntityManager em = factory.createEntityManager();
		Treaty row = em.find(Treaty.class, "cbd");
		
		assertEquals("08609c37-86bc-458c-8f60-8c7365e2886e", row.getUuid());
		assertEquals("http://www.informea.org/treaties/cbd", row.getUrl());
		assertEquals("CBD", row.getTitleEnglish());
		assertEquals("The Convention on Biological Diversity", row.getOfficialNameEnglish());

		List<TreatyTitle> titles = row.getTitles();
		org.junit.Assert.assertTrue(1 <= titles.size());
		TreatyTitle title = titles.get(0);
		assertEquals("CBD", title.getTitle());
		assertEquals("en", title.getLanguage());

		em.close();
	}
}
