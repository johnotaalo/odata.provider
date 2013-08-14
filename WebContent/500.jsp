<%@ page isErrorPage="true" %>
<h1>An internal error has occurred (500)</h1>
<p>
    The error message is: <em><%= exception.getMessage() %></em>
</p>
<h2>Technical details</h2>
<pre>
   <% exception.printStackTrace(new java.io.PrintWriter(out)); %>
</pre>
