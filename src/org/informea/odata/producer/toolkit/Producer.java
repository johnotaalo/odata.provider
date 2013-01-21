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
package org.informea.odata.producer.toolkit;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.criterion.Order;
import org.informea.odata.constants.Treaty;
import org.informea.odata.pojo.AbstractContact;
import org.informea.odata.pojo.AbstractCountryProfile;
import org.informea.odata.pojo.AbstractCountryReport;
import org.informea.odata.pojo.AbstractDecision;
import org.informea.odata.pojo.AbstractMeeting;
import org.informea.odata.pojo.AbstractNationalPlan;
import org.informea.odata.pojo.AbstractSite;
import org.informea.odata.pojo.DecisionDocument;
import org.informea.odata.pojo.LocalizableString;
import org.informea.odata.pojo.VocabularyTerm;
import org.informea.odata.producer.AbstractInformeaProducer;
import org.informea.odata.producer.toolkit.impl.Contact;
import org.informea.odata.producer.toolkit.impl.CountryProfile;
import org.informea.odata.producer.toolkit.impl.CountryReport;
import org.informea.odata.producer.toolkit.impl.Decision;
import org.informea.odata.producer.toolkit.impl.Meeting;
import org.informea.odata.producer.toolkit.impl.NationalPlan;
import org.informea.odata.producer.toolkit.impl.Site;
import org.odata4j.producer.QueryInfo;


/**
 * Toolkit producer main class
 * <br />
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 1.3.3
 */
public class Producer extends AbstractInformeaProducer {

    public static final int MAJOR = 1;
    public static final int MINOR = 5;
    public static final int REVISION = 6;
    public static final boolean BETA = true;
    public static final String UPDATE_URL = "http://www.informea.org/api.properties";

    private static final Logger log = Logger.getLogger(Producer.class.getName());

    /**
     * Build new producer
     */
    public Producer() {
        super("informea");
        log.log(Level.FINE, "Instantiating InforMEA OData producer");
    }


    @Override
    public AbstractMeeting getMeeting(IDataProvider dataProvider, Object id) {
        return (AbstractMeeting)dataProvider.getPrimaryEntity(Meeting.class, id.toString());
    }


    @Override
    public List<AbstractMeeting> getMeetings(IDataProvider dataProvider, QueryInfo q, int startResult, Integer pageSize) {
        List meetings = dataProvider.getPrimaryEntities(Meeting.class, q, startResult, pageSize, Order.desc("start"));
        return meetings;
    }


    @Override
    public Integer getMeetingsCount(IDataProvider dataProvider, QueryInfo q) {
        int ret = dataProvider.countPrimaryEntities(Meeting.class, q);
        return ret;
    }


    @Override
    public List<LocalizableString> getMeetingTitle(IDataProvider dataProvider, Object id, QueryInfo q) {
        AbstractMeeting ob = (AbstractMeeting)dataProvider.getPrimaryEntity(Meeting.class, id.toString());
        if (ob != null) {
            return ob.getTitle();
        }
        log.log(Level.WARNING, "No meeting has been found with ID: {0}", id);
        return new ArrayList<LocalizableString>();
    }


    @Override
    public List<LocalizableString> getMeetingDescription(IDataProvider dataProvider, Object id, QueryInfo q) {
        AbstractMeeting ob = (AbstractMeeting)dataProvider.getPrimaryEntity(Meeting.class, id.toString());
        if (ob != null) {
            return ob.getDescription();
        }
        log.log(Level.WARNING, "No meeting has been found with ID: {0}", id);
        return new ArrayList<LocalizableString>();
    }


    @Override
    public AbstractDecision getDecision(IDataProvider dataProvider, Object id) {
        return (AbstractDecision)dataProvider.getPrimaryEntity(Decision.class, id.toString());
    }


    @Override
    public List<AbstractDecision> getDecisions(IDataProvider dataProvider, QueryInfo q, int startResult, Integer pageSize) {
        return dataProvider.getPrimaryEntities(Decision.class, q, startResult, pageSize, Order.asc("treaty"));
    }


    @Override
    public Integer getDecisionsCount(IDataProvider dataProvider, QueryInfo q) {
        return dataProvider.countPrimaryEntities(Decision.class, q);
    }


