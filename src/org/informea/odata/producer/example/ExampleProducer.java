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
package org.informea.odata.producer.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.informea.odata.constants.Treaty;
import org.informea.odata.pojo.AbstractContact;
import org.informea.odata.pojo.AbstractCountryProfile;
import org.informea.odata.pojo.AbstractCountryReport;

import org.informea.odata.producer.AbstractInformeaProducer;
import org.informea.odata.pojo.AbstractDecision;
import org.informea.odata.pojo.AbstractMeeting;
import org.informea.odata.pojo.AbstractNationalPlan;
import org.informea.odata.pojo.AbstractSite;
import org.informea.odata.pojo.DecisionDocument;
import org.informea.odata.pojo.LocalizableString;
import org.informea.odata.pojo.VocabularyTerm;
import org.informea.odata.producer.example.impl.ExampleContact;
import org.informea.odata.producer.example.impl.ExampleDecision;
import org.informea.odata.producer.toolkit.IDataProvider;
import org.odata4j.producer.QueryInfo;

/**
 * This class implements a sample reference producer. Although all objects are stored in memory, one can use as reference and retrieve the list of objects from other sources such as database.
 * Exposed services
 * <ul>
 *  <li>Decisions</li>
 *  <li>Contacts</li>
 *  <li>Meetings</li>
 *  <li>CountryReports</li>
 *  <li>CountryProfiles</li>
 *  <li>NationalPlans</li>
 *  <li>Sites</li>
 * </ul>
 *
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 0.6
 */
public final class ExampleProducer extends AbstractInformeaProducer {

    private static final Logger log = Logger.getLogger(ExampleProducer.class.getName());

    private final AbstractMeeting meeting = ExampleObjectFactory.getSampleMeeting();
    private final ExampleDecision decision = (ExampleDecision)ExampleObjectFactory.getSampleDecision();
    private final ExampleContact contact = (ExampleContact)ExampleObjectFactory.getSampleContact();

    private final AbstractCountryProfile countryProfile = ExampleObjectFactory.getSampleCountryProfile();
    private final AbstractCountryReport countryReport = ExampleObjectFactory.getSampleCountryReport();
    private final AbstractSite countrySite = ExampleObjectFactory.getSampleSite();
    private final AbstractNationalPlan nationalPlan = ExampleObjectFactory.getSampleNationalPlan();

    /**
     * Build new producer
     */
	public ExampleProducer() {
		super("informea");
	}

    //////////////////////////////////////////////////////////////////////////////////////////// Contact related methods
    @Override
    public AbstractContact getContact(IDataProvider dataProvider, Object id) {
        log.log(Level.INFO, "OData client requested Contact({0})", id);
        if(!id.toString().equals("1")) {
            throw new RuntimeException("No such contact with specified id: " + id );
        }
        return contact;
    }

    @Override
    public List<AbstractContact> getContacts(IDataProvider dataProvider, QueryInfo q, int startResult, Integer pageSize) {
        log.log(Level.INFO, "OData client requested the list of Contacts. Additional filtering may be found in QueryInfo object");
        setSkipToken("done");
        return Arrays.asList((AbstractContact)contact);
    }

    @Override
    public Integer getContactsCount(IDataProvider dataProvider, QueryInfo q) {
        log.log(Level.INFO, "OData client requested the count of Contacts. Additional filtering may be found in QueryInfo object");
        return 1;
    }

    @Override
    public List<Treaty> getContactTreaties(IDataProvider dataProvider, Object id, QueryInfo q) {
        log.log(Level.INFO, "OData client requested navigable property 'treaties' for Contact({0}). Additional filtering may be found in QueryInfo object", id);
        setSkipToken("done");
        return contact.getTreaties();
    }
    ///////////////////////////


    ///////////////////////////////////////////////////////////////////////////////////// CountryProfile related methods
    @Override
    public AbstractCountryProfile getCountryProfile(IDataProvider dataProvider, Object id) {
        log.log(Level.INFO, "OData client requested CountryProfile({0})", id);
        if(!id.toString().equals(Treaty.CARTAGENA.toString())) {
            throw new RuntimeException("No such country profile with specified id: " + id );
        }
        return countryProfile;
    }

    @Override
    public List<AbstractCountryProfile> getCountryProfiles(IDataProvider dataProvider, QueryInfo q, int startResult, Integer pageSize) {
        log.log(Level.INFO, "OData client requested the list of CountryProfiles. Additional filtering may be found in QueryInfo object");
        setSkipToken("done");
        return Arrays.asList(countryProfile);
    }

