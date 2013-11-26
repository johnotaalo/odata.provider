package org.informea.odata.producer.toolkit.impl;

import java.util.Date;
import java.util.List;

import org.informea.odata.constants.Treaty;
import org.informea.odata.pojo.AbstractContact;

public class LDAPContact extends AbstractContact {

    private static final long serialVersionUID = 6928879507588988429L;

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
    private boolean primary;
    private Date updated;

    private List<Treaty> treaties;

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
    public Boolean isPrimary() {
        return primary;
    }

    @Override
    public Date getUpdated() {
        return updated;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public void setTreaties(List<Treaty> treaties) {
        this.treaties = treaties;
    }

}
