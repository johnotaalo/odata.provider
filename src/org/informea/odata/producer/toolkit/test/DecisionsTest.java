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

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.prefs.BackingStoreException;
import org.informea.odata.constants.DecisionStatus;
import org.informea.odata.constants.DecisionType;
import org.informea.odata.constants.MimeType;
import org.informea.odata.constants.Treaty;
import org.informea.odata.pojo.AbstractDecision;
import org.informea.odata.pojo.DecisionDocument;
import org.informea.odata.pojo.LocalizableString;
import org.informea.odata.pojo.VocabularyTerm;
import org.informea.odata.producer.InvalidValueException;
import org.informea.odata.producer.toolkit.Configuration;
import org.informea.odata.producer.toolkit.IDataProvider;
import org.informea.odata.producer.toolkit.Producer;
import org.informea.odata.producer.toolkit.impl.DatabaseDataProvider;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author cristiroma
 */
public class DecisionsTest {

    private static Producer p;
    private static AbstractDecision d1;
    private static AbstractDecision d2;
    private static AbstractDecision d3;


    @BeforeClass
    public static void setUp() {
        p = new Producer();
        IDataProvider dp = new DatabaseDataProvider();
        dp.openResources();
        d1 = p.getDecision(dp, "1"); // Valid
        d2 = p.getDecision(dp, "2"); // Invalid
        d3 = p.getDecision(dp, "3"); // Invalid
        dp.closeResources();
    }


    @Test public void testId() {
        assertEquals("id is invalid", "1", d1.getId());
    }


    @Test public void testLink() {
        assertEquals("link is invalid", "http://decision.link", d1.getLink());
    }


    @Test(expected=InvalidValueException.class)
    public void testLinkInvalid() {
        d2.getLink();
    }


    @Test public void testType() {
        assertEquals("type is invalid", DecisionType.DECISION, d1.getType());
    }


    @Test(expected=InvalidValueException.class)
    public void testTypeInvalid() {
        d2.getType();
    }


    @Test public void testStatus() {
        assertEquals("status is invalid", DecisionStatus.ACTIVE, d1.getStatus());
    }


    @Test(expected=InvalidValueException.class)
    public void testStatusInvalid() {
        d2.getStatus();
    }


    @Test public void testNumber() {
        assertEquals("number is invalid", "NUMBER1", d1.getNumber());
    }


    @Test(expected=InvalidValueException.class)
    public void testNumberInvalid() {
        d2.getNumber();
    }


    @Test public void testTreaty() {
        assertEquals("treaty is invalid", Treaty.BASEL, d1.getTreaty());
    }


    @Test(expected=InvalidValueException.class)
    public void testTreatyInvalid() {
        d2.getTreaty();
    }


    @Test public void testPublished() {
        Calendar c = Calendar.getInstance();
        c.set(2011, 1, 2, 1, 1, 1);
        c.set(Calendar.MILLISECOND, 0);
        assertEquals("published is invalid", c.getTime(), d1.getPublished());
    }


    @Test(expected=InvalidValueException.class)
    public void testPublishedInvalid() {
        d2.getPublished();
    }


    @Test public void testUpdated() {
        Calendar c = Calendar.getInstance();
        c.set(2011, 2, 3, 3, 3, 3);
        c.set(Calendar.MILLISECOND, 0);
        assertEquals("updated is invalid", c.getTime(), d1.getUpdated());
    }


    @Test public void testMeetingId() {
        assertEquals("meetingId is invalid", "meetingId", d1.getMeetingId());
    }


    @Test public void testMeetingTitle() {
        assertEquals("meetingTitle is invalid", "meetingTitle", d1.getMeetingTitle());
    }


    @Test(expected=InvalidValueException.class)
    public void testMeetingInvalid() {
        d3.getMeetingTitle();
    }


    @Test(expected=InvalidValueException.class)
    public void testMeetingInvalid2() {
        d3.getMeetingId();
    }


    @Test public void testMeetingUrl() {
        assertEquals("meetingUrl is invalid", "http://meetingUrl", d1.getMeetingUrl());
    }


    @Test public void testTitle() {
        List<LocalizableString> v = d1.getTitle();
        assertEquals(2, v.size());
        assertTrue(Arrays.asList("en", "ro").contains(v.get(0).getLanguage()));
        assertTrue(Arrays.asList("en", "ro").contains(v.get(1).getLanguage()));

        assertTrue(Arrays.asList("English", "Romaneste").contains(v.get(0).getValue()));
        assertTrue(Arrays.asList("English", "Romaneste").contains(v.get(1).getValue()));
    }


