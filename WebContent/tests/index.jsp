<%@page import="org.informea.odata.util.ToolkitUtil"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Map<String, String> breadcrumbtrail = new LinkedHashMap<String, String>();
    breadcrumbtrail.put("Home", ToolkitUtil.url(request, "/"));
    breadcrumbtrail.put("Tests", "");
    request.setAttribute("breadcrumbtrail", breadcrumbtrail);
%>
<jsp:include page="../WEB-INF/includes/header.jsp">
    <jsp:param name="html_title" value="Regression testing" />
    <jsp:param name="current_menu_item" value="tests" />
</jsp:include>
        <h1>Regression testing</h1>
        From this page you can run the regression testing for the web application in order to ensure everything is correct.
        <p>
            Before you proceed, make sure you have installed the correct testing data set, used for testing.
            <br />
            <strong>Please download and install the following MySQL script from <a href="tests.sql">here</a></strong>.
            <br />
            <em>
                Note: This script will create the required informea_* tables inside the database. If you already have other tables or views created with same name it will fail.
                <br />
                The following tables will be created:
                <ol>
                    <li>informea_contacts</li>
                    <li>informea_contacts_treaties</li>
                    <li>informea_country_profiles</li>
                    <li>informea_country_reports</li>
                    <li>informea_country_reports_title</li>
                    <li>informea_decisions</li>
                    <li>informea_decisions_content</li>
                    <li>informea_decisions_documents</li>
                    <li>informea_decisions_keywords</li>
                    <li>informea_decisions_longtitle</li>
                    <li>informea_decisions_summary</li>
                    <li>informea_decisions_title</li>
                    <li>informea_meetings</li>
                    <li>informea_meetings_description</li>
                    <li>informea_meetings_title</li>
                    <li>informea_national_plans</li>
                    <li>informea_national_plans_title</li>
                    <li>informea_sites</li>
                </ol>
            </em>
        </p>
        <form action="">
            <input type="submit" name="run" value="Run the tests" />
        </form>
        <%
            String run = request.getParameter("run");
            if(run != null) {
        %>
        <jsp:include page="../WEB-INF/includes/tests.functional.jsp" />
        <%
            }
        %>
        <br />
        <br />
<jsp:include page="../WEB-INF/includes/footer.jsp" />
