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
package org.informea.odata;

import java.util.Date;
import java.util.List;

import org.informea.odata.constants.Treaty;
import org.informea.odata.pojo.DecisionDocument;
import org.informea.odata.pojo.LocalizableString;
import org.informea.odata.pojo.VocabularyTerm;

/**
 * Defines the general attributes for a decision.
 * <em>
 *   Note: Not all producer may have all attributes available,
 *   but some of them are required in order to successfully use the synchronization process.
 *   They are marked as <strong>REQUIRED</strong> below.
 * </em>
 * Determining if a record was updated and must be modified in InforMEA can be achieved by comparing their getUpdated() value.
 * @see IDecision#getUpdated on how you should handle this.
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 0.5
 */
public interface IDecision extends java.io.Serializable {

    /**
     * <strong>OData property: <em>protocolVersion</em></strong><br />
     * This method returns the protocol version for this decision format. This information will be used by InforMEA
     * portal to negotiate various aspects of data retrieval for this entity regarding future updates. For example if
     * an provider decides to upgrade its service and it will not longer be compatible with InforMEA, will change this
     * protocol version to another value (ex. from 1 to 2) and <em>will notify InforMEA team about this change to handle
     * the schema update using the new metadata schema, otherwise synchronization will no longer work</em>.
     * This protocol update could be applied for retrieval of resources such as localized content (short title, long title, documents etc.).
     * The protocol version MUST NOT be updated if API still maintains compatibility with the old InforMEA portal.
     * Example:
     * For now the shotTitle and longTitle are retrieved as navigation properties. In future this may change to: ODATA Function - GetShortTitle(), ODATA complex type or OData property bags etc.
     * If maintains shotTitle as navigable property it would work, but if not, update getProtocolVersion.
     * @return Protocol version.
     */
    Short getProtocolVersion();

    /**
     * <strong>OData property: <em>id</em></strong><br />
     * <strong>REQUIRED.</strong>
     * Decision unique identification in MEA database.
     * In InforMEA portal this is mapped to <strong>ai_decision.original_id</strong> column.
     * @return Decision unique ID as identified in the original MEA database. String because possible some providers may not have INTEGER
     */
    String getId();

    /**
     * <strong>OData property: <em>link</em></strong><br />
     * Get link to Convention web site where decision can be browsed.
     * @return URL to the decision
     */
    String getLink();

    /**
     * <strong>OData navigable property: <em>title</em></strong><br />
     * <strong>REQUIRED.</strong>
     * Title of the decision.
     * @return List of LocalizableString objects. Title may be translated in multiple languages.
     */
    List<LocalizableString> getTitle();

    /**
     * <strong>OData navigable property: <em>longTitle</em></strong><br />
     * Long title of the decision.
     * @return Long title of the decision
     */
    List<LocalizableString> getLongTitle();

    /**
     * <strong>OData navigable property: <em>summary</em></strong><br />
     * Get the summary of the decision.
     * In InforMEA portal this is mapped to <strong>ai_decision.summary</strong> column.
     * @return Summary of the decision
     */
    List<LocalizableString> getSummary();

    /**
     * <strong>OData property: <em>type</em></strong><br />
     * <strong>REQUIRED.</strong>
     * Get the type of the decision.
     * In InforMEA portal this is mapped to <strong>ai_decision.type</strong> column.
     * @return Type of decision
     */
    String getType();

    /**
     * <strong>OData property: <em>status</em></strong><br />
     * <strong>REQUIRED.</strong>
     * Get the status of the decision.
     * In InforMEA portal this is mapped to <strong>ai_decision.status</strong> column.
     * @return Status of decision
     */
    String getStatus();

    /**
     * <strong>OData property: <em>number</em></strong><br />
     * Get the number of the decision. Number is not necessarily numeric, but something like 'X/10 COP2'.
     * In InforMEA portal this is mapped to <strong>ai_decision.number</strong> column.
     * @return Number of the decision
     */
    String getNumber();

    /**
     * <strong>OData property: <em>treaty</em></strong><br />
     * <strong>REQUIRED.</strong>
     * Get the treaty of the decision.
     * In InforMEA portal this corresponds to <strong>ai_decision.id_treaty</strong> column that must be decoded.
     * @return Treaty of decision
     */
    Treaty getTreaty();

    /**
     * <strong>OData property: <em>published</em></strong><br />
     * Get the date/time when decision was published.
     * In InforMEA portal this corresponds to <strong>ai_decision.published</strong> column.
     * @return Date when decision was published
     */
    Date getPublished();

    /**
     * <strong>OData property: <em>updated</em></strong><br />
     * Date returned by this record will be used by the synchronization process to retrieve only the newest
     * records from the database. It is not mandatory, but if not provided, the service will retrieve always all records.
     * If you don't have this information always return current date.
     * @see org.informea.odata.producer.Producer#getEntities as an example on how to handle queries that will return only the newest records
     * @return Date when this record was last updated.
     */
    Date getUpdated();

    /**
     * <strong>OData property: <em>meetingId</em></strong><br />
     * Get the ID of the meeting from original database. If this field is not available
     * use the getMeetingTitle and getMeetingUrl to identify the meeting.
     * In InforMEA portal this corresponds to <strong>ai_event.original_id</strong> column.
     * @return ID of the meeting when decision was taken
     */
    String getMeetingId();

    /**
     * <strong>OData property: <em>meetingTitle</em></strong><br />
     * Get the title of the meeting in English when decision was made.
     * In InforMEA portal this corresponds to <strong>ai_decision.meeting_title</strong> column.
     * @return Title of the meeting when decision was taken
     */
    String getMeetingTitle();

    /**
     * <strong>OData property: <em>meetingUrl</em></strong><br />
     * Get the URL of the meeting event when decision was made. This may point, for example, to Convention website.
     * In InforMEA portal this corresponds to <strong>ai_decision.meeting_url</strong> column.
     * @return URL of the meeting when decision was taken
     */
    String getMeetingUrl();

    /**
     * <strong>OData navigable property: <em>content</em></strong><br />
     * Get the content of the decision. Conventions can store their decision in binary format such as PDF/Word, but also in database tables.
     * If decision is stored in database, this method will return that content (additionally to PDF/Word returned by getDocuments()).
     * In InforMEA portal this corresponds to <strong>ai_decision.body</strong> column for English
     * @return Entire body of the decision. We assume conventions do not break the text into paragraphs so full content will be returned.
     */
    List<LocalizableString> getContent();

    /**
     * <strong>OData navigable property: <em>keywords</em></strong><br />
     * Get the list of terms that are used to tag this decision.
     * In InforMEA portal this corresponds to multiple entries in <strong>ai_decision_vocabulary</strong> table (IDs determined based on lookup in voc_concept).
     * <em>Conventions will keep a mapping between local terms and global terms used by central InforMEA portal</em>
     * @return A list of terms that are used by the InforMEA Vocabulary. Not the local terms used internally by the Convention.
     */
    List<VocabularyTerm> getKeywords();

    /**
     * <strong>OData navigable property: <em>documents</em></strong><br />
     * Retrieve the documents (DOC/PDF etc.) available for this decision
     * @return List of documents
     */
    List<DecisionDocument> getDocuments();
}
