<%@ page pageEncoding="UTF-8" %>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.junit.runner.notification.Failure"%>
<%@page import="java.util.List"%>
<%@page import="org.informea.odata.constants.Treaty"%>
<%
    Map<String, Class> suites = new HashMap<String, Class>();
    suites.put("Toolkit Producer", org.informea.odata.producer.toolkit.test.ProducerTest.class);
    suites.put("Meetings", org.informea.odata.producer.toolkit.test.MeetingsTest.class);
    suites.put("Decisions", org.informea.odata.producer.toolkit.test.DecisionsTest.class);
    suites.put("Decisions", org.informea.odata.producer.toolkit.test.DecisionsTest.class);
    suites.put("Contacts", org.informea.odata.producer.toolkit.test.ContactsTest.class);
    suites.put("Country Profiles", org.informea.odata.producer.toolkit.test.CountryProfilesTest.class);
    suites.put("Country Reports", org.informea.odata.producer.toolkit.test.CountryReportsTest.class);
    suites.put("Sites", org.informea.odata.producer.toolkit.test.SitesTest.class);

    for(Map.Entry<String, Class> test : suites.entrySet()) {
        String name = test.getKey();
        Class klass = test.getValue();
%>
        <h2><%=name%></h2>
        <%
            org.junit.runner.Result r = org.junit.runner.JUnitCore.runClasses(klass);
        %>
        <table border="1">
            <tbody>
                <tr><td>Status</td><td><%= r.wasSuccessful() ? "<span class='success'>Success</span>" : "<span class='failure'>Failed</span>" %></td></tr>
                <tr><td>Tests run</td><td><%= r.getRunCount() %></td></tr>
                <tr><td>Ignored tests</td><td><%= r.getIgnoreCount() %></td></tr>
                <tr><td>Failed tests</td><td><%= r.getFailureCount() %></td></tr>
                <tr><td>Time</td><td><%= r.getRunTime() %> ms</td></tr>
                <tr>
                    <td>Failures</td>
                    <td>
                        <%
                            if(!r.wasSuccessful()) {
                                out.print("<ul class='failure'>");
                                List<Failure> failures = r.getFailures();
                                for (Failure failure : failures) {
                                    out.print(String.format("<li title=\"%s\">%s (%s)</li>", failure.getTrace(), failure.getDescription(), failure.getMessage()));
                                }
                                out.print("</ul>");
                            }
                        %>
                    </td>
                </tr>
            </tbody>
        </table>
<% } %>
