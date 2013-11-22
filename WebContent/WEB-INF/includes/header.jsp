<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="org.informea.odata.producer.toolkit.Configuration"%>
<%@page import="org.informea.odata.util.ToolkitUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<!DOCTYPE html>
<%
String currentItem = request.getParameter("current_menu_item");
if(currentItem == null || "".equalsIgnoreCase(currentItem)) {
    currentItem = "home";
}
pageContext.setAttribute("menu", currentItem);
%>
<html>
    <head>
        <title><%= request.getParameter("html_title")%></title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta charset="UTF-8" />
        <link rel="stylesheet" type="text/css" href="<%= ToolkitUtil.url(request, "/themes/css/bootstrap.min.css") %>"></link>
        <link rel="stylesheet" type="text/css" href="<%= ToolkitUtil.url(request, "/themes/css/informea.css") %>"></link>
    </head>
    <body>
      <!-- Static navbar -->
      <div class="navbar navbar-default" role="navigation">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="<%= ToolkitUtil.url(request, null) %>">InforMEA toolkit</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
                    <li<c:if test="${menu == 'home' }"> class="active"</c:if>>
                        <a href="<%= ToolkitUtil.url(request, "/") %>">Home</a>
                    </li>
                    <li<c:if test="${menu == 'configuration' }"> class="active"</c:if>>
                        <a href="<%= ToolkitUtil.url(request, "/configuration") %>">Configuration</a>
                    </li>
                    <li<c:if test="${menu == 'about' }"> class="active"</c:if>>
                        <a href="<%= ToolkitUtil.url(request, "/about") %>">About</a>
                    </li>
                    <li<c:if test="${menu == 'faq' }"> class="active"</c:if>>
                        <a href="<%= ToolkitUtil.url(request, "/faq") %>">FAQ</a>
                    </li>
                    <li id="updateButton" <c:if test="${menu == 'update' }"> class="active"</c:if><c:if test="${menu != 'update' }"> class="hidden"</c:if>>
                        <a href="<%= ToolkitUtil.url(request, "/update") %>">Update</a>
                    </li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li>
                <div id="version" class="label label-info pull-right">
                    <%= ToolkitUtil.getToolkitVersion() %>
                </div>
            </li>
            <li>
                <div id="updateStatus" class="label label-info pull-right">Checking for newer version ...</div>
            </li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>

      