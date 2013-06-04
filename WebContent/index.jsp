<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.informea.odata.producer.toolkit.Configuration"%>
<%@page import="java.util.prefs.*"%>
<% Configuration cfg = Configuration.getInstance(); %>
<jsp:include page="WEB-INF/includes/header.jsp">
    <jsp:param name="html_title" value="InforMEA OData provider" />
</jsp:include>
<div class="content">
    <div class="left details-column-2">
        <h1>InforMEA OData provider</h1>
        <h2>Deployment</h2>
        This section will help you configure your OData source in order to expose it online.
    <% if(!cfg.isInstalled()) { %>
        <p class="error">
            It seems that your service is not yet configured. Please go to the <strong>Configuration</strong> tab above and perform the necessary steps to set it up.
            <br />
            <jsp:include page="WEB-INF/includes/warning.jsp" />
        </p>
    <% } else { %>
        <p>
            <br />
            <span class="success">
                Your service is configured. Please go to the <strong>Status</strong> tab to check the configuration. You can try to reconfigure from the <strong>Configuration</strong> tab.
            </span>
            <br />
        </p>
    <% } %>
        <h2>Introduction</h2>
        The InforMEA OData provider helps MEAs expose their data to the other third parties through the Open Data protocol (<a target="_blank" href="http://www.odata.org">http://www.odata.org</a>).
        <br />
        The provider is built around the concept of exposing the data stored inside a database (MySQL is currently supported, altough project can be easily extended to work with any JDBC-enabled databases would work).
        <br />
        <br />
        <h2>References</h2>
        For further references, you can check out the following resources:
        <ul class="full">
            <li>
                <a target="_blank" href="http://www.informea.org/api">InforMEA specifications</a> - describes the service and the each of the exposed entities. <em>Always look for the latest version</em>.
            </li>
            <li>
                <a target="_blank" href="http://www.hibernate.org/">Hibernate framework</a> - provides the database access layer
            </li>
            <li>
                <a target="_blank" href="http://code.google.com/p/odata4j/">OData4J</a> - framework that exposes entities as OData service (svc, servlet, XML encoding/decoding)
            </li>
            <li>
                <a target="_blank" href="http://informea.org/">InforMEA portal</a> - You can contact us for support
            </li>
        </ul>
        <div class="clear"></div>
        <br />
        <br />
        <% out.flush(); %>
        <jsp:include page="WEB-INF/includes/update.jsp" />
        <br />
        <br />
    </div>
</div>
<jsp:include page="WEB-INF/includes/footer.jsp" />