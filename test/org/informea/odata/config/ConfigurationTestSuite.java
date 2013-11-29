package org.informea.odata.config;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ConfigurationTest.class, DatabaseConfigurationTest.class,
        LDAPConfigurationTest.class })
public class ConfigurationTestSuite {

}
