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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Decision primary entity
 * <br />
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 1.3.3
 */
@Entity
@Table(name = "informea_decisions")
public class Decision {

    private static final Logger log = Logger.getLogger(Decision.class.getName());
    @Id
    @Column(name = "id")
    private String id;
    private String link;
    @Column (nullable = false)
    private String number;
    @Column (nullable = false)
    private String status;
    @Column (nullable = false)
    private String type;
    @Column (nullable = false)
    private String treaty;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date published;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updated;
    @Column(name="meetingId")
    private String meetingId;
    @Column(name="meetingTitle")
    private String meetingTitle;
    @Column(name="meetingUrl")
    private String meetingUrl;

    @OneToMany(mappedBy = "decision", cascade = CascadeType.ALL)
    private List<DecisionTitle> titles = new ArrayList<DecisionTitle>();

    @OneToMany(mappedBy = "decision", cascade = CascadeType.ALL)
    private List<DecisionLongTitle> longTitles = new ArrayList<DecisionLongTitle>();

    @OneToMany(mappedBy = "decision", cascade = CascadeType.ALL)
    private List<DecisionSummary> summaries = new ArrayList<DecisionSummary>();

    @OneToMany(mappedBy = "decision", cascade = CascadeType.ALL)
    private List<DecisionContent> contents = new ArrayList<DecisionContent>();

    @OneToMany(mappedBy = "decision", cascade = CascadeType.ALL)
    private List<DecisionKeyword> keywords = new ArrayList<DecisionKeyword>();

    @OneToMany(mappedBy = "decision", cascade = CascadeType.ALL)
    private List<DecisionFile> files = new ArrayList<DecisionFile>();

    public String getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getNumber() {
        return this.number;
    }

    public String getTreaty() {
        return treaty;
    }

    public Date getPublished() {
        return this.published;
    }

    public Date getUpdated() {
        return updated;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getMeetingTitle() {
        return meetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }

    public String getMeetingUrl() {
        return meetingUrl;
    }

    public void setMeetingUrl(String meetingUrl) {
        this.meetingUrl = meetingUrl;
    }
}
