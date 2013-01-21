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
import org.informea.odata.IMeeting;
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
 * Encapsulates a Meeting entity used for synchronization handling all the OData specifics. This means that extenders
 * of this class must implement only abstract methods defined by the {@code org.informea.odata.IMeeting interface} to
 * encode the primitive properties of the primitive properties of a meeting object.
 * @see org.informea.odata.IMeeting
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 0.5
 */
public abstract class AbstractMeeting implements IMeeting, IAbstractEntity {

    private static final Logger log = Logger.getLogger(AbstractMeeting.class.getName());
    /**
     * Name of the collection as it appears in the OData metadata and response
     */
    public static final String COLLECTION_NAME = "Meetings";
    public static final String NAV_PROP_TITLE = "title";
    public static final String NAV_PROP_DESCRIPTION = "description";

    // OData specific definitions
    /**
     * Transforms each object's field into OProperty and object into OEntity.
     * Used by OData4J framework to serialize output.
     * @param ees Entity set that describes this object. This was defined in metadata for this object,
     * see {@link org.informea.odata.pojo.AbstractMeeting#getSchema(String)}.
     * @return This object encoded as OEntity
     */
    @Override
    public OEntity asEntity(EdmEntitySet ees) {
        final List<OProperty<?>> properties = new ArrayList<OProperty<?>>();
        properties.add(OProperties.int16("protocolVersion", getProtocolVersion()));
        properties.add(OProperties.string("id", getId()));
        properties.add(OProperties.string("treaty", getTreaty().toString()));
        properties.add(OProperties.string("url", getUrl()));
        properties.add(OProperties.datetime("start", getStart()));
        properties.add(OProperties.datetime("end", getEnd()));
        properties.add(OProperties.string("repetition", getRepetition() != null ? getRepetition().toString() : null));
        properties.add(OProperties.string("kind", getKind() != null ? getKind().toString() : null));
        properties.add(OProperties.string("type", getType() != null ? getType().toString() : null));
        properties.add(OProperties.string("access", getAccess() != null ? getAccess().toString() : null));
        properties.add(OProperties.string("status", getStatus() != null ? getStatus().toString() : null));
        properties.add(OProperties.string("imageUrl", getImageUrl()));
        properties.add(OProperties.string("imageCopyright", getImageCopyright()));
        properties.add(OProperties.string("location", getLocation()));
        properties.add(OProperties.string("city", getCity()));
        properties.add(OProperties.string("country", getCountry()));
        properties.add(OProperties.double_("latitude", getLatitude()));
        properties.add(OProperties.double_("longitude", getLongitude()));
        properties.add(OProperties.datetime("updated", getUpdated()));
        return OEntities.create(ees, OEntityKey.create(getId()), properties, new ArrayList<OLink>(), null, null);
    }