    @Test(expected=InvalidValueException.class)
    public void testTitleInvalid() {
        d2.getTitle();
    }


    @Test public void testLongTitle() {
        List<LocalizableString> v = d1.getLongTitle();
        assertEquals(1, v.size());
        assertTrue(Arrays.asList("cn").contains(v.get(0).getLanguage()));

        assertTrue(Arrays.asList("Chinese").contains(v.get(0).getValue()));
    }


    @Test(expected=InvalidValueException.class)
    public void testLongTitleInvalid() {
        d2.getLongTitle();
    }


    @Test public void testSummary() {
        List<LocalizableString> v = d1.getSummary();
        assertEquals(1, v.size());
        assertTrue(Arrays.asList("de").contains(v.get(0).getLanguage()));

        assertTrue(Arrays.asList("Deutsch").contains(v.get(0).getValue()));
    }


    @Test(expected=InvalidValueException.class)
    public void testSummaryInvalid() {
        d2.getLongTitle();
    }


    @Test public void testContent() {
        List<LocalizableString> v = d1.getContent();
        assertEquals(1, v.size());
        assertTrue(Arrays.asList("es").contains(v.get(0).getLanguage()));

        assertTrue(Arrays.asList("Spanish").contains(v.get(0).getValue()));
    }


    @Test(expected=InvalidValueException.class)
    public void testContentInvalid() {
        d2.getContent();
    }


    @Test public void testKeywords() {
        List<VocabularyTerm> v = d1.getKeywords();
        assertEquals(2, v.size());
        assertTrue(Arrays.asList("http://www.unep.org").contains(v.get(0).getNamespace()));
        assertTrue(Arrays.asList("http://www.unep.org").contains(v.get(1).getNamespace()));

        assertTrue(Arrays.asList("Biological Diversity", "Endangered Species").contains(v.get(0).getTerm()));
        assertTrue(Arrays.asList("Biological Diversity", "Endangered Species").contains(v.get(1).getTerm()));
    }


    @Test(expected=InvalidValueException.class)
    public void testKeywordsInvalid() {
        d2.getKeywords();
    }


    @Test(expected=InvalidValueException.class)
    public void testKeywordsInvalid2() {
        d3.getKeywords();
    }


    @Test public void testDocuments() throws BackingStoreException {
        Configuration.getInstance().setUsePathPrefix(false);

        File pdf = new File("/tmp/test.pdf");
        File doc = new File("/tmp/test.doc");
        if(!pdf.exists()) {
            fail("Missing test file /tmp/test.pdf. Test cannot continue. Put there a Word document file with this name");
            return;
        }
        if(!doc.exists()) {
            fail("Missing test file /tmp/test.doc. Test cannot continue. Put there a PDF document with this name");
            return;
        }

        List<DecisionDocument> v = d1.getDocuments();

        assertEquals(2, v.size());

        assertTrue(Arrays.asList("1", "2").contains(v.get(0).getId()));
        assertTrue(Arrays.asList("1", "2").contains(v.get(1).getId()));

        assertTrue(Arrays.asList("http://test.pdf", "http://test.doc").contains(v.get(0).getUrl()));
        assertTrue(Arrays.asList("http://test.pdf", "http://test.doc").contains(v.get(1).getUrl()));

        assertTrue(Arrays.asList(1L, 1L).contains(v.get(0).getSize()));
        assertTrue(Arrays.asList(1L, 1L).contains(v.get(1).getSize()));

        assertTrue(Arrays.asList(MimeType.PDF, MimeType.DOC).contains(v.get(0).getMimeType()));
        assertTrue(Arrays.asList(MimeType.PDF, MimeType.DOC).contains(v.get(1).getMimeType()));

        assertTrue(Arrays.asList("en", "ar").contains(v.get(0).getLanguage()));
        assertTrue(Arrays.asList("en", "ar").contains(v.get(1).getLanguage()));

        assertTrue(Arrays.asList("test.doc", "test.pdf").contains(v.get(0).getFilename()));
        assertTrue(Arrays.asList("test.doc", "test.pdf").contains(v.get(1).getFilename()));

        assertNotSame(0, v.get(0).getContent().length);
        assertNotSame(0, v.get(1).getContent().length);
    }


    @Test(expected=InvalidValueException.class)
    public void testDocumentsInvalid() throws BackingStoreException {
        Configuration.getInstance().setUsePathPrefix(false);
        d2.getDocuments();
    }


    @Test public void testDocumentsPathPrefix() throws BackingStoreException {
        Configuration.getInstance().setUsePathPrefix(true);
        Configuration.getInstance().setPathPrefix("/tmp");
        d3.getDocuments();
    }

}
