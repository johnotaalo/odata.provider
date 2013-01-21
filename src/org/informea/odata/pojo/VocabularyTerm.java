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
 * Describes the vocabulary term entity. An vocabulary term contains the term and an namespace used to uniquely identify this term in InforMEA portal as a source.
 * This namespace may be an URL, ex: http://ozone.unep.org.
 * <br />
 * TODO: Will we accept terms outside Global Vocabulary?
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 0.5
 */
public class VocabularyTerm {

    private static final Logger log = Logger.getLogger(VocabularyTerm.class.getName());
    /**
     * If terms are from Global Vocabulary set namespace to this value
     */
    public static final String UNEP_NAMESPACE = "http://www.unep.org";
    private String term;
    private String namespace;

    public VocabularyTerm(String term, String namespace) {
        this.term = term;
        this.namespace = namespace;
    }

    /**
     * @return Term in English
     */
    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    /**
     * Namespaces are used in InforMEA context to differentiate between Global Vocabulary and MEA node local Vocabulary.
     * Normally keywords used to tag the decisions come from the Global Vocabulary, but in case that these cannot be
     * matched we can keep these in another namespace and figure out what to do with them. So namespaces acts more like
     * source of the keyword.
     * @return Namespace in which term is located, example: http://ozone.unep.org etc.
     */
    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    // OData specific methods
    /**
     * This is used by OData4J framework to build the metadata document for the service
     * @return The list with primitive properties of this object.
     */
    public static List<EdmProperty> getPrimitiveProperties() {
        List<EdmProperty> properties = new ArrayList<EdmProperty>();
        properties.add(new EdmProperty("term", EdmType.STRING, false));
        properties.add(new EdmProperty("namespace", EdmType.STRING, false));
        return properties;
    }

    /**
     * This is used by OData4J framework to build the metadata document for the service
     * @param namespace Namespace to assign to entity when registering
     * @return The entire object as entity within the metadata
     */
    public static EdmEntityType getEntityType(String namespace) {
        return new EdmEntityType(namespace, null, getEntityName(), null, Enumerable.create(getEntityName()).toList(), getPrimitiveProperties(), null);
    }

    /**
     * Get the entity set that describes this object. Used to build service metadata and response with payload.
     * @param namespace Namespace to put this entity into
     * @return Entity description
     */
    public static EdmEntitySet getEntitySet(String namespace) {
        return new EdmEntitySet("keywords", getEntityType(namespace));
    }

    /**
     * Return the Entity name as will be encoded in OData entity service metadata
     * @return String with entity name
     */
    public static String getEntityName() {
        return "VocabularyTerm";
    }

    /**
     * Transforms each object's field into OProperty and object into OEntity.
     * Used by OData4J framework to serialize output.
     * @param namespace Namespace where this entity will be placed
     * @return This object encoded as OEntity
     */
    public OEntity asEntity(String namespace) {
        final List<OProperty<?>> properties = new ArrayList<OProperty<?>>();
        properties.add(OProperties.string("term", getTerm()));
        properties.add(OProperties.string("namespace", getNamespace()));
        return OEntities.create(VocabularyTerm.getEntitySet(namespace), OEntityKey.create(getTerm()), properties, new ArrayList<OLink>(), getTerm(), null);
    }

    /**
     * This method works the same as {@link org.informea.odata.pojo.VocabularyTerm#asEntity(java.lang.String)}, but for a list
     * of entities.
     * @param namespace Namespace where this entity will be placed
     * @param terms List of objects to encode
     * @return List of encoded objects
     */
    public static List<OEntity> asEntities(final String namespace, List<VocabularyTerm> terms) {
        return Enumerable.create(terms).select(new Func1<VocabularyTerm, OEntity>() {

            @Override
            public OEntity apply(VocabularyTerm term) {
                return term.asEntity(namespace);
            }
        }).toList();
    }

    public boolean validate() {
        boolean ret = true;

        if (null == getTerm()) {
            log.warning("validate(): term property cannot be null");
            ret = false;
        }
        if (null == getNamespace()) {
            log.warning("validate(): namespace property cannot be null");
            ret = false;
        } else {
            if (!getNamespace().toLowerCase().startsWith("http://")) {
                log.warning("validate(): namespace property does not start with 'http://'");
                ret = false;
            }
        }
        return ret;
    }
}
