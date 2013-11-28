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
package org.informea.odata.data;

import java.util.Date;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.informea.odata.constants.Treaty;
import org.informea.odata.pojo.AbstractCountryProfile;
import org.informea.odata.producer.InvalidValueException;


/**
 * Country profile primary entity
 * <br />
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 1.3.3
 */
@Entity
@Table(name = "informea_country_profiles")
@Cacheable
public class CountryProfile extends AbstractCountryProfile {

    private static final long serialVersionUID = 1521108468187837205L;

    @Id
    private String id;
    private String country;
    private String treaty;
    private Short entryIntoForce;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updated;


    @Override
    public String getCountry() {
        if(country == null) {
            throw new InvalidValueException(String.format("'country' is invalid. Each country profile must have a valid non-null country. Check informea_country_profiles (Affected profile with ID:country=%s, entryIntoForce=%s)", country, entryIntoForce));
        }
        return country;
    }


    @Override
    public Treaty getTreaty() {
        if(treaty == null || treaty.isEmpty()) {
            throw new InvalidValueException(String.format("'treaty' is invalid. Each country profile must have a valid non-null treaty. Check informea_country_profiles (Affected profile with ID:country=%s, entryIntoForce=%s)", country, entryIntoForce));
        }
        return Treaty.getTreaty(treaty);
    }


    @Override
    public Short getEntryIntoForce() {
        if(entryIntoForce == null || entryIntoForce <= 0) {
            throw new InvalidValueException(String.format("'entryIntoForce' is invalid. Each country profile must have a valid non-null entryIntoForce. Check informea_country_profiles (Affected profile with ID:country=%s, entryIntoForce=%s)", country, entryIntoForce));
        }
        return entryIntoForce;
    }


    @Override
    public Date getUpdated() {
        return updated;
    }
}
