<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.informea.odata.producer.toolkit.Configuration" %>
<%
    // If user drops to this page and setup is not configured, just redirect to start
    if(session.getAttribute(Configuration.DB_TYPE) == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    Configuration cfg = Configuration.getInstance();
    cfg.putFromSession(session);
    cfg.setInstalled(true);

    boolean validDecisions = false;
    boolean validMeetings = false;
    boolean validContacts = false;
    boolean validCountryReports = false;
    boolean validCountryProfiles = false;
    boolean validNationalPlans = false;
    boolean validSites = false;
    boolean validProjects = false;
    boolean validBestPractices = false;
    boolean validTechnicalReports = false;

%>
<jsp:include page="../WEB-INF/includes/header.jsp">
    <jsp:param name="html_title" value="Step 3. Finish" />
    <jsp:param name="current_menu_item" value="configuration" />
</jsp:include>

<h1>Step 3. Finish</h1>
<p>
	Your server was successfully configured!
	
	<form method="POST" action="../">
		<input type="submit" name="finish" value="Finish" />
	</form>
</p>

<br />
<br />
<jsp:include page="../WEB-INF/includes/footer.jsp" />
