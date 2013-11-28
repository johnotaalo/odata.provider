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
import org.informea.odata.pojo.LocalizableString;

/**
 * Defines the general attributes for a WHC or Ramsar site.
 * <em>
 *   Note: Not all producer may have all attributes available,
 *   but some of them are required in order to successfully use the synchronization process.
 *   They are marked as <strong>REQUIRED</strong> below.
 * </em>
 * Determining if a record was updated and must be modified in InforMEA can be achieved by comparing their getUpdated() value.
 * @see ISite#getUpdated on how you should handle this.
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 0.5
 */
public interface ISite extends java.io.Serializable {

    /**
     * <strong>OData property: <em>id</em></strong><br />
     * <strong>REQUIRED.</strong>
     * Event Id in the original database. This record will be used during the synchronization process to detect if event
     * was previously imported into the database and application needs to update existing record or insert a new one.
     * @return ID (PK) of the event.
     */
    String getId();

    /**
     * <strong>OData property: <em>type</em></strong><br />
     * <strong>REQUIRED.</strong>
     * @return Type of site
     */
    String getType();

    /**
     * <strong>OData property: <em>country</em></strong><br />
     * <strong>REQUIRED.</strong>
     * Country where this person is located
     * @return ISO 3166-1 - 3-letter code for the country (http://en.wikipedia.org/wiki/ISO_3166-1_alpha-3) or ISO 3166-1 - 2-letter country code (http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2)
     */
    String getCountry();

    /**
     * <strong>OData property: <em>treaty</em></strong><br />
     * <strong>REQUIRED.</strong>
     * @return Treaty associated with this report
     */
    Treaty getTreaty();

    /**
     * <strong>OData property: <em>name</em></strong><br />
     * <strong>REQUIRED.</strong>
     * @return Name of the site. Localizable property
     */
    List<LocalizableString> getName();

    /**
     * <strong>OData property: <em>URL</em></strong><br />
     * @return URL where site information is available online
     */
    String getUrl();

    /**
     * <strong>OData property: <em>latitude</em></strong><br />
     * @return Site geographical latitude
     */
    Double getLatitude();

    /**
     * <strong>OData property: <em>longitude</em></strong><br />
     * @return Site geographical longitude
     */
    Double getLongitude();

    /**
     * <strong>OData property: <em>updated</em></strong><br />
     * Date returned by this record will be used by the synchronization process to retrieve only the newest
     * records from the database. It is not mandatory, but if not provided, the service will retrieve always all records.
     * If you don't have this information always return current date.
     * @see org.informea.odata.producer.Producer#getEntities as an example on how to handle queries that will return only the newest records
     * @return Date when this record was last updated.
     */
    Date getUpdated();
}
