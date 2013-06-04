<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.informea.odata.util.ToolkitUtil"%>
<%@page import="org.informea.odata.util.JDBCHelper"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%
    boolean disconnect = ToolkitUtil.isOnRequest("disconnect", request);
    if(disconnect) {
        session.removeAttribute("sql.db_type");
        session.removeAttribute("sql.db_host");
        session.removeAttribute("sql.db_port");
        session.removeAttribute("sql.db_user");
        session.removeAttribute("sql.db_pass");
        session.removeAttribute("sql.db_database");
        session.removeAttribute("sql.configured");
    }


    boolean connect = ToolkitUtil.isOnRequest("connect", request);
    int db_port = 0;
    String db_type = (String)session.getAttribute("sql.db_type");
    String db_host = (String)session.getAttribute("sql.db_host");
    if(session.getAttribute("sql.db_port") != null) {
        db_port = (Integer)session.getAttribute("sql.db_port");
    }
    String db_user = (String)session.getAttribute("sql.db_user");
    String db_pass = (String)session.getAttribute("sql.db_pass");
    String db_database = (String)session.getAttribute("sql.db_database");
    JDBCHelper jdbc = null;
    if(connect) {
        db_type = ToolkitUtil.getRequestValue("sql.db_type", request);
        db_host = ToolkitUtil.getRequestValue("sql.db_host", request);
        db_port = ToolkitUtil.getRequestInteger("sql.db_port", request);
        db_user = ToolkitUtil.getRequestValue("sql.db_user", request);
        db_pass = ToolkitUtil.getRequestValue("sql.db_pass", request);
        db_database = ToolkitUtil.getRequestValue("sql.db_database", request);
        jdbc = new JDBCHelper(db_type, db_host, db_port, db_user, db_pass, db_database);
        try {
            if(jdbc.validateDBConnection()) {
                    session.removeAttribute("sql_connect_error");
                    session.setAttribute("sql.db_type", db_type);
                    session.setAttribute("sql.db_host", db_host);
                    session.setAttribute("sql.db_port", db_port);
                    session.setAttribute("sql.db_user", db_user);
                    session.setAttribute("sql.db_pass", db_pass);
                    session.setAttribute("sql.db_database", db_database);
                    session.setAttribute("sql.configured", true);
            }
        } catch(Exception ex) {
            session.setAttribute("sql_connect_error", ex.getMessage());
        }
    }

    boolean configured = session.getAttribute("sql.configured") != null ? true : false;
    jdbc = new JDBCHelper(db_type, db_host, db_port, db_user, db_pass, db_database);

    String sql = ToolkitUtil.getRequestValue("sql", request);
%>
<jsp:include page="../WEB-INF/includes/header.jsp">
    <jsp:param name="html_title" value="InforMEA OData provider" />
    <jsp:param name="current_menu_item" value="sql" />
</jsp:include>
<div class="content">
    <h1>SQL Web console</h1>
    <% if(!configured) { %>
        <h2>Connect to database</h2>
        <jsp:include page="../WEB-INF/includes/sql.connect.jsp" />
    <% } else { %>
        You are connected to database <strong><%= db_database %></strong>.
        <a class="button search-explorer-submit mousedown" title="Button" href="index.jsp?disconnect=1">
            <span>Disconnect</span>
        </a>
        <h2>Query editor</h2>
        <form action="" method="POST">
            <label for="sql">Enter your SQL statement below</label>
            <br />
            <div class="clear"></div>
            <textarea id="sql" name="sql" rows="10" cols="120"><%= sql %></textarea>
            <br />
            <div class="clear"></div>
            <input type="submit" name="execute" value="Execute" />
        </form>
        <%
        if(sql != null && !"".equals(sql)) {
            String error = null;
            List<List<String>> data = new ArrayList<List<String>>();
            try {
                data = jdbc.executeQuery(sql);
            } catch( Exception ex) {
                ex.printStackTrace();
                error = ex.getMessage();
            }
            if(error == null && data.size() > 0) {
        %>
        <div style="overflow: auto; width: 900px; height: 400px;">
            <h2>Query results</h2>
            <table class="sql-table">
                <% List<String> header = data.get(0); %>
                <thead>
                    <tr>
                        <% for(String column : header) { %><th><%= column %></th><% } %>
                    </tr>
                </thead>
            <%
            int i = 0;
            List<List<String>> data1 = data.subList(1, data.size() - 1);
            for(List<String> row : data1) {
                i++;
            %>
            <tr<%= (i % 2 == 0) ? " class=\"zebra\"" : ""%>><% for(String column : row) { %><td><%= column %></td><% } %></tr>
            <% } %>
            </table>
        </div>
            <%
            } else {
                if(error != null) {
                    out.print(String.format("<p class=\"error\">%s</p>", error));
                } else {
                    out.print("<p class=\"success\">Empty data set</p>");
                }
            }
        }
    } %>
    <br />
    <br />
    <br />
</div>
<jsp:include page="../WEB-INF/includes/footer.jsp" />