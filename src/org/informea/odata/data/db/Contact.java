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
package org.informea.odata.data.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.informea.odata.constants.Treaty;
import org.informea.odata.data.ContactTreaty;
import org.informea.odata.pojo.AbstractContact;
import org.informea.odata.producer.InvalidValueException;


/**
 * Contacts primary entity
 * <br />
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 1.3.3
 */
@Entity
@Table(name = "informea_contacts")
@Cacheable(true)
public class Contact extends AbstractContact {

    private static final long serialVersionUID = -7114130043649439586L;

    @Id
    private String id;
    private String country;
    private String prefix;
    private String firstName;
    private String lastName;
    private String position;
    private String institution;
    private String department;
    private String address;
    private String email;
    private String phoneNumber;
    private String fax;
    private Boolean primary;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updated;

    @OneToMany(cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    @JoinColumn(name="contact_id")
    private Set<ContactTreaty> treaties;


    @Override
    public Short getProtocolVersion() {
        return 1;
    }


    @Override
    public String getId() {
        return id;
    }


    @Override
    public String getAddress() {
        return address;
    }


    @Override
    public String getCountry() {
        return country;
    }


    @Override
    public String getDepartment() {
        return department;
    }


    @Override
    public String getEmail() {
        return email;
    }


    @Override
    public String getFax() {
        return fax;
    }


    @Override
    public String getFirstName() {
        return firstName;
    }


    @Override
    public String getInstitution() {
        return institution;
    }


    @Override
    public String getLastName() {
        return lastName;
    }


    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }


    @Override
    public String getPosition() {
        return position;
    }


    @Override
    public String getPrefix() {
        return prefix;
    }


    @Override
    public Boolean isPrimary() {
        return primary;
    }


    @Override
    public Date getUpdated() {
        return updated;
    }


    @Override
    public List<Treaty> getTreaties() {
        List<Treaty> ret = new ArrayList<Treaty>();

        if(treaties.isEmpty()) {
            throw new InvalidValueException(String.format("'treaties' is invalid. Each contact must have at least one treaty assigned (Affected contact with ID:%s)", id));
        }

        for(ContactTreaty ob : treaties) {
            String t = ob.getTreaty();
            if(t == null || "".equals(t.trim())) {
                throw new InvalidValueException(String.format("'treaties' is invalid. Each contact must have at least one treaty assigned (Affected contact with ID:%s)", id));
            }
            try {
                ret.add(Treaty.getTreaty(t));
            } catch (Exception ex) {
                throw new InvalidValueException(String.format("Invalid 'treaty' value. Each contact must have valid values for 'treaty' (Affected contact with ID:%s)", id), ex);
            }
        }
        return ret;
    }
}
