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
package org.informea.odata.producer.example.impl;

import java.util.Date;
import java.util.List;
import org.informea.odata.constants.Treaty;
import org.informea.odata.pojo.AbstractContact;

/**
 * Sample contact implemented for reference.
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 0.6
 */
public class ExampleContact extends AbstractContact {

    public Short protocolVersion;
    public String id;
    public List<Treaty> treaties;
    public String country;
    public String prefix;
    public String firstName;
    public String lastName;
    public String position;
    public String institution;
    public String department;
    public String address;
    public String email;
    public String phoneNumber;
    public String fax;
    public Date updated;

    @Override
    public Short getProtocolVersion() {
        return 1;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public List<Treaty> getTreaties() {
        return treaties;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getPosition() {
        return position;
    }

    @Override
    public String getInstitution() {
        return institution;
    }

    @Override
    public String getDepartment() {
        return department;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String getFax() {
        return fax;
    }

    @Override
    public Date getUpdated() {
        return updated;
    }

    @Override
    public Boolean isPrimary() {
        return false;
    }
}
