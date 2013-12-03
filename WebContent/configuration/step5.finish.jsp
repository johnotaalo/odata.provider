<%@page import="org.informea.odata.util.ToolkitUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.informea.odata.config.Configuration" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
Configuration cfg = Configuration.getInstance();

if(!"done".equals(session.getAttribute("step3.entities"))) {
    response.sendRedirect("index.jsp");
    return;
}

if(ToolkitUtil.isOnRequest("save", request)) {
    cfg.setInstalled(true);
    cfg.save();
    response.sendRedirect(request.getContextPath());
    return;
}
pageContext.setAttribute("cfg", cfg);
pageContext.setAttribute("ldap", cfg.getLDAPConfiguration());
%>
<jsp:include page="../WEB-INF/includes/header.jsp">
    <jsp:param name="html_title" value="Step 3. Finish" />
    <jsp:param name="current_menu_item" value="configuration" />
</jsp:include>

<ol class="breadcrumb">
    <li><a href="<%= ToolkitUtil.url(request, null) %>">Home</a></li>
    <li><a href="<%= ToolkitUtil.url(request, "/configuration") %>">Configuration</a></li>
    <li class="active">Done</li>
</ol>

<div class="progress progress-striped">
    <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
        <span class="sr-only">100% Complete</span>
    </div>
</div>

<h1>Success!</h1>
<p>
    Configuration is now finished. Please review the settings below and press <strong>Save</strong> button to finish configuration.
</p>
<h2>Configuration summary</h2>
<table class="table table-bordered table-striped">
    <thead>
        <tr>
            <th>Entity</th>
            <th>Exposed</th>
            <th>Data source</th>
            <th>Notes</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>Decisions</td>
            <td>
                <c:if test="${cfg.isUseDecisions()}">
                    <span class="glyphicon glyphicon-ok"></span>
                </c:if>
                <c:if test="${!cfg.isUseDecisions()}">
                    <span class="glyphicon glyphicon-remove"></span>
                </c:if>
            </td>
            <td>
                <c:if test="${cfg.isUseDecisions()}">SQL database</c:if>
                <c:if test="${!cfg.isUseDecisions()}">-</c:if>
            </td>
            <td>
                <c:if test="${cfg.isUsePathPrefix()}">
                    Using path prefix for documents: 
                    <strong><c:out value="${cfg.getPathPrefix()}" /></strong>
                </c:if>
            </td>
        </tr>
        <tr>
            <td>Meetings</td>
            <td>
                <c:if test="${cfg.isUseMeetings()}">
                    <span class="glyphicon glyphicon-ok"></span>
                </c:if>
                <c:if test="${!cfg.isUseMeetings()}">
                    <span class="glyphicon glyphicon-remove"></span>
                </c:if>
            </td>
            <td>
                <c:if test="${cfg.isUseMeetings()}">SQL database</c:if>
                <c:if test="${!cfg.isUseMeetings()}">-</c:if>
            </td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>Country Reports</td>
            <td>
                <c:if test="${cfg.isUseCountryReports()}">
                    <span class="glyphicon glyphicon-ok"></span>
                </c:if>
                <c:if test="${!cfg.isUseCountryReports()}">
                    <span class="glyphicon glyphicon-remove"></span>
                </c:if>
            </td>
            <td>
                <c:if test="${cfg.isUseCountryReports()}">SQL database</c:if>
                <c:if test="${!cfg.isUseCountryReports()}">-</c:if>
            </td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>Country Profiles</td>
            <td>
                <c:if test="${cfg.isUseCountryProfiles()}">
                    <span class="glyphicon glyphicon-ok"></span>
                </c:if>
                <c:if test="${!cfg.isUseCountryProfiles()}">
                    <span class="glyphicon glyphicon-remove"></span>
                </c:if>
            </td>
            <td>
                <c:if test="${cfg.isUseCountryProfiles()}">SQL database</c:if>
                <c:if test="${!cfg.isUseCountryProfiles()}">-</c:if>
            </td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>National Plans</td>
            <td>
                <c:if test="${cfg.isUseNationalPlans()}">
                    <span class="glyphicon glyphicon-ok"></span>
                </c:if>
                <c:if test="${!cfg.isUseNationalPlans()}">
                    <span class="glyphicon glyphicon-remove"></span>
                </c:if>
            </td>
            <td>
                <c:if test="${cfg.isUseNationalPlans()}">SQL database</c:if>
                <c:if test="${!cfg.isUseNationalPlans()}">-</c:if>
            </td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>Sites</td>
            <td>
                <c:if test="${cfg.isUseSites()}">
                    <span class="glyphicon glyphicon-ok"></span>
                </c:if>
                <c:if test="${!cfg.isUseSites()}">
                    <span class="glyphicon glyphicon-remove"></span>
                </c:if>
            </td>
            <td>
                <c:if test="${cfg.isUseSites()}">SQL database</c:if>
                <c:if test="${!cfg.isUseSites()}">-</c:if>
            </td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>Contacts</td>
            <td>
                <c:if test="${cfg.isUseContacts()}">
                    <span class="glyphicon glyphicon-ok"></span>
                </c:if>
                <c:if test="${!cfg.isUseContacts()}">
                    <span class="glyphicon glyphicon-remove"></span>
                </c:if>
            </td>
            <td>
                <c:if test="${cfg.isUseContacts()}">
                    <c:if test="${ldap == null}">
                        SQL database
                    </c:if>
                    <c:if test="${ldap != null}">
                        LDAP server
                    </c:if>
                </c:if>
                <c:if test="${!cfg.isUseContacts()}">-</c:if>
            </td>
            <td>&nbsp;</td>
        </tr>
    </tbody>
</table>
<hr />
<form action="" method="post">
    <input type="submit" class="btn btn-primary" name="save" id="save" value="Save">
</form>
<jsp:include page="../WEB-INF/includes/footer.jsp" />
