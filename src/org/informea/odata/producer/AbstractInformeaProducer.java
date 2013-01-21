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
package org.informea.odata.producer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.core4j.Enumerable;
import org.informea.odata.constants.Treaty;
import org.informea.odata.pojo.AbstractContact;
import org.informea.odata.pojo.AbstractCountryProfile;
import org.informea.odata.pojo.AbstractCountryReport;
import org.informea.odata.pojo.AbstractDecision;
import org.informea.odata.pojo.AbstractMeeting;
import org.informea.odata.pojo.AbstractNationalPlan;
import org.informea.odata.pojo.AbstractPebldsBestPractice;
import org.informea.odata.pojo.AbstractPebldsProject;
import org.informea.odata.pojo.AbstractPebldsTechnicalReport;
import org.informea.odata.pojo.AbstractPebldsTopic;
import org.informea.odata.pojo.AbstractPebldsWpfbFile;
import org.informea.odata.pojo.AbstractSite;
import org.informea.odata.pojo.DecisionDocument;
import org.informea.odata.pojo.IAbstractEntity;
import org.informea.odata.pojo.LocalizableString;
import org.informea.odata.pojo.VocabularyTerm;
import org.informea.odata.producer.toolkit.Configuration;
import org.informea.odata.producer.toolkit.IDataProvider;
import org.informea.odata.producer.toolkit.impl.DatabaseDataProvider;
import org.informea.odata.producer.toolkit.impl.PebldsBestPractice;
import org.informea.odata.producer.toolkit.impl.PebldsCountry;
import org.informea.odata.producer.toolkit.impl.PebldsProject;
import org.informea.odata.producer.toolkit.impl.PebldsTechnicalReport;
import org.informea.odata.producer.toolkit.impl.PebldsTopic;
import org.informea.odata.producer.toolkit.impl.PebldsTreaty;
import org.informea.odata.producer.toolkit.impl.PebldsWpfbFiles;
import org.informea.odata.util.ODataTransformationUtil;
import org.odata4j.core.ODataConstants;
import org.odata4j.core.OEntity;
import org.odata4j.edm.EdmAssociation;
import org.odata4j.edm.EdmDataServices;
import org.odata4j.edm.EdmEntityContainer;
import org.odata4j.edm.EdmEntitySet;
import org.odata4j.edm.EdmEntityType;
import org.odata4j.edm.EdmSchema;
import org.odata4j.producer.BaseResponse;
import org.odata4j.producer.EntitiesResponse;
import org.odata4j.producer.EntityResponse;
import org.odata4j.producer.ODataProducer;
import org.odata4j.producer.QueryInfo;

/**
 * This is the base class for all implementors that want to expose their data over OData protocol to be harvestable by InforMEA portal.
 * Within the package, there is a sample producer implemented for Toolkit.
 * <br />
 * The {@code buildMetadata()} method will be used in the service to construct the metadata for the service, while queries are handled by
 * {@code getEntities}, {@code getEntity} and {@code getNavProperty} methods.
 * <br />
 * In order to successfully create your producer you must implement a factory class for your producer, see {@link org.informea.odata.producer.toolkit.ProducerFactory} and configure the factory
 * from web.xml as in the following example:
 * <pre>
 * {@code
 *  <servlet>
 *    <servlet-name>informea-toolkit</servlet-name>
 *	  <servlet-class>com.sun.jersey.spi.container.servlet.ServletContainer</servlet-class>
 *	  <init-param>
 *	    <param-name>com.sun.jersey.config.property.resourceConfigClass</param-name>
 *      <param-value>org.odata4j.producer.resources.ODataResourceConfig</param-value>
 *    </init-param>
 *    <init-param>
 *      <param-name>odata4j.producerfactory</param-name>
 *      <param-value>org.odata4j.producer.jpa.JPAProducerFactory</param-value>
 *    </init-param>
 *    <init-param>
 *      <param-name>odata4j.producerfactory</param-name>
 *      <param-value>org.informea.odata.producer.toolkit.ProducerFactory</param-value>
 *    </init-param>
 *    <load-on-startup>1</load-on-startup>
 *  </servlet>
 *  <servlet-mapping>
 *    <servlet-name>informea-service</servlet-name>
 *    <url-pattern>/services/service.svc/*</url-pattern>
 *  </servlet-mapping>}
 * </pre>
 * @see org.informea.odata.producer.toolkit.Producer
 *
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 0.5
 */
public abstract class AbstractInformeaProducer implements ODataProducer {

    private static final Logger log = Logger.getLogger(AbstractInformeaProducer.class.getName());
    /**
     * @see org.informea.odata.producer.AbstractInformeaProducer#getSkipToken()
     */
    protected String skipToken;
    private EdmDataServices metadata;
    /**
     * Current namespace.
     * @see org.informea.odata.producer.AbstractInformeaProducer#getNamespace()
     */
    protected static String namespace = "";

    /**
     * Construct new producer for InforMEA portal
     * @param namespace Namespace for this service endpoint. You may invent something for namespace as it's not relevant,
     * but must not contain dots or spaces. Valid values for example: "ozone", "cbd" etc.
     * <em>//TODO IS IT CORRECT TO PASS ANOTHER NAMESPACE BESIDE 'informea'?</em>
     */
    public AbstractInformeaProducer(String namespace) {
        AbstractInformeaProducer.namespace = namespace;
    }

    /**
     * Retrieve a single decision from local storage based on its unique ID
     * @param dataProvider Data source used to extract information
     * @param id Decision id
     * @return Decision object
     */
    public abstract AbstractDecision getDecision(IDataProvider dataProvider, Object id);

