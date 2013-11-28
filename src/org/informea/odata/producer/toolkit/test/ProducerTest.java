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
package org.informea.odata.producer.toolkit.test;

import java.util.Arrays;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.informea.odata.IContact;
import org.informea.odata.ICountryProfile;
import org.informea.odata.ICountryReport;
import org.informea.odata.IDecision;
import org.informea.odata.IMeeting;
import org.informea.odata.INationalPlan;
import org.informea.odata.ISite;
import org.informea.odata.constants.Treaty;
import org.informea.odata.data.DataProviderFactory;
import org.informea.odata.pojo.AbstractContact;
import org.informea.odata.pojo.AbstractCountryProfile;
import org.informea.odata.pojo.AbstractCountryReport;
import org.informea.odata.pojo.AbstractDecision;
import org.informea.odata.pojo.AbstractMeeting;
import org.informea.odata.pojo.AbstractNationalPlan;
import org.informea.odata.pojo.AbstractSite;
import org.informea.odata.pojo.DecisionDocument;
import org.informea.odata.pojo.LocalizableString;
import org.informea.odata.pojo.VocabularyTerm;
import org.informea.odata.producer.toolkit.IDataProvider;
import org.informea.odata.producer.toolkit.Producer;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author cristiroma
 */
public class ProducerTest {

    private static Producer p;

    @BeforeClass
    public static void setUp() {
        p = new Producer();
    }

    @Test public void testGetMeeting() {
        IDataProvider dp = DataProviderFactory.getDataProvider(IMeeting.class);

        AbstractMeeting m1 = p.getMeeting(dp, "1");
        assertNotNull(m1);
        AbstractMeeting m2 = p.getMeeting(dp, "xx");
        assertNull(m2);

        dp.closeResources();
    }


    @Test public void testGetMeetings() {
        IDataProvider dp = DataProviderFactory.getDataProvider(IMeeting.class);

        List<AbstractMeeting> v = p.getMeetings(dp, null, 0, null);
        assertEquals(5, v.size());
        List<String> keys = Arrays.asList("1", "2", "3", "4", "5");
        for(AbstractMeeting ob : v) {
            assertTrue(keys.contains(ob.getId()));
        }

        dp.closeResources();
    }


    @Test public void testGetMeetingsCount() {
        IDataProvider dp = DataProviderFactory.getDataProvider(IMeeting.class);

        assertEquals(5, (int)p.getMeetingsCount(dp, null));

        dp.closeResources();
    }


    @Test public void testGetMeetingTitle() {
        IDataProvider dp = DataProviderFactory.getDataProvider(IMeeting.class);

        AbstractMeeting m1 = p.getMeeting(dp, "1");
        List<LocalizableString> t = m1.getTitle();
        assertEquals(2, t.size());
        List<String> keys = Arrays.asList("ro", "en");
        for(LocalizableString ob : t) {
            assertTrue(keys.contains(ob.getLanguage()));
        }

        keys = Arrays.asList("Romaneste", "English");
        for(LocalizableString ob : t) {
            assertTrue(keys.contains(ob.getValue()));
        }

        dp.closeResources();
    }


    @Test public void testGetMeetingDescription() {
        IDataProvider dp = DataProviderFactory.getDataProvider(IMeeting.class);

        AbstractMeeting m1 = p.getMeeting(dp, "1");
        List<LocalizableString> t = m1.getDescription();
        assertEquals(2, t.size());
        List<String> keys = Arrays.asList("es", "en");
        for(LocalizableString ob : t) {
            assertTrue(keys.contains(ob.getLanguage()));
        }

        keys = Arrays.asList("desc_en", "desc_es");
        for(LocalizableString ob : t) {
            assertTrue(keys.contains(ob.getValue()));
        }

        dp.closeResources();
    }


    @Test public void testGetDecision() {
        IDataProvider dp = DataProviderFactory.getDataProvider(IDecision.class);

        AbstractDecision m1 = p.getDecision(dp, "1");
        assertNotNull(m1);

        AbstractDecision m2 = p.getDecision(dp, "xx");
        assertNull(m2);

        dp.closeResources();
    }


    @Test public void testGetDecisions() {
        IDataProvider dp = DataProviderFactory.getDataProvider(IDecision.class);

        List<AbstractDecision> v = p.getDecisions(dp, null, 0, null);
        assertEquals(3, v.size());
        List<String> keys = Arrays.asList("1", "2", "3");
        for(AbstractDecision ob : v) {
            assertTrue(keys.contains(ob.getId()));
        }

        dp.closeResources();
    }


    @Test public void testGetDecisionsCount() {
        IDataProvider dp = DataProviderFactory.getDataProvider(IDecision.class);

        assertEquals(3, (int)p.getDecisionsCount(dp, null));

        dp.closeResources();
    }


