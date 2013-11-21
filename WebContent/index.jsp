<%@page import="org.informea.odata.util.ToolkitUtil"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.informea.odata.producer.toolkit.Configuration"%>
<%@page import="java.util.prefs.*"%>
<% Configuration cfg = Configuration.getInstance(); %>

    <jsp:include page="WEB-INF/includes/header.jsp">
        <jsp:param name="html_title" value="InforMEA OData provider" />
    </jsp:include>
    <div class="jumbotron">
        <h1>InforMEA toolkit</h1>
        <p class="lead">
            OData web service endpoint
        </p>
    </div>
    <div class="row marketing">
    <% if(!cfg.isInstalled()) { %>
        <div class="alert alert-warning">
            <h4>Service not configured</h4>
            <p>
                Your service is not yet configured.
            </p>
            <p>
                <span class="glyphicon glyphicon-exclamation-sign"></span>
                Configuration area is protected via HTTP Basic authentication, configured as following:
                <pre>
    $TOMCAT_HOME/conf/tomcat-users.xml, user with role 'informea'

    Enable MemoryRealm in $TOMCAT_HOME/conf/server.xml, 
    &lt;Realm className="org.apache.catalina.realm.MemoryRealm /&gt;
                </pre>
                <a target="_blank" href="http://tomcat.apache.org/tomcat-7.0-doc/realm-howto.html">Read more about Tomcat security configuration</a></li>
            </p>
            <p>
                <a href="<%= ToolkitUtil.url(request, "/configuration") %>" class="btn btn-primary">Configure</a>
            </p>
        </div>
    <% } else { %>
        <h2>Service status</h2>
        <jsp:include page="WEB-INF/includes/status.jsp" />
    <% } %>
    </div>
    <jsp:include page="WEB-INF/includes/footer.jsp" />