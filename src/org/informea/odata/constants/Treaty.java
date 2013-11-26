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
package org.informea.odata.constants;

import java.util.ArrayList;
import java.util.List;
import org.core4j.Enumerable;
import org.core4j.Func1;
import org.informea.odata.producer.InvalidValueException;
import org.odata4j.core.OEntities;
import org.odata4j.core.OEntity;
import org.odata4j.core.OEntityKey;
import org.odata4j.core.OLink;
import org.odata4j.core.OProperties;
import org.odata4j.core.OProperty;
import org.odata4j.edm.EdmEntitySet;
import org.odata4j.edm.EdmEntityType;
import org.odata4j.edm.EdmProperty;
import org.odata4j.edm.EdmType;

/**
 * List of treaties implemented. InforMEA portal will use one of these predefined constants
 * @see org.informea.odata.IDecision
 * @see org.informea.odata.IContact
 * @see org.informea.odata.IMeeting
 * @see org.informea.odata.ICountryReport
 * @see org.informea.odata.ICountryProfile
 * @see org.informea.odata.ISite
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 0.5
 */
public enum Treaty {
    AARHUS("aarhus"),
    ABIDJAN("abidjan"),
    AEWA("aewa"),
    ANTIGUA("antigua"),
    APIA("apia"),
    ASCOBANS("ascobans"),
    BAMAKO("bamako"),
    BARCELONA("barcelona"),
    BARCELONA_SPA("barc-spa"),
    BASEL("basel"),
    CARTAGENA("cartagena"),
    CARTAGENA_CONV("cartagena-conv"),
    CBD("cbd"),
    CITES("cites"),
    CMS("cms"),
    DUMPING_PROTOCOL("dumping"),
    EUROBATS("eurobats"),
    ESPOO("espoo"),
    HAZARDOUS("hazardous"),
    JEDDAH("jeddah"),
    KUWAIT("kuwait"),
    KYIVSEA("kyivsea"),
    KYOTO("kyoto"),
    LAND_BASED("land-based"),
    LRTAP("lrtap"),
    MONTREAL("montreal"),
    NAGOYA("nagoya"),
    NAIROBI("nairobi"),
    NOUMEA("noumea"),
    OFFSHORE("offshore"),
    PLANT_TREATY("plant-treaty"),
    PREVENTION_EMERGENCY("preventionemergency"),
    PROTOCOL_WATER_HEALTH("protocolwaterhealth"),
    RAMSAR("ramsar"),
    ROTTERDAM("rotterdam"),
    STOCKHOLM("stockholm"),
    UNCCD("unccd"),
    UNEP("unep"),
    UNFCCC("unfccc"),
    VIENNA("vienna"),
    WATER_CONVENTION("waterconvention"),
    WHC("whc");

    private String name;

    private Treaty(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }


    public static Treaty getTreaty(String value) {
        try {
            return valueOf(value.toUpperCase());
        } catch(Exception ex) {
            if("plant-treaty".equalsIgnoreCase(value)) {
                return PLANT_TREATY;
            } else if("cartagena-conv".equalsIgnoreCase(value)) {
                return CARTAGENA_CONV;
            } else if("barc-spa".equalsIgnoreCase(value)) {
                return BARCELONA_SPA;
            } else if("land-based".equalsIgnoreCase(value)) {
                return LAND_BASED;
            }
            throw new InvalidValueException(
                    String.format(
                            "Unknown treaty value. Invalid value '%s' for  <treaty> property. Treaties are enumerated values and %s cannot is not among them", value, value),
                            ex);
        }
    }


    /**
     * This is used by OData4J framework to build the metadata document for the service
     * @return The list with primitive properties of this object.
     */
    public static List<EdmProperty> getPrimitiveProperties() {
        List<EdmProperty> properties = new ArrayList<EdmProperty>();
        properties.add(new EdmProperty("value", EdmType.STRING, false));
        return properties;
    }

    /**
     * Get the entity description for this object. Used to build service metadata while adding this as property of other objects
     * @param namespace Namespace to put this entity into
     * @return Entity description
     */
    public static EdmEntityType getEntityType(String namespace) {
        return new EdmEntityType(namespace, null, getEntityName(), null, Enumerable.create("value").toList(), getPrimitiveProperties(), null);
    }

    /**
     * Get the entity set that describes this object. Used to build service metadata and response with payload.
     * @param namespace Namespace to put this entity into
     * @return Entity description
     */
    public static EdmEntitySet getEntitySet(String namespace) {
        return new EdmEntitySet(getEntityName(), getEntityType(namespace));
    }

    /**
     * Return the Entity name as will be encoded in OData entity service metadata
     * @return String with entity name
     */
    public static String getEntityName() {
        return "Treaty";
    }

    /**
     * Transforms each object's field into OProperty and object into OEntity.
     * Used by OData4J framework to serialize output.
     * @param namespace Namespace where this entity will be placed
     * @return This object encoded as OEntity
     */
    public OEntity asEntity(String namespace) {
        final List<OProperty<?>> properties = new ArrayList<OProperty<?>>();
        properties.add(OProperties.string("value", name));
        return OEntities.create(getEntitySet(namespace), OEntityKey.create(name), properties, new ArrayList<OLink>(), name, null);
    }

    /**
     * This method works the same as {@link org.informea.odata.constants.Treaty#asEntity(java.lang.String)}, but for a list
     * of entities.
     * @param namespace Namespace where this entity will be placed
     * @param strings List of objects to encode
     * @return List of encoded objects
     */
    public static List<OEntity> asEntities(final String namespace, List<Treaty> strings) {
        return Enumerable.create(strings).select(new Func1<Treaty, OEntity>() {
            @Override
            public OEntity apply(Treaty s) {
                return s.asEntity(namespace);
            }
        }).toList();
    }
}