    /**
     * Retrieve the list of decisions from local storage.
     * @param dataProvider Data source used to extract information
     * @param q Clients may choose to filter the data. Filtering information is contained here. Filtering is optional for InforMEA.
     * @param startResult Where to begint results from in context of pagination
     * @param pageSize How many results per page
     * @return List of decisions. If pagination is implemented (not all entities at once, use <code>skipToken</code> to provide
     * information for next page. skipToken is sent back to the client to you in <code>q.skipToken</code>.
     */
    public abstract List<AbstractDecision> getDecisions(IDataProvider dataProvider, QueryInfo q, int startResult, Integer pageSize);

    /**
     * The total count of decisions taking into account the filtering, if provided.
     * @param dataProvider Data source used to extract information
     * @param q Clients may choose to filter the data. Filtering information is contained here. Filtering is optional for InforMEA.
     * @return Total count of items, used by the client to compute pagination.
     */
    public abstract Integer getDecisionsCount(IDataProvider dataProvider, QueryInfo q);

    /**
     * Retrieve the documents available for a decision. Used for OData navigable property 'documents'.
     * @param dataProvider Data source used to extract information
     * @param id Decision ID
     * @param q Additional query to filter the documents. For future extensibility.
     * @return List of documents
     */
    public abstract List<DecisionDocument> getDecisionDocuments(IDataProvider dataProvider, Object id, QueryInfo q);

    /**
     * Retrieve the title of a decision. Used for OData navigable property 'title'. Multilingual.
     * @param dataProvider Data source used to extract information
     * @param id Decision ID
     * @param q Additional query to filter the titles. For future extensibility.
     * @return List of titles in various languages
     */
    public abstract List<LocalizableString> getDecisionTitle(IDataProvider dataProvider, Object id, QueryInfo q);

    /**
     * Retrieve the long title of a decision. Used for OData navigable property 'longTitle'. Multilingual.
     * @param dataProvider Data source used to extract information
     * @param id Decision ID
     * @param q Additional query to filter the titles. For future extensibility.
     * @return List of long titles in various languages
     */
    public abstract List<LocalizableString> getDecisionLongTitle(IDataProvider dataProvider, Object id, QueryInfo q);

    /**
     * Retrieve the summary of a decision. Used for OData navigable property 'summary'. Multilingual.
     * @param dataProvider Data source used to extract information
     * @param id Decision ID
     * @param q Additional query to filter the titles. For future extensibility.
     * @return List of summaries in various languages
     */
    public abstract List<LocalizableString> getDecisionSummary(IDataProvider dataProvider, Object id, QueryInfo q);

    /**
     * Retrieve the content of a decision. Used for OData navigable property 'content'. Multilingual.
     * @param dataProvider Data source used to extract information
     * @param id Decision ID
     * @param q Additional query to filter the titles. For future extensibility.
     * @return List of contents in various languages
     */
    public abstract List<LocalizableString> getDecisionContent(IDataProvider dataProvider, Object id, QueryInfo q);

    /**
     * Retrieve the keywords used to tag a decision. Used for OData navigable property 'keywords'. NOT MULTILINGUAL.
     * @param dataProvider Data source used to extract information
     * @param id Decision ID
     * @param q Additional query to filter the keywords. For future extensibility.
     * @return List of keywords in english only
     */
    public abstract List<VocabularyTerm> getDecisionKeywords(IDataProvider dataProvider, Object id, QueryInfo q);

    /**
     * Retrieve a single contact from local storage based on its unique ID
     * @param dataProvider Data source used to extract information
     * @param id Contact id
     * @return Contact object
     */
    public abstract AbstractContact getContact(IDataProvider dataProvider, Object id);

    /**
     * Retrieve the list of contacts from local storage.
     * @param dataProvider Data source used to extract information
     * @param q Clients may choose to filter the data. Filtering information is contained here. Filtering is optional for InforMEA.
     * @param startResult Where to begint results from in context of pagination
     * @param pageSize How many results per page
     * @return List of contacts. If pagination is implemented (not all entities at once, use <code>skipToken</code> to provide
     * information for next page. skipToken is sent back to the client to you in <code>q.skipToken</code>.
     */
    public abstract List<AbstractContact> getContacts(IDataProvider dataProvider, QueryInfo q, int startResult, Integer pageSize);

    /**
     * The total count of contacts taking into account the filtering, if provided.
     * @param dataProvider Data source used to extract information
     * @param q Clients may choose to filter the data. Filtering information is contained here. Filtering is optional for InforMEA.
     * @return Total count of items, used by the client to compute pagination.
     */
    public abstract Integer getContactsCount(IDataProvider dataProvider, QueryInfo q);

    /**
     * Retrieve the treaties a person is assigned to. Used for OData navigable property 'treaties'.
     * @param dataProvider Data source used to extract information
     * @param id Decision ID
     * @param q Additional query to filter the keywords. For future extensibility.
     * @return List of keywords
     */
    public abstract List<Treaty> getContactTreaties(IDataProvider dataProvider, Object id, QueryInfo q);

    /**
     * Retrieve a single country profile from local storage based on its unique ID
     * @param dataProvider Data source used to extract information
     * @param id CountryProfile id
     * @return CountryProfile object
     */
    public abstract AbstractCountryProfile getCountryProfile(IDataProvider dataProvider, Object id);

