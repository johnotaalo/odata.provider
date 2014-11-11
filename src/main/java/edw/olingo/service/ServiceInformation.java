package edw.olingo.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import edw.olingo.model.Contact;
import edw.olingo.model.CountryReport;
import edw.olingo.model.Decision;
import edw.olingo.model.Meeting;
import edw.olingo.model.NationalPlan;
import edw.olingo.model.Site;

public class ServiceInformation {

	public static final int VERSION_MAJOR_VERSION = 2;
	public static final int VERSION_MINOR_VERSION = 0;
	public static final int VERSION_REVISION = 0;	
	public static final String PERSISTENCE_UNIT_NAME = "persistence_unit";

	private static final Logger log = Logger.getLogger(ServiceInformation.class.getName());

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static long countEntities(Class entityKlass) {
		long count = 0;
		try {
			EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factory.createEntityManager();
			CriteriaBuilder qb = em.getCriteriaBuilder();
			CriteriaQuery<Long> query = qb.createQuery(Long.class); 
			query.select(qb.count(query.from(entityKlass)));
			count = em.createQuery(query).getSingleResult();
		} catch(Exception ex) {
			log.log(Level.WARNING, "Error getting entity count for %s", ex);
		}
		return count;
	}

	public static long countMeetings() {
		return ServiceInformation.countEntities(Meeting.class);
	}

	public static long countContacts() {
		return ServiceInformation.countEntities(Contact.class);
	}

	public static long countDecisions() {
		return ServiceInformation.countEntities(Decision.class);
	}

	public static long countCountryReports() {
		return ServiceInformation.countEntities(CountryReport.class);
	}

	public static long countNationalPlans() {
		return ServiceInformation.countEntities(NationalPlan.class);
	}

	public static long countSites() {
		return ServiceInformation.countEntities(Site.class);
	}
}
