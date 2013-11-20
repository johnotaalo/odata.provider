<%@page import="org.informea.odata.constants.EntityType"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.informea.odata.util.ToolkitUtil" %>
<%@page import="org.informea.odata.producer.toolkit.Configuration" %>
<%@page import="org.informea.odata.util.JDBCHelper"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    List<EntityType> entities = EntityType.getEntities();

%>
<jsp:include page="../WEB-INF/includes/header.jsp">
    <jsp:param name="html_title" value="Select available entities" />
    <jsp:param name="current_menu_item" value="configuration" />
</jsp:include>

<ol class="breadcrumb">
    <li><a href="<%= ToolkitUtil.url(request, null) %>">Home</a></li>
    <li><a href="<%= ToolkitUtil.url(request, "/configuration") %>">Configuration</a></li>
    <li class="active">Choose entities</li>
</ol>

<div class="progress progress-striped">
    <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="width: 50%">
        <span class="sr-only">50% Complete</span>
    </div>
</div>

<h1>Choose entities</h1>
<% if(next && invalidSelection) { %>
<p class="error">Invalid selection. Please select at least one entity to have exposed</p>
<% } %>

<c:set var="allMissing" value="${jdbc.isMissingAllEntities()}" />
<c:if test="${allMissing}">
    <div class="alert alert-danger">
        <h4>Fatal error</h4>
        <p>
            The database is not contain any correctly entity.
        </p>
        <p>
            Please create the appropriate views in database for the entities you want exposed.
        </p>
        <p>
            <strong>Configuration cannot continue!</strong>
        </p>
    </div>
</c:if>
<c:if test="${!allMissing}">
<form action="" method="post" class="form-horizontal" role="form">
    <div class="form-group">
        <div class="col-sm-6">
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Entity</th>
                    <th class="text-right">Expose</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>
                        <label for="<%= Configuration.USE_DECISIONS %>">Decisions</label>
                    </td>
                    <td class="text-right">
                        <c:set var="use" value="${jdbc.detectDecisions()}" />
                        <input type="checkbox" id="<%= Configuration.USE_DECISIONS %>" name="<%= Configuration.USE_DECISIONS %>"
                            value="ON" tabindex="1"
                            <c:if test="${!use}"> disabled="disabled"</c:if>
                            <c:if test="${use}"> checked="checked"</c:if>
                        />
                    </td>
                </tr>
                <tr>
                    <td><label for="<%= Configuration.USE_MEETINGS %>">Meetings</label></td>
                    <td class="text-right">
                        <c:set var="use" value="${jdbc.detectMeetings()}" />
                        <input type="checkbox" id="<%= Configuration.USE_MEETINGS %>" name="<%= Configuration.USE_MEETINGS %>"
                            value="ON" tabindex="2"
                            <c:if test="${!use}"> disabled="disabled"</c:if>
                            <c:if test="${use}"> checked="checked"</c:if>
                        />
                    </td>
                </tr>
                <tr>
                    <td><label for="<%= Configuration.USE_CONTACTS %>">Contacts</label></td>
                    <td class="text-right">
                        <c:set var="use" value="${jdbc.detectContacts()}" />
                        <input type="checkbox" id="<%= Configuration.USE_CONTACTS %>" name="<%= Configuration.USE_CONTACTS %>"
                            value="ON" tabindex="3"
                            <c:if test="${!use}"> disabled="disabled"</c:if>
                            <c:if test="${use}"> checked="checked"</c:if>
                        />
                    </td>
                </tr>
                <tr>
                    <td><label for="<%= Configuration.USE_COUNTRY_REPORTS %>">Country reports</label></td>
                    <td class="text-right">
                        <c:set var="use" value="${jdbc.detectCountryReports()}" />
                        <input type="checkbox" id="<%= Configuration.USE_COUNTRY_REPORTS %>" name="<%= Configuration.USE_COUNTRY_REPORTS %>"
                            value="ON" tabindex="4"
                            <c:if test="${!use}"> disabled="disabled"</c:if>
                            <c:if test="${use}"> checked="checked"</c:if>
                        />
                    </td>
                </tr>
                <tr>
                    <td><label for="<%= Configuration.USE_COUNTRY_PROFILES %>">Country profiles</label></td>
                    <td class="text-right">
                        <c:set var="use" value="${jdbc.detectCountryProfiles()}" />
                        <input type="checkbox" id="<%= Configuration.USE_COUNTRY_PROFILES %>" name="<%= Configuration.USE_COUNTRY_PROFILES %>"
                            value="ON" tabindex="5"
                            <c:if test="${!use}"> disabled="disabled"</c:if>
                            <c:if test="${use}"> checked="checked"</c:if>
                        />
                    </td>
                </tr>
                <tr>
                    <td><label for="<%= Configuration.USE_NATIONAL_PLANS %>">National plans</label></td>
                    <td class="text-right">
                        <c:set var="use" value="${jdbc.detectNationalPlans()}" />
                        <input type="checkbox" id="<%= Configuration.USE_NATIONAL_PLANS %>" name="<%= Configuration.USE_NATIONAL_PLANS %>"
                            value="ON" tabindex="6"
                            <c:if test="${!use}"> disabled="disabled"</c:if>
                            <c:if test="${use}"> checked="checked"</c:if>
                        />
                    </td>
                </tr>
                <tr>
                    <td><label for="<%= Configuration.USE_SITES %>">Sites</label></td>
                    <td class="text-right">
                        <c:set var="use" value="${jdbc.detectSites()}" />
                        <input type="checkbox" id="<%= Configuration.USE_SITES %>" name="<%= Configuration.USE_SITES %>"
                            value="ON" tabindex="7"
                            <c:if test="${!use}"> disabled="disabled"</c:if>
                            <c:if test="${use}"> checked="checked"</c:if>
                       />
                    </td>
                </tr>
            </tbody>
        </table>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-6">
            <input type="submit" name="next" value="Next &raquo;" tabindex="8" class="btn btn-primary" />
        </div>
    </div>
</form>
</c:if>
<jsp:include page="../WEB-INF/includes/footer.jsp" />