    /**
     * Retrieve the list of country profiles from local storage.
     * @param dataProvider Data source used to extract information
     * @param q Clients may choose to filter the data. Filtering information is contained here. Filtering is optional for InforMEA.
     * @param startResult Where to begint results from in context of pagination
     * @param pageSize How many results per page
     * @return List of country profiles or null if not implemented
     */
    public abstract List<AbstractCountryProfile> getCountryProfiles(IDataProvider dataProvider, QueryInfo q, int startResult, Integer pageSize);

    /**
     * The total count of country profiles taking into account the filtering, if provided.
     * @param dataProvider Data source used to extract information
     * @param q Clients may choose to filter the data. Filtering information is contained here. Filtering is optional for InforMEA.
     * @return Total count of items, used by the client to compute pagination.
     */
    public abstract Integer getCountryProfilesCount(IDataProvider dataProvider, QueryInfo q);

    /**
     * Retrieve a single country report from local storage based on its unique ID
     * @param dataProvider Data source used to extract information
     * @param id CountryReport id
     * @return CountryReport object
     */
    public abstract AbstractCountryReport getCountryReport(IDataProvider dataProvider, Object id);

    /**
     * Retrieve the list of country reports from local storage.
     * @param dataProvider Data source used to extract information
     * @param q Clients may choose to filter the data. Filtering information is contained here. Filtering is optional for InforMEA.
     * @param startResult Where to begint results from in context of pagination
     * @param pageSize How many results per page
     * @return List of country reports. If pagination is implemented (not all entities at once, use <code>skipToken</code> to provide
     * information for next page. skipToken is sent back to the client to you in <code>q.skipToken</code>.
     */
    public abstract List<AbstractCountryReport> getCountryReports(IDataProvider dataProvider, QueryInfo q, int startResult, Integer pageSize);

    /**
     * The total count of country reports taking into account the filtering, if provided.
     * @param dataProvider Data source used to extract information
     * @param q Clients may choose to filter the data. Filtering information is contained here. Filtering is optional for InforMEA.
     * @return Total count of items, used by the client to compute pagination.
     */
    public abstract Integer getCountryReportsCount(IDataProvider dataProvider, QueryInfo q);

    /**
     * Retrieve the title of a country report. Used for OData navigable property 'title'. Multilingual.
     * @param dataProvider Data source used to extract information
     * @param id Country report ID
     * @param q Additional query to filter the titles. For future extensibility.
     * @return List of titles in various languages
     */
    public abstract List<LocalizableString> getCountryReportTitle(IDataProvider dataProvider, Object id, QueryInfo q);

    /**
     * Retrieve a single meeting from local storage based on its unique ID
     * @param dataProvider Data source used to extract information
     * @param id Meeting id
     * @return Meeting object
     */
    public abstract AbstractMeeting getMeeting(IDataProvider dataProvider, Object id);

    /**
     * Retrieve the list of meetings from local storage.
     * @param dataProvider Data source used to extract information
     * @param q Clients may choose to filter the data. Filtering information is contained here. Filtering is optional for InforMEA.
     * @param startResult Where to begint results from in context of pagination
     * @param pageSize How many results per page
     * @return List of meetings. If pagination is implemented (not all entities at once, use <code>skipToken</code> to provide
     * information for next page. skipToken is sent back to the client to you in <code>q.skipToken</code>.
     */
    public abstract List<AbstractMeeting> getMeetings(IDataProvider dataProvider, QueryInfo q, int startResult, Integer pageSize);

    /**
     * The total count of meetings taking into account the filtering, if provided.
     * @param dataProvider Data source used to extract information
     * @param q Clients may choose to filter the data. Filtering information is contained here. Filtering is optional for InforMEA.
     * @return Total count of items, used by the client to compute pagination.
     */
    public abstract Integer getMeetingsCount(IDataProvider dataProvider, QueryInfo q);

    /**
     * Retrieve the title of a meeting. Used for OData navigable property 'title'. Multilingual.
     * @param dataProvider Data source used to extract information
     * @param id Meeting ID
     * @param q Additional query to filter the titles. For future extensibility.
     * @return List of titles in various languages
     */
    public abstract List<LocalizableString> getMeetingTitle(IDataProvider dataProvider, Object id, QueryInfo q);

    /**
     * Retrieve the description of a decision. Used for OData navigable property 'description'. Multilingual.
     * @param dataProvider Data source used to extract information
     * @param id Meeting ID
     * @param q Additional query to filter the description. For future extensibility.
     * @return List of descriptions in various languages
     */
    public abstract List<LocalizableString> getMeetingDescription(IDataProvider dataProvider, Object id, QueryInfo q);

    /**
     * Retrieve a single national plan from local storage based on its unique ID
     * @param dataProvider Data source used to extract information
     * @param id NationalPlan id
     * @return NationalPlan object
     */
    public abstract AbstractNationalPlan getNationalPlan(IDataProvider dataProvider, Object id);

    /**
     * Retrieve the list of national plans from local storage.
     * @param dataProvider Data source used to extract information
     * @param q Clients may choose to filter the data. Filtering information is contained here. Filtering is optional for InforMEA.
     * @param startResult Where to begint results from in context of pagination
     * @param pageSize How many results per page
     * @return List of national plans. If pagination is implemented (not all entities at once, use <code>skipToken</code> to provide
     * information for next page. skipToken is sent back to the client to you in <code>q.skipToken</code>.
     */
    public abstract List<AbstractNationalPlan> getNationalPlans(IDataProvider dataProvider, QueryInfo q, int startResult, Integer pageSize);

