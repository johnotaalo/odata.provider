<%@page import="org.informea.odata.util.ToolkitUtil"%>
<h3><img src="<%= ToolkitUtil.url(request, "/themes/images/warning-big.png") %>" style="vertical-align: middle" />Security notice</h3>
<br />
Configuration area is protected via HTTP Basic authentication, configured in:
<ul>
    <li><span class="code">$TOMCAT_HOME/conf/tomcat-users.xml</span> - where you define an username/password and an role "informea" that you assign to new created user - <a target="_blank" href="http://tomcat.apache.org/tomcat-7.0-doc/realm-howto.html">more details ...</a></li>
    <li>
        <span class="code">$TOMCAT_HOME/conf/server.xml</span> - configured to use the MemoryRealm.
        Example:
        <span class="code">&lt;Realm className="org.apache.catalina.realm.MemoryRealm" /&gt;</span>
    </li>
</ul>
