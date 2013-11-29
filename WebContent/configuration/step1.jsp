<%@page import="org.informea.odata.config.LDAPConfiguration"%>
<%@page import="org.informea.odata.config.DatabaseConfiguration"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.informea.odata.config.Configuration"%>
<%@page import="org.informea.odata.config.HibernateConfigurator"%>
<%@page import="org.informea.odata.util.ToolkitUtil" %>
<%@page import="org.informea.odata.util.JDBCHelper"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
boolean verify = ToolkitUtil.isOnRequest("verify", request);
boolean useDB =  ToolkitUtil.getRequestCheckbox(Configuration.USE_DATABASE, request);
boolean useLDAP = ToolkitUtil.getRequestCheckbox(Configuration.USE_LDAP, request);
boolean databaseOK = false, ldapOK = false;

DatabaseConfiguration db = DatabaseConfiguration.fromRequest(request);
//LDAPConfiguration ldap = LDAPConfiguration.fromRequest(request);

Exception e = null;
boolean validConnection = false;
if(verify) {
    if(useDB) {
        try {
            JDBCHelper jdbc = new JDBCHelper(db);
            databaseOK = jdbc.validateDBConnection();
        } catch(Exception ex) {
            e = ex;
        }
    } else {
        databaseOK = true;
    }
    if(useLDAP) {
        
    } else {
        ldapOK = true;
    }
    
}

if((useDB && databaseOK) || (useLDAP && ldapOK)) {
    session.setAttribute("informea.step1", "done");
    response.sendRedirect("step2.jsp");
    return;
}


pageContext.setAttribute("db", db);

out.clear();%><jsp:include page="../WEB-INF/includes/header.jsp">
    <jsp:param name="html_title" value="Database configuration" />
    <jsp:param name="current_menu_item" value="configuration" />
