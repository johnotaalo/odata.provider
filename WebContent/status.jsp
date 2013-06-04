<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="org.informea.odata.producer.toolkit.impl.PebldsProject"%>
<%@page import="org.informea.odata.producer.toolkit.impl.PebldsTechnicalReport"%>
<%@page import="org.informea.odata.producer.toolkit.impl.PebldsBestPractice"%>
<%@page import="org.informea.odata.producer.toolkit.IDataProvider"%>
<%@page import="org.informea.odata.producer.toolkit.impl.DatabaseDataProvider"%>
<%@page import="org.informea.odata.producer.toolkit.Configuration"%>
<%@page import="org.informea.odata.producer.toolkit.Producer"%>
<%@page import="org.informea.odata.util.ToolkitUtil"%>
<%
    String endpoint = "services/odata.svc";
    Configuration cfg = Configuration.getInstance();
    IDataProvider dp = null;
    try {
        dp = new DatabaseDataProvider();
        dp.openResources();
    } catch(Exception ex) {
        ex.printStackTrace();
    }
    pageContext.setAttribute("cfg", cfg);
%>
<jsp:include page="WEB-INF/includes/header.jsp">
    <jsp:param name="html_title" value="InforMEA service status" />
    <jsp:param name="current_menu_item" value="status" />
