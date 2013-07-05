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
import org.informea.odata.ICountryProfile;
import org.odata4j.core.OEntities;
import org.odata4j.core.OEntity;
import org.odata4j.core.OEntityKey;
import org.odata4j.core.OLink;
import org.odata4j.core.OProperties;
import org.odata4j.core.OProperty;
import org.odata4j.edm.EdmEntityContainer;
import org.odata4j.edm.EdmEntitySet;
import org.odata4j.edm.EdmEntityType;
import org.odata4j.edm.EdmProperty;
import org.odata4j.edm.EdmSchema;
import org.odata4j.edm.EdmType;

/**
 * Encapsulates a CountryProfile entity used for synchronization handling all the OData specifics. This means that extenders
 * of this class must implement only abstract methods defined by the {@code org.informea.odata.ICountryProfile interface} to
 * encode the primitive properties of the primitive properties of a country profile object.
 * @see org.informea.odata.ICountryProfile
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 0.5
 */
public abstract class AbstractCountryProfile implements ICountryProfile, IAbstractEntity {

    private static final long serialVersionUID = -6318759600833034058L;

    private static final Logger log = Logger.getLogger(AbstractCountryProfile.class.getName());
    /**
     * Name of the collection as it appears in the OData metadata and response
     */
    public static final String COLLECTION_NAME = "CountryProfiles";

    // OData specific definitions
    /**
     * Transforms each object's field into OProperty and object into OEntity.
     * Used by OData4J framework to serialize output.
     * @param ees Entity set that describes this object. This was defined in metadata for this object,
     * see {@link org.informea.odata.pojo.AbstractCountryProfile#getSchema(String)}.
     * @return This object encoded as OEntity
     */
    @Override
    public OEntity asEntity(EdmEntitySet ees) {
        final List<OProperty<?>> properties = new ArrayList<OProperty<?>>();
        properties.add(OProperties.string("country", getCountry()));
        properties.add(OProperties.string("treaty", getTreaty() != null ? getTreaty().toString() : null));
        properties.add(OProperties.int16("entryIntoForce", getEntryIntoForce()));
        properties.add(OProperties.datetime("updated", getUpdated()));
        return OEntities.create(ees, OEntityKey.create(getCountry()), properties, new ArrayList<OLink>(), null, null);
    }

    /**
     * This method works the same as {@link org.informea.odata.pojo.AbstractCountryProfile#getSchema(String)}, but for a list
     * of entities.
     * @param ees Entity set that describes this object. This was defined in metadata for this object,
     * @param cps List of objects to encode
     * @return List of encoded objects
     */
    public static List<OEntity> asEntities(final EdmEntitySet ees, List<AbstractCountryProfile> cps) {
        return Enumerable.create(cps).select(new Func1<AbstractCountryProfile, OEntity>() {

            @Override
            public OEntity apply(AbstractCountryProfile cp) {
                return cp.asEntity(ees);
            }
        }).toList();
    }

    // OData specific definitions
    /**
     * This is used by OData4J framework to build the metadata document for the service
     * @return The list with primitive properties of this object.
     */
    public static List<EdmProperty> getPrimitiveProperties() {
        List<EdmProperty> properties = new ArrayList<EdmProperty>();
        properties.add(new EdmProperty("country", EdmType.STRING, false));
        properties.add(new EdmProperty("entryIntoForce", EdmType.INT16, false));
        properties.add(new EdmProperty("treaty", EdmType.STRING, false));
        properties.add(new EdmProperty("updated", EdmType.DATETIME, false, null, true, false, null, null, "text", "true", null, null));

        return properties;
    }

    /**
     * This is used by OData4J framework to build the metadata document for the service
     * @param namespace Namespace to assign to entity when registering
     * @return The entire object as entity within the metadata
     */
    public static EdmEntityType getEntityType(String namespace) {
        return new EdmEntityType(namespace, null, "CountryProfile", null, Enumerable.create("country").toList(), getPrimitiveProperties(), null);
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
        EdmEntityContainer container = new EdmEntityContainer("InforMEAService", true, null, entitySets, null, null);
        List<EdmEntityContainer> containers = new ArrayList<EdmEntityContainer>();
        containers.add(container);

        return new EdmSchema(namespace, null, entityTypes, null, null, containers);
    }

    @Override
    public boolean validate() {
        boolean ret = true;
        if (null == getCountry()) {
            log.warning("validate(): country property cannot be null");
            ret = false;
        }
        if (null == getEntryIntoForce()) {
            log.warning("validate(): entryIntoForce property cannot be null");
            ret = false;
        }
        if (null == getTreaty()) {
            log.warning("validate(): treaty property cannot be null");
            ret = false;
        }
        if (null == getUpdated()) {
            log.warning("validate(): updated property cannot be null. Hint: set to current date/time");
            ret = false;
        }
        return ret;
    }
}
