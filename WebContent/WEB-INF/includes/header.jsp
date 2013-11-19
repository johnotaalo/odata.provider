<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="org.informea.odata.producer.toolkit.Configuration"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.informea.odata.util.ToolkitUtil"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<!DOCTYPE html>
<html>
    <head>
        <title><%= request.getParameter("html_title")%></title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta charset="UTF-8" />
        <link rel="stylesheet" type="text/css" href="<%= ToolkitUtil.url(request, "/themes/css/bootstrap.min.css") %>"></link>
        <link rel="stylesheet" type="text/css" href="<%= ToolkitUtil.url(request, "/themes/css/informea.css") %>"></link>
    </head>
    <body>
        <div class="container">
            <div class="header">
                <ul class="nav nav-pills pull-right">
                    <li class="active"><a href="#">Home</a></li>
                    <li><a href="#">Manage</a></li>
                </ul>
                <h3 class="text-muted">InforMEA toolkit</h3>
            </div>