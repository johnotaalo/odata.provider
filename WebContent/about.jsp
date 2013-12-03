<%@page import="org.informea.odata.util.ToolkitUtil"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.informea.odata.config.Configuration"%>
<%@page import="java.util.prefs.*"%>
<% Configuration cfg = Configuration.getInstance(); %>
<jsp:include page="WEB-INF/includes/header.jsp">
    <jsp:param name="html_title" value="InforMEA OData provider" />
    <jsp:param name="current_menu_item" value="about" />
</jsp:include>
<div class="content">
    <ol class="breadcrumb">
        <li><a href="<%= ToolkitUtil.url(request, null) %>">Home</a></li>
        <li class="active">About</li>
    </ol>

    <h1>About</h1>
    <div class="row">
    <div class="col-md-8">
        <p>
            The InforMEA toolkit project aims at facilitating the MEAs (Conventions) to expose their data to the outside world 
            as a web service.
        </p>
        <p>
            An web service allows other interested parties on access and retrieval of the data via electronic means, by allowing
            computer-to-computer communication.
        </p>
        
        <p>
            On top of this, the toolkit also offers the data structure in a way which is the same across all Conventions. This
            was achieved by establishing a common structure for each of the entities that are shared (National Focal Points,
            Meetings, Decisions etc.)
        </p>
        
        <p>
            The protocol used to expose the information is called <a href="http://www.odata.org">OData</a>.
        </p>
        
        <p>
            The toolkit is part of the InforMEA project, developed by UNEP and supported by the member conventions.
        </p>
    </div><!-- /.col-md-8 -->
    </div><!-- /.row -->
</div><!-- /.content -->
<jsp:include page="WEB-INF/includes/footer.jsp" />