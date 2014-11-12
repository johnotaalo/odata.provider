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
package edw.olingo.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Contacts primary entity
 * <br />
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 1.3.3
 */
@Entity
@Table(name = "informea_contacts")
public class Contact {

    @Id
    @Column (name = "id")
    private String id;
    @Column (nullable = false)
    private String country;
    private String prefix;
    private String firstName;
    private String lastName;
    private String type;
    private String position;
    private String institution;
    private String department;
    private String address;
    private String email;
    private String phoneNumber;
    private String fax;

    // primary is a reserved word in MySQL
    @Column (name="\"primary\"")
    private int primary;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updated;

    @OneToMany(mappedBy = "contact", cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    private List<ContactTreaty> treaties;

    public String getCountry() {
        return country;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPosition() {
        return position;
    }

    public String getInstitution() {
        return institution;
    }

    public String getDepartment() {
        return department;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFax() {
        return fax;
    }

    public int getPrimary() {
        return primary;
    }

    public Date getUpdated() {
        return updated;
    }

    public List<ContactTreaty> getTreaties() {
        return treaties;
    }

    public String getType() {
        return type;
    }
}
