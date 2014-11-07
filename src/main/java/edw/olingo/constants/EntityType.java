package edw.olingo.constants;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public enum EntityType {

    DECISIONS("Decisions"),
    MEETINGS("Meetings"),
    CONTACTS("Contacts"),
    COUNTRY_REPORTS("CountryReports"),
    NATIONAL_PLANS("NationalPlans"),
    COUNTRY_PROFILES("CountryProfiles"),
    SITES("Sites");

    private String name;

    private EntityType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        if(this.name != null) {
            if(this.name.equalsIgnoreCase("Decisions")) {
                return "Decisions";
            }
            if(this.name.equalsIgnoreCase("Meetings")) {
                return "Meetings";
            }
            if(this.name.equalsIgnoreCase("Contacts")) {
                return "Contacts";
            }
            if(this.name.equalsIgnoreCase("CountryReports")) {
                return "National reports";
            }
            if(this.name.equalsIgnoreCase("NationalPlans")) {
                return "National plans";
            }
            if(this.name.equalsIgnoreCase("CountryProfiles")) {
                return "Ratification data";
            }
            if(this.name.equalsIgnoreCase("Sites")) {
                return "Sites";
            }
        }
        return null;
    }

    public static List<EntityType> getEntities() {
        ArrayList<EntityType> ret = new ArrayList<EntityType>();
        ret.add(MEETINGS);
        ret.add(DECISIONS);
        ret.add(CONTACTS);
        ret.add(COUNTRY_REPORTS);
        ret.add(COUNTRY_PROFILES);
        ret.add(SITES);
        ret.add(NATIONAL_PLANS);
        return ret;
    }

    /**
     * Construct new enum type from string value.
     * @param value Value to be parsed (case-insensitive)
     * @return Parsed value as instance of enum or null if no match is found
     */
    public static EntityType fromString(String value) {
        if(value != null) {
            if(value.equalsIgnoreCase("Decisions")) {
                return EntityType.DECISIONS;
            }
            if(value.equalsIgnoreCase("Meetings")) {
                return EntityType.MEETINGS;
            }
            if(value.equalsIgnoreCase("Contacts")) {
                return EntityType.CONTACTS;
            }
            if(value.equalsIgnoreCase("CountryReports")) {
                return EntityType.COUNTRY_REPORTS;
            }
            if(value.equalsIgnoreCase("NationalPlans")) {
                return EntityType.NATIONAL_PLANS;
            }
            if(value.equalsIgnoreCase("CountryProfiles")) {
                return EntityType.COUNTRY_PROFILES;
            }
            if(value.equalsIgnoreCase("Sites")) {
                return EntityType.SITES;
            }
        }
        return null;
    }

    /**
     * Build the URL to the endpoint for an entity
     * @param request HTTP request to get the context URL
     * @param type Type of entity
     * @param servicePath relative path (to svc)
     * @return URL
     */
    public static String getEndpointURL(HttpServletRequest request, EntityType type, String servicePath) {
        switch(type) {
            case CONTACTS:
                return String.format("%s/%s/%s", request.getContextPath(), servicePath, "Contacts");
            case COUNTRY_PROFILES:
                return String.format("%s/%s/%s", request.getContextPath(), servicePath, "CountryProfiles");
            case COUNTRY_REPORTS:
                return String.format("%s/%s/%s", request.getContextPath(), servicePath, "CountryReports");
            case DECISIONS:
                return String.format("%s/%s/%s", request.getContextPath(), servicePath, "Decisions");
            case MEETINGS:
                return String.format("%s/%s/%s", request.getContextPath(), servicePath, "Meetings");
            case NATIONAL_PLANS:
                return String.format("%s/%s/%s", request.getContextPath(), servicePath, "NationalPlans");
            case SITES:
                return String.format("%s/%s/%s", request.getContextPath(), servicePath, "Sites");
        }
        return String.format("%s/%s/%s", request.getContextPath(), servicePath);
    }
}
