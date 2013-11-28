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
import javax.persistence.*;
import org.informea.odata.constants.*;
import org.informea.odata.pojo.AbstractMeeting;
import org.informea.odata.pojo.LocalizableString;
import org.informea.odata.producer.InvalidValueException;


/**
 * Meeting primary entity
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 1.3.3
 */
@Entity
@Table(name="informea_meetings")
@Cacheable
public class Meeting extends AbstractMeeting {

    private static final long serialVersionUID = 7232022560226661367L;

    @Id
    private String id;
    private String treaty;
    private String url;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date start;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date end;
    private String repetition;
    private String kind;
    private String type;
    private String access;
    private String status;
    private String imageUrl;
    private String imageCopyright;
    private String location;
    private Double latitude;
    private Double longitude;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updated;
    private String country;
    private String city;

    @OneToMany(cascade=CascadeType.ALL, fetch= FetchType.LAZY)
    @JoinColumn(name="meeting_id")
    private Set<MeetingTitle> titles;

    @OneToMany(cascade=CascadeType.ALL, fetch= FetchType.LAZY)
    @JoinColumn(name="meeting_id")
    private Set<MeetingDescription> descriptions;


    @Override
    public Short getProtocolVersion() {
        return 1;
    }


    @Override
    public String getId() {
        return id;
    }


    @Override
    public Treaty getTreaty() {
        if(treaty == null || treaty.isEmpty()) {
            throw new InvalidValueException(String.format("'treaty' property cannot be null (Affected meeting with ID:%s)", id));
        }
        return Treaty.getTreaty(treaty);
    }


    @Override
    public String getUrl() {
        return url;
    }


    @Override
    public List<LocalizableString> getTitle() {
        List<LocalizableString> ret = new ArrayList<LocalizableString>();
        if (titles.isEmpty()) {
            throw new InvalidValueException(String.format("'title' property is empty. Every meeting must have at least an title in english. Check informea_meetings_title view. (Affected meeting with ID:%s)", id));
        }
        boolean hasEnglish = false;
        for(MeetingTitle t : titles) {
            String tt = t.getTitle();
            String language = t.getLanguage();
            if("en".equalsIgnoreCase(language)) {
                hasEnglish = true;
            }
            if(tt != null && language != null && !"".equals(tt.trim()) && !"".equals(language.trim())) {
                ret.add(new LocalizableString(t.getLanguage(), tt));
            } else {
                throw new InvalidValueException(String.format("'title' is invalid (language/value pairs must be non-null and one of language/value was null). (Affected meeting with ID:%s)", id));
            }
        }
        if(!hasEnglish) {
            throw new InvalidValueException(String.format("Every meeting must have the title in english. Check informea_meetings_title view. (Affected meeting with ID:%s)", id));
        }
        return ret;
    }


    @Override
    public List<LocalizableString> getDescription() {
        List<LocalizableString> ret = new ArrayList<LocalizableString>();
        Set<MeetingDescription>_desc = descriptions;
        for(MeetingDescription t : _desc) {
            String d = t.getDescription();
            String language = t.getLanguage();
            if(d != null && language != null && !"".equals(d.trim()) && !"".equals(language.trim())) {
                ret.add(new LocalizableString(t.getLanguage(), d));
            } else {
                throw new InvalidValueException(String.format("'description' is invalid (if exists, language/value pairs must be non-null). One of language/value was null. Check informea_meetings_description view. (Affected meeting with ID:%s)", id));
            }
        }
        return ret;
    }


    @Override
    public Date getStart() {
        if(start == null) {
            throw new InvalidValueException(String.format("'start' property cannot be null (Affected meeting with ID:%s)", id));
        }
        return start;
    }


    @Override
    public Date getEnd() {
        return end;
    }


    @Override
    public String getRepetition() {
        return repetition;
    }


    @Override
    public String getKind() {
        return kind;
    }


    @Override
    public String getType() {
        return type;
    }


    @Override
    public String getAccess() {
        return access;
    }


    @Override
    public String getStatus() {
        return status;
    }


    @Override
    public String getImageUrl() {
        return imageUrl;
    }


    @Override
    public String getImageCopyright() {
        return imageCopyright;
    }


    @Override
    public String getLocation() {
        return location;
    }


    @Override
    public String getCity() {
        return city;
    }


    @Override
    public String getCountry() {
        if(country == null) {
            throw new InvalidValueException(String.format("'country' property cannot be null (Affected meeting with ID:%s)", id));
        }
        if(country.length() != 2 && country.length() != 3) {
            throw new InvalidValueException(String.format("'country' property must be ISO 2 or 3-letter ISO 3166-1 code (Affected meeting with ID:%s. Actual value is: %s)", id, country));
        }
        return country;
    }


    @Override
    public Double getLatitude() {
        return latitude;
    }


    @Override
    public Double getLongitude() {
        return longitude;
    }


    @Override
    public Date getUpdated() {
        return updated;
    }
}
