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
    </head>
    <body class="toolkit informea customize-support">
    <ul class="nav nav-pills">
        <li class="active"><a href="/">Home</a></li>
        <li><a href="#">Administration</a></li>
    </ul>
    