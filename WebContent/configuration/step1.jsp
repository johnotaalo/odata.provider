<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.informea.odata.producer.toolkit.HibernateConfigurator"%>
<%@page import="org.informea.odata.util.ToolkitUtil" %>
<%@page import="org.informea.odata.producer.toolkit.Configuration" %>
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
            db_type = cfg.getDBType();
            db_host = cfg.getDBHost();
            db_port = cfg.getDBPort();
            db_user = cfg.getDBUser();
            db_pass = cfg.getDBPassword();
            db_database = cfg.getDBName();
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
    <div id="breadcrumb">
        You are here: <a href="<%= ToolkitUtil.url(request, null) %>">home</a>
        &raquo;
        <a href="<%= ToolkitUtil.url(request, "/configuration") %>">configuration</a>
        &raquo;
        database configuration
    </div>
    <h1>Database configuration</h1>
    <% if(verify && !validConnection) { %>
    <div class="error">
        Error connecting to the specified database. Please try again!
        <% if(e != null) { %>
        <pre>Details: <%= e.getMessage() %></pre>
        <% } %>
    </div>
    <% } %>
    <form action="" method="post" onsubmit="return validateOnSubmit();">
        <div class="field">
            <label for="<%= Configuration.DB_TYPE %>">
                Database type
            </label>
            <select id="<%= Configuration.DB_TYPE %>" name="<%= Configuration.DB_TYPE %>" tabindex="1">
                <option value=""<%= ("".equals(db_type)) ? " selected=\"selected\"" : "" %>>-- Please select --</option>
                <option value="<%= JDBCHelper.DB_TYPE_MYSQL %>"<%= (JDBCHelper.DB_TYPE_MYSQL.equals(db_type)) ? " selected=\"selected\"" : "" %>>MySQL</option>
                <option value="<%= JDBCHelper.DB_TYPE_POSTGRESQL %>"<%= (JDBCHelper.DB_TYPE_POSTGRESQL.equals(db_type)) ? " selected=\"selected\"" : "" %>>PostgreSQL</option>
            </select>
        </div>
        <br class="clear" />
        <div class="field">
            <label for="<%= Configuration.DB_HOST %>">
                Server address:
            </label>
            <input type="text" name="<%= Configuration.DB_HOST %>" id="<%= Configuration.DB_HOST %>" value="<%=db_host%>" tabindex="2" />
        </div>

        <div class="field">
            <label for="<%= Configuration.DB_PORT %>">
                Server port:
            </label>
            <input type="text" name="<%= Configuration.DB_PORT %>" id="<%= Configuration.DB_PORT %>" value="<%=str_db_port%>" tabindex="3" />
        </div>

        <div class="field">
            <label for="<%= Configuration.DB_USER%>">
                Username:
            </label>
            <input type="text" name="<%= Configuration.DB_USER %>" id="<%= Configuration.DB_USER %>" value="<%=db_user%>" tabindex="4" />
        </div>

        <div class="field">
            <label for="<%= Configuration.DB_PASS%>">
                Password:
            </label>
            <input type="password" name="<%= Configuration.DB_PASS %>" id="<%= Configuration.DB_PASS %>" value="" tabindex="5" />
        </div>

        <div class="field">
            <label for="<%= Configuration.DB_DATABASE%>">
                Database name
            </label>
            <input type="text" name="<%= Configuration.DB_DATABASE %>" id="<%= Configuration.DB_DATABASE %>" value="<%=db_database%>" tabindex="6" />
        </div>
        <% if(HibernateConfigurator.getInstance().isConfigured()) { %>
        <p class="error">
            Warning: Database is already configured, please <strong>restart the servlet container</strong> or <strong>restart when the configuration is completed</strong>!
        </p>
        <% } %>
        <input type="submit" name="verify" value="Verify" tabindex="7" />
    </form>
    <br />
    <br />
    <br />
    Notice: As a securiy measure, you are advised to create a dedicated user
    for the toolkit that has only SELECT permission on the database.
    On MySQL database, this is accomplished by issuing the following statement:
    <pre>
        GRANT SELECT ON database_name.* TO 'informea_user'@'localhost' IDENTIFIED BY 'secure-password'
    </pre>
</div>
<jsp:include page="../WEB-INF/includes/footer.jsp" />