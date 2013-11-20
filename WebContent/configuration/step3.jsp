<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.informea.odata.util.ToolkitUtil" %>
<%@page import="org.informea.odata.producer.toolkit.Configuration" %>
<%@page import="org.informea.odata.util.JDBCHelper"%>
<%
    Configuration cfg = Configuration.getInstance();
    // If user drops to this page and setup is not configured, just redirect to start
    if(session.getAttribute(Configuration.DB_TYPE) == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    boolean next = ToolkitUtil.isOnRequest("next", request);
    if(next) {
        cfg.setUsePathPrefix(ToolkitUtil.getRequestCheckbox(Configuration.USE_PATH_PREFIX, request));
        cfg.setPathPrefix(ToolkitUtil.getRequestValue(Configuration.PATH_PREFIX, request));

        response.sendRedirect("step4.jsp");
    }
    pageContext.setAttribute("cfg", cfg);

%>
<jsp:include page="../WEB-INF/includes/header.jsp">
    <jsp:param name="html_title" value="Configure decisions' documents location on server disk" />
    <jsp:param name="current_menu_item" value="configuration" />
</jsp:include>

<ol class="breadcrumb">
    <li><a href="<%= ToolkitUtil.url(request, null) %>">Home</a></li>
    <li><a href="<%= ToolkitUtil.url(request, "/configuration") %>">Configuration</a></li>
    <li class="active">Decision files</li>
</ol>

<div class="progress progress-striped">
    <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100" style="width: 75%">
        <span class="sr-only">75% Complete</span>
    </div>
</div>

<h1>Decisions files</h1>

<p>
    Please use the form below to determine how the toolkit will locate documents on the server's disk for each decision.
</p>
<p>
    You have two options:
    <ol>
        <li>
            Documents contain absolute path when stored inside the view <code>informea_decision_documents</code>.
            In this case just click <strong>Next</strong>.
        </li>
        <li>
            <p>
            Documents are stored with a relative path. Enter the base path (used as prefix) and the toolkit will 
            take care of creating the absolute paths.
            </p>
            <h3>Example</h3>
            <table class="table">
                <tr>
                    <th>decision_id</th>
                    <th>diskPath</th>
                    <th>filename</th>
                </tr>
                <tr>
                    <td>1</td>
                    <td>dec-01</td>
                    <td><strong>uploads/COP1/res1.pdf</strong></td>
                </tr>
            </table>

            In this case we would need to set the path prefix to something like 
            <code>/var/www/mywebsite</code>, so the final path for the file will look like this:
            <pre>/var/www/mywebsite/uploads/COP1/res1.pdf</pre>
            The toolkit will append automatically <code>/</code> between the paths.
        </li>
    </ol>
</p>

<form action="" method="post" class="form-horizontal">
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <div class="checkbox">
                <label>
                    <input type="checkbox" id="<%= Configuration.USE_PATH_PREFIX %>" name="<%= Configuration.USE_PATH_PREFIX %>"
                    <% if(cfg.isUsePathPrefix()) { %> checked="checked"<% } %> value="ON" tabindex="1" />
                    <strong>Append the prefix path below</strong>
                </label>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label for="<%= Configuration.PATH_PREFIX %>" class="col-sm-2 control-label">Prefix</label>
        <div class="col-sm-10">
            <input type="text" class="form-control"
                id="<%= Configuration.PATH_PREFIX %>" name="<%= Configuration.PATH_PREFIX %>"
                   <% if(cfg.isUsePathPrefix()) { %> value="<%= cfg.getPathPrefix() %>"<% } %>
                   size="60" tabindex="2" />
               </div>
    </div>
    <div class="form-group">
        <div class="col-sm-10">
            <input type="submit" name="next" value="Next &raquo;" tabindex="3" class="btn btn-primary" />
        </div>
    </div>
</form>

<jsp:include page="../WEB-INF/includes/footer.jsp" />
