<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.informea.odata.util.ToolkitUtil"%>
<jsp:include page="../WEB-INF/includes/header.jsp">
    <jsp:param name="html_title" value="InforMEA OData provider" />
    <jsp:param name="current_menu_item" value="faq" />
</jsp:include>
<div class="content">

    <h1>Frequently Asked Questions</h1>

    <div class="row">
    <div class="col-md-8">
        <div class="panel-group" id="accordion">

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#q1">
                            Where are the toolkit preferences stored?
                            <span class="pull-right glyphicon glyphicon-chevron-down"></span>
                        </a>
                    </h4>
                </div>
                <div id="q1" class="panel-collapse collapse in">
                    <div class="panel-body">
                        The applications is using the
                        <a target="_blank" href="http://download.oracle.com/javase/6/docs/technotes/guides/preferences/overview.html">Java Preferences API</a>
                        to store application configuration. We use the <code>userRoot()</code> store,
                        which means that this is settings are stored inside an XML file under user's home directory (<code>~/.java/.userPrefs/prefs.xml</code>).
                        The user in this context reffers to the user that runs the current Tomcat process.
                        <br />
                        <strong>Note</strong>: Starting with version 1.5.4, the toolkit uses new namespace mechanism to host multiple toolkits on same server.
                        Please edit WEB-INF/classes/informea.api.properties file to set an unique namespace for each toolkit instance
                    </div>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#q2">
                            What database systems are supported?
                            <span class="pull-right glyphicon glyphicon-chevron-down"></span>
                        </a>
                    </h4>
                </div>
                <div id="q2" class="panel-collapse collapse">
                    <div class="panel-body">
                        Currently only MySQL is supported. If we find the need to support other databases,
                        toolkit can be easily modify to implement any JDBC enabled databases. Basically you will need to add the JDBC driver inside
                        <code>WEB-INF/lib</code> and implement the appropriate code inside the following classes:
                        <ul class="full">
                            <li><code>org.informea.odata.util.JDBCHelper::getDBConnection() and ::getTables()</code></li>
                            <li><code>org.informea.odata.producer.toolkit.Configuration::getHibernateDialect()</code></li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#q3">
                            How can I configure login for the Configuration area?
                            <span class="pull-right glyphicon glyphicon-chevron-down"></span>
                        </a>
                    </h4>
                </div>
                <div id="q3" class="panel-collapse collapse">
                    <div class="panel-body">
                    The configuration area is protected by HTTP BASIC authentication in order to prevent anyone to access the service configuration area.
                    The configuration is handled via Tomcat's <code>Realm</code> authentication. This means that in order to authenticate, you will need to create a new file
        <code>$CATALINA_HOME/conf/tomcat-users.xml</code> and add there a new role called "informea" and create an username with password, and assign the role "informea".
        The file might look like this:
        <pre>
            &lt;tomcat-users&gt;
                &lt;role rolename="tomcat" /&gt;
                &lt;role rolename="informea" /&gt;
                &lt;user username="tomcat" password="tomcat" roles="tomcat" /&gt;
                &lt;user username="informea" password="informea" roles="informea" /&gt;
            &lt;/tomcat-users&gt;
        </pre>
        The <code>server.xml</code> file might be required to be modified, to use the tomcat-users.xml file.
        Here's an excerpt from server.xml:
        <pre>
            &lt;Realm className="org.apache.catalina.realm.LockOutRealm"&gt;
                &lt;Realm className="org.apache.catalina.realm.MemoryRealm" debug="4" /&gt;
            &lt;/Realm&gt;
        </pre>
                        In order for changes to take effect, remember to restart Tomcat after changing these files.
                    </div>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#q4">
                            Is it safe to let Tomcat serve the content directly?
                            <span class="pull-right glyphicon glyphicon-chevron-down"></span>
                        </a>
                    </h4>
                </div>
                <div id="q4" class="panel-collapse collapse">
                    <div class="panel-body">
                        The best configuration will be to have Apache HTTP server in front of 
                        Tomcat and use mod_proxy or mod_ajp (or newer mod_proxy_ajp) to handle the requests.
                    </div>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#q5">
                                While browsing my web service with Sesame OData browser I get <code>SecurityException</code>.
                            <span class="pull-right glyphicon glyphicon-chevron-down"></span>
                        </a>
                    </h4>
                </div>
                <div id="q5" class="panel-collapse collapse">
                    <div class="panel-body">
                    You'll have to put in the root of your web server two files that allows these clients to browse your server:
                    <br />
                    1. crossdomain.xml
        <pre>
            &lt;?xml version="1.0"?&gt;
            &lt;!DOCTYPE cross-domain-policy SYSTEM "http://www.macromedia.com/xml/dtds/cross-domain-policy.dtd"&gt;
            &lt;cross-domain-policy&gt;
              &lt;allow-http-request-headers-from domain="*" headers="SOAPAction,Content-Type"/&gt;
            &lt;/cross-domain-policy&gt;
        </pre>
        <br />
                    2. clientaccesspolicy.xml
        <pre>
            &lt;?xml version="1.0" encoding="utf-8"?&gt;
            &lt;access-policy&gt;
              &lt;cross-domain-access&gt;
                &lt;policy&gt;
                  &lt;allow-from http-request-headers="SOAPAction"&gt;
                    &lt;domain uri="*"/&gt;
                  &lt;/allow-from&gt;
                  &lt;grant-to&gt;
                    &lt;resource path="/" include-subpaths="true"/&gt;
                  &lt;/grant-to&gt;
                &lt;/policy&gt;
              &lt;/cross-domain-access&gt;
            &lt;/access-policy&gt;
        </pre>
                    </div>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#q6">
                            I am running JRun and I want to install the toolkit
                            <span class="pull-right glyphicon glyphicon-chevron-down"></span>
                        </a>
                    </h4>
                </div>
                <div id="q6" class="panel-collapse collapse">
                    <div class="panel-body">
                        JRun is not supported. Basically it's dead and its' a headache to support this platform due to poor JSP support.
                    </div>
                </div>
            </div>
    </div><!-- /.panel-group -->
    </div><!-- /.col-md-8 -->
    </div><!-- /.row -->
</div>
<jsp:include page="../WEB-INF/includes/footer.jsp" />