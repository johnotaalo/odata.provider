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

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.informea.odata.IMeeting;
import org.informea.odata.constants.Treaty;
import org.informea.odata.data.DataProviderFactory;
import org.informea.odata.data.IDataProvider;
import org.informea.odata.pojo.AbstractMeeting;
import org.informea.odata.pojo.LocalizableString;
import org.informea.odata.producer.InvalidValueException;
import org.informea.odata.producer.Producer;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author cristiroma
 */
public class MeetingsTest {

    private static Producer p;
    private static AbstractMeeting m1;
    private static AbstractMeeting m2;
    private static AbstractMeeting m3;
    private static AbstractMeeting m4;
    private static AbstractMeeting m5;


    @BeforeClass
    public static void setUp() {
        p = new Producer();
        IDataProvider dp = DataProviderFactory.getDataProvider(IMeeting.class);
        dp.openResources();
        m1 = p.getMeeting(dp, "1"); // Valid
        m2 = p.getMeeting(dp, "2"); // Invalid treaty
        m3 = p.getMeeting(dp, "3"); // NULL treaty
        m4 = p.getMeeting(dp, "4"); // Invalid: url, start, city, null country
        m5 = p.getMeeting(dp, "5"); // Invalid country
        dp.closeResources();
    }


    @Test public void testTreaty(){
        assertEquals("Treaty is not basel", Treaty.BASEL, m1.getTreaty());
    }

    @Test(expected=InvalidValueException.class)
    public void testTreatyInvalid(){
        m2.getTreaty();
    }


    @Test(expected=InvalidValueException.class)
    public void testTreatyInvalid2(){
        m3.getTreaty();
    }




    @Test public void testUrl() {
        assertEquals("url is invalid", "http://www.test.org", m1.getUrl());
    }


    @Test(expected=InvalidValueException.class)
    public void testUrlInvalid() {
        m4.getUrl();
    }


    @Test public void testStart() {
        Calendar c = Calendar.getInstance();
        c.set(2010, 9, 10, 1, 1, 1);
        c.set(Calendar.MILLISECOND, 0);
        assertEquals("start is invalid", c.getTime(), m1.getStart());
    }


    @Test(expected=InvalidValueException.class)
    public void testStartInvalid() {
        m4.getStart();
    }


    @Test public void testEnd() {
        Calendar c = Calendar.getInstance();
        c.set(2010, 9, 11, 2, 2, 2);
        c.set(Calendar.MILLISECOND, 0);
        assertEquals("end is invalid", c.getTime(), m1.getEnd());
    }


    @Test public void testRepetition() {
        assertEquals("repetition is invalid", "yearly", m1.getRepetition());
    }


    @Test public void testKind() {
        assertEquals("kind is invalid", "official", m1.getKind());
    }


    @Test public void testType() {
        assertTrue("type is invalid", m1.getType() == "cop");
    }


    @Test public void testAccess() {
        assertEquals("access is invalid", "public", m1.getAccess());
    }


    @Test public void testStatus() {
        assertEquals("status is invalid", "confirmed", m1.getStatus());
    }


    @Test public void testImageUrl() {
        assertEquals("imageUrl is invalid", "http://imageurl", m1.getImageUrl());
    }


    @Test public void testImageCopyright() {
        assertEquals("imageCopyright is invalid", "img copyright", m1.getImageCopyright());
    }


    @Test public void testLocation() {
        assertEquals("location is invalid", "location", m1.getLocation());
    }


    @Test public void testCity() {
        assertEquals("city is invalid", "city", m1.getCity());
    }


    @Test(expected=InvalidValueException.class)
    public void testCountry() {
        m4.getCountry();
    }


    @Test(expected=InvalidValueException.class)
    public void testCityInvalid() {
        m4.getCity();
    }


    @Test(expected=InvalidValueException.class)
    public void testCountryInvalid2() {
        m5.getCountry();
    }


    @Test public void testLatitude() {
        assertEquals("latitude is invalid", new Double(1.01), m1.getLatitude());
    }


    @Test public void testLongitude() {
        assertEquals("longitude is invalid", new Double(2.02), m1.getLongitude());
    }


    @Test public void testUpdated() {
        Calendar c = Calendar.getInstance();
        c.set(2010, 0, 1, 1, 1, 1);
        c.set(Calendar.MILLISECOND, 0);
        assertEquals("updated is invalid", c.getTime(), m1.getUpdated());
    }


    @Test public void testTitle() {
        List<LocalizableString> v = m1.getTitle();
        assertEquals(2, v.size());
        assertTrue(Arrays.asList("en", "ro").contains(v.get(0).getLanguage()));
        assertTrue(Arrays.asList("en", "ro").contains(v.get(1).getLanguage()));

        assertTrue(Arrays.asList("English", "Romaneste").contains(v.get(0).getValue()));
        assertTrue(Arrays.asList("English", "Romaneste").contains(v.get(1).getValue()));
    }

    @Test(expected=InvalidValueException.class)
    public void testTitleInvalid() {
        m2.getTitle();
    }


    @Test public void testDescription() {
        List<LocalizableString> v = m1.getDescription();
        assertEquals(2, v.size());
        assertTrue(Arrays.asList("en", "es").contains(v.get(0).getLanguage()));
        assertTrue(Arrays.asList("en", "es").contains(v.get(1).getLanguage()));

        assertTrue(Arrays.asList("desc_en", "desc_es").contains(v.get(0).getValue()));
        assertTrue(Arrays.asList("desc_en", "desc_es").contains(v.get(1).getValue()));
    }

    @Test(expected=InvalidValueException.class)
    public void testDescriptionInvalid() {
        m2.getDescription();
    }
}