</jsp:include>
<script type="text/javascript">
jQuery(document).ready(function() {
    var dbCheck = jQuery('#<%= Configuration.USE_DATABASE %>');
    var ldapCheck = jQuery('#<%= Configuration.USE_LDAP %>');

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

    
    ldapCheck.click(function() {
        if(dbCheck.is(':checked')) {
            
        } else {
            
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
    if(dbCheck.is(':checked')) {
        if(dbType.val() == '') {
            dbType.parent().addClass('has-error');
            alert("Please select the database type");
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

    <h1>Data source configuration</h1>

    <form action="" method="post" onsubmit="return validateOnSubmit();" role="form" class="form-horizontal">
            <h2>Database</h2>
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
        <div class="form-group">
            <div class="col-sm-offset-3 col-sm-10">
                <div class="checkbox">
                    <label>
                        <input type="checkbox" name="<%= Configuration.USE_DATABASE %>" 
                            id="<%= Configuration.USE_DATABASE %>" 
                            value="1" tabindex="1" />
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
            </div>
        </div>

        <div class="form-group">
            <label for="<%= DatabaseConfiguration.DB_HOST %>" class="col-sm-3 control-label">Server address</label>
            <div class="col-sm-4">
                <input type="text" class="form-control"
                    name="<%= DatabaseConfiguration.DB_HOST %>" 
                    id="<%= DatabaseConfiguration.DB_HOST %>" 
                    value="<c:out value="${db.getHost()}" />" tabindex="2" />
            </div>
        </div>

        <div class="form-group">
            <label for="<%= DatabaseConfiguration.DB_PORT %>" class="col-sm-3 control-label">Server port</label>
            <div class="col-sm-4">
                <input type="text" class="form-control"
                    name="<%= DatabaseConfiguration.DB_PORT %>" 
                    id="<%= DatabaseConfiguration.DB_PORT %>" 
                    value="<c:out value="${db.getPort()}" />" tabindex="3" />
            </div>
        </div>

        <div class="form-group">
            <label for="<%= DatabaseConfiguration.DB_USER%>" class="col-sm-3 control-label">Username</label>
            <div class="col-sm-4">
                <input type="text" class="form-control"
                    name="<%= DatabaseConfiguration.DB_USER %>" 
                    id="<%= DatabaseConfiguration.DB_USER %>" 
                    value="<c:out value="${db.getUser()}" />" tabindex="4" />
            </div>
        </div>

        <div class="form-group">
            <label for="<%= DatabaseConfiguration.DB_PASS%>" class="col-sm-3 control-label">Password</label>
            <div class="col-sm-4">
                <input type="password" class="form-control"
                    name="<%= DatabaseConfiguration.DB_PASS %>" 
                    id="<%= DatabaseConfiguration.DB_PASS %>" value="" tabindex="5" />
            </div>
        </div>

        <div class="form-group">
            <label for="<%= DatabaseConfiguration.DB_DATABASE%>" class="col-sm-3 control-label">Database name</label>
            <div class="col-sm-4">
                <input type="text" class="form-control"
                    name="<%= DatabaseConfiguration.DB_DATABASE %>" 
                    id="<%= DatabaseConfiguration.DB_DATABASE %>"
                    value="<c:out value="${db.getDatabase()}" />" 
                    tabindex="6" />
            </div>
        </div>


        <h2>LDAP for Contacts</h2>
        <div class="form-group">
            <div class="col-sm-offset-3 col-sm-10">
                <div class="checkbox">
                    <label>
                        <input type="checkbox" name="<%= Configuration.USE_LDAP %>" 
                            id="<%= Configuration.USE_LDAP %>" 
                            value="1" tabindex="1" />
                            Use LDAP
                    </label>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label for="<%= LDAPConfiguration.LDAP_HOST%>" class="col-sm-3 control-label">Server</label>
            <div class="col-sm-4">
                <input type="text" class="form-control"
                    name="ldapHost"  
                    id="ldapHost"
                    value="<c:out value="${ldap.getHost()}" />" 
                    tabindex="7" />
            </div>
        </div>

        <div class="form-group">
            <label for="<%= LDAPConfiguration.LDAP_PORT %>" class="col-sm-3 control-label">Server port</label>
            <div class="col-sm-4">
                <input type="text" class="form-control"
                    name="<%= LDAPConfiguration.LDAP_PORT %>" 
                    id="<%= LDAPConfiguration.LDAP_PORT %>" 
                    value="<c:out value="${ldap.getPort()}" />" tabindex="3" />
            </div>
        </div>

        <div class="form-group">
            <label for="<%= LDAPConfiguration.LDAP_BIND_DN %>" class="col-sm-3 control-label">Bind DN</label>
            <div class="col-sm-4">
                <input type="text" class="form-control"
                    name="<%= LDAPConfiguration.LDAP_BIND_DN %>" 
                    id="<%= LDAPConfiguration.LDAP_BIND_DN %>" 
                    value="<c:out value="${ldap.getBindDN()}" />" tabindex="3" />
            </div>
            <span class="help-block">LDAP credential to connect to the server</span>
        </div>

        <div class="form-group">
            <label for="<%= LDAPConfiguration.LDAP_USER_BASE_DN %>" class="col-sm-3 control-label">Single user Base DN</label>
            <div class="col-sm-4">
                <input type="text" class="form-control"
                    placeholder="ex. ou=People,dc=example,dc=com"
                    name="<%= LDAPConfiguration.LDAP_USER_BASE_DN %>" 
                    id="<%= LDAPConfiguration.LDAP_USER_BASE_DN %>" 
                    value="<c:out value="${ldap.getUserBaseDN()}" />" tabindex="3" />
            </div>
            <span class="help-block">Base of the user filtering</span>
        </div>

        <div class="form-group">
            <label for="<%= LDAPConfiguration.LDAP_USER_QUERY_FILTER %>" class="col-sm-3 control-label">Query filter for single user</label>
            <div class="col-sm-4">
                <input type="text" class="form-control"
                    placeholder="ex: dn=ou=Users"
                    name="<%= LDAPConfiguration.LDAP_USER_QUERY_FILTER %>" 
                    id="<%= LDAPConfiguration.LDAP_USER_QUERY_FILTER %>" 
                    value="<c:out value="${ldap.getUserQueryFilter()}" />" tabindex="3" />
            </div>
        </div>

        <div class="form-group">
            <label for="<%= LDAPConfiguration.LDAP_USERS_BASE_DN %>" class="col-sm-3 control-label">Users query Base DN</label>
            <div class="col-sm-4">
                <input type="text" class="form-control"
                    placeholder="ex. ou=People,dc=example,dc=com"
                    name="<%= LDAPConfiguration.LDAP_USERS_BASE_DN %>" 
                    id="<%= LDAPConfiguration.LDAP_USERS_BASE_DN %>" 
                    value="<c:out value="${ldap.getUsersBaseDN()}" />" tabindex="3" />
            </div>
            <span class="help-block">Base DN for querying all users</span>
        </div>

        <div class="form-group">
            <label for="<%= LDAPConfiguration.LDAP_USERS_QUERY_FILTER %>" class="col-sm-3 control-label">Query filter for users</label>
            <div class="col-sm-4">
                <input type="text" class="form-control"
                    placeholder="o=CMS"
                    name="<%= LDAPConfiguration.LDAP_USERS_QUERY_FILTER %>" 
                    id="<%= LDAPConfiguration.LDAP_USERS_QUERY_FILTER %>" 
                    value="<c:out value="${ldap.getUsersQueryFilter()}" />" tabindex="3" />
            </div>
            <span class="help-block">Only users matching this filter will be returned as Contacts</span>
        </div>

        <div class="form-group">
            <div class="col-sm-7">
                <input type="submit" name="verify" value="Next" tabindex="7" class="btn btn-primary pull-right" />
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
