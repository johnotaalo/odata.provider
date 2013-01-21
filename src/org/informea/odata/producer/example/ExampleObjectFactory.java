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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.informea.odata.constants.DecisionStatus;
import org.informea.odata.constants.DecisionType;
import org.informea.odata.constants.MeetingAccess;
import org.informea.odata.constants.MeetingKind;
import org.informea.odata.constants.MeetingRepetition;
import org.informea.odata.constants.MeetingStatus;
import org.informea.odata.constants.MeetingType;
import org.informea.odata.constants.MimeType;
import org.informea.odata.constants.NationalPlanType;
import org.informea.odata.constants.SiteType;
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
import org.informea.odata.producer.example.impl.ExampleContact;
import org.informea.odata.producer.example.impl.ExampleDecision;

/**
 * Sample data source for web service.
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 0.6
 * Servlet is informea-example
 */
public class ExampleObjectFactory {

    public static AbstractMeeting getSampleMeeting() {
        return new AbstractMeeting() {

            @Override
            public Short getProtocolVersion() {
                return 1;
            }

            @Override
            public String getId() {
                return "1";
            }

            @Override
            public Treaty getTreaty() {
                return Treaty.MONTREAL;
            }

            @Override
            public String getUrl() {
                return "http://meeting.online.com";
            }

            @Override
            public List<LocalizableString> getTitle() {
                return Arrays.asList(new LocalizableString("en", "Sample meeting in english"), new LocalizableString("fr", "Sample meeting in french"));
            }

            @Override
            public List<LocalizableString> getDescription() {
                return Arrays.asList(new LocalizableString("en", "Sample meeting description in english"), new LocalizableString("fr", "Sample meeting description in french"));
            }

            @Override
            public Date getStart() {
                Calendar pub = Calendar.getInstance();
                pub.set(2010, 4, 19);
                return pub.getTime();
            }

            @Override
            public Date getEnd() {
                Calendar pub = Calendar.getInstance();
                pub.set(2010, 4, 23);
                return pub.getTime();
            }

            @Override
            public MeetingRepetition getRepetition() {
                return MeetingRepetition.WEEKLY;
            }

            @Override
            public MeetingKind getKind() {
                return MeetingKind.OFFICIAL;
            }

            @Override
            public MeetingType getType() {
                return MeetingType.SYMPOSIA;
            }

            @Override
            public MeetingAccess getAccess() {
                return MeetingAccess.PUBLIC;
            }

            @Override
            public MeetingStatus getStatus() {
                return MeetingStatus.TENTATIVE;
            }

            @Override
            public String getImageUrl() {
                return "http://ia.media-imdb.com/images/M/MV5BMjA5NzMwMjA0OV5BMl5BanBnXkFtZTcwNDMzMzc5Mw@@._V1._SX640_SY948_.jpg";
            }

            @Override
            public String getImageCopyright() {
                return "(C) 2011 IMDB";
            }

            @Override
            public String getLocation() {
                return "Brooklyn 123.City Avenue";
            }

            @Override
            public String getCity() {
                return "New York";
            }

            @Override
            public String getCountry() {
                return "usa";
            }

            @Override
            public Double getLatitude() {
                return 23.442332;
            }

            @Override
            public Double getLongitude() {
                return 33.3243433;
            }

            @Override
            public Date getUpdated() {
                return Calendar.getInstance().getTime();
            }
        };
    }

    /**
     * Sample method to return a decision. This build one in memory, but you can get decisions from database etc.
     * @return Sample decision
     */
    public static AbstractDecision getSampleDecision() {
        ExampleDecision ob = new ExampleDecision();

        // Put some dummy data inside decision
        ob.id = "1";
        ob.link = "http://example.com/decisions/1";
        ob.title = Arrays.asList(new LocalizableString("en", "Sample decision #1 title"), new LocalizableString("fr", "French title of decision 1"));
        ob.longTitle = Arrays.asList(new LocalizableString("en", "Long title of decision #1"), new LocalizableString("fr", "French long title of decision #1"));
        ob.summary = Arrays.asList(new LocalizableString("en", "Summary of decision #1"), new LocalizableString("fr", "French summary of decision #1"));
        ob.type = DecisionType.DECISION;
        ob.status = DecisionStatus.ACTIVE;
        ob.number = "X/1";
        ob.treaty = Treaty.CARTAGENA;

        Calendar pub = Calendar.getInstance();
        pub.set(2009, 12, 23, 0, 0, 0);
        pub.set(Calendar.MILLISECOND, 0);
        pub.setTimeZone(TimeZone.getTimeZone("GMT"));
        ob.published = pub.getTime();

        Calendar up = Calendar.getInstance();
        up.set(2011, 12, 23, 0, 0, 0);
        up.set(2011, 2, 12, 0, 0, 0);
        up.set(Calendar.MILLISECOND, 0);
        up.setTimeZone(TimeZone.getTimeZone("GMT"));
        ob.updated = up.getTime();

        ob.meetingId = "2";
        ob.meetingTitle = "Meeting for conference of parties 10";
        ob.meetingUrl = "http://meeting.url.com/10";
        ob.content = Arrays.asList(new LocalizableString("en", "Sample decision content in english"), new LocalizableString("fr", "Sample decision content in french"));
        ob.keywords = Arrays.asList(new VocabularyTerm("chlorophile", "http://example.com"), new VocabularyTerm("CFC-X01", VocabularyTerm.UNEP_NAMESPACE));
        List<DecisionDocument> documents = new ArrayList<DecisionDocument>();

        BufferedInputStream in = null;
        try {
            String filename = "sample_document.doc";
            byte[] content = new byte[(int) new java.io.File(filename).length()];
            in = new BufferedInputStream(new FileInputStream(filename));
            in.read(content);
            documents.add(new DecisionDocument("1_sample_document.doc", "http://www.example.com/downloads/" + filename, content, content.length, MimeType.DOC, "en", filename));
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        }

        try {
            String filename = "sample_document.odt";
            byte[] content = new byte[(int) new java.io.File(filename).length()];
            in = new BufferedInputStream(new FileInputStream(filename));
            in.read(content);
            documents.add(new DecisionDocument("1_sample_document.odt", "http://www.example.com/downloads/" + filename, content, content.length, MimeType.ODT, "en", filename));
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        }

        try {
            String filename = "sample_document.pdf";
            byte[] content = new byte[(int) new java.io.File(filename).length()];
            in = new BufferedInputStream(new FileInputStream(filename));
            in.read(content);
            documents.add(new DecisionDocument("1_sample_document.pdf", "http://www.example.com/downloads/" + filename, content, content.length, MimeType.PDF, "en", filename));
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        }

        try {
            String filename = "sample_document.rtf";
            byte[] content = new byte[(int) new java.io.File(filename).length()];
            in = new BufferedInputStream(new FileInputStream(filename));
            in.read(content);
            documents.add(new DecisionDocument("1_sample_document.rtf", "http://www.example.com/downloads/" + filename, content, content.length, MimeType.RTF, "en", filename));
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        }

        ob.documents = documents;
        return ob;
    }