    @Override
    public List<DecisionDocument> getDecisionDocuments(IDataProvider dataProvider, Object id, QueryInfo q) {
        AbstractDecision ob = (AbstractDecision)dataProvider.getPrimaryEntity(Decision.class, id.toString());
        if (ob != null) {
            return ob.getDocuments();
        }
        log.log(Level.WARNING, "No decision has been found with ID: {0}", id);
        return new ArrayList<DecisionDocument>();
    }


    @Override
    public List<LocalizableString> getDecisionTitle(IDataProvider dataProvider, Object id, QueryInfo q) {
        AbstractDecision ob = (AbstractDecision)dataProvider.getPrimaryEntity(Decision.class, id.toString());
        if (ob != null) {
            return ob.getTitle();
        }
        log.log(Level.WARNING, "No decision has been found with ID: {0}", id);
        return new ArrayList<LocalizableString>();
    }


    @Override
    public List<LocalizableString> getDecisionLongTitle(IDataProvider dataProvider, Object id, QueryInfo q) {
        AbstractDecision ob = (AbstractDecision)dataProvider.getPrimaryEntity(Decision.class, id.toString());
        if (ob != null) {
            return ob.getLongTitle();
        }
        log.log(Level.WARNING, "No decision has been found with ID: {0}", id);
        return new ArrayList<LocalizableString>();
    }


    @Override
    public List<LocalizableString> getDecisionSummary(IDataProvider dataProvider, Object id, QueryInfo q) {
        AbstractDecision ob = (AbstractDecision)dataProvider.getPrimaryEntity(Decision.class, id.toString());
        if (ob != null) {
            return ob.getSummary();
        }
        log.log(Level.WARNING, "No decision has been found with ID: {0}", id);
        return new ArrayList<LocalizableString>();
    }


    @Override
    public List<LocalizableString> getDecisionContent(IDataProvider dataProvider, Object id, QueryInfo q) {
        AbstractDecision ob = (AbstractDecision)dataProvider.getPrimaryEntity(Decision.class, id.toString());
        if (ob != null) {
            return ob.getContent();
        }
        log.log(Level.WARNING, "No decision has been found with ID: {0}", id);
        return new ArrayList<LocalizableString>();
    }


    @Override
    public List<VocabularyTerm> getDecisionKeywords(IDataProvider dataProvider, Object id, QueryInfo q) {
        AbstractDecision ob = (AbstractDecision)dataProvider.getPrimaryEntity(Decision.class, id.toString());
        if (ob != null) {
            return ob.getKeywords();
        }
        log.log(Level.WARNING, "No decision has been found with ID: {0}", id);
        return new ArrayList<VocabularyTerm>();
    }


    @Override
    public AbstractContact getContact(IDataProvider dataProvider, Object id) {
        return (AbstractContact)dataProvider.getPrimaryEntity(Contact.class, id.toString());
    }


    @Override
    public List<AbstractContact> getContacts(IDataProvider dataProvider, QueryInfo q, int startResult, Integer pageSize) {
        return dataProvider.getPrimaryEntities(Contact.class, q, startResult, pageSize, null);
    }


    @Override
    public Integer getContactsCount(IDataProvider dataProvider, QueryInfo q) {
        return dataProvider.countPrimaryEntities(Contact.class, q);
    }


    @Override
    public List<Treaty> getContactTreaties(IDataProvider dataProvider, Object id, QueryInfo q) {
        AbstractContact ob = (AbstractContact)dataProvider.getPrimaryEntity(Contact.class, id.toString());
        if (ob != null) {
            return ob.getTreaties();
        }
        log.log(Level.WARNING, "No decision has been found with ID: {0}", id);
        return new ArrayList<Treaty>();
    }


    @Override
    public AbstractCountryProfile getCountryProfile(IDataProvider dataProvider, Object id) {
        return (AbstractCountryProfile)dataProvider.getPrimaryEntity(CountryProfile.class, id.toString());
    }

    @Override
    public List<AbstractCountryProfile> getCountryProfiles(IDataProvider dataProvider, QueryInfo q, int startResult, Integer pageSize) {
        return dataProvider.getPrimaryEntities(CountryProfile.class, q, startResult, pageSize, null);
    }

