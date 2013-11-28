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

import org.informea.odata.IContact;
import org.informea.odata.constants.Treaty;
import org.informea.odata.data.DataProviderFactory;
import org.informea.odata.pojo.AbstractContact;
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
public class ContactsTest {

    private static Producer p;
    private static AbstractContact c1;
    private static AbstractContact c2;


    @BeforeClass
    public static void setUp() {
        p = new Producer();
        IDataProvider dp = DataProviderFactory.getDataProvider(IContact.class);
        dp.openResources();
        c1 = p.getContact(dp, "1");
        c2 = p.getContact(dp, "2");
        dp.closeResources();
    }


    @Test public void testId() {
        assertEquals("id is invalid", "1", c1.getId());
    }


    @Test public void testCountry() {
        assertEquals("country is invalid", "ro", c1.getCountry());
    }


    @Test public void testCountryInvalid() {
        c2.getCountry();
    }


    @Test public void testPrefix() {
        assertEquals("prefix is invalid", "prefix", c1.getPrefix());
    }


    @Test public void testFirstName() {
        assertEquals("firstName is invalid", "firstName", c1.getFirstName());
    }


    @Test public void testLastName() {
        assertEquals("lastName is invalid", "lastName", c1.getLastName());
    }


    @Test public void testPosition() {
        assertEquals("position is invalid", "position", c1.getPosition());
    }


    @Test public void testInstitution() {
        assertEquals("institution is invalid", "institution", c1.getInstitution());
    }


    @Test public void testDepartment() {
        assertEquals("department is invalid", "department", c1.getDepartment());
    }


    @Test public void testAddress() {
        assertEquals("address is invalid", "address", c1.getAddress());
    }


    @Test public void testEmail() {
        assertEquals("email is invalid", "email", c1.getEmail());
    }


    @Test public void testPhoneNumber() {
        assertEquals("phoneNumber is invalid", "phone", c1.getPhoneNumber());
    }


    @Test public void testFax() {
        assertEquals("fax is invalid", "fax", c1.getFax());
    }


    @Test public void testPrimary() {
        assertEquals("primary is invalid", true, c1.isPrimary());
    }


    @Test public void testUpdated() {
        Calendar c = Calendar.getInstance();
        c.set(2011, 2, 3, 3, 3, 3);
        c.set(Calendar.MILLISECOND, 0);
        assertEquals("updated is invalid", c.getTime(), c1.getUpdated());
    }


    @Test public void testTreaties() {
        List<Treaty> t = c1.getTreaties();
        assertEquals(2, t.size());

        assertTrue(Arrays.asList(Treaty.CBD, Treaty.BASEL).contains(t.get(0)));
        assertTrue(Arrays.asList(Treaty.CBD, Treaty.BASEL).contains(t.get(1)));
    }


    @Test(expected=InvalidValueException.class)
    public void testTreatiesInvalid() {
        c2.getTreaties();
    }

}