    public static AbstractContact getSampleContact() {
        ExampleContact ob = new ExampleContact();
        ob.id = "1";
        ob.treaties = Arrays.asList(Treaty.AEWA, Treaty.MONTREAL);
        ob.country = "can";
        ob.prefix = "Sir";
        ob.firstName = "Anthony";
        ob.lastName = "Hopkins";
        ob.position = "Manager";
        ob.institution = "Hollywood";
        ob.department = "Actors";
        ob.address = "First Osaka Boulevard";
        ob.email = "antony.hopkins@hollywood.com";
        ob.phoneNumber = "123666999";
        ob.fax = "321999666";
        ob.updated = Calendar.getInstance().getTime();
        return ob;
    }

    public static AbstractCountryProfile getSampleCountryProfile() {
        return new AbstractCountryProfile() {

            @Override
            public Treaty getTreaty() {
                return Treaty.CARTAGENA;
            }

            @Override
            public String getCountry() {
                return "atg"; // Antigua and Barbuda
            }

            @Override
            public Short getEntryIntoForce() {
                return 1999;
            }

            @Override
            public Date getUpdated() {
                return Calendar.getInstance().getTime();
            }
        };
    }

    public static AbstractCountryReport getSampleCountryReport() {
        return new AbstractCountryReport() {

            @Override
            public Short getProtocolVersion() {
                return 1;
            }

            @Override
            public String getId() {
                return "1";
            }

            @Override
            public String getCountry() {
                return "bgd"; // Bangladesh
            }

            @Override
            public Treaty getTreaty() {
                return Treaty.NAGOYA;
            }

            @Override
            public List<LocalizableString> getTitle() {
                return Arrays.asList(new LocalizableString("en", "Sample country report english title"), new LocalizableString("fr", "Sample country report french title"));
            }

            @Override
            public Date getSubmission() {
                Calendar pub = Calendar.getInstance();
                pub.set(2010, 2, 19);
                return pub.getTime();
            }

            @Override
            public String getUrl() {
                return "http://the.new.horizont.com/country_report";
            }

            @Override
            public Date getUpdated() {
                return Calendar.getInstance().getTime();
            }
        };
    }

    public static AbstractNationalPlan getSampleNationalPlan() {
        return new AbstractNationalPlan() {

            @Override
            public Short getProtocolVersion() {
                return 1;
            }

            @Override
            public String getId() {
                return "1";
            }

            @Override
            public Treaty getTreaty() {
                return Treaty.RAMSAR;
            }

            @Override
            public String getCountry() {
                return "bra"; // Brazil
            }

            @Override
            public NationalPlanType getType() {
                return NationalPlanType.NBSAP;
            }

            @Override
            public List<LocalizableString> getTitle() {
                return Arrays.asList(new LocalizableString("en", "Sample national plan english title"), new LocalizableString("fr", "Sample national plan french title"));
            }

            @Override
            public String getUrl() {
                return "http://national.plan.org/brazil_national_plan";
            }

            @Override
            public Date getSubmission() {
                Calendar pub = Calendar.getInstance();
                pub.set(1972, 2, 19);
                return pub.getTime();
            }

            @Override
            public Date getUpdated() {
                return Calendar.getInstance().getTime();
            }
        };
    }

    public static AbstractSite getSampleSite() {
        return new AbstractSite() {

            @Override
            public String getId() {
                return "123";
            }

            @Override
            public SiteType getType() {
                return SiteType.RAMSAR;
            }

            @Override
            public String getCountry() {
                return "bgr"; // Bulgaria
            }

            @Override
            public Treaty getTreaty() {
                return Treaty.RAMSAR;
            }

            @Override
            public List<LocalizableString> getName() {
                LocalizableString s1 = new LocalizableString("en", "Tri gospodari");
                return Arrays.asList(s1);
            }

            @Override
            public String getUrl() {
                return "http://bulgaria.com/test";
            }

            @Override
            public Double getLatitude() {
                return 100.12345;
            }

            @Override
            public Double getLongitude() {
                return 100.54321;
            }

            @Override
            public Date getUpdated() {
                return Calendar.getInstance().getTime();
            }
        };
    }
}
