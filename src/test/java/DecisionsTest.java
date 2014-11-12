import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
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

import edw.olingo.model.Decision;
import edw.olingo.model.DecisionContent;
import edw.olingo.model.DecisionFile;
import edw.olingo.model.DecisionKeyword;
import edw.olingo.model.DecisionLongTitle;
import edw.olingo.model.DecisionSummary;
import edw.olingo.model.DecisionTitle;

@RunWith(BlockJUnit4ClassRunner.class)
public class DecisionsTest {

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
		List<Decision> rows = em.createQuery(qb.createQuery(Decision.class))
				.getResultList();
		assertEquals(1, rows.size());
		em.close();
	}

	@Test
	public void testGetSingleContact() throws Exception {
		EntityManager em = factory.createEntityManager();
		Decision row = em.find(Decision.class, "3070");

		assertEquals("http://www.cbd.int/decisions/?id=11019", row.getLink());

		DecisionTitle title = row.getTitles().get(0);
		assertEquals("Global Biodiversity Outlook", title.getTitle());
		assertEquals("en", title.getLanguage());

		DecisionLongTitle longTitle = row.getLongTitles().get(0);
		assertEquals("Global Biodiversity Outlook", longTitle.getLongTitle());
		assertEquals("en", longTitle.getLanguage());

		DecisionSummary summary = row.getSummaries().get(0);
		assertEquals("<p><i>The Conference of the Parties </i></p>",
				summary.getSummary());
		assertEquals("en", summary.getLanguage());

		assertEquals("decision", row.getType());
		assertEquals("active", row.getStatus());
		assertEquals("VIII/7", row.getNumber());
		assertEquals("test", row.getTreaty());

		Calendar c = new GregorianCalendar(2006, 02, 20);
		assertEquals(c.getTime(), row.getPublished());

		c = new GregorianCalendar(2014, 01, 01);
		assertEquals(c.getTime(), row.getUpdated());

		assertEquals("1596", row.getMeetingId());

		assertEquals(
				"Eighth Ordinary Meeting of the Conference of the Parties to the Convention on Biological Diversity",
				row.getMeetingTitle());
		assertEquals("http://www.cbd.int/doc/meetings/cop/cop-08/",
				row.getMeetingUrl());

		DecisionContent content = row.getContents().get(0);
		assertEquals("<h1 align=\"center\"> Global Biodiversity Outlook </h1>",
				content.getContent());
		assertEquals("en", content.getLanguage());

		DecisionFile file = row.getFiles().get(0);
		assertEquals("1993", file.getId());
		assertEquals(
				"http://www.cbd.int/doc/decisions/COP-08/COP-08-dec-07-en.pdf",
				file.getUrl());
		assertEquals("pdf", file.getMimeType());
		assertEquals("en", file.getLanguage());
		assertEquals("COP-08-dec-07-en.pdf", file.getFilename());

		List<DecisionKeyword> tags = row.getKeywords();
		assertEquals(6, tags.size());

		List<String> witness = Arrays.asList(
				"Programme of Work (MEA Secretariat)",
				"Sustainable Use and Wise Management",
				"Integrated Ecosystems Management", "Mitigation",
				"Forest Biodiversity", "Trade and Environment");

		DecisionKeyword tag = tags.get(0);
		assertEquals("InforMEA", tag.getNamespace());
		assertTrue(witness.contains(tag.getTerm()));

		em.close();
	}
}
