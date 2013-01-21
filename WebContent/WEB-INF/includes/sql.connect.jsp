<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="org.informea.odata.util.ToolkitUtil"%>
<%
    String db_type = ToolkitUtil.getRequestValue("sql.db_type", request);
    String db_host = ToolkitUtil.getRequestValue("sql.db_host", request);
    int db_port = ToolkitUtil.getRequestInteger("sql.db_port", request);
    String db_user = ToolkitUtil.getRequestValue("sql.db_user", request);
    String db_database = ToolkitUtil.getRequestValue("sql.db_database", request);

       String str_db_port = "";
    if(db_port != 0) {
        str_db_port = String.format("%s", db_port);
    }
%>
<form action="" method="POST">
    <div class="field">
        <label for="sql.db_type">
            Database type
        </label>
        <select id="sql.db_type" name="sql.db_type" tabindex="1">
            <option value=""<%= ("".equals(db_type)) ? " selected=\"selected\"" : "" %>>-- Please select --</option>
            <option value="mysql"<%= ("mysql".equals(db_type)) ? " selected=\"selected\"" : "" %>>MySQL</option>
        </select>
    </div>
    <br class="clear" />
    <div class="field">
        <label for="sql.db_host">
            Server address:
        </label>
        <input type="text" name="sql.db_host" id="sql.db_host" value="<%=db_host%>" tabindex="2" />
    </div>

    <div class="field">
        <label for="sql.db_port">
            Server port:
        </label>
        <input type="text" name="sql.db_port" id="sql.db_port" value="<%=str_db_port%>" tabindex="3" />
    </div>

    <div class="field">
        <label for="sql.db_user">
            Username:
        </label>
        <input type="text" name="sql.db_user" id="sql.db_user" value="<%=db_user%>" tabindex="4" />
    </div>

    <div class="field">
        <label for="sql.db_pass">
            Password:
        </label>
        <input type="password" name="sql.db_pass" id="sql.db_pass" value="" tabindex="5" />
    </div>

    <div class="field">
        <label for="sql.db_database">
            Database name
        </label>
        <input type="text" name="sql.db_database" id="sql.db_database" value="<%=db_database%>" tabindex="6" />
    </div>
    <input type="submit" name="connect" value="Connect" tabindex="7" />
</form>
<c:if test="${!empty sessionScope.sql_connect_error}">
    <p class="error">
        <c:out value="${sessionScope.sql_connect_error}" />
    </p>
</c:if>
