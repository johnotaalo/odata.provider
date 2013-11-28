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
import javax.persistence.TemporalType;
import org.informea.odata.constants.Treaty;
import org.informea.odata.pojo.AbstractCountryReport;
import org.informea.odata.pojo.LocalizableString;
import org.informea.odata.producer.InvalidValueException;


/**
 * Country report primary entity
 * <br />
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 1.3.3
 */
@Entity
@Table(name = "informea_country_reports")
@Cacheable
public class CountryReport extends AbstractCountryReport {

    private static final long serialVersionUID = -4398846493070903214L;

    @Id
    private String id;
    private String treaty;
    private String country;

    @Temporal(TemporalType.TIMESTAMP)
    private Date submission;
    private String url;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @OneToMany(cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    @JoinColumn(name="country_report_id")
    private Set<CountryReportTitle> titles;


    @Override
    public List<LocalizableString> getTitle() {
        List<LocalizableString> ret = new ArrayList<LocalizableString>();
        if (titles.isEmpty()) {
            throw new InvalidValueException(String.format("'title' property is empty. Every country report must have at least an title in english. Check informea_country_report_title view. (Affected country report with ID:%s)", id));
        }
        boolean hasEnglish = false;
        for(CountryReportTitle t : titles) {
            String tt = t.getTitle();
            String language = t.getLanguage();
            if("en".equalsIgnoreCase(language)) {
                hasEnglish = true;
            }
            if(tt != null && language != null && !"".equals(tt.trim()) && !"".equals(language.trim())) {
                ret.add(new LocalizableString(t.getLanguage(), tt));
            } else {
                throw new InvalidValueException(String.format("'title' is invalid (language/value pairs must be non-null and one of language/value was null). (Affected country report with ID:%s)", id));
            }
        }
        if(!hasEnglish) {
            throw new InvalidValueException(String.format("Every country report must have the title in english. Check informea_country_report_title view. (Affected country report with ID:%s)", id));
        }
        return ret;
    }


    @Override
    public Short getProtocolVersion() {
        return 1;
    }


    @Override
    public String getId() {
        return id;
    }


    @Override
    public String getCountry() {
        if(country == null) {
            throw new InvalidValueException(String.format("'country' property cannot be null (Affected country report with ID:%s)", id));
        }
        return country;
    }


    @Override
    public Treaty getTreaty() {
        if(treaty == null || treaty.isEmpty()) {
            throw new InvalidValueException(String.format("'treaty' property cannot be null (Affected country report with ID:%s)", id));
        }
        return Treaty.getTreaty(treaty);
    }


    @Override
    public Date getSubmission() {
        return submission;
    }


    @Override
    public String getUrl() {
        if(url == null) {
            throw new InvalidValueException(String.format("'url' property cannot be null (Affected country report with ID:%s)", id));
        }
        return url;
    }


    @Override
    public Date getUpdated() {
        return updated;
    }
}
