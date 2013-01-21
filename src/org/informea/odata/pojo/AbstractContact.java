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
package org.informea.odata.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.core4j.Enumerable;
import org.core4j.Func1;
import org.informea.odata.IContact;
import org.informea.odata.constants.Treaty;
import org.odata4j.core.OEntities;
import org.odata4j.core.OEntity;
import org.odata4j.core.OEntityKey;
import org.odata4j.core.OLink;
import org.odata4j.core.OProperties;
import org.odata4j.core.OProperty;
import org.odata4j.edm.EdmAssociation;
import org.odata4j.edm.EdmAssociationEnd;
import org.odata4j.edm.EdmAssociationSet;
import org.odata4j.edm.EdmAssociationSetEnd;
import org.odata4j.edm.EdmEntityContainer;
import org.odata4j.edm.EdmEntitySet;
import org.odata4j.edm.EdmEntityType;
import org.odata4j.edm.EdmMultiplicity;
import org.odata4j.edm.EdmNavigationProperty;
import org.odata4j.edm.EdmProperty;
import org.odata4j.edm.EdmSchema;
import org.odata4j.edm.EdmType;

/**
 * Encapsulates a Contact entity used for synchronization handling all the OData specifics. This means that extenders
 * of this class must implement only abstract methods defined by the {@code org.informea.odata.IContact interface} to
 * encode the primitive properties of the primitive properties of a contact object.
 * @see org.informea.odata.IContact
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 0.5
 */
public abstract class AbstractContact implements IContact, IAbstractEntity {

    private static final Logger log = Logger.getLogger(AbstractContact.class.getName());
    /**
     * Name of the collection as it appears in the OData metadata and response
     */
    public static final String COLLECTION_NAME = "Contacts";
    public static final String NAV_PROP_TREATIES = "treaties";

    // OData specific definitions
    /**
     * Transforms each object's field into OProperty and object into OEntity.
     * Used by OData4J framework to serialize output.
     * @param ees Entity set that describes this object. This was defined in metadata for this object,
     * see {@link org.informea.odata.pojo.AbstractContact#getSchema(String)}.
     * @return This object encoded as OEntity
     */
    @Override
    public OEntity asEntity(EdmEntitySet ees) {
        final List<OProperty<?>> properties = new ArrayList<OProperty<?>>();
        properties.add(OProperties.int16("protocolVersion", getProtocolVersion()));
        properties.add(OProperties.string("id", getId()));
        properties.add(OProperties.string("country", getCountry()));
        properties.add(OProperties.string("prefix", getPrefix()));
        properties.add(OProperties.string("firstName", getFirstName()));
        properties.add(OProperties.string("lastName", getLastName()));
        properties.add(OProperties.string("position", getPosition()));
        properties.add(OProperties.string("institution", getInstitution()));
        properties.add(OProperties.string("department", getDepartment()));
        properties.add(OProperties.string("address", getAddress()));
        properties.add(OProperties.string("email", getEmail()));
        properties.add(OProperties.string("phone", getPhoneNumber()));
        properties.add(OProperties.string("fax", getFax()));
        properties.add(OProperties.datetime("updated", getUpdated()));
        return OEntities.create(ees, OEntityKey.create(getId()), properties, new ArrayList<OLink>(), null, null);
    }

    /**
     * This method works the same as {@link org.informea.odata.pojo.AbstractContact#getSchema(String)}, but for a list
     * of entities.
     * @param ees Entity set that describes this object. This was defined in metadata for this object,
     * @param contacts List of objects to encode
     * @return List of encoded objects
     */
    public static List<OEntity> asEntities(final EdmEntitySet ees, List<AbstractContact> contacts) {
        return Enumerable.create(contacts).select(new Func1<AbstractContact, OEntity>() {

            @Override
            public OEntity apply(AbstractContact contact) {
                return contact.asEntity(ees);
            }
        }).toList();
    }

