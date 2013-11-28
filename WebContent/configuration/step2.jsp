<%@page import="org.informea.odata.constants.EntityType"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.informea.odata.util.ToolkitUtil" %>
<%@page import="org.informea.odata.config.Configuration" %>
<%@page import="org.informea.odata.util.JDBCHelper"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
// If user drops to this page and setup is not configured, just redirect to start
if(session.getAttribute(Configuration.DB_TYPE) == null) {
    response.sendRedirect("index.jsp");
    return;
}
boolean next = ToolkitUtil.isOnRequest("next", request);
boolean validSelection = false;
if(next) {
    validSelection = ToolkitUtil.isValidEntitiesSelection(request);
    if(validSelection) {
        ToolkitUtil.saveEntitiesSelectionOnSession(session, request);
        boolean useDecisions = ToolkitUtil.getRequestCheckbox(Configuration.USE_DECISIONS, request);
        if(useDecisions) {
            response.sendRedirect("step3.jsp");
        } else {
            response.sendRedirect("step4.jsp");
        }
        return;
    }
}
JDBCHelper jdbc = ToolkitUtil.createJDBCHelperFromSession(session);
pageContext.setAttribute("jdbc", jdbc);
pageContext.setAttribute("allMissing", jdbc.isMissingAllEntities());
pageContext.setAttribute("useDecisions", jdbc.detectDecisions());
pageContext.setAttribute("useMeetings", jdbc.detectMeetings());
pageContext.setAttribute("useContacts", jdbc.detectContacts());
pageContext.setAttribute("useCountryReports", jdbc.detectCountryReports());
pageContext.setAttribute("useCountryProfiles", jdbc.detectCountryProfiles());
pageContext.setAttribute("useNationalPlans", jdbc.detectNationalPlans());
pageContext.setAttribute("useSites", jdbc.detectSites());
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
<% if(next && !validSelection) { %>
    <div class="alert alert-danger">
        <h4>Invalid selection</h4>
        <p>
            Hmmm, I wonder what's the purpose of an empty service?
        </p>
    </div>
<% } %>

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
<p>
    Please check all the entities that you want to expose via this service. By default this setup has checked all the
    entities detected inside the database and disabled the checkboxes for those that do not exist.
</p>
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
                        <input type="checkbox" id="<%= Configuration.USE_DECISIONS %>" name="<%= Configuration.USE_DECISIONS %>"
                            value="ON" tabindex="1"
                            <c:if test="${!useDecisions}"> disabled="disabled"</c:if>
                            <c:if test="${useDecisions}"> checked="checked"</c:if>
                        />
                    </td>
                </tr>
                <tr>
                    <td><label for="<%= Configuration.USE_MEETINGS %>">Meetings</label></td>
                    <td class="text-right">
                        <input type="checkbox" id="<%= Configuration.USE_MEETINGS %>" name="<%= Configuration.USE_MEETINGS %>"
                            value="ON" tabindex="2"
                            <c:if test="${!useMeetings}"> disabled="disabled"</c:if>
                            <c:if test="${useMeetings}"> checked="checked"</c:if>
                        />
                    </td>
                </tr>
                <tr>
                    <td><label for="<%= Configuration.USE_CONTACTS %>">Contacts</label></td>
                    <td class="text-right">
                        <input type="checkbox" id="<%= Configuration.USE_CONTACTS %>" name="<%= Configuration.USE_CONTACTS %>"
                            value="ON" tabindex="3"
                            <c:if test="${!useContacts}"> disabled="disabled"</c:if>
                            <c:if test="${useContacts}"> checked="checked"</c:if>
                        />
                    </td>
                </tr>
                <tr>
                    <td><label for="<%= Configuration.USE_COUNTRY_REPORTS %>">Country reports</label></td>
                    <td class="text-right">
                        <input type="checkbox" id="<%= Configuration.USE_COUNTRY_REPORTS %>" name="<%= Configuration.USE_COUNTRY_REPORTS %>"
                        value="ON" tabindex="4"
                            <c:if test="${!useCountryReports}"> disabled="disabled"</c:if>
                            <c:if test="${useCountryReports}"> checked="checked"</c:if>
                        />
                    </td>
                </tr>
                <tr>
                    <td><label for="<%= Configuration.USE_COUNTRY_PROFILES %>">Country profiles</label></td>
                    <td class="text-right">
                        <input type="checkbox" id="<%= Configuration.USE_COUNTRY_PROFILES %>" name="<%= Configuration.USE_COUNTRY_PROFILES %>"
                            value="ON" tabindex="5"
                            <c:if test="${!useCountryProfiles}"> disabled="disabled"</c:if>
                            <c:if test="${useCountryProfiles}"> checked="checked"</c:if>
                        />
                    </td>
                </tr>
                <tr>
                    <td><label for="<%= Configuration.USE_NATIONAL_PLANS %>">National plans</label></td>
                    <td class="text-right">
                        <input type="checkbox" id="<%= Configuration.USE_NATIONAL_PLANS %>" name="<%= Configuration.USE_NATIONAL_PLANS %>"
                            value="ON" tabindex="6"
                            <c:if test="${!useNationalPlans}"> disabled="disabled"</c:if>
                            <c:if test="${useNationalPlans}"> checked="checked"</c:if>
                        />
                    </td>
                </tr>
                <tr>
                    <td><label for="<%= Configuration.USE_SITES %>">Sites</label></td>
                    <td class="text-right">
                        <input type="checkbox" id="<%= Configuration.USE_SITES %>" name="<%= Configuration.USE_SITES %>"
                            value="ON" tabindex="7"
                            <c:if test="${!useSites}"> disabled="disabled"</c:if>
                            <c:if test="${useSites}"> checked="checked"</c:if>
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