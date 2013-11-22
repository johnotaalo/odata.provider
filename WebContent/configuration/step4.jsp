<%@page import="org.informea.odata.util.ToolkitUtil"%>
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
    The toolkit is now configured. You can check the status by pressing the <strong>Finish</strong> button below.
</p>
<p>
    <a class="btn btn-primary" href="<%= ToolkitUtil.url(request, null) %>">Finish</a>
</p>
<jsp:include page="../WEB-INF/includes/footer.jsp" />