    @Test public void testGetDecisionDocuments() throws BackingStoreException {
        IDataProvider dp = DataProviderFactory.getDataProvider(IDecision.class);

        Preferences.userRoot().putBoolean("informea.documents.usePathPrefix", false);
        Preferences.userRoot().sync();

        AbstractDecision m1 = p.getDecision(dp, "1");
        List<DecisionDocument> v = m1.getDocuments();
        assertEquals(2, v.size());

        dp.closeResources();
    }


    @Test public void testGetDecisionTitle() {
        IDataProvider dp = DataProviderFactory.getDataProvider(IDecision.class);

        AbstractDecision m1 = p.getDecision(dp, "1");
        List<LocalizableString> t = m1.getTitle();
        assertEquals(2, t.size());
        List<String> keys = Arrays.asList("ro", "en");
        for(LocalizableString ob : t) {
            assertTrue(keys.contains(ob.getLanguage()));
        }

        keys = Arrays.asList("Romaneste", "English");
        for(LocalizableString ob : t) {
            assertTrue(keys.contains(ob.getValue()));
        }

        dp.closeResources();
    }

    @Test public void testGetDecisionLongTitle() {
        IDataProvider dp = DataProviderFactory.getDataProvider(IDecision.class);

        AbstractDecision m1 = p.getDecision(dp, "1");
        List<LocalizableString> t = m1.getLongTitle();
        assertEquals(1, t.size());
        List<String> keys = Arrays.asList("cn");
        for(LocalizableString ob : t) {
            assertTrue(keys.contains(ob.getLanguage()));
        }

        keys = Arrays.asList("Chinese");
        for(LocalizableString ob : t) {
            assertTrue(keys.contains(ob.getValue()));
        }

        dp.closeResources();
    }


    @Test public void testGetDecisionSummary() {
        IDataProvider dp = DataProviderFactory.getDataProvider(IDecision.class);

        AbstractDecision m1 = p.getDecision(dp, "1");
        List<LocalizableString> t = m1.getSummary();
        assertEquals(1, t.size());
        List<String> keys = Arrays.asList("de");
        for(LocalizableString ob : t) {
            assertTrue(keys.contains(ob.getLanguage()));
        }

        keys = Arrays.asList("Deutsch");
        for(LocalizableString ob : t) {
            assertTrue(keys.contains(ob.getValue()));
        }

        dp.closeResources();
    }


    @Test public void testGetDecisionContent() {
        IDataProvider dp = DataProviderFactory.getDataProvider(IDecision.class);

        AbstractDecision m1 = p.getDecision(dp, "1");
        List<LocalizableString> t = m1.getContent();
        assertEquals(1, t.size());
        List<String> keys = Arrays.asList("es");
        for(LocalizableString ob : t) {
            assertTrue(keys.contains(ob.getLanguage()));
        }

        keys = Arrays.asList("Spanish");
        for(LocalizableString ob : t) {
            assertTrue(keys.contains(ob.getValue()));
        }

        dp.closeResources();
    }


    @Test public void testGetDecisionKeywords() {
        IDataProvider dp = DataProviderFactory.getDataProvider(IDecision.class);

        AbstractDecision m1 = p.getDecision(dp, "1");
        List<VocabularyTerm> t = m1.getKeywords();
        assertEquals(2, t.size());
        List<String> keys = Arrays.asList("Biological Diversity", "Endangered Species");
        for(VocabularyTerm ob : t) {
            assertTrue(keys.contains(ob.getTerm()));
        }

        keys = Arrays.asList("http://www.unep.org");
        for(VocabularyTerm ob : t) {
            assertTrue(keys.contains(ob.getNamespace()));
        }

        dp.closeResources();
    }


    @Test public void testGetContact() {
        IDataProvider dp = DataProviderFactory.getDataProvider(IContact.class);

        AbstractContact m1 = p.getContact(dp, "1");
        assertNotNull(m1);

        AbstractContact m2 = p.getContact(dp, "xx");
        assertNull(m2);

        dp.closeResources();
    }


    @Test public void testGetContacts() {
        IDataProvider dp = DataProviderFactory.getDataProvider(IContact.class);

        List<AbstractContact> v = p.getContacts(dp, null, 0, null);
        assertEquals(2, v.size());
        List<String> keys = Arrays.asList("1", "2");
        for(AbstractContact ob : v) {
            assertTrue(keys.contains(ob.getId()));
        }

        dp.closeResources();
    }


    @Test public void testGetContactsCount() {
        IDataProvider dp = DataProviderFactory.getDataProvider(IContact.class);

        assertEquals(2, (int)p.getContactsCount(dp, null));

        dp.closeResources();
    }


    @Test public void testGetContactTreaties() {
        IDataProvider dp = DataProviderFactory.getDataProvider(IContact.class);

        AbstractContact m1 = p.getContact(dp, "1");
        List<Treaty> t = m1.getTreaties();
        assertEquals(2, t.size());
        List<Treaty> keys = Arrays.asList(Treaty.CBD, Treaty.BASEL);
        for(Treaty ob : t) {
            assertTrue(keys.contains(ob));
        }

        dp.closeResources();
    }