    @Override
    public Integer getCountryProfilesCount(IDataProvider dataProvider, QueryInfo q) {
        log.log(Level.INFO, "OData client requested the count of CountryProfiles. Additional filtering may be found in QueryInfo object");
        setSkipToken("done");
        return 1;
    }
    ///////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////// CountryReport related methods
    @Override
    public AbstractCountryReport getCountryReport(IDataProvider dataProvider, Object id) {
        log.log(Level.INFO, "OData client requested CountryReport({0})", id);
        if(!id.toString().equals(Treaty.CARTAGENA.toString())) {
            throw new RuntimeException("No such country report with specified id: " + id );
        }
        return countryReport;
    }

    @Override
    public List<AbstractCountryReport> getCountryReports(IDataProvider dataProvider, QueryInfo q, int startResult, Integer pageSize) {
        log.log(Level.INFO, "OData client requested the list of CountryReports. Additional filtering may be found in QueryInfo object");
        setSkipToken("done");
        return Arrays.asList(countryReport);
    }

    @Override
    public Integer getCountryReportsCount(IDataProvider dataProvider, QueryInfo q) {
        log.log(Level.INFO, "OData client requested the count of CountryReports. Additional filtering may be found in QueryInfo object");
        return 1;
    }

    @Override
    public List<LocalizableString> getCountryReportTitle(IDataProvider dataProvider, Object id, QueryInfo q) {
        log.log(Level.INFO, "OData client requested navigable property 'title' for CountryReport({0}). Additional filtering may be found in QueryInfo object", id);
        setSkipToken("done");
        return countryReport.getTitle();
    }
    ///////////////////////////


    /////////////////////////////////////////////////////////////////////////////////////////// Decision related methods
    @Override
    public AbstractDecision getDecision(IDataProvider dataProvider, Object id) {
        log.log(Level.INFO, "OData client requested Decision({0})", id);
        if(!id.toString().equals("1")) {
            throw new RuntimeException("No such decision with specified id: " + id );
        }
        return decision;
    }

    @Override
    public List<AbstractDecision> getDecisions(IDataProvider dataProvider, QueryInfo q, int startResult, Integer pageSize) {
        log.log(Level.INFO, "OData client requested the list of Decisions. Additional filtering may be found in QueryInfo object");
        List<AbstractDecision> ret = new ArrayList<AbstractDecision>();
        ret.add(decision);
        // We set skipToken to "done" since we have no pagination
        setSkipToken("done");
        return ret;
    }

    @Override
    public Integer getDecisionsCount(IDataProvider dataProvider, QueryInfo q) {
        log.log(Level.INFO, "OData client requested the count of Decisions. Additional filtering may be found in QueryInfo object");
        return 1;
    }

    @Override
    public List<DecisionDocument> getDecisionDocuments(IDataProvider dataProvider, Object id, QueryInfo q) {
        log.log(Level.INFO, "OData client requested navigable property 'documents' for Decision({0}). Additional filtering may be found in QueryInfo object", id);
        setSkipToken("done");
        return decision.getDocuments();
    }

    @Override
    public List<LocalizableString> getDecisionTitle(IDataProvider dataProvider, Object id, QueryInfo q) {
        log.log(Level.INFO, "OData client requested navigable property 'title' for Decision({0}). Additional filtering may be found in QueryInfo object", id);
        setSkipToken("done");
        return decision.getTitle();
    }

    @Override
    public List<LocalizableString> getDecisionLongTitle(IDataProvider dataProvider, Object id, QueryInfo q) {
        log.log(Level.INFO, "OData client requested navigable property 'longTitle' for Decision({0}). Additional filtering may be found in QueryInfo object", id);
        setSkipToken("done");
        return decision.getLongTitle();
    }

    @Override
    public List<LocalizableString> getDecisionSummary(IDataProvider dataProvider, Object id, QueryInfo q) {
        log.log(Level.INFO, "OData client requested navigable property 'summary' for Decision({0}). Additional filtering may be found in QueryInfo object", id);
        setSkipToken("done");
        return decision.getSummary();
    }

    @Override
    public List<LocalizableString> getDecisionContent(IDataProvider dataProvider, Object id, QueryInfo q) {
        log.log(Level.INFO, "OData client requested navigable property 'content' for Decision({0}). Additional filtering may be found in QueryInfo object", id);
        setSkipToken("done");
        return decision.getContent();
    }

    @Override
    public List<VocabularyTerm> getDecisionKeywords(IDataProvider dataProvider, Object id, QueryInfo q) {
        log.log(Level.INFO, "OData client requested navigable property 'keywords' for Decision({0}). Additional filtering may be found in QueryInfo object", id);
        setSkipToken("done");
        return decision.getKeywords();
    }
    ///////////////////////////



