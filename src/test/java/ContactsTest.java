import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
import edw.olingo.model.ContactTreaty;

@RunWith(BlockJUnit4ClassRunner.class)
public class ContactsTest {

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
		List<Contact> rows = em.createQuery(qb.createQuery(Contact.class))
				.getResultList();
		org.junit.Assert.assertTrue(1 <= rows.size());
		em.close();
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetSingleContact() throws Exception {
		EntityManager em = factory.createEntityManager();
		Contact row = em.find(Contact.class, "d2f69287-f5fd-4b8b-a349-aff673ce3f7a");

		assertEquals("GB", row.getCountry());
		assertEquals("Mr.", row.getPrefix());
		assertEquals("Panayiotis", row.getFirstName());
		assertEquals("Pashas", row.getLastName());
		assertEquals("DIO Planning Manager/Chief Engineer, Competent Authority for BFC", row.getPosition());
		assertEquals("Defence Infrastructure Organisation (DIO)", row.getInstitution());
		assertEquals("DIO HQ (Cyprus)", row.getDepartment());
		assertEquals("Competent Authority", row.getType());
		assertEquals("“B” Block\r\nBFPO 53 Episkopi", row.getAddress());

		assertTrue(row.getEmail().contains("Panayiotis"));
		assertNull(row.getPhoneNumber());

		assertTrue(row.getFax().contains("357"));
		assertEquals(0, row.getPrimary());


		List<ContactTreaty> treaties = row.getTreaties();
		assertEquals(1, treaties.size());

		ContactTreaty treaty = treaties.get(0);
		assertEquals("basel", treaty.getTreaty());

		Calendar c = new GregorianCalendar();
		assertEquals(c.getTime().getYear(), row.getUpdated().getYear());
		assertEquals(c.getTime().getMonth(), row.getUpdated().getMonth());
		assertEquals(c.getTime().getDay(), row.getUpdated().getDay());
		assertEquals(c.getTime().getHours(), row.getUpdated().getHours());

		em.close();
	}
}
