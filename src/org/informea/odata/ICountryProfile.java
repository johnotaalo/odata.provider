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
import org.informea.odata.constants.Treaty;

/**
 * Defines the general attributes expected to be exposed by the service for a country profile
 * <br />
 * <em>
 * Note: Not all implementors may have all attributes available, but some of them are required in order to
 * successfully use the synchronization process. They are marked as <strong>REQUIRED</strong> below.
 * </em>
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 0.5
 */
public interface ICountryProfile extends java.io.Serializable {

    /**
     * <strong>OData property: <em>treaty</em></strong><br />
     * @return Treaty for this country profile
     */
    Treaty getTreaty();

    /**
     * <strong>OData property: <em>country</em></strong><br />
     * <strong>REQUIRED.</strong>. Primary Key.
     * Country to which profile belongs to
     * @return ISO 3166-1 - 3-letter code for the country (http://en.wikipedia.org/wiki/ISO_3166-1_alpha-3) or ISO 3166-1 - 2-letter country code (http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2)
     */
    String getCountry();

    /**
     * <strong>OData property: <em>entryIntoForce</em></strong><br />
     * A list of pairs (treaty, entry into force of the treaty) objects.
     * @return Information about the Country membership to treaties
     */
    Short getEntryIntoForce();

    /**
     * Date returned by this record will be used by the synchronization process to retrieve only the newest
     * records from the database. It is not mandatory, but if not provided, the service will retrieve always all records.
     * If you don't have this information always return current date.
     * @see org.informea.odata.producer.Producer#getEntities as an example on how to handle queries that will return only the newest records
     * @return Date when this record was last updated.
     */
    Date getUpdated();
}
