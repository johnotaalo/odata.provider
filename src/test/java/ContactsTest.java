import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import edw.olingo.model.Contact;

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
		Query q = em.createQuery("select m from Contact m");
		@SuppressWarnings("unchecked")
		List<Contact> rows = q.getResultList();
		assertEquals(1, rows.size());
		em.close();
	}

	@Test
	public void testGetSingleContact() throws Exception {
		EntityManager em = factory.createEntityManager();
		Query q = em.createQuery("select m from Contact m");
		Contact row = (Contact)q.getSingleResult();

		assertEquals("1830", row.getId());
		assertEquals("AFG", row.getCountry());
		assertEquals("H.R.H. Prince", row.getPrefix());
		assertEquals("Mostapha", row.getFirstName());
		assertEquals("Zaher", row.getLastName());
		assertEquals("Director General/Advisor to the President of Afghanistan on Environment", row.getPosition());
		assertEquals("National Environmental Protection Agency", row.getInstitution());
		assertEquals("department name", row.getDepartment());
		assertEquals("Central Post Office Box Number 209", row.getAddress());
		assertEquals("mihaita_zaharia@hotmail.com", row.getEmail());
		assertEquals("telephone no", row.getPhoneNumber());
		assertEquals("fax no", row.getFax());
		assertEquals("licensed", row.getType());
		assertEquals(1, row.getPrimary());
		
		Calendar c = new GregorianCalendar(2014, 10, 10, 15, 18, 35);
		assertEquals(c.getTime(), row.getUpdated());

		em.close();
	}
}
