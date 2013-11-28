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
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.informea.odata.constants.Treaty;
import org.informea.odata.pojo.AbstractSite;
import org.informea.odata.pojo.LocalizableString;
import org.informea.odata.producer.InvalidValueException;


/**
 * Site primary entity
 * <br />
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 1.3.3
 */
@Entity
@Table(name = "informea_sites")
@Cacheable
public class Site extends AbstractSite {

    private static final long serialVersionUID = 1817551213460990482L;

    @Id
    private String id;
    private String type;
    private String country;
    private String treaty;
    private String url;
    private Double latitude;
    private Double longitude;


    @OneToMany(cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    @JoinColumn(name="site_id")
    private List<SiteName> names;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;


    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getCountry() {
        if(country == null) {
            throw new InvalidValueException(String.format("'country' is invalid. Each site must have a valid non-null country. Check informea_sites (Affected site with ID:%s)", id));
        }
        return country;
    }

    @Override
    public Treaty getTreaty() {
        if(treaty == null || treaty.isEmpty()) {
            throw new InvalidValueException(String.format("'treaty' property cannot be null (Affected site with ID:%s)", id));
        }
        return Treaty.getTreaty(treaty);
    }

    @Override
    public List<LocalizableString> getName() {
        List<LocalizableString> ret = new ArrayList<LocalizableString>();
        if (names.isEmpty()) {
            throw new InvalidValueException(String.format("'name' property is empty. Every site must have at least an name in english. Check informea_sites_name view. (Affected site with ID:%s)", id));
        }
        boolean hasEnglish = false;
        for(SiteName t : names) {
            String tt = t.getName();
            String language = t.getLanguage();
            if("en".equalsIgnoreCase(language)) {
                hasEnglish = true;
            }
            if(tt != null && language != null && !"".equals(tt.trim()) && !"".equals(language.trim())) {
                ret.add(new LocalizableString(t.getLanguage(), tt));
            } else {
                throw new InvalidValueException(String.format("'name' is invalid (language/value pairs must be non-null and one of language/value was null). (Affected site with ID:%s)", id));
            }
        }
        if(!hasEnglish) {
            throw new InvalidValueException(String.format("Every site must have the name in english. Check informea_sites_name view. (Affected site with ID:%s)", id));
        }
        return ret;
    }

    @Override
    public String getUrl() {
        return url;
    }


    @Override
    public Date getUpdated() {
        return updated;
    }


    @Override
    public Double getLatitude() {
        return latitude;
    }

    @Override
    public Double getLongitude() {
        return longitude;
    }

}
