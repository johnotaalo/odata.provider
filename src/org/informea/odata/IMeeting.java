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
import org.informea.odata.constants.MeetingAccess;
import org.informea.odata.constants.MeetingKind;
import org.informea.odata.constants.MeetingRepetition;
import org.informea.odata.constants.MeetingStatus;
import org.informea.odata.constants.MeetingType;
import org.informea.odata.constants.Treaty;
import org.informea.odata.pojo.LocalizableString;

/**
 * Defines the general attributes for an event/meeting.
 * <em>
 *   Note: Not all producer may have all attributes available,
 *   but some of them are required in order to successfully use the synchronization process.
 *   They are marked as <strong>REQUIRED</strong> below.
 * </em>
 * Determining if a record was updated and must be modified in InforMEA can be achieved by comparing their getUpdated() value.
 * @see IMeeting#getUpdated on how you should handle this.
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 0.5
 */
public interface IMeeting extends java.io.Serializable {

    /**
     * <strong>OData property: <em>protocolVersion</em></strong><br />
     * This method returns the protocol version for this meeting format. This information will be used by InforMEA
     * portal to negotiate various aspects of data retrieval for this entity regarding future updates. For example if
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
     * Event Id in the original database. This record will be used during the synchronization process to detect if event
     * was previously imported into the database and application needs to update existing record or insert a new one.
     * @return ID (PK) of the event.
     */
    String getId();

    /**
     * <strong>OData property: <em>treaty</em></strong><br />
     * <strong>REQUIRED.</strong>
     * @return Treaty associated with this event
     */
    Treaty getTreaty();

    /**
     * <strong>OData property: <em>URL</em></strong><br />
     * @return Link to an URL where this event was published on the Internet.
     */
    String getUrl();

    /**
     * <strong>OData navigable property: <em>title</em></strong><br />
     * <strong>REQUIRED.</strong>
     * You must provide at least the English title of the event.
     * Title of the meeting
     * @return List of LocalizableString objects. Title may be translated in multiple languages.
     */
    List<LocalizableString> getTitle();

    /**
     * <strong>OData navigable property: <em>description</em></strong><br />
     * Summary description of the meeting
     * @return List of LocalizableString objects. Description may be translated in multiple languages.
     */
    List<LocalizableString> getDescription();

    /**
     * <strong>OData property: <em>start</em></strong><br />
     * <strong>REQUIRED.</strong>
     * @return Date when meeting starts
     */
    Date getStart();

    /**
     * <strong>OData property: <em>end</em></strong><br />
     * @return Date when meeting ends
     */
    Date getEnd();

    /**
     * <strong>OData property: <em>repetition</em></strong><br />
     * Frequency of this meeting.
     * @return Event repetition pattern
     */
    MeetingRepetition getRepetition();

    /**
     * <strong>OData property: <em>kind</em></strong><br />
     * @return Get the kind of meeting (official, partner, interest etc.)
     */
    MeetingKind getKind();

    /**
     * <strong>OData property: <em>type</em></strong><br />
     * @return Type of event
     */
    MeetingType getType();

    /**
     * <strong>OData property: <em>access</em></strong><br />
     * @return Public access to the event or by invitation.
     */
    MeetingAccess getAccess();

    /**
     * <strong>OData property: <em>status</em></strong><br />
     * @return Status of the event
     */
    MeetingStatus getStatus();

    /**
     * <strong>OData property: <em>imageUrl</em></strong><br />
     * @return Image URL of the event
     */
    String getImageUrl();

    /**
     * <strong>OData property: <em>imageCopyright</em></strong><br />
     * @return Copyright holder of the event's image
     */
    String getImageCopyright();

    /**
     * <strong>OData property: <em>location</em></strong><br />
     * @return Location of the event (ex. Nottingham Castle, grand ball room)
     */
    String getLocation();

    /**
     * <strong>OData property: <em>city</em></strong><br />
     * @return City where event is held (ex. London)
     */
    String getCity();

    /**
     * <strong>OData property: <em>country</em></strong><br />
     * Country where event is located
     * @return ISO 3166-1 - 3-letter code for the country (http://en.wikipedia.org/wiki/ISO_3166-1_alpha-3) or ISO 3166-1 - 2-letter country code (http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2)
     */
    String getCountry();

    /**
     * <strong>OData property: <em>latitude</em></strong><br />
     * Latitude of the event place in decimal degrees (ex. 23.456323). This value may be used to represent the
     * event on a map using existing geographical applications such as Google Maps, Virtual Earth, Yahoo Maps etc.
     * Coordinate returned by this function (projection) must be compatible with the existing applications.
     * @return Latitude as double value
     */
    Double getLatitude();

    /**
     * <strong>OData property: <em>longitude</em></strong><br />
     * Longitude of the event place in decimal degrees (ex. 23.456323). This value may be used to represent the
     * event on a map using existing geographical applications such as Google Maps, Virtual Earth, Yahoo Maps etc.
     * Coordinate returned by this function (projection) must be compatible with the existing applications.
     * @return Longitude as double value
     */
    Double getLongitude();

    /**
     * <strong>OData property: <em>updated</em></strong><br />
     * Date returned by this record will be used by the synchronization process to retrieve only the newest
     * records from the database. It is not mandatory, but if not provided, the service will retrieve always all records.
     * If you don't have this information always return current date.
     * @see org.informea.odata.producer.toolkit.Producer#getEntities as an example on how to handle queries that will return only the newest records
     * @return Date when this record was last updated.
     */
    Date getUpdated();
}
