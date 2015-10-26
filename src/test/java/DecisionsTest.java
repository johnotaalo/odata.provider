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
	public void testGetDecisions() throws Exception {
		EntityManager em = factory.createEntityManager();
		CriteriaBuilder qb = em.getCriteriaBuilder();
		List<Decision> rows = em.createQuery(qb.createQuery(Decision.class)).getResultList();
		org.junit.Assert.assertTrue(1 <= rows.size());
		em.close();
	}

	@Test
	public void testGetSingleDecision() throws Exception {
		EntityManager em = factory.createEntityManager();
		Decision row = em.find(Decision.class, "c8374b11-e53b-4743-a320-6c46dd93cd0e");

		assertEquals("http://www.informea.org/node/68658", row.getLink());
		assertEquals("decision", row.getType());
		assertEquals("active", row.getStatus());
		assertEquals("SC-4/28", row.getNumber());
		assertEquals("stockholm", row.getTreaty());

		Calendar c = new GregorianCalendar(2009, 9, 1, 7, 15, 3);
		assertEquals(c.getTime(), row.getPublished());

		DecisionTitle title = row.getTitles().get(0);
		assertEquals("Additional guidance to the financial mechanism", title.getTitle());
		assertEquals("en", title.getLanguage());

		/*
		DecisionLongTitle longTitle = row.getLongTitles().get(0);
		assertEquals("Orientación adicional al mecanismo financiero", longTitle.getLongTitle());
		assertEquals("en", longTitle.getLanguage());
		*/

		assertEquals("693774bb-9045-4583-97ea-1afc2424cc36", row.getMeetingId());

		assertEquals("Fourth Meeting of the Conference of the Parties to the Stockholm Convention", row.getMeetingTitle());
		assertEquals("http://chm.pops.int/linkclick.aspx?link=404&amp;amp;tabid=276&amp;amp;language=en-us", row.getMeetingUrl());

		c = new GregorianCalendar(2015, 8, 3, 17, 36, 27);
		assertEquals(c.getTime(), row.getUpdated());

		/* @todo
		DecisionSummary summary = row.getSummaries().get(0);
		assertEquals("<p><i>The Conference of the Parties </i></p>", summary.getSummary());
		assertEquals("en", summary.getLanguage());

		DecisionContent content = row.getContents().get(0);
		assertEquals("<h1 align=\"center\"> Global Biodiversity Outlook </h1>", content.getContent());
		assertEquals("en", content.getLanguage());
		*/

		DecisionFile file = row.getFiles().get(0);
		assertEquals("http://chm.pops.int/Portals/0/download.aspx?d=UNEP-POPS-COP.4-SC-4-28.Spanish.doc", file.getUrl());
		assertEquals("application/msword", file.getMimeType());
		assertEquals("en", file.getLanguage());
		assertEquals("UNEP-POPS-COP.4-SC-4-28.Spanish.doc", file.getFilename());

		/* @todo
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
		*/

		em.close();
	}
}
