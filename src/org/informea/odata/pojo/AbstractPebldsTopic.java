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
import org.informea.odata.ISite;
import org.informea.odata.producer.toolkit.impl.PebldsTopic;
import org.odata4j.core.OEntity;
import org.odata4j.edm.EdmAssociation;
import org.odata4j.edm.EdmAssociationSet;
import org.odata4j.edm.EdmEntityContainer;
import org.odata4j.edm.EdmEntitySet;
import org.odata4j.edm.EdmEntityType;
import org.odata4j.edm.EdmProperty;
import org.odata4j.edm.EdmSchema;
import org.odata4j.edm.EdmType;

/**
 * Encapsulates a Site entity used for synchronization handling all the OData specifics. This means that extenders
 * of this class must implement only abstract methods defined by the {@code org.informea.odata.ISite interface}.
 * @see org.informea.odata.ISite
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 0.5
 */
public abstract class AbstractPebldsTopic implements Serializable, IAbstractEntity {

    private static final Logger log = Logger.getLogger(AbstractPebldsTopic.class.getName());
    /**
     * Name of the collection as it appears in the OData metadata and response
     */
    public static final String COLLECTION_NAME = "PebldsTopics";

    /**
     * This is used by OData4J framework to build the metadata document for the service
     * @return The list with primitive properties of this object.
     */
    public static List<EdmProperty> getPrimitiveProperties() {
        List<EdmProperty> properties = new ArrayList<EdmProperty>();
        properties.add(new EdmProperty("id", EdmType.INT32, false));
        properties.add(new EdmProperty("name", EdmType.STRING, false));

        return properties;
    }

    /**
     * This method works the same as {@link org.informea.odata.pojo.AbstractPebldsTopic#getSchema(String)}, but for a list
     * of entities.
     * @param ees Entity set that describes this object. This was defined in metadata for this object,
     * @param entities List of objects to encode
     * @return List of encoded objects
     */
    public static List<OEntity> asEntities(final EdmEntitySet ees, List<PebldsTopic> entities) {
        return Enumerable.create(entities).select(new Func1<PebldsTopic, OEntity>() {

            @Override
            public OEntity apply(PebldsTopic entityA) {
                return entityA.asEntity(ees);
            }
        }).toList();
    }


    /**
     * This is used by OData4J framework to build the metadata document for the service
     * @return The entire object as entity within the metadata
     */
    public static EdmEntityType getEntityType(String namespace) {
        return new EdmEntityType(namespace, null, "PebldsTopic", null, Enumerable.create("id").toList(), getPrimitiveProperties(), null);
    }

    /**
     * Build the schema metadata for this object
     * @param namespace Namespace to place this entity into
     * @return Schema used by the producer to declare entities into the metadata document
     */
    public static EdmSchema getSchema(String namespace) {
        List<EdmEntitySet> entitySets = new ArrayList<EdmEntitySet>();
        List<EdmEntityType> entityTypes = new ArrayList<EdmEntityType>();

        EdmEntityType eet = getEntityType(namespace);
        EdmEntitySet ees = new EdmEntitySet(COLLECTION_NAME, eet);

        entitySets.add(ees);
        entityTypes.add(eet);

        // Output everything
        EdmEntityContainer container = new EdmEntityContainer("InforMEAService", true, null, entitySets, new ArrayList<EdmAssociationSet>(), null);
        List<EdmEntityContainer> containers = new ArrayList<EdmEntityContainer>();
        containers.add(container);

        return new EdmSchema(namespace, null, entityTypes, null, new ArrayList<EdmAssociation>(), containers);
    }

    @Override
    public boolean validate() {
        boolean ret = true;
//        if (null == getId()) {
//            log.warning("validate(): id property cannot be null");
//            ret = false;
//        }
//        if (null == getType()) {
//            log.warning("validate(): type property cannot be null");
//            ret = false;
//        }
//        if (null == getCountry()) {
//            log.warning("validate(): country property cannot be null");
//            ret = false;
//        }
//        if (null == getTreaty()) {
//            log.warning("validate(): treaty property cannot be null");
//            ret = false;
//        }
//        List<LocalizableString> name = getName();
//        if (null == name) {
//            log.warning("validate(): name property cannot be null");
//            ret = false;
//        } else {
//            if (name.isEmpty()) {
//                log.warning("validate(): name property cannot be empty");
//                ret = false;
//            }
//            boolean hasEnglish = false;
//            for (LocalizableString t : name) {
//                if ("en".equalsIgnoreCase(t.getLanguage())) {
//                    hasEnglish = true;
//                    break;
//                }
//            }
//            if (!hasEnglish) {
//                log.warning("validate(): name property does not have English translation");
//                ret = false;
//            }
//        }
//        if (null == getUpdated()) {
//            log.warning("validate(): updated property cannot be null. Hint: set to current date/time");
//            ret = false;
//        }
        return ret;
    }
}
