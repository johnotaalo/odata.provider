<%@page import="org.informea.odata.config.DatabaseConfiguration"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.informea.odata.config.Configuration"%>
<%@page import="org.informea.odata.config.HibernateConfigurator"%>
<%@page import="org.informea.odata.util.ToolkitUtil" %>
<%@page import="org.informea.odata.util.JDBCHelper"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
boolean verify = ToolkitUtil.isOnRequest("verify", request);
boolean useDB =  ToolkitUtil.isOnRequest(Configuration.USE_DATABASE, request);
boolean databaseOK = false;
DatabaseConfiguration db = DatabaseConfiguration.fromHttpRequest(request);
int port = db.getPort();

Exception e = null;
boolean validConnection = false;
if(useDB) {
    if(verify) {
        try {
            JDBCHelper jdbc = new JDBCHelper(db);
            databaseOK = jdbc.validateDBConnection();
        } catch(Exception ex) {
            e = ex;
        }
    }
} else {
    databaseOK = true;
}

if((useDB && databaseOK) || (verify && !useDB)) {
    session.setAttribute("step1.database", "done");
    session.setAttribute("db", db);
    response.sendRedirect("ldap");
    return;
}
pageContext.setAttribute("port", port == 0 ? "" : port);
pageContext.setAttribute("useDB", useDB);
pageContext.setAttribute("db", db);
out.clear();%><jsp:include page="../WEB-INF/includes/header.jsp">
    <jsp:param name="html_title" value="Database configuration" />
    <jsp:param name="current_menu_item" value="configuration" />
</jsp:include>
<script type="text/javascript">
jQuery(document).ready(function() {
    var dbCheck = jQuery('#<%= Configuration.USE_DATABASE %>');

    var dbType = jQuery('#<%= DatabaseConfiguration.DB_TYPE %>');
    var dbHost = jQuery('#<%= DatabaseConfiguration.DB_HOST %>');
    var dbPort = jQuery('#<%= DatabaseConfiguration.DB_PORT %>');
    var dbUser = jQuery('#<%= DatabaseConfiguration.DB_USER %>');
    var dbPassword = jQuery('#<%= DatabaseConfiguration.DB_PASS %>');
    var dbName = jQuery('#<%= DatabaseConfiguration.DB_DATABASE %>');

    dbCheck.click(function(){
        if(dbCheck.is(':checked')) {
            dbType.removeAttr('disabled');
            dbHost.removeAttr('disabled');
            dbPort.removeAttr('disabled');
            dbUser.removeAttr('disabled');
            dbPassword.removeAttr('disabled');
            dbName.removeAttr('disabled');
        } else {
            dbType.attr('disabled', 'disabled');
            dbHost.attr('disabled', 'disabled');
            dbPort.attr('disabled', 'disabled');
            dbUser.attr('disabled', 'disabled');
            dbPassword.attr('disabled', 'disabled');
            dbName.attr('disabled', 'disabled');
        }
    });

    <c:if test="${installed == true}">
    dbCheck.trigger('click'); dbCheck.trigger('click');
    </c:if>
    <c:if test="${installed == false}">
    dbCheck.trigger('click');
    </c:if>
});

