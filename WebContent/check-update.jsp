<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="org.informea.odata.util.ToolkitUtil"%>
<%@page contentType="application/json" pageEncoding="UTF-8"%>
<%
Map<String, Object> update = ToolkitUtil.checkUpdate();
String ret = String.format(
        "{ \"needsUpdate\": %s, \"remoteVersion\": \"%s\" , \"success\" : %s }",
        update.get("needsUpdate"), update.get("remoteVersion"), update.get("success"));
out.clear();
out.print(ret);%>