</jsp:include>
<div class="content">
    <h1>Service status</h1>
    <% if(!cfg.isInstalled()) { %>
        <p class="error">
            Service is not configured. There is nothing to see here.
            <br />
            <br />
            <br />
        </p>
    <%
        } else {
            Producer p = new Producer();
    %>
        <p>
            OData service enpoint - <a href="services/odata.svc">Browse</a>
        </p>
        <p>
            Service metadata - <a href="services/odata.svc/$metadata">See metadata</a>
        </p>
        <table border="1">
            <thead>
                <tr>
                    <th>Resource</th>
                    <th>Enabled</th>
                    <th>Items count</th>
                    <th>Endpoint URL</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Decisions</td>
                    <td>
                        <% if(cfg.isUseDecisions()) { %>
                            <div class="icon-blue-checkbox"><img src="<%= ToolkitUtil.url(request, "/themes/images/pixel.gif") %>" title="True" /></div>
                        <% } else { %>
                            <div class="icon-red-x"><img src="<%= ToolkitUtil.url(request, "/themes/images/pixel.gif") %>" title="True" /></div>
                        <% } %>
                    </td>
                    <td class="tright"><%= (cfg.isUseDecisions()) ? p.getDecisionsCount(dp, null) : "-" %></td>
                    <td><% if(cfg.isUseDecisions()) { %><a href="<%= endpoint %>/Decisions">Browse</a><% } else { %> - <% } %></td>
                </tr>
                <tr>
                    <td>Meetings</td>
                    <td>
                        <% if(cfg.isUseMeetings()) { %>
                            <div class="icon-blue-checkbox"><img src="<%= ToolkitUtil.url(request, "/themes/images/pixel.gif") %>" title="True" /></div>
                        <% } else { %>
                            <div class="icon-red-x"><img src="<%= ToolkitUtil.url(request, "/themes/images/pixel.gif") %>" title="True" /></div>
                        <% } %>
                    </td>
                    <td class="tright"><%= (cfg.isUseMeetings()) ? p.getMeetingsCount(dp, null) : "-" %></td>
                    <td><% if(cfg.isUseMeetings()) { %><a href="<%= endpoint %>/Meetings">Browse</a><% } else { %> - <% } %></td>
                </tr>
                <tr>
                    <td>Contacts</td>
                    <td>
                        <% if(cfg.isUseContacts()) { %>
                            <div class="icon-blue-checkbox"><img src="<%= ToolkitUtil.url(request, "/themes/images/pixel.gif") %>" title="True" /></div>
                        <% } else { %>
                            <div class="icon-red-x"><img src="<%= ToolkitUtil.url(request, "/themes/images/pixel.gif") %>" title="True" /></div>
                        <% } %>
                    </td>
                    <td class="tright"><%= (cfg.isUseContacts()) ? p.getContactsCount(dp, null) : "-" %></td>
                    <td><% if(cfg.isUseContacts()) { %><a href="<%= endpoint %>/Contacts">Browse</a><% } else { %> - <% } %></td>
                </tr>
                <tr>
                    <td>Country reports</td>
                    <td>
                        <% if(cfg.isUseCountryReports()) { %>
                            <div class="icon-blue-checkbox"><img src="<%= ToolkitUtil.url(request, "/themes/images/pixel.gif") %>" title="True" /></div>
                        <% } else { %>
                            <div class="icon-red-x"><img src="<%= ToolkitUtil.url(request, "/themes/images/pixel.gif") %>" title="True" /></div>
                        <% } %>
                    </td>
                    <td class="tright"><%= (cfg.isUseCountryReports()) ? p.getCountryReportsCount(dp, null) : "-" %></td>
                    <td><% if(cfg.isUseCountryReports()) { %><a href="<%= endpoint %>/CountryReports">Browse</a><% } else { %> - <% } %></td>
                </tr>
                <tr>
                    <td>Country profiles</td>
                    <td>
                        <% if(cfg.isUseCountryProfiles()) { %>
                            <div class="icon-blue-checkbox"><img src="<%= ToolkitUtil.url(request, "/themes/images/pixel.gif") %>" title="True" /></div>
                        <% } else { %>
                            <div class="icon-red-x"><img src="<%= ToolkitUtil.url(request, "/themes/images/pixel.gif") %>" title="True" /></div>
                        <% } %>
                    </td>
                    <td class="tright"><%= (cfg.isUseCountryProfiles()) ? p.getCountryProfilesCount(dp, null) : "-" %></td>
                    <td><% if(cfg.isUseCountryProfiles()) { %><a href="<%= endpoint %>/CountryProfiles">Browse</a><% } else { %> - <% } %></td>
                </tr>
                <tr>
                    <td>National Plans</td>
                    <td>
                        <% if(cfg.isUseNationalPlans()) { %>
                            <div class="icon-blue-checkbox"><img src="<%= ToolkitUtil.url(request, "/themes/images/pixel.gif") %>" title="True" /></div>
                        <% } else { %>
                            <div class="icon-red-x"><img src="<%= ToolkitUtil.url(request, "/themes/images/pixel.gif") %>" title="True" /></div>
                        <% } %>
                    </td>
                    <td class="tright"><%= (cfg.isUseNationalPlans()) ? p.getNationalPlansCount(dp, null) : "-" %></td>
                    <td><% if(cfg.isUseNationalPlans()) { %><a href="<%= endpoint %>/NationalPlans">Browse</a><% } else { %> - <% } %></td>
                </tr>
                <tr>
                    <td>Sites</td>
                    <td>
                        <% if(cfg.isUseSites()) { %>
                            <div class="icon-blue-checkbox"><img src="<%= ToolkitUtil.url(request, "/themes/images/pixel.gif") %>" title="True" /></div>
                        <% } else { %>
                            <div class="icon-red-x"><img src="<%= ToolkitUtil.url(request, "/themes/images/pixel.gif") %>" title="True" /></div>
                        <% } %>
                    </td>
                    <td class="tright"><%= (cfg.isUseSites()) ? p.getSitesCount(dp, null) : "-" %></td>
                    <td><% if(cfg.isUseSites()) { %><a href="<%= endpoint %>/Sites">Browse</a><% } else { %> - <% } %></td>
                </tr>
                <tr>
                    <td>Projects</td>
                    <td>
                        <% if(cfg.isUseProjects()) { %>
                            <div class="icon-blue-checkbox"><img src="<%= ToolkitUtil.url(request, "/themes/images/pixel.gif") %>" title="True" /></div>
                        <% } else { %>
                            <div class="icon-red-x"><img src="<%= ToolkitUtil.url(request, "/themes/images/pixel.gif") %>" title="True" /></div>
                        <% } %>
                    </td>
                    <td class="tright"><%= (cfg.isUseProjects()) ? p.getEntityCount(dp, null, PebldsProject.class) : "-" %></td>
                    <td><% if(cfg.isUseProjects()) { %><a href="<%= endpoint %>/PebldsProject">Browse</a><% } else { %> - <% } %></td>
                </tr>
                <tr>
                    <td>Best practices</td>
                    <td>
                        <% if(cfg.isUseBestPractices()) { %>
                            <div class="icon-blue-checkbox"><img src="<%= ToolkitUtil.url(request, "/themes/images/pixel.gif") %>" title="True" /></div>
                        <% } else { %>
                            <div class="icon-red-x"><img src="<%= ToolkitUtil.url(request, "/themes/images/pixel.gif") %>" title="True" /></div>
                        <% } %>
                    </td>
                    <td class="tright"><%= (cfg.isUseBestPractices()) ? p.getEntityCount(dp, null, PebldsBestPractice.class) : "-" %></td>
                    <td><% if(cfg.isUseBestPractices()) { %><a href="<%= endpoint %>/PebldsBestPractices">Browse</a><% } else { %> - <% } %></td>
                </tr>
                <tr>
                    <td>Technical reports</td>
                    <td>
                        <% if(cfg.isUseTechnicalReports()) { %>
                            <div class="icon-blue-checkbox"><img src="<%= ToolkitUtil.url(request, "/themes/images/pixel.gif") %>" title="True" /></div>
                        <% } else { %>
                            <div class="icon-red-x"><img src="<%= ToolkitUtil.url(request, "/themes/images/pixel.gif") %>" title="True" /></div>
                        <% } %>
                    </td>
                    <td class="tright"><%= (cfg.isUseTechnicalReports()) ? p.getEntityCount(dp, null, PebldsTechnicalReport.class) : "-" %></td>
                    <td><% if(cfg.isUseTechnicalReports()) { %><a href="<%= endpoint %>/PebldsTechnicalReport">Browse</a><% } else { %> - <% } %></td>
                </tr>
            </tbody>
        </table>
        <c:if test="${!empty pageContext.request.userPrincipal}">
        <h2>Configuration</h2>
            Change service parameters - Go to service <a href="configuration/">configuration</a>.
        </c:if>
        <h2>Documentation</h2>
        <ol>
            <li><a href="http://www.odata.org/">www.odata.org</a> - Which contain resources on how to access an OData endpoint from different programming languages</li>
            <li><a href="http://informea.org/wp-content/uploads/api/javadoc/latest/">JavaDoc API</a> - documentation for the API classes and interfaces</li>
        </ol>
    <% } %>
</div>
<jsp:include page="WEB-INF/includes/footer.jsp" />
<%
    dp.closeResources();
%>