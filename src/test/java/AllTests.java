import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ContactsTest.class, CountryReportsTest.class,
		DecisionsTest.class, MeetingsTest.class, NationalPlansTest.class,
		ServiceInformationTest.class, SitesTest.class,
		InformeaDebugCallbackTest.class })
public class AllTests {

}
