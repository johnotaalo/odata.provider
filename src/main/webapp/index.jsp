<%@page import="java.util.Map"%>
<%@page import="edw.olingo.service.ServiceInformation"%>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Map<String, Object> update = ServiceInformation
			.checkProductUpdates();
	pageContext.setAttribute("updateRequired",
			(Boolean) update.get("needsUpdate"));
	pageContext.setAttribute("updateCheckSuccessful",
			(Boolean) update.get("success"));
	pageContext.setAttribute("remoteVersion",
			update.get("remoteVersion"));

	pageContext.setAttribute("countMeetings",
			ServiceInformation.countMeetings());
	pageContext.setAttribute("countDecisions",
			ServiceInformation.countDecisions());
	pageContext.setAttribute("countContacts",
			ServiceInformation.countContacts());
	pageContext.setAttribute("countCountryReports",
			ServiceInformation.countCountryReports());
	pageContext.setAttribute("countNationalPlans",
			ServiceInformation.countNationalPlans());
	pageContext.setAttribute("countSites",
			ServiceInformation.countSites());
%>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
</head>
<body>
	<nav class="navbar navbar-default navbar-static-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="http://www.informea.org"
					target="_blank">InforMEA OData provider</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><p class="navbar-text">
							<c:choose>
								<c:when test="${not updateCheckSuccessful}">
									<span class="label label-danger">running v.
										${f:getVersion()}, cannot check for updates</span>
								</c:when>
								<c:when test="${updateCheckSuccessful}">
									<c:if test="${updateRequired}">
										<span class="label label-warning">running v.
											${f:getVersion()}, new version ${remoteVersion} available</span>
									</c:if>
									<c:if test="${not updateRequired}">
										<span class="label label-success">running v.
											${f:getVersion()}</span>
									</c:if>
								</c:when>
							</c:choose>
						</p></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</nav>
	<div class="container">
		<div class="jumbotron">
			<h1>InforMEA OData toolkit</h1>
			<p>Exposes the existing database entities as OData web service
				endpoint.</p>
		</div>
		<div class="page-header">
			<h2>Status</h2>
		</div>
		<dl class="dl-horizontal">
			<dt>Service endpoint</dt>
			<dd>
				<a href="informea.svc" target="_blank">View</a>
			</dd>
			<dt>Service metadata</dt>
			<dd>
				<a href="informea.svc/$metadata" target="_blank">View</a>
			</dd>
		</dl>
		<table class="table table-striped table-condensed">
			<caption>Current status of the web service</caption>
			<thead>
				<tr>
					<th>Entity</th>
					<th>No. of records</th>
					<th>Notes</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Meetings</td>
					<td><c:if test="${countMeetings > 0}">${countMeetings}</c:if>
						<c:if test="${countMeetings == 0}">n/a</c:if></td>
					<td>&nbsp; <c:if test="${countMeetings > 0}">
							<a target="_blank" title="Click to see the entities"
								href="informea.svc/Meetings">View</a>
						</c:if>
					</td>
				</tr>
				<tr>
					<td>Decisions</td>
					<td><c:if test="${countDecisions > 0}">
							<a href="#">${countDecisions}</a>
						</c:if> <c:if test="${countDecisions == 0}">n/a</c:if></td>
					<td>&nbsp; <c:if test="${countDecisions > 0}">
							<a target="_blank" title="Click to see the entities"
								href="informea.svc/Decisions">View</a>
						</c:if>
					</td>
				</tr>
				<tr>
					<td>Contacts</td>
					<td><c:if test="${countContacts > 0}">
							<a href="#">${countContacts}</a>
						</c:if> <c:if test="${countContacts == 0}">n/a</c:if></td>
					<td>&nbsp; <c:if test="${countContacts > 0}">
							<a target="_blank" title="Click to see the entities"
								href="informea.svc/Contacts">View</a>
						</c:if>
					</td>
				</tr>
				<tr>
					<td>National reports</td>
					<td><c:if test="${countCountryReports > 0}">
							<a href="#">${countCountryReports}</a>
						</c:if> <c:if test="${countCountryReports == 0}">n/a</c:if></td>
					<td>&nbsp; <c:if test="${countCountryReports > 0}">
							<a target="_blank" title="Click to see the entities"
								href="informea.svc/CountryReports">View</a>
						</c:if>
					</td>
				</tr>
				<tr>
					<td>National action plans</td>
					<td><c:if test="${countNationalPlans > 0}">
							<a href="#">${countNationalPlans}</a>
						</c:if> <c:if test="${countNationalPlans == 0}">n/a</c:if></td>
					<td>&nbsp; <c:if test="${countNationalPlans > 0}">
							<a target="_blank" title="Click to see the entities"
								href="informea.svc/NationalPlans">View</a>
						</c:if>
					</td>
				</tr>
				<tr>
					<td>Sites</td>
					<td><c:if test="${countSites > 0}">
							<a href="#">${countSites}</a>
						</c:if> <c:if test="${countSites == 0}">n/a</c:if></td>
					<td>&nbsp; <c:if test="${countSites > 0}">
							<a target="_blank" title="Click to see the entities"
								href="informea.svc/Sites">View</a>
							<br />
						</c:if> Note: Applies only to Ramsar &amp; WHC sites
					</td>
				</tr>
			</tbody>
		</table>
		<div class="page-header">
			<h2>Documentation</h2>
		</div>
		<p>
			This project is built and supported under the <a target="_blank"
				href="http://www.informea.org/api">InforMEA</a> project. Please
			visit the support page where you can find up to date documentation.
		</p>
	</div>
	<!-- /container -->
</body>
</html>
