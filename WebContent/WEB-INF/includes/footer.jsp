<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.informea.odata.util.ToolkitUtil"%>
      <div class="footer">
        <p>
            &copy; 2011 &minus; 2013 
            <a href="http://www.informea.org" target="_blank">InforMEA project</a>
        </p>
      </div>
    </div><!-- /container -->
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript">
        var updateURL = "<%= ToolkitUtil.url(request, "/update-check")%>";
    </script>
    <script type="text/javascript" src="<%= ToolkitUtil.url(request, "/themes/js/jquery-1.10.2.min.js") %>"></script>
    <script type="text/javascript" src="<%= ToolkitUtil.url(request, "/themes/js/bootstrap.min.js") %>"></script>
    <script type="text/javascript" src="<%= ToolkitUtil.url(request, "/themes/js/informea.js") %>"></script>
</body>