    /**
     * This is used by OData4J framework to build the metadata document for the service
     * @return The list with primitive properties of this object.
     */
    public static List<EdmProperty> getPrimitiveProperties() {
        List<EdmProperty> properties = new ArrayList<EdmProperty>();
        properties.add(new EdmProperty("protocolVersion", EdmType.INT16, false));
        properties.add(new EdmProperty("id", EdmType.STRING, false));
        properties.add(new EdmProperty("country", EdmType.STRING, false));
        properties.add(new EdmProperty("prefix", EdmType.STRING, true));
        properties.add(new EdmProperty("firstName", EdmType.STRING, false));
        properties.add(new EdmProperty("lastName", EdmType.STRING, false));
        properties.add(new EdmProperty("position", EdmType.STRING, true));
        properties.add(new EdmProperty("institution", EdmType.STRING, true));
        properties.add(new EdmProperty("department", EdmType.STRING, true));
        properties.add(new EdmProperty("address", EdmType.STRING, true));
        properties.add(new EdmProperty("email", EdmType.STRING, true));
        properties.add(new EdmProperty("phone", EdmType.STRING, true));
        properties.add(new EdmProperty("fax", EdmType.STRING, true));
        properties.add(new EdmProperty("updated", EdmType.DATETIME, false, null, true, false, null, null, "text", "true", null, null));

        return properties;
    }

    /**
     * This is used by OData4J framework to build the metadata document for the service
     * @param namespace Namespace to assign to entity when registering
     * @return The entire object as entity within the metadata
     */
    public static EdmEntityType getEntityType(String namespace) {
        return new EdmEntityType(namespace, null, "Contact", null, Enumerable.create("id").toList(), getPrimitiveProperties(), null);
    }

    /**
     * Build the schema metadata for this object
     * @param namespace Namespace to place this entity into
     * @return Schema used by the producer to declare entities into the metadata document
     */
    public static EdmSchema getSchema(String namespace) {
        List<EdmEntitySet> entitySets = new ArrayList<EdmEntitySet>();
        List<EdmEntityType> entityTypes = new ArrayList<EdmEntityType>();

        // Define contact entity
        EdmEntityType eet = getEntityType(namespace);
        EdmEntitySet ees = new EdmEntitySet(COLLECTION_NAME, eet);
        entitySets.add(ees);
        entityTypes.add(eet);

        ////////////////////////////////////////////////////////////////////////////// Define treaties navigable property
        String propertyName = NAV_PROP_TREATIES;

        EdmEntityType eetTreaty = Treaty.getEntityType(namespace);
        entityTypes.add(eetTreaty);
        EdmEntitySet eesTreaty = new EdmEntitySet(propertyName, eetTreaty);
        entitySets.add(eesTreaty);


        String assocName = String.format("FK_%s_%s", propertyName, COLLECTION_NAME);
        EdmAssociation assoc1 = new EdmAssociation(namespace, null,
                assocName,
                new EdmAssociationEnd(COLLECTION_NAME, eet, EdmMultiplicity.ZERO_TO_ONE),
                new EdmAssociationEnd(propertyName, eetTreaty, EdmMultiplicity.MANY));

        List<EdmAssociationSet> lAssoc = new ArrayList<EdmAssociationSet>();
        lAssoc.add(new EdmAssociationSet(assocName, assoc1,
                new EdmAssociationSetEnd(new EdmAssociationEnd(COLLECTION_NAME, eet, EdmMultiplicity.ZERO_TO_ONE), ees),
                new EdmAssociationSetEnd(new EdmAssociationEnd(propertyName, eetTreaty, EdmMultiplicity.MANY), eesTreaty)));

        EdmNavigationProperty np = new EdmNavigationProperty(propertyName, assoc1, assoc1.end1, assoc1.end2);
        eet.navigationProperties.add(np);

        // Output everything
        EdmEntityContainer container = new EdmEntityContainer("InforMEAService", true, null, entitySets, lAssoc, null);
        List<EdmEntityContainer> containers = new ArrayList<EdmEntityContainer>();
        containers.add(container);

        return new EdmSchema(namespace, null, entityTypes, null, Enumerable.create(assoc1).toList(), containers);
    }

    @Override
    public boolean validate() {
        boolean ret = true;
        if (null == getProtocolVersion()) {
            log.warning("validate(): protocolVersion property cannot be null");
            ret = false;
        }
        if (null == getId()) {
            log.warning("validate(): id property cannot be null");
            ret = false;
        }
        if (null == getCountry()) {
            log.warning("validate(): country property cannot be null");
            ret = false;
        }
        if (null == getUpdated()) {
            log.warning("validate(): updated property cannot be null. Hint: set to current date/time");
            ret = false;
        }
        return ret;
    }
}
