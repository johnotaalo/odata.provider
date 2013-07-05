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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.core4j.Enumerable;
import org.core4j.Func1;
import org.informea.odata.IDecision;
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
 * Encapsulates a Decision entity used for synchronization handling all the OData specifics. This means that extenders
 * of this class must implement only abstract methods defined by the {@code org.informea.odata.IDecision interface} to
 * encode the primitive properties of the primitive properties of a decision object.
 * @see org.informea.odata.IDecision
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 0.5
 */
public abstract class AbstractDecision implements IDecision, Serializable, IAbstractEntity {

    private static final long serialVersionUID = 3107046642272228923L;

    private static final Logger log = Logger.getLogger(AbstractDecision.class.getName());
    /**
     * Name of the collection as it appears in the OData metadata and response
     */
    public static final String COLLECTION_NAME = "Decisions";
    public static final String NAV_PROP_DOCUMENTS = "documents";
    public static final String NAV_PROP_TITLE = "title";
    public static final String NAV_PROP_LONG_TITLE = "longTitle";
    public static final String NAV_PROP_SUMMARY = "summary";
    public static final String NAV_PROP_CONTENT = "content";
    public static final String NAV_PROP_KEYWORDS = "keywords";

    // OData specific methodscd
    /**
     * This is used by OData4J framework to build the metadata document for the service
     * @return The list with primitive properties of this object.
     */
    public static List<EdmProperty> getPrimitiveProperties() {
        List<EdmProperty> properties = new ArrayList<EdmProperty>();
        properties.add(new EdmProperty("protocolVersion", EdmType.INT16, false));
        properties.add(new EdmProperty("id", EdmType.STRING, false));
        properties.add(new EdmProperty("link", EdmType.STRING, true));
        properties.add(new EdmProperty("type", EdmType.STRING, false));
        properties.add(new EdmProperty("status", EdmType.STRING, false));
        properties.add(new EdmProperty("number", EdmType.STRING, true));
        properties.add(new EdmProperty("treaty", EdmType.STRING, false));
        properties.add(new EdmProperty("published", EdmType.DATETIME, true, null, true, false, null, null, "text", "true", null, null));
        properties.add(new EdmProperty("meetingId", EdmType.STRING, true));
        properties.add(new EdmProperty("meetingTitle", EdmType.STRING, true));
        properties.add(new EdmProperty("meetingUrl", EdmType.STRING, true));
        properties.add(new EdmProperty("updated", EdmType.DATETIME, false));

        return properties;
    }

    /**
     * This is used by OData4J framework to build the metadata document for the service
     * @param namespace Namespace to assign to entity when registering
     * @return The entire object as entity within the metadata
     */
    public static EdmEntityType getEntityType(String namespace) {
        return new EdmEntityType(namespace, null, "Decision", null, Enumerable.create("id").toList(), getPrimitiveProperties(), null);
    }

    /**
     * Transforms each object's field into OProperty and object into OEntity.
     * Used by OData4J framework to serialize output.
     * @param ees Entity set that describes this object. This was defined in metadata for this object,
     * see {@link org.informea.odata.pojo.AbstractDecision#getSchema(java.lang.String, org.odata4j.edm.EdmEntityType, org.odata4j.edm.EdmEntityType)}.
     * @return This object encoded as OEntity
     */
    @Override
    public OEntity asEntity(EdmEntitySet ees) {
        final List<OProperty<?>> properties = new ArrayList<OProperty<?>>();
        properties.add(OProperties.int16("protocolVersion", this.getProtocolVersion()));
        properties.add(OProperties.string("id", getId()));
        properties.add(OProperties.string("link", getLink()));
        properties.add(OProperties.string("type", getType().toString().toLowerCase()));
        properties.add(OProperties.string("status", getStatus().toString().toLowerCase()));
        properties.add(OProperties.string("number", getNumber()));
        properties.add(OProperties.string("treaty", getTreaty().toString().toLowerCase()));
        properties.add(OProperties.datetime("published", getPublished()));
        properties.add(OProperties.datetime("updated", getUpdated()));
        properties.add(OProperties.string("meetingId", getMeetingId()));
        properties.add(OProperties.string("meetingTitle", getMeetingTitle()));
        properties.add(OProperties.string("meetingUrl", getMeetingUrl()));
        return OEntities.create(ees, OEntityKey.create(getId()), properties, new ArrayList<OLink>(), null, null);
    }

    /**
     * This method works the same as {@link org.informea.odata.pojo.AbstractDecision#getSchema(java.lang.String, org.odata4j.edm.EdmEntityType, org.odata4j.edm.EdmEntityType)}, but for a list
     * of entities.
     * @param ees Entity set that describes this object. This was defined in metadata for this object,
     * @param decisions List of objects to encode
     * @return List of encoded objects
     */
    public static List<OEntity> asEntities(final EdmEntitySet ees, List<AbstractDecision> decisions) {
        return Enumerable.create(decisions).select(new Func1<AbstractDecision, OEntity>() {

            @Override
            public OEntity apply(AbstractDecision decision) {
                return decision.asEntity(ees);
            }
        }).toList();
    }

