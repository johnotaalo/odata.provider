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
import org.informea.odata.constants.MimeType;
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
 * Describe the attributes for a decision document.
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 0.5
 */
public class DecisionDocument {

    private static final Logger log = Logger.getLogger(DecisionDocument.class.getName());
    private String id;
    private String url;
    private byte[] content;
    private long size;
    private MimeType mimeType;
    private String language = "en";
    private String filename;

    public DecisionDocument(String id, String url, byte[] content, long size, MimeType mimeType, String language, String filename) {
        this.id = id;
        this.url = url;
        this.content = content;
        this.size = size;
        this.mimeType = mimeType;
        this.language = language;
        this.filename = filename;
    }

    public DecisionDocument(String id, String url, long size, MimeType mimeType, String language) {
        this.id = id;
        this.url = url;
        this.size = size;
        this.mimeType = mimeType;
        this.language = language;
    }

    public DecisionDocument(String id, byte[] content, MimeType mimeType, String language) {
        this.id = id;
        this.content = content;
        this.mimeType = mimeType;
        this.language = language;
        // In content was specified, the size will default to the length of the content
        if (content != null) {
            this.size = content.length;
        }
    }

    /**
     * @return Unique ID of the document
     * @since 1.3
     */
    public String getId() {
        return id;
    }

    /**
     * @since 1.3
     * @param id Document unique id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return URL for the decision where it may be browsed online
     */
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return Binary content (file from disk) of the decision
     */
    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    /**
     * @return Size of the document on disk
     */
    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    /**
     * @return Document type
     */
    public MimeType getMimeType() {
        return mimeType;
    }

    public void setMimeType(MimeType mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * @return Document language. Use the ISO 2-letter code to specify the language. Example: en, fr etc.
     */
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }


    /* OData specific declarations */
    /**
     * This is used by OData4J framework to build the metadata document for the service
     * @return The list with primitive properties of this object.
     */
    public static List<EdmProperty> getPrimitiveProperties() {
        List<EdmProperty> properties = new ArrayList<EdmProperty>();
        properties.add(new EdmProperty("id", EdmType.STRING, false));
        properties.add(new EdmProperty("url", EdmType.STRING, true));
        properties.add(new EdmProperty("language", EdmType.STRING, false));
        properties.add(new EdmProperty("content", EdmType.BINARY, false));
        properties.add(new EdmProperty("size", EdmType.INT64, true));
        properties.add(new EdmProperty("mimeType", EdmType.STRING, false));
        properties.add(new EdmProperty("filename", EdmType.STRING, true));
        return properties;
    }

    /**
     * @param namespace Namespace where this entity will be inserted in metadata
     * @return Entity type used to link to other entities
     */
    public static EdmEntityType getEntityType(String namespace) {
        return new EdmEntityType(namespace, null, getEntityName(), null, Enumerable.create("id").toList(), getPrimitiveProperties(), null);
    }

    /**
     * @param namespace Namespace where this entity will be inserted in metadata
     * @return Entity set used to compose the metadata document
     */
    public static EdmEntitySet getEntitySet(String namespace) {
        return new EdmEntitySet(getEntityName(), getEntityType(namespace));
    }

    /**
     * @return Entity name as will be declared in metadata
     */
    public static String getEntityName() {
        return "DecisionDocument";
    }

    /**
     * Transforms each object's field into OProperty and object into OEntity.
     * Used by OData4J framework to serialize output.
     * @param namespace Namespace where this entity will be placed
     * @return This object encoded as OEntity
     */
    public OEntity asEntity(String namespace) {
        final List<OProperty<?>> properties = new ArrayList<OProperty<?>>();

        properties.add(OProperties.string("id", getId()));
        properties.add(OProperties.string("url", getUrl()));
        properties.add(OProperties.binary("content", getContent()));
        properties.add(OProperties.int64("size", getSize()));
        properties.add(OProperties.string("mimeType", getMimeType().toString()));
        properties.add(OProperties.string("language", getLanguage()));
        properties.add(OProperties.string("filename", getFilename()));
        return OEntities.create(getEntitySet(namespace), OEntityKey.create(getUrl()), properties, new ArrayList<OLink>(), getId(), null);
    }

    /**
     * This method works the same as {@link org.informea.odata.pojo.DecisionDocument#asEntity(java.lang.String)}, but for a list
     * of entities.
     * @param namespace Namespace where this entity will be placed
     * @param dds List of objects to encode
     * @return List of encoded objects
     */
    public static List<OEntity> asEntities(final String namespace, List<DecisionDocument> dds) {
        return Enumerable.create(dds).select(new Func1<DecisionDocument, OEntity>() {

            @Override
            public OEntity apply(DecisionDocument doc) {
                return doc.asEntity(namespace);
            }
        }).toList();
    }

    public boolean validate() {
        boolean ret = true;

        if (null == getContent()) {
            log.warning("validate(): content property cannot be null");
            ret = false;
        }
        if (null == getMimeType()) {
            log.warning("validate(): mimeType property cannot be null");
            ret = false;
        }
        if (null == getLanguage()) {
            log.warning("validate(): language property cannot be null");
            ret = false;
        }
        return ret;
    }
}
