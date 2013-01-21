/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.informea.odata.producer.toolkit;

import java.util.List;
import org.hibernate.criterion.Order;
import org.odata4j.producer.QueryInfo;

/**
 * Abstract the data provider so we can enable one transaction/request. This way
 * we can safely use hibernate sessions without being closed in the middle of a
 * request. The abstract producer handles resource creation and destruction
 * before/after calling producer methods.
 * <br />
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 1.3.6
 */
public interface IDataProvider {

    /**
     * Open resources here for use (i.e. hibernate database sessions)
     */
    void openResources();

    /**
     * Release resources here
     */
    void closeResources();


    /**
     * Count the primary entities by issuing "SELECT COUNT(*) ... "
     * @param entityClass Class to count
     * @param q Filtering query, optional
     * @return Count of rows
     */
    Integer countPrimaryEntities(Class entityClass, QueryInfo q);

    /**
     * Get a list of primary entities
     * @param entityClass Hibernate annotated class corresponding to the entity
     * @param query Filtering query (optional)
     * @param startResult Used for pagination, first result to be retrieved
     * @param pageSize Used for pagination to define the size of the page. If null, all results are retrieved until the end
     * @return List of entities
     */
    List getPrimaryEntities(Class entityClass, QueryInfo query, int startResult, Integer pageSize, Order orderBy);

    /**
     * Return one of the primary entities.
     * @param entityClass Hibernate annotated class corresponding to the entity
     * @param id Object ID
     * @return Entity object
     */
    Object getPrimaryEntity(Class entityClass, String id);
    Object getEntity(Class entityClass, Object id);
}
