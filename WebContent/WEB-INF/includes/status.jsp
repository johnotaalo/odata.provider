<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="org.informea.odata.constants.EntityType"%>
<%@page import="org.informea.odata.util.ToolkitUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String endpoint = "services/odata.svc";
    Map<EntityType, Map<String, Object>> entityStatus = ToolkitUtil.getEntityStatus(request, endpoint);
    List<EntityType> entities = EntityType.getEntities();
    pageContext.setAttribute("entities", entities);
%>
    <table class="table">
        <thead>
            <tr>
                <th>Exposed entity</th>
                <th>Enabled</th>
                <th>Count</th>
                <th>URL</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="e" items="${entities}">
            <%
                EntityType e = (EntityType)pageContext.getAttribute("e");
                if(entityStatus.containsKey(e)) {
                    Map<String, Object> cfg = entityStatus.get(e);
                    pageContext.setAttribute("inUse", cfg.get("inUse"));
                    pageContext.setAttribute("count", cfg.get("count"));
                    pageContext.setAttribute("url", cfg.get("url"));
                }
            %>
            <tr<c:if test="${inUse}"> class="success"</c:if><c:if test="${!inUse}"> class="danger"</c:if>>
                <td>
                    <c:out value="${e}" />
                    <c:out value="${item.count}" />
                </td>
                <td>
                   <c:if test="${inUse}"><span class="glyphicon glyphicon-check"></span></c:if>
                   <c:if test="${!inUse}">No</c:if>
                </td>
                <td>
                    <c:if test="${inUse}"><c:out value="${count}" /></c:if>
                    <c:if test="${!inUse}">n/a</c:if>
                </td>
                <td>
                <c:if test="${inUse}">
                    <a target="_blank" href="<c:out value="${url}" />">Browse</a>
                </c:if>&nbsp;
                </td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
