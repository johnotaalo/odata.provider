<%@page import="org.informea.odata.producer.toolkit.Producer"%>
<%@page import="java.net.URLConnection"%>
<%@page import="java.net.URL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
Informea Producer version: <strong><%= Producer.MAJOR %>.<%= Producer.MINOR %>.<%= Producer.REVISION %><%= (Producer.BETA == true ? " beta" : "") %></strong>
<br />
Checking for newer version ...
<%
    try {
        boolean needsUpdate = false;
        URL url = new URL(Producer.UPDATE_URL);
        URLConnection c = url.openConnection();
        c.setConnectTimeout(4000);
        java.io.InputStream fis = c.getInputStream();
        java.util.Properties props = new java.util.Properties();
        props.load(fis);

        int major = Integer.parseInt(props.getProperty("MAJOR"));
        int minor = Integer.parseInt(props.getProperty("MINOR"));
        int revision = Integer.parseInt(props.getProperty("REVISION"));
        boolean beta = Boolean.parseBoolean(props.getProperty("BETA"));
        String changes = props.getProperty("CHANGES");

        if(major > Producer.MAJOR ) {
            needsUpdate = true;
        } else {
            if(minor > Producer.MINOR) {
                needsUpdate = true;
            } else {
                if(revision > Producer.REVISION) {
                    needsUpdate = true;
                } else {
                    if(Producer.BETA && !beta) {
                        needsUpdate = true;
                    }
                }
            }
        }
        if(needsUpdate) {
		%>
            New version available: <strong class="text-red"><%= major %>.<%= minor %>.<%= revision %><%= (beta) ? " beta" : "" %></strong>
            <pre><%= changes %></pre>
		<%
        } else {
		%>
			<strong class="text-green">Toolkit is up to date</strong>
		<%
        }
    } catch(Exception ex) {
	%>
		Cannot check (<%= ex.getMessage() %>)
	<%
    }
%>