    @Override
    public Integer getCountryProfilesCount(IDataProvider dataProvider, QueryInfo q) {
        return dataProvider.countPrimaryEntities(CountryProfile.class, q);
    }

    @Override
    public AbstractCountryReport getCountryReport(IDataProvider dataProvider, Object id) {
        return (AbstractCountryReport)dataProvider.getPrimaryEntity(CountryReport.class, id.toString());
    }

    @Override
    public List<AbstractCountryReport> getCountryReports(IDataProvider dataProvider, QueryInfo q, int startResult, Integer pageSize) {
        return dataProvider.getPrimaryEntities(CountryReport.class, q, startResult, pageSize, null);
    }

    @Override
    public Integer getCountryReportsCount(IDataProvider dataProvider, QueryInfo q) {
        return dataProvider.countPrimaryEntities(CountryReport.class, q);
    }

    @Override
    public List<LocalizableString> getCountryReportTitle(IDataProvider dataProvider, Object id, QueryInfo q) {
        AbstractCountryReport ob = (AbstractCountryReport)dataProvider.getPrimaryEntity(CountryReport.class, id.toString());
        if (ob != null) {
            return ob.getTitle();
        }
        log.log(Level.WARNING, "No country report has been found with ID: {0}", id);
        return new ArrayList<LocalizableString>();
    }

    @Override
    public AbstractNationalPlan getNationalPlan(IDataProvider dataProvider, Object id) {
        return (AbstractNationalPlan)dataProvider.getPrimaryEntity(NationalPlan.class, id.toString());
    }

    @Override
    public List<AbstractNationalPlan> getNationalPlans(IDataProvider dataProvider, QueryInfo q, int startResult, Integer pageSize) {
        return dataProvider.getPrimaryEntities(NationalPlan.class, q, startResult, pageSize, null);
    }

    @Override
    public Integer getNationalPlansCount(IDataProvider dataProvider, QueryInfo q) {
        return dataProvider.countPrimaryEntities(NationalPlan.class, q);
    }

    @Override
    public List<LocalizableString> getNationalPlanTitle(IDataProvider dataProvider, Object id, QueryInfo q) {
        AbstractNationalPlan ob = (AbstractNationalPlan)dataProvider.getPrimaryEntity(AbstractNationalPlan.class, id.toString());
        if (ob != null) {
            return ob.getTitle();
        }
        log.log(Level.WARNING, "No national plan has been found with ID: {0}", id);
        return new ArrayList<LocalizableString>();
    }

    @Override
    public AbstractSite getSite(IDataProvider dataProvider, Object id) {
        return (AbstractSite)dataProvider.getPrimaryEntity(Site.class, id.toString());
    }

    @Override
    public List<AbstractSite> getSites(IDataProvider dataProvider, QueryInfo q, int startResult, Integer pageSize) {
        return dataProvider.getPrimaryEntities(Site.class, q, startResult, pageSize, null);
    }

    @Override
    public Integer getSitesCount(IDataProvider dataProvider, QueryInfo q) {
        return dataProvider.countPrimaryEntities(Site.class, q);
    }

    @Override
    public List<LocalizableString> getSiteName(IDataProvider dataProvider, Object id, QueryInfo q) {
        AbstractSite ob = (AbstractSite)dataProvider.getPrimaryEntity(AbstractSite.class, id.toString());
        if (ob != null) {
            return ob.getName();
        }
        log.log(Level.WARNING, "No site has been found with ID: {0}", id);
        return new ArrayList<LocalizableString>();

    }

    @Override
    public Integer getEntityCount(IDataProvider dataProvider, QueryInfo q, Class entity) {
        return dataProvider.countPrimaryEntities(entity, q);
    }

    @Override
    public Object getEntity(IDataProvider dataProvider, Object id, Class entity) {
        return dataProvider.getEntity(entity, id);
    }

    @Override
    public List getEntityList(IDataProvider dataProvider, QueryInfo q, int startResult, Integer pageSize, Class entity) {
        return dataProvider.getPrimaryEntities(entity, q, startResult, pageSize, null);
    }
}
