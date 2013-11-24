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
package org.informea.odata.producer.toolkit.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.informea.odata.Configuration;
import org.informea.odata.constants.MimeType;
import org.informea.odata.constants.Treaty;
import org.informea.odata.pojo.AbstractDecision;
import org.informea.odata.pojo.DecisionDocument;
import org.informea.odata.pojo.LocalizableString;
import org.informea.odata.pojo.VocabularyTerm;
import org.informea.odata.producer.InvalidValueException;
import org.informea.odata.util.ToolkitUtil;

/**
 * Decision primary entity
 * <br />
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 1.3.3
 */
@Entity
@Table(name = "informea_decisions")
@Cacheable
public class Decision extends AbstractDecision {

    private static final long serialVersionUID = -6671176154328872876L;

    private static final Logger log = Logger.getLogger(Decision.class.getName());
    @Id
    private String id;
    private String link;
    private String number;
    private String status;
    private String type;
    private String treaty;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date published;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updated;
    private String meetingId;
    private String meetingTitle;
    private String meetingUrl;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "decision_id")
    private Set<DecisionTitle> titles;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "decision_id")
    private Set<DecisionLongTitle> longTitles;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "decision_id")
    private Set<DecisionSummary> summaries;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "decision_id")
    private Set<DecisionContent> contents;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "decision_id")
    private Set<DecisionKeyword> keywords;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "decision_id")
    private Set<DecisionDbDocument> documents;

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
        if (this.link != null && !this.link.toLowerCase().startsWith("http://")) {
            throw new InvalidValueException(String.format("'link' property must start with 'http://' (Affected decision with ID:%s)", id));
        }
        return link;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public String getNumber() {
        if (this.number != null && !"".equals(this.number.trim())) {
            return this.number;
        } else {
            throw new InvalidValueException(String.format("'number' cannot be null or empty (Affected decision with ID:%s)", id));
        }
    }

    @Override
    public Treaty getTreaty() {
        if (treaty == null || treaty.isEmpty()) {
            throw new InvalidValueException(String.format("'treaty' property cannot be null (Affected decision with ID:%s)", id));
        }
        return Treaty.getTreaty(treaty);
    }

    @Override
    public Date getPublished() {
        if (this.published != null) {
            return this.published;
        }
        throw new InvalidValueException(String.format("'published' property cannot be null (Affected decision with ID:%s)", id));
    }

    @Override
    public Date getUpdated() {
        return updated;
    }

    @Override
    public String getMeetingId() {
        if (this.meetingId != null && !"".equals(this.meetingId.trim())) {
            return this.meetingId;
        } else {
            if (this.meetingTitle == null || "".equals(this.meetingTitle.trim())) {
                throw new InvalidValueException(String.format("'meetingId/meetingTitle' are invalid. One of them must be non-null (Affected decision with ID:%s)", id));
            }
            return this.meetingId;
        }
    }

    @Override
    public String getMeetingTitle() {
        if (this.meetingTitle != null && !"".equals(this.meetingTitle.trim())) {
            return this.meetingTitle;
        } else {
            if (this.meetingId == null || "".equals(this.meetingId.trim())) {
                throw new InvalidValueException(String.format("'meetingId/meetingTitle' are invalid. One of them must be non-null (Affected decision with ID:%s)", id));
            }
            return this.meetingTitle;
        }
    }

    @Override
    public String getMeetingUrl() {
        if (this.meetingUrl != null && !this.meetingUrl.toLowerCase().startsWith("http://")) {
            throw new InvalidValueException(String.format("'url' property must start with 'http://' (Affected decision with ID:%s)", id));
        }
        return this.meetingUrl;
    }

    @Override
    public List<LocalizableString> getTitle() {
        List<LocalizableString> ret = new ArrayList<LocalizableString>();
        if (titles.isEmpty()) {
            throw new InvalidValueException(String.format("'title' property is empty. Each decision must have at least an title in english. Check informea_decisions_title view. (Affected decision with ID:%s)", id));
        }
        boolean hasEnglish = false;
        for (DecisionTitle t : titles) {
            String tt = t.getTitle();
            String language = t.getLanguage();
            if ("en".equalsIgnoreCase(language)) {
                hasEnglish = true;
            }
            if (tt != null && language != null && !"".equals(tt.trim()) && !"".equals(language.trim())) {
                ret.add(new LocalizableString(t.getLanguage(), tt));
            } else {
                throw new InvalidValueException(String.format("'title' is invalid ( language/value pairs must be non-null, one of language/value was null) (Affected decision with ID:%s)", id));
            }
        }
        if (!hasEnglish) {
            throw new InvalidValueException(String.format("Every decision must have the title in english. Check informea_decisions_title view. (Affected decision with ID:%s)", id));
        }
        return ret;
    }

    @Override
    public List<LocalizableString> getLongTitle() {
        List<LocalizableString> ret = new ArrayList<LocalizableString>();
        for (DecisionLongTitle t : longTitles) {
            String tt = t.getLongTitle();
            String language = t.getLanguage();
            if (tt != null && language != null && !"".equals(tt.trim()) && !"".equals(language.trim())) {
                ret.add(new LocalizableString(t.getLanguage(), tt));
            } else {
                throw new InvalidValueException(String.format("'longTitle' is invalid (if exists, language/value pairs must be non-null, one of language/value was null) (Affected decision with ID:%s)", id));
            }
        }
        return ret;
    }

    @Override
    public List<LocalizableString> getSummary() {
        List<LocalizableString> ret = new ArrayList<LocalizableString>();
        for (DecisionSummary t : summaries) {
            String tt = t.getSummary();
            String language = t.getLanguage();
            if (tt != null && language != null && !"".equals(tt.trim()) && !"".equals(language.trim())) {
                ret.add(new LocalizableString(t.getLanguage(), tt));
            } else {
                throw new InvalidValueException(String.format("'summary' is invalid (if exists, language/value pairs must be non-null, one of language/value was null) (Affected decision with ID:%s)", id));
            }
        }
        return ret;
    }

    @Override
    public List<LocalizableString> getContent() {
        List<LocalizableString> ret = new ArrayList<LocalizableString>();
        Set<DecisionContent> _v = contents;
        for (DecisionContent t : _v) {
            String tt = t.getContent();
            String language = t.getLanguage();
            if (tt != null && language != null && !"".equals(tt.trim()) && !"".equals(language.trim())) {
                ret.add(new LocalizableString(t.getLanguage(), tt));
            } else {
                throw new InvalidValueException(String.format("'content' is invalid (if exists, language/value pairs must be non-null, one of language/value was null) (Affected decision with ID:%s)", id));
            }
        }
        return ret;
    }

    @Override
    public List<VocabularyTerm> getKeywords() {
        List<VocabularyTerm> ret = new ArrayList<VocabularyTerm>();
        for (DecisionKeyword k : keywords) {
            String namespace = k.getNamespace();
            String term = k.getTerm();
            // Validate
            if (namespace == null || "".equals(namespace.trim())) {
                throw new InvalidValueException(String.format("'keywords' is invalid (if exists, keyword->namespace value must be non-null. Check informea_decisions_keywords) (Affected decision with ID:%s)", id));
            }
            if (term == null || "".equals(term.trim())) {
                throw new InvalidValueException(String.format("'keywords' is invalid (if exists, keyword->term value must be non-null. Check informea_decisions_keywords) (Affected decision with ID:%s)", id));
            }
            ret.add(new VocabularyTerm(term, namespace));
        }
        return ret;
    }

    @Override
    public List<DecisionDocument> getDocuments() {
        List<DecisionDocument> ret = new ArrayList<DecisionDocument>();
        for (DecisionDbDocument doc : documents) {
            MimeType mime = doc.getMimeType();
            String language = doc.getLanguage();

            StringBuilder real_path = new StringBuilder();
            if (Configuration.getInstance().isUsePathPrefix()) {
                real_path.append(Configuration.getInstance().getPathPrefix());
                real_path.append(System.getProperty("file.separator"));
            }
            real_path.append(doc.getDiskPath());

            // Validate
            if (mime == null) {
                throw new InvalidValueException(String.format("'documents' is invalid (if exists, document->mimeType value must be non-null. Check informea_decisions_documents) (Affected decision with ID:%s, document with ID:%s)", id, doc.getId()));
            }
            if (language == null || "".equalsIgnoreCase(language.trim())) {
                throw new InvalidValueException(String.format("'documents' is invalid (if exists, document->language value must be non-null. Check informea_decisions_documents) (Affected decision with ID:%s, document with ID:%s)", id, doc.getId()));
            }

            // Validate document phisically exists on the disk or on remote destination
            if (doc.getDiskPath() == null) {
                if (doc.getUrl() == null) {
                    throw new InvalidValueException(String.format("Both 'diskPath and url' properties of the document are null. One of them must point to a real file (Affected decision with ID:%s, document with ID:%s)", id, doc.getId()));
                }
            }

            byte[] content = null;
            if (doc.getDiskPath() != null) {
                log.fine(String.format("Reading decision document from disk: %s", real_path.toString()));
                content = ToolkitUtil.readDecisionDocument(id, mime, real_path.toString());
                if (content == null || content.length == 0) {
                    throw new InvalidValueException(String.format("Could not read document content from '%s'. Check informea_decisions_documents) (Affected decision with ID:%s, document with ID:%s)", real_path.toString(), id, doc.getId()));
                }
            } else {
                log.fine(String.format("Downloading decision document from URL: %s", doc.getUrl()));
                content = ToolkitUtil.downloadDecisionDocument(id, mime, doc.getUrl());
                if (content == null || content.length == 0) {
                    throw new InvalidValueException(String.format("Could not read document content from '%s'. Check informea_decisions_documents) (Affected decision with ID:%s, document with ID:%s)", doc.getUrl(), id, doc.getId()));
                }
            }

            ret.add(new DecisionDocument(doc.getId(), doc.getUrl(), content, content.length, mime, doc.getLanguage(), doc.getFilename()));
        }
        return ret;
    }
}
