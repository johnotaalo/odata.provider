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

import org.informea.odata.ISite;
import org.informea.odata.constants.Treaty;
import org.informea.odata.data.DataProviderFactory;
import org.informea.odata.pojo.AbstractSite;
import org.informea.odata.pojo.LocalizableString;
import org.informea.odata.producer.InvalidValueException;
import org.informea.odata.producer.toolkit.IDataProvider;
import org.informea.odata.producer.toolkit.Producer;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 *
 * @author cristiroma
 */
public class SitesTest {

    private static Producer p;
    private static AbstractSite s1;
    private static AbstractSite s2;


    @BeforeClass
    public static void setUp() {
        p = new Producer();
        IDataProvider dp = DataProviderFactory.getDataProvider(ISite.class);
        dp.openResources();
        s1 = p.getSite(dp, "1");
        s2 = p.getSite(dp, "2");
        dp.closeResources();
    }


    @Test public void testId() {
        assertEquals("id is invalid", "1", s1.getId());
    }


    @Test public void testType() {
        assertEquals("type is invalid", "whc", s1.getType());
    }


    @Test public void testCountry() {
        assertEquals("country is invalid", "ro", s1.getCountry());
    }


    @Test(expected=InvalidValueException.class)
    public void testCountryInvalid() {
        s2.getCountry();
    }


    @Test public void testTreaty() {
        assertEquals("treaty is invalid", Treaty.CBD, s1.getTreaty());
    }


    @Test(expected=InvalidValueException.class)
    public void testTreatyInvalid() {
        s2.getTreaty();
    }


    @Test public void testName() {
        List<LocalizableString> v = s1.getName();
        assertEquals(2, v.size());
        assertTrue(Arrays.asList("en", "ro").contains(v.get(0).getLanguage()));
        assertTrue(Arrays.asList("en", "ro").contains(v.get(1).getLanguage()));

        assertTrue(Arrays.asList("English", "Romaneste").contains(v.get(0).getValue()));
        assertTrue(Arrays.asList("English", "Romaneste").contains(v.get(1).getValue()));
    }


    @Test(expected=InvalidValueException.class)
    public void testNameInvalid() {
        s2.getName();
    }


    @Test public void testUrl() {
        assertEquals("name is invalid", "http://url.com", s1.getUrl());
    }


    @Test public void testUpdated() {
        Calendar c = Calendar.getInstance();
        c.set(2011, 2, 3, 3, 3, 3);
        c.set(Calendar.MILLISECOND, 0);
        assertEquals("updated is invalid", c.getTime(), s1.getUpdated());
    }
}