    /**
     * The total count of national plans taking into account the filtering, if provided.
     * @param dataProvider Data source used to extract information
     * @param q Clients may choose to filter the data. Filtering information is contained here. Filtering is optional for InforMEA.
     * @return Total count of items, used by the client to compute pagination.
     */
    public abstract Integer getNationalPlansCount(IDataProvider dataProvider, QueryInfo q);

    /**
     * Retrieve the title of a national plan. Used for OData navigable property 'title'. Multilingual.
     * @param dataProvider Data source used to extract information
     * @param id National plan ID
     * @param q Additional query to filter the titles. For future extensibility.
     * @return List of titles in various languages
     */
    public abstract List<LocalizableString> getNationalPlanTitle(IDataProvider dataProvider, Object id, QueryInfo q);

    /**
     * Retrieve a single site from local storage based on its unique ID
     * @param dataProvider Data source used to extract information
     * @param id Site id
     * @return Site object
     */
    public abstract AbstractSite getSite(IDataProvider dataProvider, Object id);

    /**
     * Retrieve the list of sites from local storage.
     * @param dataProvider Data source used to extract information
     * @param q Clients may choose to filter the data. Filtering information is contained here. Filtering is optional for InforMEA.
     * @param startResult Where to begint results from in context of pagination
     * @param pageSize How many results per page
     * @return List of sites. If pagination is implemented (not all entities at once, use <code>skipToken</code> to provide
     * information for next page. skipToken is sent back to the client to you in <code>q.skipToken</code>.
     */
    public abstract List<AbstractSite> getSites(IDataProvider dataProvider, QueryInfo q, int startResult, Integer pageSize);

    /**
     * The total count of sites taking into account the filtering, if provided.
     * @param dataProvider Data source used to extract information
     * @param q Clients may choose to filter the data. Filtering information is contained here. Filtering is optional for InforMEA.
     * @return Total count of items, used by the client to compute pagination.
     */
    public abstract Integer getSitesCount(IDataProvider dataProvider, QueryInfo q);

    /**
     * Retrieve a single site from local storage based on its unique ID
     * @param dataProvider Data source used to extract information
     * @param id Site id
     * @return Site object
     */
    public abstract Object getEntity(IDataProvider dataProvider, Object id, Class entity);

    /**
     * Retrieve the list of entities from local storage.
     * @param dataProvider Data source used to extract information
     * @param q Clients may choose to filter the data. Filtering information is contained here. Filtering is optional for InforMEA.
     * @param startResult Where to begint results from in context of pagination
     * @param pageSize How many results per page
     * @return List of sites. If pagination is implemented (not all entities at once, use <code>skipToken</code> to provide
     * information for next page. skipToken is sent back to the client to you in <code>q.skipToken</code>.
     */
    public abstract List getEntityList(IDataProvider dataProvider, QueryInfo q, int startResult, Integer pageSize, Class entity);

    /**
     * The total count of sites taking into account the filtering, if provided.
     * @param dataProvider Data source used to extract information
     * @param qm Clients may choose to filter the data. Filtering information is contained here. Filtering is optional for InforMEA.
     * @param entity Entity to load data;
     * @return Total count of items, used by the client to compute pagination.
     */
    public abstract Integer getEntityCount(IDataProvider dataProvider, QueryInfo qm, Class entity);

    /**
     * Retrieve the title of a national plan. Used for OData navigable property 'title'. Multilingual.
     * @param dataProvider Data source used to extract information
     * @param id National plan ID
     * @param q Additional query to filter the titles. For future extensibility.
     * @return List of titles in various languages
     */
    public abstract List<LocalizableString> getSiteName(IDataProvider dataProvider, Object id, QueryInfo q);

