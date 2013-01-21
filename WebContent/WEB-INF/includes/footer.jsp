<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.informea.odata.util.ToolkitUtil"%>
<%@page import="org.informea.odata.producer.toolkit.Configuration"%>
<%@page import="java.util.HashMap"%>
<%= ToolkitUtil.httpPostCached(Configuration.getInstance().getTemplateFooterUrl(), new HashMap<String, String>()) %>
</body>