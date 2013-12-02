<%@page import="org.informea.odata.config.LDAPConfiguration"%>
<%@page import="org.informea.odata.config.DatabaseConfiguration"%>
<%@page import="org.informea.odata.constants.EntityType"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.informea.odata.util.ToolkitUtil" %>
<%@page import="org.informea.odata.config.Configuration" %>
<%@page import="org.informea.odata.util.JDBCHelper"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
// If user drops to this page and setup is not configured, just redirect to start
if(!"done".equals(session.getAttribute("step2.ldap"))) {
    response.sendRedirect("index.jsp");
    return;
}

DatabaseConfiguration db = (DatabaseConfiguration)session.getAttribute("db");
LDAPConfiguration ldap = (LDAPConfiguration)session.getAttribute("ldap");
Configuration cfg = Configuration.getInstance();

boolean next = ToolkitUtil.isOnRequest("next", request);
boolean validSelection = false;
if(next) {
    validSelection = ToolkitUtil.isValidEntitiesSelection(request);
    if(validSelection) {
        cfg.loadFromRequest(request);
        if(cfg.isUseDecisions()) {
            response.sendRedirect("step3.jsp");
        } else {
            response.sendRedirect("step4.jsp");
        }
        return;
    }
}

if(db != null) {
    JDBCHelper jdbc = new JDBCHelper(db);
    pageContext.setAttribute("useDecisions", jdbc.detectDecisions());
    pageContext.setAttribute("useMeetings", jdbc.detectMeetings());
    pageContext.setAttribute("useCountryReports", jdbc.detectCountryReports());
    pageContext.setAttribute("useCountryProfiles", jdbc.detectCountryProfiles());
    pageContext.setAttribute("useNationalPlans", jdbc.detectNationalPlans());
    pageContext.setAttribute("useSites", jdbc.detectSites());
    pageContext.setAttribute("useContacts", jdbc.detectContacts());
}
if(ldap != null) {
    pageContext.setAttribute("useContacts", ldap != null);
}
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

<p>
    Please check all the entities that you want to expose via this service.
    <br />
    Setup has detected automatically all entities according to your previous steps
    and <strong>disabled</strong> those not found.
    <br />
    If nothing is selectable, go back and review your settings and/or database views/structure.
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
                        <label for="useDecisions">Decisions</label>
                    </td>
                    <td class="text-right">
                        <input type="checkbox" id="useDecisions" name="useDecisions"
                            value="ON" tabindex="1"
                            <c:if test="${!useDecisions}"> disabled="disabled"</c:if>
                            <c:if test="${useDecisions}"> checked="checked"</c:if>
                        />
                    </td>
                </tr>
                <tr>
                    <td><label for="useMeetings">Meetings</label></td>
                    <td class="text-right">
                        <input type="checkbox" id="useMeetings" name="useMeetings"
                            value="ON" tabindex="2"
                            <c:if test="${!useMeetings}"> disabled="disabled"</c:if>
                            <c:if test="${useMeetings}"> checked="checked"</c:if>
                        />
                    </td>
                </tr>
                <tr>
                    <td><label for="useContacts">Contacts</label></td>
                    <td class="text-right">
                        <input type="checkbox" id="useContacts" name="useContacts"
                            value="ON" tabindex="3"
                            <c:if test="${!useContacts}"> disabled="disabled"</c:if>
                            <c:if test="${useContacts}"> checked="checked"</c:if>
                        />
                    </td>
                </tr>
                <tr>
                    <td><label for="useCountryReports">Country reports</label></td>
                    <td class="text-right">
                        <input type="checkbox" id="useCountryReports" name="useCountryReports"
                        value="ON" tabindex="4"
                            <c:if test="${!useCountryReports}"> disabled="disabled"</c:if>
                            <c:if test="${useCountryReports}"> checked="checked"</c:if>
                        />
                    </td>
                </tr>
                <tr>
                    <td><label for="useCountryProfiles">Country profiles</label></td>
                    <td class="text-right">
                        <input type="checkbox" id="useCountryProfiles" name="useCountryProfiles"
                            value="ON" tabindex="5"
                            <c:if test="${!useCountryProfiles}"> disabled="disabled"</c:if>
                            <c:if test="${useCountryProfiles}"> checked="checked"</c:if>
                        />
                    </td>
                </tr>
                <tr>
                    <td><label for="useNationalPlans">National plans</label></td>
                    <td class="text-right">
                        <input type="checkbox" id="useNationalPlans" name="useNationalPlans"
                            value="ON" tabindex="6"
                            <c:if test="${!useNationalPlans}"> disabled="disabled"</c:if>
                            <c:if test="${useNationalPlans}"> checked="checked"</c:if>
                        />
                    </td>
                </tr>
                <tr>
                    <td><label for="useSites">Sites</label></td>
                    <td class="text-right">
                        <input type="checkbox" id="useSites" name="useSites"
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
<p>
    <span class="label label-info">Contacts usage</span> If you have both database and LDAP contacts configured, only LDAP will be used.
</p>
<jsp:include page="../WEB-INF/includes/footer.jsp" />