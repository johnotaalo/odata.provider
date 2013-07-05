/* Copyright 2011 UNEP (http://www.unep.org)
 * This file is part of InforMEA Toolkit project.
 * InforMEA Toolkit is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * InforMEA Toolkit is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * You should have received a copy of the GNU General Public License along with
 * InforMEA Toolkit. If not, see http://www.gnu.org/licenses/.
 */
package org.informea.odata.producer.toolkit;

import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.informea.odata.producer.toolkit.impl.*;


/**
 * Dynamically configure Hibernate at runtime
 * <br />
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 1.3.3
 */
public class HibernateConfigurator {
    private static final Logger log = Logger.getLogger(HibernateConfigurator.class.getName());

    private static HibernateConfigurator _instance;

    private static SessionFactory sessionFactory = null;

    private HibernateConfigurator() {}


    /**
     * @return Singleton instance for this class
     */
    public static HibernateConfigurator getInstance() {
        if(_instance == null) {
            _instance = new HibernateConfigurator();
        }
        return _instance;
    }


    private void configure() {
        org.informea.odata.producer.toolkit.Configuration cfg = org.informea.odata.producer.toolkit.Configuration.getInstance();

        //Preferences.userRoot().getBoolean(USE_PATH_PREFIX, false);
        Configuration hc = new Configuration();

        // Use C3P0 connection pooling instead of Tomcat DBCP - which cannot be configured at runtime dynamically
        hc.setProperty("connection.provider_class", "org.hibernate.connection.C3P0ConnectionProvider");
        hc.setProperty("hibernate.c3p0.acquire_increment", "1");
        hc.setProperty("hibernate.c3p0.idle_test_period", "100");
        hc.setProperty("hibernate.c3p0.max_size", "30");
        hc.setProperty("hibernate.c3p0.max_statements", "0");
        hc.setProperty("hibernate.c3p0.min_size", "1");
        hc.setProperty("hibernate.c3p0.timeout", "100");
        hc.setProperty("hibernate.c3p0.testConnectionOnCheckout", "true"); // Very important to validate connections - for ITPGRFA fails if false since it's remote DB.

        // Database connection configuration
        hc.setProperty("hibernate.connection.driver_class", cfg.getJDBCDriver());
        hc.setProperty("hibernate.connection.url", cfg.getJDBCUrl());
        hc.setProperty("hibernate.connection.username", cfg.getDBUser());
        hc.setProperty("hibernate.connection.password", cfg.getDBPassword());
        hc.setProperty("hibernate.connection.zeroDateTimeBehavior", "convertToNull");

        hc.setProperty("hibernate.dialect", cfg.getHibernateDialect());

        // Cache configuration - Second level
        // log.info("Secondary cache is disabled - Dynamic enabling not implemented.");
        // hc.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        /*
        hc.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.SingletonEhCacheProvider");
        hc.setProperty("hibernate.cache.use_query_cache", "true");
        hc.setProperty("hibernate.cache.use_second_level_cache", "true");
        hc.setProperty("hibernate.cache.provider_configuration", "/ehcache.xml");
        */

        hc.setProperty("hibernate.current_session_context_class", "thread");
        hc.setProperty("hibernate.show_sql", "true");
        hc.setProperty("zeroDateTimeBehavior", "convertToNull");

        if(cfg.isUseContacts()) {
            hc.addAnnotatedClass(org.informea.odata.producer.toolkit.impl.ContactTreaty.class);
            hc.addAnnotatedClass(org.informea.odata.producer.toolkit.impl.Contact.class);
        }

        if(cfg.isUseCountryProfiles()) {
            hc.addAnnotatedClass(org.informea.odata.producer.toolkit.impl.CountryProfile.class);
        }

        if(cfg.isUseCountryReports()) {
            hc.addAnnotatedClass(org.informea.odata.producer.toolkit.impl.CountryReport.class);
            hc.addAnnotatedClass(org.informea.odata.producer.toolkit.impl.CountryReportTitle.class);
        }

        if(cfg.isUseDecisions()) {
            hc.addAnnotatedClass(org.informea.odata.producer.toolkit.impl.DecisionDbDocument.class);
            hc.addAnnotatedClass(org.informea.odata.producer.toolkit.impl.DecisionKeyword.class);
            hc.addAnnotatedClass(org.informea.odata.producer.toolkit.impl.DecisionContent.class);
            hc.addAnnotatedClass(org.informea.odata.producer.toolkit.impl.DecisionSummary.class);
            hc.addAnnotatedClass(org.informea.odata.producer.toolkit.impl.DecisionLongTitle.class);
            hc.addAnnotatedClass(org.informea.odata.producer.toolkit.impl.DecisionTitle.class);
            hc.addAnnotatedClass(org.informea.odata.producer.toolkit.impl.Decision.class);
        }

        if(cfg.isUseMeetings()) {
            hc.addAnnotatedClass(org.informea.odata.producer.toolkit.impl.MeetingDescription.class);
            hc.addAnnotatedClass(org.informea.odata.producer.toolkit.impl.MeetingTitle.class);
            hc.addAnnotatedClass(org.informea.odata.producer.toolkit.impl.Meeting.class);
        }

        if(cfg.isUseNationalPlans()) {
            hc.addAnnotatedClass(org.informea.odata.producer.toolkit.impl.NationalPlanTitle.class);
            hc.addAnnotatedClass(org.informea.odata.producer.toolkit.impl.NationalPlan.class);
        }

        if(cfg.isUseSites()) {
            hc.addAnnotatedClass(org.informea.odata.producer.toolkit.impl.Site.class);
            hc.addAnnotatedClass(org.informea.odata.producer.toolkit.impl.SiteName.class);
        }
        sessionFactory = hc.buildSessionFactory();
    }


    /**
     * Lazy hibernate configuration support
     * @return Hibernate session factory
     */
    public SessionFactory getFactory() {
        if(sessionFactory == null) {
            try {
                this.configure();
            } catch(Exception ex) {
                throw new RuntimeException(String.format("Database layer (Hibernate) could not be configured"), ex);
            }
        }
        return sessionFactory;
    }

    public boolean isConfigured() {
        return sessionFactory != null;
    }


    /**
     * Close the Hibernate factory. Called when the servlet engine is stopping.
     */
    public void closeFactory() {
        if(sessionFactory != null) {
            log.info("Closing Hibernate session factory");
            sessionFactory.close();
        }
    }

    public Session openSession() {
        log.info("Opening Hibernate database session");
        return getFactory().openSession();
    }
}