    @Override
    public final EntitiesResponse getEntities(String entitySetName, QueryInfo q) {
        log.info(String.format("getEntities(entity=%s)", entitySetName, q));
        log.info(String.format("OData parameters: $skipToken=%s, $skip=%s, $top=%s", q.skipToken, q.skip, q.top));

        IDataProvider dataProvider = new DatabaseDataProvider();
        dataProvider.openResources();

        EdmEntitySet ees = null;
        try {
            ees = getMetadata().getEdmEntitySet(entitySetName);
        } catch(Exception ex) {
            throw new InvalidValueException(String.format("Cannot find entity or function named '%s'", entitySetName), ex);
        }
        if ("done".equals(q.skipToken)) {
            return ODataTransformationUtil.emptyEntitiesResponse(ees);
        }

        int startResult = 0;
        Integer pageSize = 10;
        // client-side pagination
        if (q.skip != null && q.skip > 0) {
            startResult = q.skip;
        } else if (q.skipToken != null) {
            // Server-side pagination
            try {
                startResult = Integer.parseInt(q.skipToken);
            } catch (Exception ex) { /* swallow */ }
        }

        // client-side pagination
        if (q.top != null) {
            pageSize = q.top;
        }
        if (pageSize > 100) {
            log.warning("Client requested too many entries within a single page of results. Adjusting to 100 to prevent server overload");
            pageSize = 100;
        }

        if (pageSize != null) {
            setSkipToken("" + (startResult + pageSize.intValue()));
        }

        List<OEntity> entities = null;
        int count = 0;
        // Get the decisions
        if (AbstractDecision.COLLECTION_NAME.equals(entitySetName)) {
            entities = AbstractDecision.asEntities(ees, getDecisions(dataProvider, q, startResult, pageSize));
            count = getDecisionsCount(dataProvider, q);
        }
        if (AbstractContact.COLLECTION_NAME.equals(entitySetName)) {
            entities = AbstractContact.asEntities(ees, getContacts(dataProvider, q, startResult, pageSize));
            count = getContactsCount(dataProvider, q);
        }
        if (AbstractCountryProfile.COLLECTION_NAME.equals(entitySetName)) {
            entities = AbstractCountryProfile.asEntities(ees, getCountryProfiles(dataProvider, q, startResult, pageSize));
            count = getCountryProfilesCount(dataProvider, q);
        }
        if (AbstractCountryReport.COLLECTION_NAME.equals(entitySetName)) {
            entities = AbstractCountryReport.asEntities(ees, getCountryReports(dataProvider, q, startResult, pageSize));
            count = getCountryReportsCount(dataProvider, q);
        }
        if (AbstractMeeting.COLLECTION_NAME.equals(entitySetName)) {
            entities = AbstractMeeting.asEntities(ees, getMeetings(dataProvider, q, startResult, pageSize));
            count = getMeetingsCount(dataProvider, q);
        }
        if (AbstractNationalPlan.COLLECTION_NAME.equals(entitySetName)) {
            entities = AbstractNationalPlan.asEntities(ees, getNationalPlans(dataProvider, q, startResult, pageSize));
            count = getNationalPlansCount(dataProvider, q);
        }
        if (AbstractSite.COLLECTION_NAME.equals(entitySetName)) {
            entities = AbstractSite.asEntities(ees, getSites(dataProvider, q, startResult, pageSize));
            count = getSitesCount(dataProvider, q);
        }

        // Peblds entities

        if (AbstractPebldsProject.COLLECTION_NAME.equals(entitySetName)){
            entities = AbstractPebldsProject.asEntities(ees, getEntityList(dataProvider, q, startResult, pageSize, PebldsProject.class));
        }
        if (AbstractPebldsBestPractice.COLLECTION_NAME.equals(entitySetName)){
            entities = AbstractPebldsBestPractice.asEntities(ees, getEntityList(dataProvider, q, startResult, pageSize, PebldsBestPractice.class));
        }
        if (AbstractPebldsTechnicalReport.COLLECTION_NAME.equals(entitySetName)){
            entities = AbstractPebldsTechnicalReport.asEntities(ees, getEntityList(dataProvider, q, startResult, pageSize, PebldsTechnicalReport.class));
        }
        if (AbstractPebldsTopic.COLLECTION_NAME.equals(entitySetName)){
            entities = AbstractPebldsTopic.asEntities(ees, getEntityList(dataProvider, q, startResult, pageSize, PebldsTopic.class));
        }
        if (AbstractPebldsWpfbFile.COLLECTION_NAME.equals(entitySetName)){
            entities = AbstractPebldsWpfbFile.asEntities(ees, getEntityList(dataProvider, q, startResult, pageSize, PebldsWpfbFiles.class));
        }

        dataProvider.closeResources();

        if (entities != null) {
            return ODataTransformationUtil.createEntitiesResponse(entities, count, ees, getSkipToken());
        }
        return ODataTransformationUtil.emptyEntitiesResponse(ees);
    }

    @Override
    public final EntityResponse getEntity(String entitySetName, Object entityKey) {
        log.info(String.format("getEntity(entity=%s, key=%s)", entitySetName, entityKey));
        EntityResponse ret = null;

        IDataProvider dataProvider = new DatabaseDataProvider();
        dataProvider.openResources();

        EdmEntitySet ees = getMetadata().getEdmEntitySet(entitySetName);
        IAbstractEntity object = null;

        if (AbstractDecision.COLLECTION_NAME.equals(entitySetName)) {
            object = getDecision(dataProvider, entityKey);
        }
        if (AbstractContact.COLLECTION_NAME.equals(entitySetName)) {
            object = getContact(dataProvider, entityKey);
        }
        if (AbstractCountryProfile.COLLECTION_NAME.equals(entitySetName)) {
            object = getCountryProfile(dataProvider, entityKey);
        }
        if (AbstractCountryReport.COLLECTION_NAME.equals(entitySetName)) {
            object = getCountryReport(dataProvider, entityKey);
        }
        if (AbstractMeeting.COLLECTION_NAME.equals(entitySetName)) {
            object = getMeeting(dataProvider, entityKey);
        }
        if (AbstractNationalPlan.COLLECTION_NAME.equals(entitySetName)) {
            object = getNationalPlan(dataProvider, entityKey);
        }
        if (AbstractSite.COLLECTION_NAME.equals(entitySetName)) {
            object = getSite(dataProvider, entityKey);
        }
        if (AbstractPebldsProject.COLLECTION_NAME.equals(entitySetName)){
            object = (IAbstractEntity)getEntity(dataProvider, entityKey, PebldsProject.class);
        }
        if (AbstractPebldsBestPractice.COLLECTION_NAME.equals(entitySetName)){
            object = (IAbstractEntity)getEntity(dataProvider, entityKey, PebldsBestPractice.class);
        }
        if (AbstractPebldsTechnicalReport.COLLECTION_NAME.equals(entitySetName)){
            object = (IAbstractEntity)getEntity(dataProvider, entityKey, PebldsTechnicalReport.class);
        }
        if (AbstractPebldsTopic.COLLECTION_NAME.equals(entitySetName)){
            object = (IAbstractEntity)getEntity(dataProvider, entityKey, PebldsTopic.class);
        }
        if (AbstractPebldsWpfbFile.COLLECTION_NAME.equals(entitySetName)){
            object = (IAbstractEntity)getEntity(dataProvider, entityKey, PebldsWpfbFiles.class);
        }
        if (object != null) {
            OEntity entity = object.asEntity(ees);
            ret = ODataTransformationUtil.createEntityResponse(entity, ees);
        }
        dataProvider.closeResources();
        if(ret == null) {
            throw new RuntimeException(String.format("No %s entity was found matching ID %s", entitySetName, entityKey));
        }
        return ret;
    }

