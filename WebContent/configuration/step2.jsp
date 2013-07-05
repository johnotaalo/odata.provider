<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.informea.odata.util.ToolkitUtil" %>
<%@page import="org.informea.odata.producer.toolkit.Configuration" %>
<%@page import="org.informea.odata.util.JDBCHelper"%>
<%
    // If user drops to this page and setup is not configured, just redirect to start
    if(session.getAttribute(Configuration.DB_TYPE) == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    String db_type = (String)session.getAttribute(Configuration.DB_TYPE);
    String db_host = (String)session.getAttribute(Configuration.DB_HOST);
    int db_port = ((Integer)session.getAttribute(Configuration.DB_PORT)).intValue();
    String db_user = (String)session.getAttribute(Configuration.DB_USER);
    String db_pass = (String)session.getAttribute(Configuration.DB_PASS);
    String db_database = (String)session.getAttribute(Configuration.DB_DATABASE);

    boolean next = ToolkitUtil.isOnRequest("next", request);
    boolean invalidSelection = false;
    if(next) {
        // We're okay, save the DB configuration from session
        // Configuration.getInstance().putFromSession(session);

        boolean useDecisions = ToolkitUtil.getRequestCheckbox(Configuration.USE_DECISIONS, request);
        boolean useMeetings = ToolkitUtil.getRequestCheckbox(Configuration.USE_MEETINGS, request);
        boolean useContacts = ToolkitUtil.getRequestCheckbox(Configuration.USE_CONTACTS, request);
        boolean useCountryReports = ToolkitUtil.getRequestCheckbox(Configuration.USE_COUNTRY_REPORTS, request);
        boolean useCountryProfiles = ToolkitUtil.getRequestCheckbox(Configuration.USE_COUNTRY_PROFILES, request);
        boolean useNationalPlans = ToolkitUtil.getRequestCheckbox(Configuration.USE_NATIONAL_PLANS, request);
        boolean useSites = ToolkitUtil.getRequestCheckbox(Configuration.USE_SITES, request);

        invalidSelection = !useDecisions && !useMeetings && !useContacts
                && !useCountryReports && !useCountryProfiles && !useNationalPlans
                && !useSites;
        if(!invalidSelection) {
            session.setAttribute(Configuration.USE_DECISIONS, new Boolean(useDecisions));
            session.setAttribute(Configuration.USE_MEETINGS, new Boolean(useMeetings));
            session.setAttribute(Configuration.USE_CONTACTS, new Boolean(useContacts));
            session.setAttribute(Configuration.USE_COUNTRY_REPORTS, new Boolean(useCountryReports));
            session.setAttribute(Configuration.USE_COUNTRY_PROFILES, new Boolean(useCountryProfiles));
            session.setAttribute(Configuration.USE_NATIONAL_PLANS, new Boolean(useNationalPlans));
            session.setAttribute(Configuration.USE_SITES, new Boolean(useSites));

            if(useDecisions) {
                response.sendRedirect("step3.jsp");
            } else {
                response.sendRedirect("step4.jsp");
            }
            return;
        }
    }

    JDBCHelper jdbc = new JDBCHelper(db_type, db_host, db_port, db_user, db_pass, db_database);
    pageContext.setAttribute("jdbc", jdbc);
%>
<jsp:include page="../WEB-INF/includes/header.jsp">
    <jsp:param name="html_title" value="Select available entities" />
    <jsp:param name="current_menu_item" value="configuration" />
</jsp:include>
<div class="content">
    <div id="breadcrumb">
        You are here: <a href="<%= ToolkitUtil.url(request, null) %>">home</a>
        &raquo;
        <a href="<%= ToolkitUtil.url(request, "/configuration") %>">configuration</a>
        &raquo;
        <a href="<%= ToolkitUtil.url(request, "/configuration/step1.jsp") %>">database configuration</a>
        &raquo;
        select available entities
    </div>
    <h1>Select available entities</h1>
    <% if(next && invalidSelection) { %>
    <p class="error">Invalid selection. Please select at least one entity to have exposed</p>
    <% } %>
    <form action="" method="post">
        <table style="width: 40%;" class="entities">
            <tr>
                <th width="1%">Check</th>
                <th>Entity</th>
                <th width="1%">Present</th>
            </tr>
            <tr>
                <td>
                    <input type="checkbox" id="<%= Configuration.USE_DECISIONS %>" name="<%= Configuration.USE_DECISIONS %>"
                           <% if(!jdbc.detectDecisions()) { %> disabled="disabled" <% } %>
                           <% if(jdbc.detectDecisions()) { %> checked="checked" <% } %>
                           value="ON" tabindex="1" />
                </td>
                <td><label for="<%= Configuration.USE_DECISIONS %>">Decisions</label></td>
                <td><% if(jdbc.detectDecisions()) { %>Yes<% } else { %>-<% } %></td>
            </tr>
            <tr class="zebra">
                <td>
                    <input type="checkbox" id="<%= Configuration.USE_MEETINGS %>" name="<%= Configuration.USE_MEETINGS %>"
                           <% if(!jdbc.detectMeetings()) { %> disabled="disabled" <% } %>
                           <% if(jdbc.detectMeetings()) { %> checked="checked" <% } %>
                           value="ON" tabindex="2" />
                </td>
                <td><label for="<%= Configuration.USE_MEETINGS %>">Meetings</label></td>
                <td>
                <% if(jdbc.detectMeetings()) { %>Yes<% } else { %>-<% } %>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="checkbox" id="<%= Configuration.USE_CONTACTS %>" name="<%= Configuration.USE_CONTACTS %>"
                           <% if(!jdbc.detectContacts()) { %> disabled="disabled" <% } %>
                           <% if(jdbc.detectContacts()) { %> checked="checked" <% } %>
                           value="ON" tabindex="3" />
                </td>
                <td><label for="<%= Configuration.USE_CONTACTS %>">Contacts</label></td>
                <td>
                <% if(jdbc.detectContacts()) { %>Yes<% } else { %>-<% } %>
                </td>
            </tr>
            <tr class="zebra">
                <td>
                    <input type="checkbox" id="<%= Configuration.USE_COUNTRY_REPORTS %>" name="<%= Configuration.USE_COUNTRY_REPORTS %>"
                           <% if(!jdbc.detectCountryReports()) { %> disabled="disabled" <% } %>
                           <% if(jdbc.detectCountryReports()) { %> checked="checked" <% } %>
                           value="ON" tabindex="4" />
                </td>
                <td><label for="<%= Configuration.USE_COUNTRY_REPORTS %>">Country reports</label></td>
                <td>
                <% if(jdbc.detectCountryReports()) { %>Yes<% } else { %>-<% } %>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="checkbox" id="<%= Configuration.USE_COUNTRY_PROFILES %>" name="<%= Configuration.USE_COUNTRY_PROFILES %>"
                           <% if(!jdbc.detectCountryProfiles()) { %> disabled="disabled" <% } %>
                           <% if(jdbc.detectCountryProfiles()) { %> checked="checked" <% } %>
                           value="ON" tabindex="5" />
                </td>
                <td><label for="<%= Configuration.USE_COUNTRY_PROFILES %>">Country profiles</label></td>
                <td>
                <% if(jdbc.detectCountryProfiles()) { %>Yes<% } else { %>-<% } %>
                </td>
            </tr>
            <tr class="zebra">
                <td>
                    <input type="checkbox" id="<%= Configuration.USE_NATIONAL_PLANS %>" name="<%= Configuration.USE_NATIONAL_PLANS %>"
                           <% if(!jdbc.detectNationalPlans()) { %> disabled="disabled" <% } %>
                           <% if(jdbc.detectNationalPlans()) { %> checked="checked" <% } %>
                           value="ON" tabindex="6" />
                </td>
                <td><label for="<%= Configuration.USE_NATIONAL_PLANS %>">National plans</label></td>
                <td>
                <% if(jdbc.detectNationalPlans()) { %>Yes<% } else { %>-<% } %>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="checkbox" id="<%= Configuration.USE_SITES %>" name="<%= Configuration.USE_SITES %>"
                           <% if(!jdbc.detectSites()) { %> disabled="disabled" <% } %>
                           <% if(jdbc.detectSites()) { %> checked="checked" <% } %>
                           value="ON" tabindex="7" />
                </td>
                <td><label for="<%= Configuration.USE_SITES %>">Sites</label></td>
                <td>
                <% if(jdbc.detectSites()) { %>Yes<% } else { %>-<% } %>
                </td>
            </tr>
        </table>
        <% if(jdbc.isMissingAllEntities()) { %>
            <p class="error">
                The database is missing all the entities. Please create the appropriate views in database for the entities you want exposed.
                <br />
                Configuration cannot continue.
                <br />
                <a class="button search-explorer-submit mousedown" title="Back button" href="index.jsp">
                    <span>Abort</span>
                </a>
            </p>
        <% } else { %>
        <input type="submit" name="next" value="Next &raquo;" tabindex="8" />
        <% } %>
    </form>
    <br />
    <br />
</div>
<jsp:include page="../WEB-INF/includes/footer.jsp" />