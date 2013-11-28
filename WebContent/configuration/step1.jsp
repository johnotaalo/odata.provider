<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.informea.odata.Configuration"%>
<%@page import="org.informea.odata.producer.toolkit.HibernateConfigurator"%>
<%@page import="org.informea.odata.util.ToolkitUtil" %>
<%@page import="org.informea.odata.util.JDBCHelper"%>
<%
Configuration cfg = Configuration.getInstance();

String db_type = ToolkitUtil.getRequestValue(Configuration.DB_TYPE, request);
String db_host = ToolkitUtil.getRequestValue(Configuration.DB_HOST, request);
int db_port = ToolkitUtil.getRequestInteger(Configuration.DB_PORT, request);
String db_user = ToolkitUtil.getRequestValue(Configuration.DB_USER, request);
String db_pass = ToolkitUtil.getRequestValue(Configuration.DB_PASS, request);
String db_database = ToolkitUtil.getRequestValue(Configuration.DB_DATABASE, request);

boolean verify = ToolkitUtil.isOnRequest("verify", request);
Exception e = null;
boolean validConnection = false;
if(verify) {
    try {
        JDBCHelper jdbc = new JDBCHelper(db_type, db_host, db_port, db_user, db_pass, db_database);
        validConnection = jdbc.validateDBConnection();
        if(validConnection) {
    out.println(jdbc.validateDBConnection());
    session.setAttribute(Configuration.DB_TYPE, new String(db_type));
    session.setAttribute(Configuration.DB_HOST, new String(db_host));
    session.setAttribute(Configuration.DB_PORT, new Integer(db_port));
    session.setAttribute(Configuration.DB_USER, db_user);
    session.setAttribute(Configuration.DB_PASS, db_pass);
    session.setAttribute(Configuration.DB_DATABASE, db_database);
    response.sendRedirect("step2.jsp");
    return;
        }
    } catch(Exception ex) {
        e = ex;
    }
} else {
    if(cfg.isInstalled()) {
        db_type = cfg.getString(Configuration.DB_TYPE);
        db_host = cfg.getString(Configuration.DB_HOST);
        db_port = cfg.getInt(Configuration.DB_PORT);
        db_user = cfg.getString(Configuration.DB_USER);
        db_pass = cfg.getString(Configuration.DB_PASS);
        db_database = cfg.getString(Configuration.DB_DATABASE);
    }
}
if(db_user == null) { db_user = ""; }
if(db_database == null) { db_database = ""; }
if(db_type == null) { db_type = "mysql"; } // @todo: We only have mysql now, remove when multiple databases are available

String str_db_port = "";
if(db_port != 0) {
    str_db_port = "" + db_port;
}
%>
<jsp:include page="../WEB-INF/includes/header.jsp">
    <jsp:param name="html_title" value="Database configuration" />
    <jsp:param name="current_menu_item" value="configuration" />
</jsp:include>
<script type="text/javascript">
    function validateOnSubmit() {
        var dbTypeCtrl = document.getElementById('<%= Configuration.DB_TYPE %>');
        if(dbTypeCtrl.selectedIndex == 0) {
            alert("Please select the database type you want to use");
            return false;
        }
        return true;
    }
</script>
<div class="content">
    <ol class="breadcrumb">
        <li><a href="<%= ToolkitUtil.url(request, null) %>">Home</a></li>
        <li><a href="<%= ToolkitUtil.url(request, "/configuration") %>">Configuration</a></li>
        <li class="active">Database</li>
    </ol>

    <div class="progress progress-striped">
        <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width: 20%">
            <span class="sr-only">20% Complete</span>
        </div>
    </div>

    <h1>Database configuration</h1>

    <% if(verify && !validConnection) { %>
    <div class="alert alert-danger">
        <h4>Error validating the data</h4>
        <% if(e != null) { %>
            <%= e.getMessage() %>
        <% } %>
        <p>
            Please review the settings and try again.
        </p>
    </div>
    <% } %>

    <% if(HibernateConfigurator.getInstance().isConfigured()) { %>
    <div class="alert alert-danger">
        <h4>Warning</h4>
        <p>
            Database is already configured, <strong>restart the servlet container when done with the configuration</strong>!
        </p>
    </div>
    <% } %>

    <form action="" method="post" onsubmit="return validateOnSubmit();" role="form" class="form-horizontal">
        <div class="form-group">
            <label for="<%= Configuration.DB_TYPE %>" class="col-sm-3 control-label">Database type</label>
            <div class="col-sm-4">
                <select id="<%= Configuration.DB_TYPE %>" name="<%= Configuration.DB_TYPE %>" tabindex="1" class="form-control">
                    <option value=""<%= ("".equals(db_type)) ? " selected=\"selected\"" : "" %>>-- Please select --</option>
                    <option value="<%= JDBCHelper.DB_TYPE_MYSQL %>"<%= (JDBCHelper.DB_TYPE_MYSQL.equals(db_type)) ? " selected=\"selected\"" : "" %>>MySQL</option>
                    <option value="<%= JDBCHelper.DB_TYPE_POSTGRESQL %>"<%= (JDBCHelper.DB_TYPE_POSTGRESQL.equals(db_type)) ? " selected=\"selected\"" : "" %>>PostgreSQL</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label for="<%= Configuration.DB_HOST %>" class="col-sm-3 control-label">Server address</label>
            <div class="col-sm-4">
                <input type="text" class="form-control"
                    name="<%= Configuration.DB_HOST %>" id="<%= Configuration.DB_HOST %>" value="<%=db_host%>" tabindex="2" />
            </div>
        </div>

        <div class="form-group">
            <label for="<%= Configuration.DB_PORT %>" class="col-sm-3 control-label">Server port</label>
            <div class="col-sm-4">
                <input type="text" class="form-control"
                    name="<%= Configuration.DB_PORT %>" id="<%= Configuration.DB_PORT %>" value="<%=str_db_port%>" tabindex="3" />
            </div>
        </div>

        <div class="form-group">
            <label for="<%= Configuration.DB_USER%>" class="col-sm-3 control-label">Username</label>
            <div class="col-sm-4">
                <input type="text" class="form-control"
                    name="<%= Configuration.DB_USER %>" id="<%= Configuration.DB_USER %>" value="<%=db_user%>" tabindex="4" />
            </div>
        </div>

        <div class="form-group">
            <label for="<%= Configuration.DB_PASS%>" class="col-sm-3 control-label">Password</label>
            <div class="col-sm-4">
                <input type="password" class="form-control"
                    name="<%= Configuration.DB_PASS %>" id="<%= Configuration.DB_PASS %>" value="" tabindex="5" />
            </div>
        </div>

        <div class="form-group">
            <label for="<%= Configuration.DB_DATABASE%>" class="col-sm-3 control-label">Database name</label>
            <div class="col-sm-4">
                <input type="text" class="form-control"
                    name="<%= Configuration.DB_DATABASE %>" id="<%= Configuration.DB_DATABASE %>" value="<%=db_database%>" tabindex="6" />
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-7">
                <input type="submit" name="verify" value="Check configuration" tabindex="7" class="btn btn-primary pull-right" />
            </div>
        </div>
    </form>

    <span class="label label-info">Security advice</span>
    <p>
        For stronger security, you are advised to create a dedicated user for the toolkit that has only 
        <code>SELECT</code> permission on the database tables.
    </p>
    </div>
    On MySQL this is accomplished by issuing the following statement:
    <pre>GRANT SELECT ON database_name.* TO 'informea_user'@'localhost' IDENTIFIED BY 'secure-password'</pre>
</div>
<jsp:include page="../WEB-INF/includes/footer.jsp" />