    /**
     * Build the schema metadata for this object
     * @param namespace Namespace to place this entity into
     * @param eetDocuments EDM entity type of the 'documents' property
     * @param eetTerms EDM entity type for the 'terms' property
     * @return Schema used by the producer to declare entities into the metadata document
     */
    public static EdmSchema getSchema(String namespace, EdmEntityType eetDocuments, EdmEntityType eetTerms) {
        List<EdmEntitySet> entitySets = new ArrayList<EdmEntitySet>();
        List<EdmEntityType> entityTypes = new ArrayList<EdmEntityType>();


        // Define Decision entity
        EdmEntityType eetDecision = getEntityType(namespace);
        EdmEntitySet eesDecision = new EdmEntitySet(COLLECTION_NAME, eetDecision);
        entitySets.add(eesDecision);
        entityTypes.add(eetDecision);

        // Decision associations
        List<EdmAssociationSet> lAssoc = new ArrayList<EdmAssociationSet>();
        ////////////////////////////////////////////////////////////////////////// Define documents property of decision
        String propertyName = NAV_PROP_DOCUMENTS;
        EdmEntitySet eesDocuments = new EdmEntitySet(propertyName, eetDocuments);
        entitySets.add(eesDocuments);

        String assocName = String.format("FK_%s_%s", propertyName, COLLECTION_NAME);
        EdmAssociation assoc = new EdmAssociation(namespace, null,
                assocName,
                new EdmAssociationEnd(COLLECTION_NAME, eetDecision, EdmMultiplicity.ZERO_TO_ONE),
                new EdmAssociationEnd(propertyName, eetDocuments, EdmMultiplicity.MANY));

        lAssoc.add(new EdmAssociationSet(assocName, assoc,
                new EdmAssociationSetEnd(new EdmAssociationEnd(COLLECTION_NAME, eetDecision, EdmMultiplicity.ZERO_TO_ONE), eesDecision),
                new EdmAssociationSetEnd(new EdmAssociationEnd(propertyName, eetDocuments, EdmMultiplicity.MANY), eesDocuments)));

        EdmNavigationProperty np = new EdmNavigationProperty(propertyName, assoc, assoc.end1, assoc.end2);
        eetDecision.navigationProperties.add(np);


        propertyName = NAV_PROP_TITLE;
        EdmEntityType eetLocString = LocalizableString.getEntityType(namespace);
        EdmEntitySet eesLocString = new EdmEntitySet(propertyName, eetLocString);
        // entitySets.add(eesLocString);

        assocName = String.format("FK_%s_%s", propertyName, COLLECTION_NAME);
        EdmAssociation assoc1 = new EdmAssociation(namespace, null,
                assocName,
                new EdmAssociationEnd(COLLECTION_NAME, eetDecision, EdmMultiplicity.ZERO_TO_ONE),
                new EdmAssociationEnd(propertyName, eetLocString, EdmMultiplicity.MANY));

        lAssoc.add(new EdmAssociationSet(assocName, assoc1,
                new EdmAssociationSetEnd(new EdmAssociationEnd(COLLECTION_NAME, eetDecision, EdmMultiplicity.ZERO_TO_ONE), eesDecision),
                new EdmAssociationSetEnd(new EdmAssociationEnd(propertyName, eetLocString, EdmMultiplicity.MANY), eesLocString)));

        np = new EdmNavigationProperty(propertyName, assoc1, assoc1.end1, assoc1.end2);
        eetDecision.navigationProperties.add(np);


        ////////////////////////////////////////////////////////////////////////// Define longTitle property of decision
        eetLocString = LocalizableString.getEntityType(namespace);
        propertyName = NAV_PROP_LONG_TITLE;
        eesLocString = new EdmEntitySet(propertyName, eetLocString);
        entitySets.add(eesLocString);

        assocName = String.format("FK_%s_%s", propertyName, COLLECTION_NAME);
        EdmAssociation assoc2 = new EdmAssociation(namespace, null,
                assocName,
                new EdmAssociationEnd(COLLECTION_NAME, eetDecision, EdmMultiplicity.ZERO_TO_ONE),
                new EdmAssociationEnd(propertyName, eetLocString, EdmMultiplicity.MANY));

        lAssoc.add(new EdmAssociationSet(assocName, assoc2,
                new EdmAssociationSetEnd(new EdmAssociationEnd(COLLECTION_NAME, eetDecision, EdmMultiplicity.ZERO_TO_ONE), eesDecision),
                new EdmAssociationSetEnd(new EdmAssociationEnd(propertyName, eetLocString, EdmMultiplicity.MANY), eesLocString)));

        np = new EdmNavigationProperty(propertyName, assoc2, assoc2.end1, assoc2.end2);
        eetDecision.navigationProperties.add(np);

        ///////////////////////////////////////////////////////////////////////////// Define sumary property of decision
        propertyName = NAV_PROP_SUMMARY;
        eesLocString = new EdmEntitySet(propertyName, eetLocString);
        entitySets.add(eesLocString);

        assocName = String.format("FK_%s_%s", propertyName, COLLECTION_NAME);
        EdmAssociation assoc3 = new EdmAssociation(namespace, null,
                assocName,
                new EdmAssociationEnd(COLLECTION_NAME, eetDecision, EdmMultiplicity.ZERO_TO_ONE),
                new EdmAssociationEnd(propertyName, eetLocString, EdmMultiplicity.MANY));

        lAssoc.add(new EdmAssociationSet(assocName, assoc3,
                new EdmAssociationSetEnd(new EdmAssociationEnd(COLLECTION_NAME, eetDecision, EdmMultiplicity.ZERO_TO_ONE), eesDecision),
                new EdmAssociationSetEnd(new EdmAssociationEnd(propertyName, eetLocString, EdmMultiplicity.MANY), eesLocString)));

        np = new EdmNavigationProperty(propertyName, assoc3, assoc3.end1, assoc3.end2);
        eetDecision.navigationProperties.add(np);

        //////////////////////////////////////////////////////////////////////////// Define content property of decision
        propertyName = NAV_PROP_CONTENT;
        eesLocString = new EdmEntitySet(propertyName, eetLocString);
        entitySets.add(eesLocString);

        assocName = String.format("FK_%s_%s", propertyName, COLLECTION_NAME);
        EdmAssociation assoc4 = new EdmAssociation(namespace, null,
                assocName,
                new EdmAssociationEnd(COLLECTION_NAME, eetDecision, EdmMultiplicity.ZERO_TO_ONE),
                new EdmAssociationEnd(propertyName, eetLocString, EdmMultiplicity.MANY));

        lAssoc.add(new EdmAssociationSet(assocName, assoc4,
                new EdmAssociationSetEnd(new EdmAssociationEnd(COLLECTION_NAME, eetDecision, EdmMultiplicity.ZERO_TO_ONE), eesDecision),
                new EdmAssociationSetEnd(new EdmAssociationEnd(propertyName, eetLocString, EdmMultiplicity.MANY), eesLocString)));

        np = new EdmNavigationProperty(propertyName, assoc4, assoc4.end1, assoc4.end2);
        eetDecision.navigationProperties.add(np);

        /////////////////////////////////////////////////////////////////////////// Define keywords property of decision
        propertyName = NAV_PROP_KEYWORDS;
        eesLocString = new EdmEntitySet(propertyName, eetTerms);
        entitySets.add(eesLocString);

        assocName = String.format("FK_%s_%s", propertyName, COLLECTION_NAME);
        EdmAssociation assoc5 = new EdmAssociation(namespace, null,
                assocName,
                new EdmAssociationEnd(COLLECTION_NAME, eetDecision, EdmMultiplicity.ZERO_TO_ONE),
                new EdmAssociationEnd(propertyName, eetLocString, EdmMultiplicity.MANY));

        lAssoc.add(new EdmAssociationSet(assocName, assoc5,
                new EdmAssociationSetEnd(new EdmAssociationEnd(COLLECTION_NAME, eetDecision, EdmMultiplicity.ZERO_TO_ONE), eesDecision),
                new EdmAssociationSetEnd(new EdmAssociationEnd(propertyName, eetLocString, EdmMultiplicity.MANY), eesLocString)));

        np = new EdmNavigationProperty(propertyName, assoc5, assoc5.end1, assoc5.end2);
        eetDecision.navigationProperties.add(np);

        // Output everything
        EdmEntityContainer container = new EdmEntityContainer("InforMEAService", true, null, entitySets, lAssoc, null);
        List<EdmEntityContainer> containers = new ArrayList<EdmEntityContainer>();
        containers.add(container);

        // return new EdmSchema(namespace, null, entityTypes, null, Enumerable.create(assoc, assoc1, assoc2, assoc3, assoc4, assoc5).toList(), containers);
        return new EdmSchema(namespace, null, entityTypes, null, Enumerable.create(assoc, assoc2, assoc3, assoc4, assoc5).toList(), containers);
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
        if (null == getType()) {
            log.warning("validate(): type property cannot be null");
            ret = false;
        }
        if (null == getStatus()) {
            log.warning("validate(): status property cannot be null");
            ret = false;
        }
        if (null == getTreaty()) {
            log.warning("validate(): treaty property cannot be null");
            ret = false;
        }
        if (null == getMeetingId() && null == getMeetingUrl()) {
            log.warning("validate(): One of meetingId and/or meetingTitle must be non-null");
            ret = false;
        }
        if (null == getUpdated()) {
            log.warning("validate(): updated property cannot be null. Hint: set to current date/time");
            ret = false;
        }
        return ret;
    }
}