    //////////////////////////////////////////////////////////////////////////////////////////// Meeting related methods
    @Override
    public AbstractMeeting getMeeting(IDataProvider dataProvider, Object id) {
        log.log(Level.INFO, "OData client requested Meeting({0})", id);
        if(!id.toString().equals("1")) {
            throw new RuntimeException("No such meeting with specified id: " + id );
        }
        return meeting;
    }

    @Override
    public List<AbstractMeeting> getMeetings(IDataProvider dataProvider, QueryInfo q, int startResult, Integer pageSize) {
        log.log(Level.INFO, "OData client requested the list of Meetings. Additional filtering may be found in QueryInfo object");
        return Arrays.asList(meeting);
    }

    @Override
    public Integer getMeetingsCount(IDataProvider dataProvider, QueryInfo q) {
        log.log(Level.INFO, "OData client requested the count of Meetings. Additional filtering may be found in QueryInfo object");
        return 1;
    }

    @Override
    public List<LocalizableString> getMeetingTitle(IDataProvider dataProvider, Object id, QueryInfo q) {
        log.log(Level.INFO, "OData client requested navigable property 'title' for Meeting({0}). Additional filtering may be found in QueryInfo object", id);
        return meeting.getTitle();
    }

    @Override
    public List<LocalizableString> getMeetingDescription(IDataProvider dataProvider, Object id, QueryInfo q) {
        log.log(Level.INFO, "OData client requested navigable property 'description' for Meeting({0}). Additional filtering may be found in QueryInfo object", id);
        return meeting.getDescription();
    }
    /////////////////////////////


    /////////////////////////////////////////////////////////////////////////////////////// NationalPlan related methods
    @Override
    public AbstractNationalPlan getNationalPlan(IDataProvider dataProvider, Object id) {
        log.log(Level.INFO, "OData client requested NationalPlan({0})", id);
        if(!id.toString().equals("1")) {
            throw new RuntimeException("No such national plan with specified id: " + id );
        }
        return nationalPlan;
    }

    @Override
    public List<AbstractNationalPlan> getNationalPlans(IDataProvider dataProvider, QueryInfo q, int startResult, Integer pageSize) {
        log.log(Level.INFO, "OData client requested the list of NationalPlans. Additional filtering may be found in QueryInfo object");
        return Arrays.asList(nationalPlan);
    }

    @Override
    public Integer getNationalPlansCount(IDataProvider dataProvider, QueryInfo q) {
        log.log(Level.INFO, "OData client requested the count of NationalPlans. Additional filtering may be found in QueryInfo object");
        return 1;
    }

    @Override
    public List<LocalizableString> getNationalPlanTitle(IDataProvider dataProvider, Object id, QueryInfo q) {
        log.log(Level.INFO, "OData client requested navigable property 'title' for NationalPlan({0}). Additional filtering may be found in QueryInfo object", id);
        return nationalPlan.getTitle();
    }
    /////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////// Sites related methods
    @Override
    public AbstractSite getSite(IDataProvider dataProvider, Object id) {
        log.log(Level.INFO, "OData client requested Site({0})", id);
        if(!id.toString().equals("123")) {
            throw new RuntimeException("No such decision with specified id: " + id );
        }
        return countrySite;
    }

    @Override
    public List<AbstractSite> getSites(IDataProvider dataProvider, QueryInfo q, int startResult, Integer pageSize) {
        log.log(Level.INFO, "OData client requested the list of Sites. Additional filtering may be found in QueryInfo object");
        return Arrays.asList(countrySite);
    }

    @Override
    public Integer getSitesCount(IDataProvider dataProvider, QueryInfo q) {
        log.log(Level.INFO, "OData client requested the count of Sites. Additional filtering may be found in QueryInfo object");
        return 1;
    }

    @Override
    public List<LocalizableString> getSiteName(IDataProvider dataProvider, Object id, QueryInfo q) {
        log.log(Level.INFO, "OData client requested navigable property 'name' for Site({0}). Additional filtering may be found in QueryInfo object", id);
        return nationalPlan.getTitle();
    }
    ///////////////////////////

    @Override
    public Object getEntity(IDataProvider dataProvider, Object id, Class entity)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List getEntityList(IDataProvider dataProvider, QueryInfo q,
            int startResult, Integer pageSize, Class entity)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer getEntityCount(IDataProvider dataProvider, QueryInfo qm,
            Class entity)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
