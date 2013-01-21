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
import java.util.Calendar;
import java.util.List;
import org.informea.odata.constants.Treaty;
import org.informea.odata.pojo.AbstractCountryReport;
import org.informea.odata.pojo.LocalizableString;
import org.informea.odata.producer.InvalidValueException;
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
public class CountryReportsTest {

    private static Producer p;
    private static AbstractCountryReport r1;
    private static AbstractCountryReport r2;
    private static AbstractCountryReport r3;
    private static AbstractCountryReport r4;
    private static AbstractCountryReport r5;


    @BeforeClass
    public static void setUp() {
        p = new Producer();
        IDataProvider dp = new DatabaseDataProvider();
        dp.openResources();
        r1 = p.getCountryReport(dp, "1");
        r2 = p.getCountryReport(dp, "2");
        r3 = p.getCountryReport(dp, "3");
        r4 = p.getCountryReport(dp, "4");
        r5 = p.getCountryReport(dp, "5");
        dp.closeResources();
    }


    @Test public void testId() {
        assertEquals("id is invalid", "1", r1.getId());
    }


    @Test public void testCountry() {
        assertEquals("country is invalid", "es", r1.getCountry());
    }


    @Test(expected=InvalidValueException.class)
    public void testCountryInvalid() {
        r2.getCountry();
    }


    @Test public void testTreaty() {
        assertEquals("treaty is invalid", Treaty.CBD, r1.getTreaty());
    }


    @Test(expected=InvalidValueException.class)
    public void testTreatyInvalid() {
        r2.getTreaty();
    }


    @Test public void testTitle() {
        List<LocalizableString> v = r1.getTitle();
        assertEquals(2, v.size());
        assertTrue(Arrays.asList("en", "es").contains(v.get(0).getLanguage()));
        assertTrue(Arrays.asList("en", "es").contains(v.get(1).getLanguage()));

        assertTrue(Arrays.asList("English", "Spanish").contains(v.get(0).getValue()));
        assertTrue(Arrays.asList("English", "Spanish").contains(v.get(1).getValue()));
    }


    @Test(expected=InvalidValueException.class)
    public void testTitleInvalid() {
        r2.getTitle();
    }


    @Test(expected=InvalidValueException.class)
    public void testTitleInvalid2() {
        r3.getTitle();
    }


    @Test(expected=InvalidValueException.class)
    public void testTitleInvalid3() {
        r4.getTitle();
    }


    @Test(expected=InvalidValueException.class)
    public void testTitleInvalid4() {
        r5.getTitle();
    }


    @Test public void testUrl() {
        assertEquals("url is invalid", "http://url.com", r1.getUrl());
    }


    @Test(expected=InvalidValueException.class)
    public void testTreatyUrlInvalid() {
        r2.getUrl();
    }


    @Test public void testSubmission() {
        Calendar c = Calendar.getInstance();
        c.set(2011, 4, 5, 5, 5, 5);
        c.set(Calendar.MILLISECOND, 0);
        assertEquals("submission is invalid", c.getTime(), r1.getSubmission());
    }


    @Test public void testUpdated() {
        Calendar c = Calendar.getInstance();
        c.set(2011, 5, 6, 6, 6, 6);
        c.set(Calendar.MILLISECOND, 0);
        assertEquals("updated is invalid", c.getTime(), r1.getUpdated());
    }
}
