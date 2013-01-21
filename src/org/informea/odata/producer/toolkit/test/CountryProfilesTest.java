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

import java.util.Calendar;
import org.informea.odata.constants.Treaty;
import org.informea.odata.pojo.AbstractCountryProfile;
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
public class CountryProfilesTest {

    private static Producer p;
    private static AbstractCountryProfile p1;
    private static AbstractCountryProfile p2;


    @BeforeClass
    public static void setUp() {
        p = new Producer();
        IDataProvider dp = new DatabaseDataProvider();
        dp.openResources();
        p1 = p.getCountryProfile(dp, "1");
        p2 = p.getCountryProfile(dp, "2");
        dp.closeResources();
    }


    @Test public void testCountry() {
        assertEquals("country is invalid", "es", p1.getCountry());
    }


    @Test(expected=InvalidValueException.class)
    public void testCountryInvalid() {
        p2.getCountry();
    }


    @Test public void testTreaty() {
        assertEquals("treaty is invalid", Treaty.CBD, p1.getTreaty());
    }


    @Test(expected=InvalidValueException.class)
    public void testTreatyInvalid() {
        p2.getTreaty();
    }


    @Test public void testEntryIntoForce() {
        assertEquals("treaty is invalid", (short)127, (short)p1.getEntryIntoForce());
    }


    @Test(expected=InvalidValueException.class)
    public void testEntryIntoForceInvalid() {
        p2.getEntryIntoForce();
    }


    @Test public void testUpdated() {
        Calendar c = Calendar.getInstance();
        c.set(2011, 3, 4, 4, 4, 4);
        c.set(Calendar.MILLISECOND, 0);
        assertEquals("updated is invalid", c.getTime(), p1.getUpdated());
    }
}
