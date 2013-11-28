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
package org.informea.odata.data;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.informea.odata.HibernateConfigurator;
import org.joda.time.LocalDateTime;
import org.odata4j.expression.DateTimeLiteral;
import org.odata4j.expression.EntitySimpleProperty;
import org.odata4j.expression.GtExpression;
import org.odata4j.producer.QueryInfo;

/**
 * Helper method to extract entities from database
 * <br />
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 1.3.3
 */
public class DatabaseDataProvider implements IDataProvider {

    private static final Logger log = Logger.getLogger(DatabaseDataProvider.class.getName());

    protected Session session = null;

    public DatabaseDataProvider() {}

    @Override
    public void openResources() {
        session = HibernateConfigurator.getInstance().openSession();
    }

    @Override
    public void closeResources() {
        if(this.session != null) {
            log.info("Closing Hibernate database session");
            session.close();
        }
    }

    /**
     * Return one of the primary entities.
     * @param entityClass Hibernate annotated class corresponding to the entity
     * @param id Object ID
     * @return Entity object
     */
    @SuppressWarnings("rawtypes")
	@Override
    public Object getPrimaryEntity(Class entityClass, String id) {
        return session.createCriteria(entityClass).add(Restrictions.idEq(id)).setCacheable(true).uniqueResult();
    }
    
    @SuppressWarnings("rawtypes")
	@Override
    public Object getEntity(Class entityClass, Object id) {
        return session.createCriteria(entityClass).add(Restrictions.idEq(id)).setCacheable(true).uniqueResult();
    }


    /**
     * Get a list of primary entities
     * @param entityClass Hibernate annotated class corresponding to the entity
     * @param query Filtering query (optional)
     * @param startResult Used for pagination, first result to be retrieved
     * @param pageSize Used for pagination to define the size of the page. If null, all results are retrieved until the end
     * @return List of entities
     */
    @SuppressWarnings("rawtypes")
	@Override
    public List getPrimaryEntities(Class entityClass, QueryInfo query, int startResult, Integer pageSize, Order orderBy) {
        Criteria q = session.createCriteria(entityClass).setCacheable(true);
        q.setFirstResult(startResult);
        if(pageSize != null) {
            q.setMaxResults(pageSize);
        }
        if(orderBy != null) {
            q = q.addOrder(orderBy);
        }
        return q.list();
    }


    /**
     * Count the primary entities by issuing "SELECT COUNT(*) ... "
     * @param entityClass Class to count
     * @param q Filtering query, optional
     * @return Count of rows
     */
    @SuppressWarnings("rawtypes")
	@Override
    public Integer countPrimaryEntities(Class entityClass, QueryInfo q) {
        Criteria query = session.createCriteria(entityClass);
        query = query.setProjection(Projections.rowCount());
        return ((Long)query.setCacheable(true).uniqueResult()).intValue();
    }


    /**
     * Transform query from OData query into SQL Hibernate query
     * @param updatedColumn Column to filter by
     * @param q OData query
     * @return Hibernate Criteria object, null if no filter have to be set
     */
    protected Criterion filterLatest(String updatedColumn, QueryInfo q) {
        if(q != null && q.filter instanceof GtExpression) {
            GtExpression filter = (GtExpression)q.filter;
            log.info(filter.toString());
            if(filter.getLHS() instanceof EntitySimpleProperty) {
                EntitySimpleProperty l = (EntitySimpleProperty)filter.getLHS();
                if("published".equals(l.getPropertyName())) {
                    if(filter.getRHS() instanceof DateTimeLiteral) {
                        LocalDateTime dt = ((DateTimeLiteral)filter.getRHS()).getValue();
                        Date latest = dt.toDateTime().toDate();
                        log.info(String.format("Retrieving latest decisions after %s", latest.toString()));
                        return Restrictions.ge("updated", latest);
                    }
                }
            }
        }
        return null;
    }
}
