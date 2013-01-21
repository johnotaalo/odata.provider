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
package org.informea.odata.util;

import java.util.ArrayList;
import java.util.List;

import org.odata4j.core.OEntity;
import org.odata4j.edm.EdmEntitySet;
import org.odata4j.producer.EntitiesResponse;
import org.odata4j.producer.EntityResponse;

/**
 * OData utilities.
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 0.5
 */
public class ODataTransformationUtil {

    /**
     * Utility method to create EntitiesResponse from a list of OEntity objects
     * @param entities Entities to encode into response
     * @param resultSetSize The total number of results (for pagination)
     * @param ees Entity description
     * @param skipToken OData skiptoken to pass back to XML (used for pagination)
     * @return EntitiesResponse required to generate the OData response
     */
    public static EntitiesResponse createEntitiesResponse(final List<OEntity> entities, final Integer resultSetSize, final EdmEntitySet ees, final String skipToken) {
        return new EntitiesResponse() {

            @Override
            public String getSkipToken() {
                return skipToken;
            }

            @Override
            public Integer getInlineCount() {
                return resultSetSize;
            }

            @Override
            public EdmEntitySet getEntitySet() {
                return ees;
            }

            @Override
            public List<OEntity> getEntities() {
                return entities;
            }
        };
    }

    /**
     * Utility method to create EntityResponse from OEntity
     * @param entity Entity to encode in response
     * @param ees Entity description
     * @return EntitiesResponse required to generate the OData response
     */
    public static EntityResponse createEntityResponse(final OEntity entity, final EdmEntitySet ees) {
        return new EntityResponse() {

            @Override
            public OEntity getEntity() {
                return entity;
            }
        };
    }

    /**
     * Shortcut to create an empty entity response (when no results are available).
     * @param ees Description of the metadata
     * @return A vali, empty entity response
     */
    public static EntitiesResponse emptyEntitiesResponse(final EdmEntitySet ees) {
        return new EntitiesResponse() {

            @Override
            public EdmEntitySet getEntitySet() {
                return ees;
            }

            @Override
            public List<OEntity> getEntities() {
                return new ArrayList<OEntity>();
            }

            @Override
            public Integer getInlineCount() {
                return null;
            }

            /**
             * Not used?
             * @return the skip token for this entity set
             */
            @Override
            public String getSkipToken() {
                return null;
            }
        };
    }
}