    @Test public void testGetCountryProfile() {
        IDataProvider dp = DataProviderFactory.getDataProvider(ICountryProfile.class);

        AbstractCountryProfile m1 = p.getCountryProfile(dp, "1");
        assertNotNull(m1);

        AbstractCountryProfile m2 = p.getCountryProfile(dp, "xx");
        assertNull(m2);

        dp.closeResources();
    }


    @Test public void testGetCountryProfiles() {
        IDataProvider dp = DataProviderFactory.getDataProvider(ICountryProfile.class);

        List<AbstractCountryProfile> v = p.getCountryProfiles(dp, null, 0, null);
        assertEquals(2, v.size());

        dp.closeResources();
    }


    @Test public void testGetCountryProfilesCount() {
        IDataProvider dp = DataProviderFactory.getDataProvider(ICountryProfile.class);

        assertEquals(2, (int)p.getCountryProfilesCount(dp, null));

        dp.closeResources();
    }


    @Test public void testGetCountryReport() {
        IDataProvider dp = DataProviderFactory.getDataProvider(ICountryReport.class);

        AbstractCountryReport m1 = p.getCountryReport(dp, "1");
        assertNotNull(m1);

        AbstractCountryReport m2 = p.getCountryReport(dp, "xx");
        assertNull(m2);

        dp.closeResources();
    }


    @Test public void testGetCountryReports() {
        IDataProvider dp = DataProviderFactory.getDataProvider(ICountryReport.class);

        List<AbstractCountryReport> v = p.getCountryReports(dp, null, 0, null);
        assertEquals(5, v.size());

        dp.closeResources();
    }


    @Test public void testGetCountryReportsCount() {
        IDataProvider dp = DataProviderFactory.getDataProvider(ICountryReport.class);

        assertEquals(5, (int)p.getCountryReportsCount(dp, null));

        dp.closeResources();
    }


    @Test public void testGetCountryReportsTitle() {
        IDataProvider dp = DataProviderFactory.getDataProvider(ICountryReport.class);

        AbstractCountryReport m1 = p.getCountryReport(dp, "1");
        List<LocalizableString> t = m1.getTitle();
        assertEquals(2, t.size());
        List<String> keys = Arrays.asList("en", "es");
        for(LocalizableString ob : t) {
            assertTrue(keys.contains(ob.getLanguage()));
        }

        keys = Arrays.asList("English", "Spanish");
        for(LocalizableString ob : t) {
            assertTrue(keys.contains(ob.getValue()));
        }

        dp.closeResources();
    }


    @Test public void testGetNationalPlan() {
        IDataProvider dp = DataProviderFactory.getDataProvider(INationalPlan.class);

        AbstractNationalPlan m1 = p.getNationalPlan(dp, "1");
        assertNotNull(m1);

        AbstractNationalPlan m2 = p.getNationalPlan(dp, "xx");
        assertNull(m2);

        dp.closeResources();
    }


    @Test public void testGetNationalPlans() {
        IDataProvider dp = DataProviderFactory.getDataProvider(INationalPlan.class);

        List<AbstractNationalPlan> v = p.getNationalPlans(dp, null, 0, null);
        assertEquals(4, v.size());

        dp.closeResources();
    }


    @Test public void testGetNationalPlanCount() {
        IDataProvider dp = DataProviderFactory.getDataProvider(INationalPlan.class);

        assertEquals(4, (int)p.getNationalPlansCount(dp, null));

        dp.closeResources();
    }


    @Test public void testGetNationalPlanTitle() {
        IDataProvider dp = DataProviderFactory.getDataProvider(INationalPlan.class);

        AbstractNationalPlan m1 = p.getNationalPlan(dp, "1");
        List<LocalizableString> t = m1.getTitle();
        assertEquals(2, t.size());
        List<String> keys = Arrays.asList("en", "ro");
        for(LocalizableString ob : t) {
            assertTrue(keys.contains(ob.getLanguage()));
        }

        keys = Arrays.asList("English", "Romaneste");
        for(LocalizableString ob : t) {
            assertTrue(keys.contains(ob.getValue()));
        }

        dp.closeResources();
    }


    @Test public void testGetSite() {
        IDataProvider dp = DataProviderFactory.getDataProvider(ISite.class);

        AbstractSite m1 = p.getSite(dp, "1");
        assertNotNull(m1);

        AbstractSite m2 = p.getSite(dp, "xx");
        assertNull(m2);

        dp.closeResources();
    }


    @Test public void testGetSites() {
        IDataProvider dp = DataProviderFactory.getDataProvider(ISite.class);

        List<AbstractSite> v = p.getSites(dp, null, 0, null);
        assertEquals(2, v.size());

        dp.closeResources();
    }


    @Test public void testGetSitesCount() {
        IDataProvider dp = DataProviderFactory.getDataProvider(ISite.class);

        assertEquals(2, (int)p.getSitesCount(dp, null));

        dp.closeResources();
    }


}
