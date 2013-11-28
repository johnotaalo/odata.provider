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

import org.informea.odata.INationalPlan;
import org.informea.odata.constants.Treaty;
import org.informea.odata.data.DataProviderFactory;
import org.informea.odata.data.IDataProvider;
import org.informea.odata.pojo.AbstractNationalPlan;
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
public class NationalPlansTest {

    private static Producer p;
    private static AbstractNationalPlan p1;
    private static AbstractNationalPlan p2;
    private static AbstractNationalPlan p3;
    private static AbstractNationalPlan p4;


    @BeforeClass
    public static void setUp() {
        p = new Producer();
        IDataProvider dp = DataProviderFactory.getDataProvider(INationalPlan.class);
        dp.openResources();
        p1 = p.getNationalPlan(dp, "1");
        p2 = p.getNationalPlan(dp, "2");
        p3 = p.getNationalPlan(dp, "3");
        p4 = p.getNationalPlan(dp, "4");
        dp.closeResources();
    }


    @Test public void testId() {
        assertEquals("id is invalid", "1", p1.getId());
    }


    @Test public void testTreaty() {
        assertEquals("treaty is invalid", Treaty.CBD, p1.getTreaty());
    }


    @Test(expected=InvalidValueException.class)
    public void testTreatyInvalid() {
        p2.getTreaty();
    }


    @Test public void testCountry() {
        assertEquals("country is invalid", "ro", p1.getCountry());
    }


    @Test(expected=InvalidValueException.class)
    public void testCountryInvalid() {
        p2.getCountry();
    }


    @Test public void testType() {
        assertEquals("type is invalid", "nama", p1.getType());
    }


    @Test public void testUrl() {
        assertEquals("name is invalid", "http://url.com", p1.getUrl());
    }


    @Test public void testTitle() {
        List<LocalizableString> v = p1.getTitle();
        assertEquals(2, v.size());
        assertTrue(Arrays.asList("en", "ro").contains(v.get(0).getLanguage()));
        assertTrue(Arrays.asList("en", "ro").contains(v.get(1).getLanguage()));

        assertTrue(Arrays.asList("English", "Romaneste").contains(v.get(0).getValue()));
        assertTrue(Arrays.asList("English", "Romaneste").contains(v.get(1).getValue()));
    }

    @Test(expected=InvalidValueException.class)
    public void testTitleInvalid() {
        p2.getTitle();
    }


    @Test(expected=InvalidValueException.class)
    public void testTitleInvalid2() {
        p3.getTitle();
    }


    @Test(expected=InvalidValueException.class)
    public void testTitleInvalid3() {
        p4.getTitle();
    }


    @Test public void testSubmission() {
        Calendar c = Calendar.getInstance();
        c.set(2011, 2, 3, 3, 3, 3);
        c.set(Calendar.MILLISECOND, 0);
        assertEquals("submission is invalid", c.getTime(), p1.getSubmission());
    }


    @Test public void testUpdated() {
        Calendar c = Calendar.getInstance();
        c.set(2011, 3, 4, 4, 4, 4);
        c.set(Calendar.MILLISECOND, 0);
        assertEquals("updated is invalid", c.getTime(), p1.getUpdated());
    }

}
