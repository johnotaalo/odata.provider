<%@page import="java.util.Map"%>
<%@page import="org.informea.odata.util.ToolkitUtil"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.informea.odata.producer.toolkit.Configuration"%>
<%@page import="java.util.prefs.*"%>
<% 
Configuration cfg = Configuration.getInstance();
Map<String, Object>update = ToolkitUtil.checkUpdate();
boolean needsUpdate = (Boolean)update.get("needsUpdate");
pageContext.setAttribute("needsUpdate", needsUpdate);
%>
    <jsp:include page="WEB-INF/includes/header.jsp">
        <jsp:param name="html_title" value="InforMEA OData provider" />
        <jsp:param name="current_menu_item" value="update" />
    </jsp:include>
    <h1>Toolkit update</h1>

    <c:if test="${needsUpdate}">
        <p>
            There is a new toolkit version available. We strongly recommend to update to the latest version of the
            toolkit in order to benefit from the security updates and also new features included into the toolkit.
        </p>
        <h2>Summary of the update</h2>
        <table class="table">
            <tr>
                <th>Current version</th>
                <td><%= ToolkitUtil.getToolkitVersion() %></td>
            </tr>
            <tr>
                <th>New version</th>
                <td><%= update.get("remoteVersion") %></td>
            </tr>
            <tr>
                <td><label for="changes">Changes</label></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td colspan="2">
                    <textarea id="changes" rows="10" cols="120"><%= update.get("changes") %></textarea>
                </td>
            </tr>
        </table>
    </c:if>
    
    <h2>How to update the toolkit</h2>
    <p>
        Download the latest toolkit, available from the InforMEA website
        <br /> 
        <a class="btn btn-large btn-primary" href="http://www.informea.org/api-data/dist/toolkit-webapp-latest.zip">Download</a>
    </p>
    <p>
        Steps for the update:
        <ol>
            <li>Backup the old provider somewhere (directory <code>$TOMCAT_HOME/webapps/informea</code>)</li>
            <li>Read the change log above to learn the changes between your current version and newer version. 
                Look closer for compatibility changes into the structures. It may be required to refactor the database views.
            </li>
            <li>Remove the old application directory (i.e. <code>$TOMCAT_HOME/webapps/informea</code>)</li>
            <li>
                Copy the ZIP archive (WAR) to your server replacing the old one 
                (<code>$TOMCAT_HOME/webapps/informea</code>) and unpack it
            </li>
            <li>
                (<em>Optional</em>) Edit the <code>WEB-INF/classes/informea.api.properties</code> file to suit your needs (prefix etc.)
                <br />
                <em>NOTE:</em> editing <code>informea.preferences-api.prefix</code> is required only if you host 
                multiple toolkits on the same servlet container.
            </li>
            <li>
                Restart the servlet container to load the new application
            </li>
            <li>
                Test that the new service works fine
            </li>
        </ol>
    </p>

    <h3>Technical notes</h3>
    <ol>
        <li>
            The configuration of the toolkit is using 
            <a href="http://docs.oracle.com/javase/7/docs/technotes/guides/preferences/">Preferences API</a>. This means
            that the configuration file storage path is platform dependent:
            <table class="table">
                <tr>
                    <th>Windows</th>
                    <td>Windows Registry &reg;</td>
                </tr>
                <tr>
                    <th>Linux</th>
                    <td><code>~/.java/userPrefs/prefs.xml</code> - Home of the user which Tomcat is running on</td>
                </tr>
                <tr>
                    <th>Apple OSX</th>
                    <td><code>~/Library/Preferences/com.apple.java.util.prefs.plist</code> - Home of the user which Tomcat is running on)</td>
                </tr>
            </table>
            <p>If you remove this file and restart Tomcat, the toolkit settings are reset</p>
        </li>
    </ol>
    <jsp:include page="WEB-INF/includes/footer.jsp" />