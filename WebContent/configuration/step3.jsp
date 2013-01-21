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
    <div id="breadcrumb">
        You are here: <a href="<%= ToolkitUtil.url(request, null) %>">home</a>
        &raquo;
        <a href="<%= ToolkitUtil.url(request, "/configuration") %>">configuration</a>
        &raquo;
        <a href="<%= ToolkitUtil.url(request, "/configuration/step1.jsp") %>">database configuration</a>
        &raquo;
        <a href="<%= ToolkitUtil.url(request, "/configuration/step2.jsp") %>">select available entities</a>
        &raquo;
        configure decision documents
    </div>
    <h1>Configure decisions' documents location on server disk</h1>

        Please use the form below to determine how the toolkit will locate documents on the server's disk for each decision.
        <br />
        There are two possibilities available here:
        <br />
        <ol class="full">
            <li>
                Documents have the absolute path stored in the database. <em>In this case just click 'Next';</em>
            </li>
            <li>
                Documents are stored with a relative path, in this case just enter the path prefix here and the toolkit will take care of creating the absolute paths.
                <br />
                <em>Example</em>
                <br />
                The table informea_decisions_documents might look like this:
                <table>
                    <tr>
                        <th>id</th>
                        <th>decision_id</th>
                        <th>diskPath</th>
                        <th>url</th>
                        <th>mimeType</th>
                        <th>language</th>
                        <th>filename</th>
                    </tr>
                    <tr>
                        <td>1</td>
                        <td>dec-01</td>
                        <td class="error">uploads/COP1/res1.pdf</td>
                        <td>http://my.website.com/uploads/COP1/res1.pdf</td>
                        <td>application/x-pdf</td>
                        <td>en</td>
                        <td>res1.pdf</td>
                    </tr>
                </table>
                In this case we would need to set the path prefix to something like <strong class="error">/var/www/mywebsite</strong>, so the final path for the file will look like this:
                <pre>/var/www/mywebsite/uploads/COP1/res1.pdf</pre>

                The toolkit will append automatically '/' between the paths.
            </li>
        </ol>
    </p>
    <form action="" method="POST">
        <input type="checkbox" id="<%= Configuration.USE_PATH_PREFIX %>" name="<%= Configuration.USE_PATH_PREFIX %>"
               <% if(cfg.isUsePathPrefix()) { %> checked="checked"<% } %>
               value="ON" tabindex="1" />
        <label for="<%= Configuration.USE_PATH_PREFIX %>">Decision documents must be prefixed with an absolute path</label>
        <br />
        <label for="<%= Configuration.PATH_PREFIX %>">The prefix absolute path is:</label>
        <input type="text" id="<%= Configuration.PATH_PREFIX %>" name="<%= Configuration.PATH_PREFIX %>"
               <% if(cfg.isUsePathPrefix()) { %> value="<%= cfg.getPathPrefix() %>"<% } %>
               size="60" tabindex="2" />
        <br />
        <input type="submit" name="next" value="Next &raquo;" tabindex="3" />
    </form>
    <br />
    <br />
<jsp:include page="../WEB-INF/includes/footer.jsp" />