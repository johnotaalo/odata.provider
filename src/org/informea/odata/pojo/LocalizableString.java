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
 * Describe the attributes for an localizable string. An localizable string contains two fields:
 * language which the value is written in and the value.
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 0.5
 */
public class LocalizableString {

    private static final Logger log = Logger.getLogger(LocalizableString.class.getName());
    private String language;
    private String value;

    public LocalizableString(String language, String value) {
        this.language = language;
        this.value = value;
    }

    /**
     * @return Language of contained value
     */
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return Value of this string
     */
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    // OData specific
    /**
     * This is used by OData4J framework to build the metadata document for the service
     * @return The list with primitive properties of this object.
     */
    public static List<EdmProperty> getPrimitiveProperties() {
        List<EdmProperty> properties = new ArrayList<EdmProperty>();
        properties.add(new EdmProperty("language", EdmType.STRING, false));
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
        return "LocalizableString";
    }

    /**
     * Transforms each object's field into OProperty and object into OEntity.
     * Used by OData4J framework to serialize output.
     * @param namespace Namespace where this entity will be placed
     * @return This object encoded as OEntity
     */
    public OEntity asEntity(String namespace) {
        final List<OProperty<?>> properties = new ArrayList<OProperty<?>>();
        properties.add(OProperties.string("language", getLanguage()));
        properties.add(OProperties.string("value", getValue()));
        return OEntities.create(getEntitySet(namespace), OEntityKey.create(getLanguage()), properties, new ArrayList<OLink>(), getValue(), null);
    }

    /**
     * This method works the same as {@link org.informea.odata.pojo.LocalizableString#asEntity(java.lang.String)}, but for a list
     * of entities.
     * @param namespace Namespace where this entity will be placed
     * @param strings List of objects to encode
     * @return List of encoded objects
     */
    public static List<OEntity> asEntities(final String namespace, List<LocalizableString> strings) {
        if (strings != null) {
            return Enumerable.create(strings).select(new Func1<LocalizableString, OEntity>() {

                @Override
                public OEntity apply(LocalizableString s) {
                    return s.asEntity(namespace);
                }
            }).toList();
        }
        return new ArrayList<OEntity>();
    }

    public boolean validate() {
        boolean ret = true;
        if (null == getLanguage()) {
            log.warning("validate(): language property cannot be null");
            ret = false;
        }
        if (null == getValue()) {
            log.warning("validate(): value property cannot be null");
            ret = false;
        }
        return ret;
    }
}