    @Override
    public final BaseResponse getNavProperty(String entitySetName, Object entityKey, String navProp, QueryInfo q) {
        log.info(String.format("getNavProperty(entity=%s, key=%s, property=%s)", entitySetName, entityKey, navProp));

        IDataProvider dataProvider = new DatabaseDataProvider();
        dataProvider.openResources();

        EdmEntitySet ees = getMetadata().getEdmEntitySet(navProp);

        // Avoid infinite loop
        if ("done".equals(q.skipToken)) {
            return ODataTransformationUtil.emptyEntitiesResponse(ees);
        }
        List<OEntity> entities = null;
        if (AbstractDecision.COLLECTION_NAME.equals(entitySetName)) {
            if (AbstractDecision.NAV_PROP_DOCUMENTS.equals(navProp)) {
                entities = DecisionDocument.asEntities(namespace, getDecisionDocuments(dataProvider, entityKey, q));
            }
            if (AbstractDecision.NAV_PROP_TITLE.equals(navProp)) {
                entities = LocalizableString.asEntities(namespace, getDecisionTitle(dataProvider, entityKey, q));
            }
            if (AbstractDecision.NAV_PROP_LONG_TITLE.equals(navProp)) {
                entities = LocalizableString.asEntities(namespace, getDecisionLongTitle(dataProvider, entityKey, q));
            }
            if (AbstractDecision.NAV_PROP_SUMMARY.equals(navProp)) {
                entities = LocalizableString.asEntities(namespace, getDecisionSummary(dataProvider, entityKey, q));
            }
            if (AbstractDecision.NAV_PROP_CONTENT.equals(navProp)) {
                entities = LocalizableString.asEntities(namespace, getDecisionContent(dataProvider, entityKey, q));
            }
            if (AbstractDecision.NAV_PROP_KEYWORDS.equals(navProp)) {
                entities = VocabularyTerm.asEntities(namespace, getDecisionKeywords(dataProvider, entityKey, q));
            }
        }
        if (AbstractContact.COLLECTION_NAME.equals(entitySetName)) {
            if (AbstractContact.NAV_PROP_TREATIES.equals(navProp)) {
                entities = Treaty.asEntities(namespace, getContactTreaties(dataProvider, entityKey, q));
            }
        }
        if (AbstractCountryReport.COLLECTION_NAME.equals(entitySetName)) {
            if (AbstractCountryReport.NAV_PROP_TITLE.equals(navProp)) {
                entities = LocalizableString.asEntities(namespace, getCountryReportTitle(dataProvider, entityKey, q));
            }
        }
        if (AbstractMeeting.COLLECTION_NAME.equals(entitySetName)) {
            if (AbstractMeeting.NAV_PROP_TITLE.equals(navProp)) {
                entities = LocalizableString.asEntities(namespace, getMeetingTitle(dataProvider, entityKey, q));
            }
            if (AbstractMeeting.NAV_PROP_DESCRIPTION.equals(navProp)) {
                entities = LocalizableString.asEntities(namespace, getMeetingDescription(dataProvider, entityKey, q));
            }
        }
        if (AbstractNationalPlan.COLLECTION_NAME.equals(entitySetName)) {
            if (AbstractNationalPlan.NAV_PROP_TITLE.equals(navProp)) {
                entities = LocalizableString.asEntities(namespace, getNationalPlanTitle(dataProvider, entityKey, q));
            }
        }
        if (AbstractSite.COLLECTION_NAME.equals(entitySetName)) {
            if (AbstractSite.NAV_PROP_NAME.equals(navProp)) {
                entities = LocalizableString.asEntities(namespace, getSiteName(dataProvider, entityKey, q));
            }
        }

        if (AbstractPebldsProject.COLLECTION_NAME.equals(entitySetName)) {
            PebldsProject pb = (PebldsProject)getEntity(dataProvider, entityKey, PebldsProject.class);
            if (AbstractPebldsProject.NAV_PROP_TREATY.equals(navProp)){
                entities = PebldsTreaty.asEntities(ees, new ArrayList<PebldsTreaty>(pb.getPebldsTreaties()));
            }
            if (AbstractPebldsProject.NAV_PROP_COUNTRY.equals(navProp)){
                entities = PebldsCountry.asEntities(ees, new ArrayList<PebldsCountry>(pb.getPebldsCountries()));
            }

            if (AbstractPebldsProject.NAV_PROP_FILES.equals(navProp)){
                entities = PebldsWpfbFiles.asEntities(ees, new ArrayList<PebldsWpfbFiles>(pb.getPebldsWpfbFiles()));
            }
        }

        if (AbstractPebldsBestPractice.COLLECTION_NAME.equals(entitySetName)) {
            PebldsBestPractice pb = (PebldsBestPractice)getEntity(dataProvider, entityKey, PebldsBestPractice.class);

            if (AbstractPebldsBestPractice.NAV_PROP_FILES.equals(navProp)){
                entities = PebldsWpfbFiles.asEntities(ees, new ArrayList<PebldsWpfbFiles>(pb.getPebldsWpfbFiles()));
            }
        }

        if (AbstractPebldsTechnicalReport.COLLECTION_NAME.equals(entitySetName)) {
            PebldsTechnicalReport pb = (PebldsTechnicalReport)getEntity(dataProvider, entityKey, PebldsTechnicalReport.class);

            if (AbstractPebldsTechnicalReport.NAV_PROP_TOPICS.equals(navProp)){
                entities = PebldsTopic.asEntities(ees, new ArrayList<PebldsTopic>(pb.getPebldsTopics()));
            }

            if (AbstractPebldsTechnicalReport.NAV_PROP_FILES.equals(navProp)){
                entities = PebldsWpfbFiles.asEntities(ees, new ArrayList<PebldsWpfbFiles>(pb.getPebldsWpfbFiles()));
            }
        }

        dataProvider.closeResources();

        if (entities != null) {
            return ODataTransformationUtil.createEntitiesResponse(entities, entities.size(), ees, getSkipToken());
        }

        return ODataTransformationUtil.emptyEntitiesResponse(ees);
    }