    /**
     * This method works the same as {@link org.informea.odata.pojo.AbstractMeeting#getSchema(String)}, but for a list
     * of entities.
     * @param ees Entity set that describes this object. This was defined in metadata for this object,
     * @param meetings List of objects to encode
     * @return List of encoded objects
     */
    public static List<OEntity> asEntities(final EdmEntitySet ees, List<AbstractMeeting> meetings) {
        return Enumerable.create(meetings).select(new Func1<AbstractMeeting, OEntity>() {

            @Override
            public OEntity apply(AbstractMeeting meeting) {
                return meeting.asEntity(ees);
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
        properties.add(new EdmProperty("treaty", EdmType.STRING, false));
        properties.add(new EdmProperty("url", EdmType.STRING, true));
        properties.add(new EdmProperty("start", EdmType.DATETIME, false, null, true, false, null, null, "text", "true", null, null));
        properties.add(new EdmProperty("end", EdmType.DATETIME, true, null, true, false, null, null, "text", "true", null, null));
        properties.add(new EdmProperty("repetition", EdmType.STRING, true));
        properties.add(new EdmProperty("kind", EdmType.STRING, true));
        properties.add(new EdmProperty("type", EdmType.STRING, true));
        properties.add(new EdmProperty("access", EdmType.STRING, true));
        properties.add(new EdmProperty("status", EdmType.STRING, true));
        properties.add(new EdmProperty("imageUrl", EdmType.STRING, true));
        properties.add(new EdmProperty("imageCopyright", EdmType.STRING, true));
        properties.add(new EdmProperty("location", EdmType.STRING, true));
        properties.add(new EdmProperty("city", EdmType.STRING, false));
        properties.add(new EdmProperty("country", EdmType.STRING, false));
        properties.add(new EdmProperty("latitude", EdmType.DOUBLE, true));
        properties.add(new EdmProperty("longitude", EdmType.DOUBLE, true));
        properties.add(new EdmProperty("updated", EdmType.DATETIME, false, null, true, false, null, null, "text", "true", null, null));

        return properties;
    }

    /**
     * This is used by OData4J framework to build the metadata document for the service
     * @param namespace Namespace to assign to entity when registering
     * @return The entire object as entity within the metadata
     */
    public static EdmEntityType getEntityType(String namespace) {
        return new EdmEntityType(namespace, null, "Meeting", null, Enumerable.create("id").toList(), getPrimitiveProperties(), null);
    }

    /**
     * Build the schema metadata for this object
     * @param namespace Namespace to place this entity into
     * @return Schema used by the producer to declare entities into the metadata document
     */
    public static EdmSchema getSchema(String namespace) {
        List<EdmEntitySet> entitySets = new ArrayList<EdmEntitySet>();
        List<EdmEntityType> entityTypes = new ArrayList<EdmEntityType>();

        // Define meetings entity
        EdmEntityType eet = getEntityType(namespace);
        EdmEntitySet ees = new EdmEntitySet(COLLECTION_NAME, eet);
        entitySets.add(ees);
        entityTypes.add(eet);

        //////////////////////////////////////////////////////////////////////////////// Define title navigable property
        EdmEntityType eetLocString = LocalizableString.getEntityType(namespace);
        String propertyName = NAV_PROP_TITLE;
        String assocName = String.format("FK_%s_%s", propertyName, COLLECTION_NAME);
        EdmAssociation assoc1 = new EdmAssociation(namespace, null,
                assocName,
                new EdmAssociationEnd(COLLECTION_NAME, eet, EdmMultiplicity.ZERO_TO_ONE),
                new EdmAssociationEnd(propertyName, eetLocString, EdmMultiplicity.MANY));

        EdmEntitySet eesLocString = new EdmEntitySet(propertyName, eetLocString);

        List<EdmAssociationSet> lAssoc = new ArrayList<EdmAssociationSet>();
        lAssoc.add(new EdmAssociationSet(assocName, assoc1,
                new EdmAssociationSetEnd(new EdmAssociationEnd(COLLECTION_NAME, eet, EdmMultiplicity.ZERO_TO_ONE), ees),
                new EdmAssociationSetEnd(new EdmAssociationEnd(propertyName, eetLocString, EdmMultiplicity.MANY), eesLocString)));

        EdmNavigationProperty np = new EdmNavigationProperty(propertyName, assoc1, assoc1.end1, assoc1.end2);
        eet.navigationProperties.add(np);

        ////////////////////////////////////////////////////////////////////////// Define description navigable property
        propertyName = NAV_PROP_DESCRIPTION;

        // Define the description entity
        eesLocString = new EdmEntitySet(propertyName, eetLocString);
        entitySets.add(eesLocString);
        //

        assocName = String.format("FK_%s_%s", propertyName, COLLECTION_NAME);
        EdmAssociation assoc2 = new EdmAssociation(namespace, null,
                assocName,
                new EdmAssociationEnd(COLLECTION_NAME, eet, EdmMultiplicity.ZERO_TO_ONE),
                new EdmAssociationEnd(propertyName, eetLocString, EdmMultiplicity.MANY));

        eesLocString = new EdmEntitySet(propertyName, eetLocString);

        lAssoc.add(new EdmAssociationSet(assocName, assoc2,
                new EdmAssociationSetEnd(new EdmAssociationEnd(COLLECTION_NAME, eet, EdmMultiplicity.ZERO_TO_ONE), ees),
                new EdmAssociationSetEnd(new EdmAssociationEnd(propertyName, eetLocString, EdmMultiplicity.MANY), eesLocString)));

        np = new EdmNavigationProperty(propertyName, assoc2, assoc2.end1, assoc2.end2);
        eet.navigationProperties.add(np);

        // Output everything
        EdmEntityContainer container = new EdmEntityContainer("InforMEAService", true, null, entitySets, lAssoc, null);
        List<EdmEntityContainer> containers = new ArrayList<EdmEntityContainer>();
        containers.add(container);

        return new EdmSchema(namespace, null, entityTypes, null, Enumerable.create(assoc1, assoc2).toList(), containers);
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
        if (null == getTreaty()) {
            log.warning("validate(): treaty property cannot be null");
            ret = false;
        }

        List<LocalizableString> title = getTitle();
        if (null == title) {
            log.warning("validate(): title property cannot be null");
            ret = false;
        } else {
            if (title.isEmpty()) {
                log.warning("validate(): title property cannot be empty");
                ret = false;
            }
            boolean hasEnglish = false;
            for (LocalizableString t : title) {
                if ("en".equalsIgnoreCase(t.getLanguage())) {
                    hasEnglish = true;
                    break;
                }
            }
            if (!hasEnglish) {
                log.warning("validate(): title property does not have English translation");
                ret = false;
            }
        }
        if (null == getStart()) {
            log.warning("validate(): start property cannot be null");
            ret = false;
        }
        if (null == getUpdated()) {
            log.warning("validate(): updated property cannot be null. Hint: set to current date/time");
            ret = false;
        }
        return ret;
    }
}