function validateOnSubmit() {
    var dbCheck = jQuery('#<%= Configuration.USE_DATABASE %>');
    var dbType = jQuery('#<%= DatabaseConfiguration.DB_TYPE %>');
    var dbHost = jQuery('#<%= DatabaseConfiguration.DB_HOST %>');
    var dbPort = jQuery('#<%= DatabaseConfiguration.DB_PORT %>');
    var dbUser = jQuery('#<%= DatabaseConfiguration.DB_USER %>');
    var dbName = jQuery('#<%= DatabaseConfiguration.DB_DATABASE %>');
    if(dbCheck.is(':checked')) {
        if(dbType.val() == '') {
            dbType.parent().addClass('has-error');
            jQuery('.help-block', dbType.parent()).text("Please select the database type");
            return false;
        }
        if(dbHost.val() == '') {
            dbHost.parent().addClass('has-error');
            jQuery('.help-block', dbHost.parent()).text("Please enter a valid hostname");
            return false;
        }
        if(dbPort.val() == '') {
            dbPort.parent().addClass('has-error');
            jQuery('.help-block', dbPort.parent()).text("Please enter a valid port");
            return false;
        }
        if(dbUser.val() == '') {
            dbUser.parent().addClass('has-error');
            jQuery('.help-block', dbUser.parent()).text("Please enter a valid username");
            return false;
        }
        if(dbName.val() == '') {
            dbName.parent().addClass('has-error');
            jQuery('.help-block', dbName.parent()).text("Please enter a valid database name");
            return false;
        }
    }
    dbType.parent().removeClass('has-error');
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

    <form action="" method="post" onsubmit="return validateOnSubmit();" role="form" class="form-horizontal">
        <% if(useDB && verify && !databaseOK) { %>
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
        <div class="form-group">
            <div class="col-sm-offset-3 col-sm-10">
                <div class="checkbox">
                    <label>
                        <input type="checkbox" name="<%= Configuration.USE_DATABASE %>" 
                            id="<%= Configuration.USE_DATABASE %>" <c:if test="${useDB}">checked="checked"</c:if>
                            value="ON" tabindex="1" />
                            Use database
                    </label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label for="<%= DatabaseConfiguration.DB_TYPE %>" class="col-sm-3 control-label">Type</label>
            <div class="col-sm-4">
                <select id="<%= DatabaseConfiguration.DB_TYPE %>" name="<%= DatabaseConfiguration.DB_TYPE %>" tabindex="1" class="form-control">
                    <option value="">-- Please select --</option>
                    <option 
                        <% if(JDBCHelper.DB_TYPE_MYSQL.equals(db.getType())) {%> selected="selected"<% } %>
                        value="<%= JDBCHelper.DB_TYPE_MYSQL %>">MySQL</option>
                    <option
                        <% if(JDBCHelper.DB_TYPE_POSTGRESQL.equals(db.getType())) {%> selected="selected"<% } %> 
                        value="<%= JDBCHelper.DB_TYPE_POSTGRESQL %>">PostgreSQL</option>
                </select>
                <p class="help-block"></p>
            </div>
        </div>

        <div class="form-group">
            <label for="<%= DatabaseConfiguration.DB_HOST %>" class="col-sm-3 control-label">Server address</label>
            <div class="col-sm-4">
                <input type="text" class="form-control"
                    name="<%= DatabaseConfiguration.DB_HOST %>" 
                    id="<%= DatabaseConfiguration.DB_HOST %>"
                    placeholder="Server name, address e.g. localhost" 
                    value="<c:out value="${db.getHost()}" />" tabindex="2" />
                <p class="help-block"></p>
            </div>
        </div>

        <div class="form-group">
            <label for="<%= DatabaseConfiguration.DB_PORT %>" class="col-sm-3 control-label">Server port</label>
            <div class="col-sm-4">
                <input type="text" class="form-control"
                    name="<%= DatabaseConfiguration.DB_PORT %>" 
                    id="<%= DatabaseConfiguration.DB_PORT %>"
                    placeholder="Server TCP port, e.g. 3306"
                    value="<c:out value="${port}" />" tabindex="3" />
                <p class="help-block"></p>
            </div>
        </div>

        <div class="form-group">
            <label for="<%= DatabaseConfiguration.DB_USER%>" class="col-sm-3 control-label">Username</label>
            <div class="col-sm-4">
                <input type="text" class="form-control"
                    name="<%= DatabaseConfiguration.DB_USER %>" 
                    id="<%= DatabaseConfiguration.DB_USER %>"
                    placeholder="User connecting to the database"
                    value="<c:out value="${db.getUser()}" />" tabindex="4" />
                <p class="help-block"></p>
            </div>
        </div>

        <div class="form-group">
            <label for="<%= DatabaseConfiguration.DB_PASS%>" class="col-sm-3 control-label">Password</label>
            <div class="col-sm-4">
                <input type="password" class="form-control"
                    name="<%= DatabaseConfiguration.DB_PASS %>"
                    placeholder="Password for the authentication" 
                    id="<%= DatabaseConfiguration.DB_PASS %>" value="" tabindex="5" />
                <p class="help-block"></p>
            </div>
        </div>

        <div class="form-group">
            <label for="<%= DatabaseConfiguration.DB_DATABASE%>" class="col-sm-3 control-label">Database name</label>
            <div class="col-sm-4">
                <input type="text" class="form-control"
                    name="<%= DatabaseConfiguration.DB_DATABASE %>" 
                    id="<%= DatabaseConfiguration.DB_DATABASE %>"
                    value="<c:out value="${db.getDatabase()}" />" 
                    placeholder="Name of the database"
                    tabindex="6" />
                <p class="help-block"></p>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-7">
                <input type="submit" name="verify" value="Next" tabindex="7" class="btn btn-primary pull-right" />
            </div>
        </div>
    </form>

    <p>
        <div class="label label-info">Security advice</div>
        To improve the security, you are advised to create a dedicated user for the toolkit having only
        <code>SELECT</code> permission on the database tables.
    </p>
    </div>
    For MySQL this is accomplished by issuing the following statement:
    <pre>GRANT SELECT ON database_name.* TO 'informea_user'@'localhost' IDENTIFIED BY 'secure-password'</pre>
</div>
<jsp:include page="../WEB-INF/includes/footer.jsp" />