    /**
     * @return OData namespace where this service endpoint runs (i.e. "ozone" or "cbd" etc.)
     */
    public static String getNamespace() {
        return namespace;
    }

    @Override
    public final EdmDataServices getMetadata() {
        if (this.metadata == null) {
            this.metadata = this.buildMetadata();
        }
        return metadata;
    }

    @Override
    public void close() {
        // noop
    }

    /**
     * You may override this method to provide "write" functionality for your service.
     * @param entitySetName Entity type
     * @param arg1 Entity ID
     */
    @Override
    public void deleteEntity(String entitySetName, Object arg1) {
        throw new RuntimeException("This is an read-only service. Deleting entities is not supported");
    }

    /**
     * You may override this method to provide "write" functionality for your service.
     * @param entitySetName Entity type
     * @param arg1 Entity properties
     * @return Operation status
     */
    @Override
    public EntityResponse createEntity(String entitySetName, OEntity arg1) {
        throw new RuntimeException("This is an read-only service. Creating entities is not supported");
    }

    /**
     * You may override this method to provide "write" functionality for your service.
     * @param entitySetName Entity type
     * @param arg1 Entity properties
     * @param arg2 Unknown
     * @param arg3 Unknown
     * @return Operation status
     */
    @Override
    public EntityResponse createEntity(String entitySetName, Object arg1, String arg2, OEntity arg3) {
        throw new RuntimeException("This is an read-only service. Creating entities is not supported");
    }

    /**
     * You may override this method to provide "write" functionality for your service.
     * @param entitySetName Entity type
     * @param arg1 Entity properties
     * @param arg2 Unknown
     */
    @Override
    public void mergeEntity(String entitySetName, Object arg1, OEntity arg2) {
        throw new RuntimeException("This is an read-only service. Merging entities is not supported");
    }

    /**
     * You may override this method to provide "write" functionality for your service.
     * @param entitySetName Entity type
     * @param arg1 Entity properties
     * @param arg2 Unknown
     */
    @Override
    public void updateEntity(String entitySetName, Object arg1, OEntity arg2) {
        throw new RuntimeException("This is an read-only service. Updating entities is not supported");
    }

