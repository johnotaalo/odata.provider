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
package org.informea.odata.producer.example;

import java.util.Arrays;
import java.util.List;
import org.core4j.Enumerable;
import org.informea.odata.constants.DecisionStatus;
import org.informea.odata.constants.DecisionType;
import org.informea.odata.constants.MeetingAccess;
import org.informea.odata.constants.MeetingKind;
import org.informea.odata.constants.MeetingRepetition;
import org.informea.odata.constants.MeetingStatus;
import org.informea.odata.constants.MeetingType;
import org.informea.odata.constants.MimeType;
import org.informea.odata.constants.NationalPlanType;
import org.informea.odata.constants.SiteType;
import org.informea.odata.constants.Treaty;
import org.informea.odata.pojo.AbstractContact;
import org.informea.odata.pojo.AbstractCountryProfile;
import org.informea.odata.pojo.AbstractCountryReport;
import org.informea.odata.pojo.AbstractDecision;
import org.informea.odata.pojo.AbstractMeeting;
import org.informea.odata.pojo.AbstractNationalPlan;
import org.informea.odata.pojo.AbstractSite;
import org.informea.odata.pojo.VocabularyTerm;
import org.informea.odata.producer.BaseTest;
import org.junit.Test;
import org.odata4j.consumer.ODataConsumer;
import org.odata4j.consumer.behaviors.BasicAuthenticationBehavior;
import org.odata4j.core.OEntity;
import org.odata4j.core.ORelatedEntitiesLink;
import org.odata4j.edm.EdmType;

/**
 * Unit tests for InforMEA example service
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.1, 04/04/2011
 * @since 1.0
 */
public class ExampleProducerTest extends BaseTest {

    @Override
    public void setUp() {
        //TODO Move to parameters from environment
        c = ODataConsumer.create("http://localhost:8080/odata/services/example.svc", new BasicAuthenticationBehavior("odata", "odata"));
    }

    @Test
    public void testListCollections() {
        Enumerable<String> sets = c.getEntitySets();
        List<String> allSets = sets.toList();

        List<String> collections = Arrays.asList(
                AbstractContact.COLLECTION_NAME,
                AbstractCountryProfile.COLLECTION_NAME,
                AbstractCountryReport.COLLECTION_NAME,
                AbstractDecision.COLLECTION_NAME,
                AbstractMeeting.COLLECTION_NAME,
                AbstractNationalPlan.COLLECTION_NAME,
                AbstractSite.COLLECTION_NAME
        );
        for(String collection : collections) {
            assertTrue(String.format("EntitySets do not contain '%s' collection", collection), allSets.contains(collection));
        }
    }

    @Test
    public void testGetDecisions() {
        Enumerable<OEntity> objects = c.getEntities(AbstractDecision.COLLECTION_NAME).execute();
        assertEquals(1, objects.count());
    }

