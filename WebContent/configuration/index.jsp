<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.informea.odata.util.ToolkitUtil"%>
<%@page import="org.informea.odata.config.Configuration" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
Configuration cfg = Configuration.getInstance();
pageContext.setAttribute("cfg", cfg);
pageContext.setAttribute("isInstalled", cfg.isInstalled());

// Reset the session
request.getSession().removeAttribute("db");
request.getSession().removeAttribute("ldap");
request.getSession().removeAttribute("cfg");
%>
<jsp:include page="../WEB-INF/includes/header.jsp">
    <jsp:param name="html_title" value="InforMEA provider configuration" />
    <jsp:param name="current_menu_item" value="configuration" />
</jsp:include>

<div class="content">
    <ol class="breadcrumb">
        <li><a href="<%= ToolkitUtil.url(request, null) %>">Home</a></li>
        <li class="active">Configuration</li>
    </ol>

    <h1>Toolkit configuration</h1>
    <c:if test="${isInstalled}">
        <div class="alert alert-warning">
            <h4>STOP!</h4>
            <br />
            The toolkit is already configured. Are you sure you want to <em>reconfigure</em>?
            <br /><br />
            <a class="btn btn-primary" href="database" tabindex="1">Reconfigure</a>
         </div>
    </c:if>
    <c:if test="${!isInstalled}">
        <div class="alert alert-info">
            <h4>Information</h4>
            Your provider is not yet configured. From this page you can configure the provider to suit your installation.
            <br />
            <h5>Prerequisites</h5>
            To successfully configure the service there are few things to know beforehand. Please reffer to checklist below:
            <ul class="full">
                <li>DB connection information (<em>server address, TCP/IP port, username, password, database</em>);</li>
                <li>Entities you want to expose to outside world (such as <em>Contacts, Decisions</em>);</li>
                <li>
                    Database views/tables created for the exposed entities. <strong>Read below</strong>!
                </li>
            </ul>
            <br />
            <a class="btn btn-primary" href="database" tabindex="1">Proceed</a>
        </div>
    </c:if>
    <jsp:include page="../WEB-INF/includes/documentation.jsp" />
</div>
<jsp:include page="../WEB-INF/includes/footer.jsp" />