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
import org.informea.odata.constants.DecisionStatus;
import org.informea.odata.constants.DecisionType;
import org.informea.odata.constants.Treaty;
import org.informea.odata.pojo.AbstractDecision;
import org.informea.odata.pojo.DecisionDocument;
import org.informea.odata.pojo.LocalizableString;
import org.informea.odata.pojo.VocabularyTerm;

/**
 * Sample decision implemented for reference.
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 0.6
 */
public class ExampleDecision extends AbstractDecision {

    public String id;
    public String link;
    public List<LocalizableString> title;
    public List<LocalizableString> longTitle;
    public List<LocalizableString> summary;
    public DecisionType type;
    public DecisionStatus status;
    public String number;
    public Treaty treaty;
    public Date published;
    public Date updated;
    public String meetingId;
    public String meetingTitle;
    public String meetingUrl;
    public List<LocalizableString> content;
    public List<VocabularyTerm> keywords;
    public List<DecisionDocument> documents;

    @Override
    public Short getProtocolVersion() {
        return 1;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getLink() {
        return link;
    }

    @Override
    public List<LocalizableString> getTitle() {
        return title;
    }

    @Override
    public List<LocalizableString> getLongTitle() {
        return longTitle;
    }

    @Override
    public List<LocalizableString> getSummary() {
        return summary;
    }

    @Override
    public DecisionType getType() {
        return type;
    }

    @Override
    public DecisionStatus getStatus() {
        return status;
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public Treaty getTreaty() {
        return treaty;
    }

    @Override
    public Date getPublished() {
        return published;
    }

    @Override
    public Date getUpdated() {
        return updated;
    }

    @Override
    public String getMeetingId() {
        return meetingId;
    }

    @Override
    public String getMeetingTitle() {
        return meetingTitle;
    }

    @Override
    public String getMeetingUrl() {
        return meetingUrl;
    }

    @Override
    public List<LocalizableString> getContent() {
        return content;
    }

    @Override
    public List<VocabularyTerm> getKeywords() {
        return keywords;
    }

    @Override
    public List<DecisionDocument> getDocuments() {
        return documents;
    }
}
