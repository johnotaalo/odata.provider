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

/**
 * Defines the general attributes expected to be exposed by the service for a contact person (ex. National Focal Point).
 * <br />
 * <em>
 * Note: Not all implementors may have all attributes available, but some of them are required in order to
 * successfully use the synchronization process. They are marked as <strong>REQUIRED</strong> below.
 * </em>
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 0.5
 */
public interface IContact extends java.io.Serializable {

    /**
     * <strong>OData property: <em>protocolVersion</em></strong><br />
     * This method returns the protocol version for this meeting format. This information will be used by InforMEA
     * portal to negotiate various aspects of data retrieval for a contact regarding future updates. For example if
     * an provider decides to upgrade its service and it will not longer be compatible with InforMEA, will change this
     * protocol version to another value (ex. from 1 to 2) and <em>will notify InforMEA team about this change to handle
     * the schema update using the new metadata schema, otherwise synchronization will no longer work</em>.
     * This protocol update could be applied for retrieval of resources such as localized content (title, description etc.).
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
     * Person Id in the original database. This record will be used during the synchronization process to detect if person
     * was previously imported into the database and application needs to update existing record or insert a new one.
     * @return ID (PK) of the person.
     */
    String getId();

    /**
     * <strong>OData property: <em>treaties</em></strong><br />
     * <strong>REQUIRED.</strong>
     * One person may be assigned to one or multiple treaties.
     * @return List of treaties that this person's is assigned to.
     */
    List<Treaty> getTreaties();

    /**
     * <strong>OData property: <em>country</em></strong><br />
     * <strong>REQUIRED.</strong>
     * Country where this person is located
     * @return ISO 3166-1 - 3-letter code for the country (http://en.wikipedia.org/wiki/ISO_3166-1_alpha-3) or ISO 3166-1 - 2-letter country code (http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2)
     */
    String getCountry();

    /**
     * <strong>OData property: <em>prefix</em></strong><br />
     * Person prefix such as Mr., Dr., Ms. etc.
     * @return Person name prefix
     */
    String getPrefix();

    /**
     * <strong>OData property: <em>firstName</em></strong><br />
     * <strong>REQUIRED.</strong>
     * @return Person's first name
     */
    String getFirstName();

    /**
     * <strong>OData property: <em>lastName</em></strong><br />
     * <strong>REQUIRED.</strong>
     * @return Person's last name
     */
    String getLastName();

    /**
     * <strong>OData property: <em>position</em></strong><br />
     * @return Person's position within organization
     */
    String getPosition();

    /**
     * <strong>OData property: <em>institution</em></strong><br />
     * @return Person's place of work
     */
    String getInstitution();

    /**
     * <strong>OData property: <em>department</em></strong><br />
     * @return Person's work department
     */
    String getDepartment();

    /**
     * <strong>OData property: <em>address</em></strong><br />
     * @return Person's (or institution's) postal address
     */
    String getAddress();

    /**
     * <strong>OData property: <em>email</em></strong><br />
     * @return Person's e-mail address
     */
    String getEmail();

    /**
     * <strong>OData property: <em>phone</em></strong><br />
     * @return Person's (or institution's) telephone number
     */
    String getPhoneNumber();

    /**
     * <strong>OData property: <em>fax</em></strong><br />
     * @return Person's (or institution's) fax number
     */
    String getFax();

    /**
     * <strong>OData property: <em>primary</em></strong><br />
     * @return If true this is a primary National Focal Point
     * @since 1.2
     */
    Boolean isPrimary();

    /**
     * <strong>OData property: <em>updated</em></strong><br />
     * Date returned by this record will be used by the synchronization process to retrieve only the newest
     * records from the database. It is not mandatory, but if not provided, the service will retrieve always all records.
     * If you don't have this information always return current date.
     * @see org.informea.odata.producer.Producer#getEntities
     * @return Date when this record was last updated.
     */
    Date getUpdated();
}
