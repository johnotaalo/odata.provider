<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.informea.odata.util.ToolkitUtil" %>
<%@page import="org.informea.odata.config.Configuration" %>
<%@page import="org.informea.odata.config.DatabaseConfiguration" %>
<%@page import="org.informea.odata.util.JDBCHelper"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
if(!"done".equals(session.getAttribute("step3.entities"))) {
    response.sendRedirect("index.jsp");
    return;
}

Configuration cfg = Configuration.getInstance();
boolean next = ToolkitUtil.isOnRequest("next", request);
if(next) {
    cfg.setUsePathPrefix(ToolkitUtil.getRequestCheckbox("usePathPrefix", request));
    cfg.setPathPrefix(ToolkitUtil.getRequestValue("pathPrefix", request));
    response.sendRedirect("finish");
}
pageContext.setAttribute("cfg", cfg);
pageContext.setAttribute("usePathPrefix", cfg.isUsePathPrefix());
%>
<jsp:include page="../WEB-INF/includes/header.jsp">
    <jsp:param name="html_title" value="Configure decisions' documents location on server disk" />
    <jsp:param name="current_menu_item" value="configuration" />
</jsp:include>

<div class="content">
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

    <div class="row">
    <div class="col col-md-8">
        <p>
            Please use the form below to determine how the toolkit will locate documents on the server's disk for each decision.
        </p>
    
        <form action="" method="post" class="form-horizontal">
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" value="ON" tabindex="1" 
                                id="usePathPrefix" name="usePathPrefix"
                                <c:if test="${usePathPrefix}"> checked="checked"</c:if>
                            />
                            <strong>Append the prefix path below</strong>
                        </label>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="pathPrefix" class="col-sm-2 control-label">Prefix</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" size="60" tabindex="2"
                        id="pathPrefix" name="pathPrefix"
                        <c:if test="${usePathPrefix}">
                            value="<c:out value="${cfg.getPathPrefix()}" />"
                       </c:if>
                    />
               </div>
            </div>
            <div class="form-group">
                <div class="col-sm-10">
                    <input type="submit" name="next" value="Next &raquo;" tabindex="3" class="btn btn-primary" />
                </div>
            </div>
        </form>
        
        <h3>Example 1 - must use prefix</h3>
        <table class="table table-bordered">
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
        <p>
            In this case, check prefix and set the prefix to <em>/var/local/www/files/mydocuments</em>
        </p>

        <h3>Example 2 - prefixed</h3>
        <table class="table table-bordered">
            <tr>
                <th>decision_id</th>
                <th>diskPath</th>
                <th>filename</th>
            </tr>
            <tr>
                <td>1</td>
                <td>dec-01</td>
                <td><strong>/var/local/www/files/mydocuments/COP1/res1.pdf</strong></td>
            </tr>
        </table>
        <p>
            In this case, leave prefix unchecked and press <strong>Next</strong>
        </p>

    </div><!-- /.col-md-8 -->
    <div class="col col-md-4">
        <h2>Help</h2>
        <ol>
            <li>
                If documents contain absolute path when stored inside the view <code>informea_decision_documents</code>,
                leave empty and click <strong>Next</strong>.
            </li>
            <li>
                <p>
                    If documents are stored with a relative path, enter the base path (<em>the prefix</em>) and the toolkit will 
                    append it, to create the absolute path
                </p>

                In this case we would need to set the path prefix to something like 
                <code>/var/www/mywebsite</code>, so the final path for the file will look like this:
                <pre>/var/www/mywebsite/uploads/COP1/res1.pdf</pre>
                The toolkit will append automatically <code>/</code> between the paths.
            </li>
        </ol>
    </div>
    </div><!-- /.row -->
</div><!-- /.content -->
<jsp:include page="../WEB-INF/includes/footer.jsp" />