    @Test
    public void testGetDecision() {
        OEntity ob = c.getEntity(AbstractDecision.COLLECTION_NAME, 1).execute();
        assertNotNull(ob);

        String property = "protocolVersion";
        Object expected = new Short("1");
        assertEquals(EdmType.INT16, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "id";
        expected = "1";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "link";
        expected = "http://example.com/decisions/1";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        List<OEntity> title = c.getEntities(ob.getLink("title", ORelatedEntitiesLink.class)).execute().toList();
        assertEquals(2, title.size());
        for (OEntity e : title) {
            String prop = (String)e.getProperty("language").getValue();
            assertTrue(String.format("Wrong value for decision title->language: %s", prop), Arrays.asList("en", "fr").contains(prop));
            prop = (String)e.getProperty("value").getValue();
            assertTrue(String.format("Wrong value for decision title->value: %s", prop), Arrays.asList("Sample decision #1 title", "French title of decision 1").contains(prop));
        }

        List<OEntity> longTitle = c.getEntities(ob.getLink("longTitle", ORelatedEntitiesLink.class)).execute().toList();
        assertEquals(2, longTitle.size());
        for(OEntity e : longTitle) {
            String prop = (String)e.getProperty("language").getValue();
            assertTrue(String.format("Wrong value for decision longTitle->language: %s", prop), Arrays.asList("en", "fr").contains(prop));
            prop = (String)e.getProperty("value").getValue();
            assertTrue(String.format("Wrong value for decision longTitle->value: %s", prop), Arrays.asList("Long title of decision #1", "French long title of decision #1").contains(prop));
        }

        List<OEntity> summary = c.getEntities(ob.getLink("summary", ORelatedEntitiesLink.class)).execute().toList();
        assertEquals(2, summary.size());
        for(OEntity e : summary) {
            String prop = (String)e.getProperty("language").getValue();
            assertTrue(String.format("Wrong value for decision summary->language: %s", prop), Arrays.asList("en", "fr").contains(prop));
            prop = (String)e.getProperty("value").getValue();
            assertTrue(String.format("Wrong value for decision summary->value: %s", prop), Arrays.asList("Summary of decision #1", "French summary of decision #1").contains(prop));
        }

        property = "type";
        expected = DecisionType.DECISION.toString();
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "status";
        expected = DecisionStatus.ACTIVE.toString();
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "number";
        expected = "X/1";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "treaty";
        expected = Treaty.CARTAGENA.toString();
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

//        property = "published";
//        Calendar pub = Calendar.getInstance();
//        pub.set(2009, 12, 23, 0, 0, 0);
//        pub.set(Calendar.MILLISECOND, 0);
//        pub.setTimeZone(TimeZone.getTimeZone("GMT"));
//        expected = pub.getTime();
//        assertEquals(EdmType.DATETIME, ob.getProperty(property).getType());
//        assertEquals(expected, ob.getProperty(property).getValue());

//        property = "updated";
//        Calendar pub = Calendar.getInstance();
//        pub.set(2009, 12, 23, 0, 0, 0);
//        pub.set(Calendar.MILLISECOND, 0);
//        pub.setTimeZone(TimeZone.getTimeZone("GMT"));
//        expected = pub.getTime();
//        assertEquals(EdmType.DATETIME, ob.getProperty(property).getType());
//        assertEquals(expected, ob.getProperty(property).getValue());

        property = "meetingId";
        expected = "2";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "meetingTitle";
        expected = "Meeting for conference of parties 10";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "meetingUrl";
        expected = "http://meeting.url.com/10";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        List<OEntity> content = c.getEntities(ob.getLink("content", ORelatedEntitiesLink.class)).execute().toList();
        assertEquals(2, content.size());
        for (OEntity e : content) {
            String prop = (String)e.getProperty("language").getValue();
            assertTrue(String.format("Wrong value for decision content->language: %s", prop), Arrays.asList("en", "fr").contains(prop));
            prop = (String)e.getProperty("value").getValue();
            assertTrue(String.format("Wrong value for decision content->value: %s", prop), Arrays.asList("Sample decision content in english", "Sample decision content in french").contains(prop));
        }

        List<OEntity> keywords = c.getEntities(ob.getLink("keywords", ORelatedEntitiesLink.class)).execute().toList();
        assertEquals(2, keywords.size());
        for (OEntity e : keywords) {
            String prop = (String)e.getProperty("namespace").getValue();
            assertTrue(String.format("Wrong value for decision keywords->namespace: %s", prop), Arrays.asList("http://example.com", VocabularyTerm.UNEP_NAMESPACE).contains(prop));
            prop = (String)e.getProperty("term").getValue();
            assertTrue(String.format("Wrong value for decision keywords->term: %s", prop), Arrays.asList("chlorophile", "CFC-X01").contains(prop));
        }

        List<OEntity> documents = c.getEntities(ob.getLink("documents", ORelatedEntitiesLink.class)).execute().toList();
        assertEquals(4, documents.size());
        for (OEntity e : documents) {
            String prop = (String)e.getProperty("url").getValue();
            assertTrue(String.format("Wrong url for decision document->url: %s", prop),
                    Arrays.asList("http://www.example.com/downloads/sample_document.doc",
                        "http://www.example.com/downloads/sample_document.pdf",
                        "http://www.example.com/downloads/sample_document.rtf",
                        "http://www.example.com/downloads/sample_document.odt").contains(prop));
            Long size = (Long)e.getProperty("size").getValue();
            assertNotSame("Document size is wrong", 0, size);
            assertNotNull("Document size is null", size);

            prop = (String)e.getProperty("mimeType").getValue();
            assertTrue(String.format("Wrong mime type for decision document->url: %s", prop),
                    Arrays.asList(MimeType.DOC.toString(), MimeType.ODT.toString(), MimeType.PDF.toString(), MimeType.RTF.toString()).contains(prop));

            prop = (String)e.getProperty("language").getValue();
            assertTrue(String.format("Wrong language for decision document->url: %s", prop),
                    Arrays.asList("en").contains(prop));

            prop = (String)e.getProperty("filename").getValue();
            assertTrue(String.format("Wrong language for decision document->url: %s", prop),
                    Arrays.asList("sample_document.doc", "sample_document.odt", "sample_document.pdf", "sample_document.rtf").contains(prop));
        }
    }

    @Test
    public void testGetContacts() {
        Enumerable<OEntity> objects = c.getEntities(AbstractContact.COLLECTION_NAME).execute();
        assertEquals(1, objects.count());
    }

    @Test
    public void testGetContact() {
        OEntity ob = c.getEntity(AbstractContact.COLLECTION_NAME, 1).execute();
        assertNotNull(ob);

        String property = "protocolVersion";
        Object expected = new Short("1");
        assertEquals(EdmType.INT16, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "id"; expected = "1";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "country"; expected = "can";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "prefix"; expected = "Sir";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "firstName"; expected = "Anthony";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "lastName"; expected = "Hopkins";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "position"; expected = "Manager";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "institution"; expected = "Hollywood";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "department"; expected = "Actors";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "address"; expected = "First Osaka Boulevard";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "email"; expected = "antony.hopkins@hollywood.com";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "phone"; expected = "123666999";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "fax"; expected = "321999666";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        //TODO updated
        List<OEntity> treaties = c.getEntities(ob.getLink("treaties", ORelatedEntitiesLink.class)).execute().toList();
        assertEquals(2, treaties.size());
        for (OEntity e : treaties) {
            String prop = (String)e.getProperty("name").getValue();
            assertTrue(String.format("Wrong treaty for contact: %s", prop), Arrays.asList(Treaty.AEWA.toString(), Treaty.MONTREAL.toString()).contains(prop));
        }

    }

    @Test
    public void testGetCountryProfiles() {
        Enumerable<OEntity> objects = c.getEntities(AbstractCountryProfile.COLLECTION_NAME).execute();
        assertEquals(1, objects.count());
    }

    @Test
    public void testGetCountryProfile() {
        OEntity ob = c.getEntity(AbstractCountryProfile.COLLECTION_NAME, Treaty.CARTAGENA.toString()).execute();
        assertNotNull(ob);

        String property = "treaty";
        Object expected = Treaty.CARTAGENA.toString();
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "country";
        expected = "atg";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "entryIntoForce";
        expected = new Short("1999");
        assertEquals(EdmType.INT16, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        //TODO updated
    }


    @Test
    public void testGetCountryReports() {
        Enumerable<OEntity> objects = c.getEntities(AbstractCountryReport.COLLECTION_NAME).execute();
        assertEquals(1, objects.count());
    }

    @Test
    public void testGetCountryReport() {
        OEntity ob = c.getEntity(AbstractCountryReport.COLLECTION_NAME, Treaty.CARTAGENA.toString()).execute();
        assertNotNull(ob);

        String property = "protocolVersion";
        Object expected = new Short("1");
        assertEquals(EdmType.INT16, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "id";
        expected = "1";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "treaty";
        expected = Treaty.NAGOYA.toString();
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "country";
        expected = "bgd";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "url";
        expected = "http://the.new.horizont.com/country_report";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        List<OEntity> title = c.getEntities(ob.getLink("title", ORelatedEntitiesLink.class)).execute().toList();
        assertEquals(2, title.size());
        for (OEntity e : title) {
            String prop = (String)e.getProperty("language").getValue();
            assertTrue(String.format("Wrong value for country report title->language: %s", prop), Arrays.asList("en", "fr").contains(prop));
            prop = (String)e.getProperty("value").getValue();
            assertTrue(String.format("Wrong value for country report title->value: %s", prop), Arrays.asList("Sample country report english title", "Sample country report french title").contains(prop));
        }

        //TODO updated and submisssion
    }


    @Test
    public void testGetMeetings() {
        Enumerable<OEntity> objects = c.getEntities(AbstractMeeting.COLLECTION_NAME).execute();
        assertEquals(1, objects.count());
    }

    @Test
    public void testGetMeeting() {
        OEntity ob = c.getEntity(AbstractMeeting.COLLECTION_NAME, 1).execute();
        assertNotNull(ob);

        String property = "protocolVersion";
        Object expected = new Short("1");
        assertEquals(EdmType.INT16, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "id";
        expected = "1";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "treaty";
        expected = Treaty.MONTREAL.toString();
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "url";
        expected = "http://meeting.online.com";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        List<OEntity> title = c.getEntities(ob.getLink("title", ORelatedEntitiesLink.class)).execute().toList();
        assertEquals(2, title.size());
        for (OEntity e : title) {
            String prop = (String)e.getProperty("language").getValue();
            assertTrue(String.format("Wrong value for meeting title->language: %s", prop), Arrays.asList("en", "fr").contains(prop));
            prop = (String)e.getProperty("value").getValue();
            assertTrue(String.format("Wrong value for meeting title->value: %s", prop), Arrays.asList("Sample meeting in english", "Sample meeting in french").contains(prop));
        }

        List<OEntity> descriptions = c.getEntities(ob.getLink("description", ORelatedEntitiesLink.class)).execute().toList();
        assertEquals(2, descriptions.size());
        for(OEntity e : descriptions) {
            String prop = (String)e.getProperty("language").getValue();
            assertTrue(String.format("Wrong value for meeting description->language: %s", prop), Arrays.asList("en", "fr").contains(prop));
            prop = (String)e.getProperty("value").getValue();
            assertTrue(String.format("Wrong value for meeting description->value: %s", prop), Arrays.asList("Sample meeting description in english", "Sample meeting description in french").contains(prop));
        }

        //TODO start and end

        property = "repetition";
        expected = MeetingRepetition.WEEKLY.toString();
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "kind";
        expected = MeetingKind.OFFICIAL.toString();
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "type";
        expected = MeetingType.SYMPOSIA.toString();
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "access";
        expected = MeetingAccess.PUBLIC.toString();
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "status";
        expected = MeetingStatus.TENTATIVE.toString();
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "imageUrl";
        expected = "http://ia.media-imdb.com/images/M/MV5BMjA5NzMwMjA0OV5BMl5BanBnXkFtZTcwNDMzMzc5Mw@@._V1._SX640_SY948_.jpg";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "imageCopyright";
        expected = "(C) 2011 IMDB";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "location";
        expected = "Brooklyn 123.City Avenue";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "city";
        expected = "New York";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "country";
        expected = "usa";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "latitude";
        expected = new Double(23.442332);
        assertEquals(EdmType.DOUBLE, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "longitude";
        expected = new Double(33.3243433);
        assertEquals(EdmType.DOUBLE, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        //TODO: updated
    }

    @Test
    public void testGetNationalPlans() {
        Enumerable<OEntity> objects = c.getEntities(AbstractNationalPlan.COLLECTION_NAME).execute();
        assertEquals(1, objects.count());
    }

    @Test
    public void testGetNationalPlan() {
        OEntity ob = c.getEntity(AbstractNationalPlan.COLLECTION_NAME, "1").execute();
        assertNotNull(ob);

        String property = "protocolVersion";
        Object expected = new Short("1");
        assertEquals(EdmType.INT16, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "id";
        expected = "1";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "treaty";
        expected = Treaty.RAMSAR.toString();
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "country";
        expected = "bra";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "type";
        expected = NationalPlanType.NBSAP.toString();
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "url";
        expected = "http://national.plan.org/brazil_national_plan";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        List<OEntity> title = c.getEntities(ob.getLink("title", ORelatedEntitiesLink.class)).execute().toList();
        assertEquals(2, title.size());
        for (OEntity e : title) {
            String prop = (String)e.getProperty("language").getValue();
            assertTrue(String.format("Wrong value for national plan title->language: %s", prop), Arrays.asList("en", "fr").contains(prop));
            prop = (String)e.getProperty("value").getValue();
            assertTrue(String.format("Wrong value for national plan title->value: %s", prop), Arrays.asList("Sample national plan english title", "Sample national plan french title").contains(prop));
        }
        //TODO submisssion and updated
    }

    @Test
    public void testGetSites() {
        Enumerable<OEntity> objects = c.getEntities(AbstractSite.COLLECTION_NAME).execute();
        assertEquals(1, objects.count());
    }

    @Test
    public void testGetSite() {
        OEntity ob = c.getEntity(AbstractSite.COLLECTION_NAME, "123").execute();
        assertNotNull(ob);

        String property = "id";
        Object expected = "123";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "type";
        expected = Treaty.RAMSAR.toString();
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "treaty";
        expected = Treaty.RAMSAR.toString();
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "country";
        expected = "bgr";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "type";
        expected = SiteType.RAMSAR.toString();
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "name";
        expected = "Tri gospodari";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        property = "url";
        expected = "http://bulgaria.com/test";
        assertEquals(EdmType.STRING, ob.getProperty(property).getType());
        assertEquals(expected, ob.getProperty(property).getValue());

        //TODO updated
    }
}