    /**
     * This method should create the metadata exposed by the service at the endpoint (ex. http://localhost/service.svc$metadata)
     * @return Service metadata
     */
    protected EdmDataServices buildMetadata() {
        Configuration cfg = Configuration.getInstance();

        List<EdmEntitySet> entitySets = new ArrayList<EdmEntitySet>();
        List<EdmEntityType> entityTypes = new ArrayList<EdmEntityType>();
        List<EdmAssociation> associations = new ArrayList<EdmAssociation>();

        String propertyName = null;
        EdmEntityType eetLocString = null;
        EdmEntitySet eesLocString = null;

        // Common properties
        if (cfg.isUseDecisions() || cfg.isUseMeetings() || cfg.isUseCountryReports() || cfg.isUseNationalPlans()) {
            // Title entitySet is defined only for above primary entities
            propertyName = "title";
            eetLocString = LocalizableString.getEntityType(namespace);
            eesLocString = new EdmEntitySet(propertyName, eetLocString);
            entitySets.add(eesLocString);
        }


        if (cfg.isUseDecisions()) {
            // Define DecisionDocument entity
            EdmEntityType eetDocuments = DecisionDocument.getEntityType(namespace);
            entityTypes.add(eetDocuments);

            // Define VocabularyTerm entity
            EdmEntityType eetTerms = VocabularyTerm.getEntityType(namespace);
            entityTypes.add(eetTerms);

            eetLocString = LocalizableString.getEntityType(namespace);
            entityTypes.add(eetLocString);


            // Define Decision entity
            EdmSchema decisionSchema = AbstractDecision.getSchema(getNamespace(), eetDocuments, eetTerms);
            EdmEntityContainer decisionContainer = decisionSchema.entityContainers.get(0);
            entityTypes.addAll(decisionSchema.entityTypes);
            associations.addAll(decisionSchema.associations);
            entitySets.addAll(decisionContainer.entitySets);
        }

        if (cfg.isUseContacts()) {
            // Define the Contact entity
            EdmSchema contactSchema = AbstractContact.getSchema(getNamespace());
            EdmEntityContainer contactContainer = contactSchema.entityContainers.get(0);
            entityTypes.addAll(contactSchema.entityTypes);
            associations.addAll(contactSchema.associations);
            entitySets.addAll(contactContainer.entitySets);
        }

        if (cfg.isUseMeetings()) {
            // Define the Meeting entity
            EdmSchema meetingSchema = AbstractMeeting.getSchema(getNamespace());
            EdmEntityContainer meetingContainer = meetingSchema.entityContainers.get(0);
            entityTypes.addAll(meetingSchema.entityTypes);
            associations.addAll(meetingSchema.associations);
            entitySets.addAll(meetingContainer.entitySets);
        }

        if (cfg.isUseCountryReports()) {
            // Define the CountryReports entity
            EdmSchema crSchema = AbstractCountryReport.getSchema(getNamespace());
            EdmEntityContainer crContainer = crSchema.entityContainers.get(0);
            entityTypes.addAll(crSchema.entityTypes);
            associations.addAll(crSchema.associations);
            entitySets.addAll(crContainer.entitySets);
        }

        if (cfg.isUseNationalPlans()) {
            // Define the NationalPlan entity
            EdmSchema npSchema = AbstractNationalPlan.getSchema(getNamespace());
            EdmEntityContainer npContainer = npSchema.entityContainers.get(0);
            entityTypes.addAll(npSchema.entityTypes);
            associations.addAll(npSchema.associations);
            entitySets.addAll(npContainer.entitySets);
        }

        if (cfg.isUseSites()) {

            propertyName = "name";
            eetLocString = LocalizableString.getEntityType(namespace);
            eesLocString = new EdmEntitySet(propertyName, eetLocString);
            entitySets.add(eesLocString);

            // Define the Sites entity
            EdmSchema sitesSchema = AbstractSite.getSchema(getNamespace());
            EdmEntityContainer sitesContainer = sitesSchema.entityContainers.get(0);
            entityTypes.addAll(sitesSchema.entityTypes);
            associations.addAll(sitesSchema.associations);
            entitySets.addAll(sitesContainer.entitySets);
        }

        if (cfg.isUseCountryProfiles()) {
            // Define the CountryProfile entity
            EdmSchema cpSchema = AbstractCountryProfile.getSchema(getNamespace());
            EdmEntityContainer cpContainer = cpSchema.entityContainers.get(0);
            entityTypes.addAll(cpSchema.entityTypes);
            associations.addAll(cpSchema.associations);
            entitySets.addAll(cpContainer.entitySets);
        }


        // Peblds entities
        if (cfg.isUseProjects()) {

            EdmSchema objectSchema = AbstractPebldsProject.getSchema(getNamespace());
            EdmEntityContainer objectContainer = objectSchema.entityContainers.get(0);
            entityTypes.addAll(objectSchema.entityTypes);
            associations.addAll(objectSchema.associations);
            entitySets.addAll(objectContainer.entitySets);
        }

        if (cfg.isUseBestPractices()) {

            EdmSchema objectSchema = AbstractPebldsBestPractice.getSchema(getNamespace());
            EdmEntityContainer objectContainer = objectSchema.entityContainers.get(0);

            entityTypes.addAll(objectSchema.entityTypes);
            associations.addAll(objectSchema.associations);

            entitySets.addAll(objectContainer.entitySets);
        }

        if (cfg.isUseTechnicalReports()) {

            EdmSchema objectSchema = AbstractPebldsTechnicalReport.getSchema(getNamespace());
            EdmEntityContainer objectContainer = objectSchema.entityContainers.get(0);
            entityTypes.addAll(objectSchema.entityTypes);
            associations.addAll(objectSchema.associations);
            entitySets.addAll(objectContainer.entitySets);
        }

        if (cfg.isUseProjects() || cfg.isUseBestPractices() || cfg.isUseTechnicalReports()) {
            EdmSchema objectSchema = AbstractPebldsTopic.getSchema(getNamespace());
            EdmEntityContainer objectContainer = objectSchema.entityContainers.get(0);
            entityTypes.addAll(objectSchema.entityTypes);
            entitySets.addAll(objectContainer.entitySets);

            objectSchema = AbstractPebldsWpfbFile.getSchema(getNamespace());
            objectContainer = objectSchema.entityContainers.get(0);
            entityTypes.addAll(objectSchema.entityTypes);
            entitySets.addAll(objectContainer.entitySets);
        }


        // Remove duplicated entity types
        entityTypes = fixDuplicateEntityTypes(entityTypes);
        // Remove duplicated entity types
        entitySets = fixDuplicateEntitySets(entitySets);


        // Output everything
        List<EdmEntityContainer> containers = new ArrayList<EdmEntityContainer>();
        containers.add(new EdmEntityContainer("InforMEAService", true, Boolean.FALSE, entitySets, null, null));

        EdmSchema schema = new EdmSchema(getNamespace(), null, entityTypes, null, associations, containers);
        List<EdmSchema> schemas = Enumerable.create(schema).toList();
        return new EdmDataServices(ODataConstants.DATA_SERVICE_VERSION, schemas);
    }

    private List<EdmEntityType> fixDuplicateEntityTypes(List<EdmEntityType> entityTypes) {
        List<EdmEntityType> ret = new ArrayList<EdmEntityType>();
        List<String> added = new ArrayList<String>();

        for(EdmEntityType entityType : entityTypes) {
            if(!added.contains(entityType.getFQNamespaceName())) {
                ret.add(entityType);
                added.add(entityType.getFQNamespaceName());
            }
        }
        return ret;
    }

    private List<EdmEntitySet> fixDuplicateEntitySets(List<EdmEntitySet> entitySets) {
        List<EdmEntitySet> ret = new ArrayList<EdmEntitySet>();
        List<String> added = new ArrayList<String>();

        for(EdmEntitySet entitySet : entitySets) {
            if(!added.contains(entitySet.name)) {
                ret.add(entitySet);
                added.add(entitySet.name);
            }
        }
        return ret;
    }

    protected final String getSkipToken() {
        return skipToken;
    }

    protected final void setSkipToken(String skipToken) {
        this.skipToken = skipToken;
    }
}
