import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ContactsTest.class, CountryReportsTest.class,
		DecisionsTest.class, MeetingsTest.class, NationalPlansTest.class,
		ServiceInformationTest.class, SitesTest.class,
		InformeaDebugCallbackTest.class })
public class AllTests {

	// Test persistence unit, as configured in persistence.xml
	private static final String PERSISTENCE_UNIT_NAME = "persistence_unit_test";

	public static String getPersistenceUnitName() {
		return PERSISTENCE_UNIT_NAME;
	}
}
