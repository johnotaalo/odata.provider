<%@page import="org.informea.odata.util.ToolkitUtil"%>
<%@page import="org.informea.odata.config.LDAPConfiguration"%>
<%@page import="org.informea.odata.config.Configuration"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
boolean verify = ToolkitUtil.isOnRequest("verify", request);
boolean useLDAP =  ToolkitUtil.isOnRequest(Configuration.USE_LDAP, request);
boolean ldapOK = false;

LDAPConfiguration ldap = LDAPConfiguration.fromHttpRequest(request);

Exception e = null;
boolean validConnection = false;
if(useLDAP) {
    if(verify) {
        try {
            ldapOK = ldap.testConnection();
        } catch(Exception ex) {
            e = ex;
        }
    }
} else {
    ldapOK = true;
}

if((useLDAP && ldapOK) || (verify && !useLDAP)) {
    session.setAttribute("step2.ldap", "done");
    if(useLDAP) {
        session.setAttribute("ldap", ldap);
    }
    response.sendRedirect("entities");
    return;
}

pageContext.setAttribute("useLDAP", useLDAP);
pageContext.setAttribute("ldap", ldap);
pageContext.setAttribute("port", ldap.getPort() <= 0 ? "389" : ldap.getPort());

out.clear();%><jsp:include page="../WEB-INF/includes/header.jsp">
<jsp:param name="html_title" value="Database configuration" />
<jsp:param name="current_menu_item" value="configuration" />
</jsp:include>
<script type="text/javascript">
jQuery(document).ready(function() {
    var ldapCheck = jQuery('#<%= Configuration.USE_LDAP %>');

    ldapCheck.click(function(){
        var enable = ldapCheck.is(':checked');
        jQuery('#dataForm input').each(function(idx, item) {
            var ctrl = jQuery(item);
            var ctrl_id = ctrl.attr('id');
            if(ctrl_id != ldapCheck.attr('id') && ctrl_id != 'next') {
                if(enable) {
                    ctrl.removeAttr('disabled');
                } else {
                    ctrl.attr('disabled', 'disabled');
                }
            }
        });
    });
    <c:if test="${!useLDAP}">
    ldapCheck.trigger('click');
    ldapCheck.trigger('click');
    </c:if>
});

function validateOnSubmit() {
    var ldapCheck = jQuery('#<%= Configuration.USE_LDAP %>');
    var ldapHost = jQuery('#<%= LDAPConfiguration.LDAP_HOST %>');
    var ldapPort = jQuery('#<%= LDAPConfiguration.LDAP_PORT %>');

    var ldapBindDN = jQuery('#<%= LDAPConfiguration.LDAP_BIND_DN %>');
    var ldapPassword = jQuery('#<%= LDAPConfiguration.LDAP_PASSWORD %>');

    var userBaseDN = jQuery('#<%= LDAPConfiguration.LDAP_USER_BASE_DN %>');
    var userQuery = jQuery('#<%= LDAPConfiguration.LDAP_USER_QUERY_FILTER %>');

    var usersBaseDN = jQuery('#<%= LDAPConfiguration.LDAP_USERS_BASE_DN %>');
    var usersQuery = jQuery('#<%= LDAPConfiguration.LDAP_USERS_QUERY_FILTER %>');

    var mappingId = jQuery('#<%= LDAPConfiguration.LDAP_MAPPING_ID %>');
    var mappingFirstName = jQuery('#<%= LDAPConfiguration.LDAP_MAPPING_FIRST_NAME %>');
    var mappingLastName = jQuery('#<%= LDAPConfiguration.LDAP_MAPPING_LAST_NAME %>');

    if(ldapCheck.is(':checked')) {
        if(ldapHost.val() == '') {
            ldapHost.parent().addClass('has-error');
            jQuery('.help-block', ldapHost.parent()).text("Please enter LDAP hostname");
            return false;
        }
        if(ldapPort.val() == '') {
            ldapPort.parent().addClass('has-error');
            jQuery('.help-block', ldapPort.parent()).text("Please enter LDAP server TCP port");
            return false;
        }
        if(ldapBindDN.val() == '') {
            ldapBindDN.parent().addClass('has-error');
            jQuery('.help-block', ldapBindDN.parent()).text("Please enter LDAP bind dn");
            return false;
        }
        if(ldapPassword.val() == '') {
            ldapPassword.parent().addClass('has-error');
            jQuery('.help-block', ldapPassword.parent()).text("Please enter LDAP password");
            return false;
        }
        if(userBaseDN.val() == '') {
            userBaseDN.parent().addClass('has-error');
            jQuery('.help-block', userBaseDN.parent()).text("Please enter base DN for the search of a single user");
            return false;
        }
        if(userQuery.val() == '') {
            userQuery.parent().addClass('has-error');
            jQuery('.help-block', userQuery.parent()).text("Please enter filter for single user");
            return false;
        }
        if(usersBaseDN.val() == '') {
            usersBaseDN.parent().addClass('has-error');
            jQuery('.help-block', usersBaseDN.parent()).text("Please enter base DN for the searching users");
            return false;
        }
        if(usersQuery.val() == '') {
            usersQuery.parent().addClass('has-error');
            jQuery('.help-block', usersQuery.parent()).text("Please enter filter for multiple users");
            return false;
        }
        if(mappingId.val() == '') {
            mappingId.parent().addClass('has-error');
            jQuery('.help-block', mappingId.parent()).text("Please enter mapping for ID field");
            return false;
        }
        if(mappingFirstName.val() == '') {
            mappingFirstName.parent().addClass('has-error');
            jQuery('.help-block', mappingFirstName.parent()).text("Please enter mapping for first name");
            return false;
        }
        if(mappingLastName.val() == '') {
            mappingLastName.parent().addClass('has-error');
            jQuery('.help-block', mappingLastName.parent()).text("Please enter mapping for last name");
            return false;
        }
    }
}
</script>
<div class="content">
    <ol class="breadcrumb">
        <li><a href="<%= ToolkitUtil.url(request, null) %>">Home</a></li>
        <li><a href="<%= ToolkitUtil.url(request, "/configuration") %>">Configuration</a></li>
        <li class="active">LDAP</li>
    </ol>
    <div class="progress progress-striped">
        <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
            <span class="sr-only">40% Complete</span>
        </div>
    </div>
    <h1>LDAP server</h1>
    <div class="row">
        <form id="dataForm" action="" method="post" onsubmit="return validateOnSubmit();" role="form" class="form-horizontal">
            <% if(useLDAP && verify && !ldapOK) { %>
            <div class="alert alert-danger">
                <h4>Error validating the data</h4>
                <% if(e != null) { %>
                    <%= e.getMessage() %>
                <% } %>
                <p>
                    Please review the settings and try again.
                </p>
            </div>
            <% } %>
            <div class="col col-md-6">
            <div class="form-group">
                <div class="col-sm-10">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="<%= Configuration.USE_LDAP %>" 
                                id="<%= Configuration.USE_LDAP %>" <c:if test="${useLDAP}">checked="checked"</c:if>
                                value="ON" tabindex="1" />
                                Use LDAP
                        </label>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label for="<%= LDAPConfiguration.LDAP_HOST%>" class="col-sm-3 control-label">Server *</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control"
                        name="<%= LDAPConfiguration.LDAP_HOST %>" 
                        id="<%= LDAPConfiguration.LDAP_HOST %>"
                        value="<c:out value="${ldap.getHost()}" />" 
                        tabindex="2" />
                    <p class="help-block"></p>
                </div>
            </div>

            <div class="form-group">
                <label for="<%= LDAPConfiguration.LDAP_PORT %>" class="col-sm-3 control-label">Server port *</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control"
                        name="<%= LDAPConfiguration.LDAP_PORT %>" 
                        id="<%= LDAPConfiguration.LDAP_PORT %>" 
                        value="<c:out value="${port}" />" tabindex="3" />
                    <p class="help-block"></p>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-3 col-sm-6">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="<%= LDAPConfiguration.LDAP_USE_TLS %>" 
                                id="<%= LDAPConfiguration.LDAP_USE_TLS %>" 
                                value="ON" tabindex="4" disabled="disabled" />
                                Use TLS <span class="label label-error">Not implemented</span>
                        </label>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label for="<%= LDAPConfiguration.LDAP_BIND_DN %>" class="col-sm-3 control-label">Bind DN *</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control"
                        name="<%= LDAPConfiguration.LDAP_BIND_DN %>" 
                        id="<%= LDAPConfiguration.LDAP_BIND_DN %>" 
                        value="<c:out value="${ldap.getBindDN()}" />" tabindex="5" />
                    <span class="help-block">LDAP credential to connect to the server (-D)</span>
                </div>
            </div>

            <div class="form-group">
                <label for="<%= LDAPConfiguration.LDAP_PASSWORD %>" class="col-sm-3 control-label">Password *</label>
                <div class="col-sm-6">
                    <input type="password" class="form-control"
                        name="<%= LDAPConfiguration.LDAP_PASSWORD %>" 
                        id="<%= LDAPConfiguration.LDAP_PASSWORD %>" 
                        value="<c:out value="" />" tabindex="6" />
                    <span class="help-block">LDAP credential to connect to the server (-w)</span>
                </div>
            </div>

            <div class="form-group">
                <label for="<%= LDAPConfiguration.LDAP_USER_BASE_DN %>" class="col-sm-3 control-label">Single user Base DN *</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control"
                        placeholder="ex. ou=People,dc=example,dc=com"
                        name="<%= LDAPConfiguration.LDAP_USER_BASE_DN %>" 
                        id="<%= LDAPConfiguration.LDAP_USER_BASE_DN %>" 
                        value="<c:out value="${ldap.getUserBaseDN()}" />" tabindex="7" />
                    <span class="help-block">Base of the user filtering</span>
                </div>
            </div>

            <div class="form-group">
                <label for="<%= LDAPConfiguration.LDAP_USER_QUERY_FILTER %>" class="col-sm-3 control-label">Query filter for single user *</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control"
                        placeholder="ex: uid=%s"
                        name="<%= LDAPConfiguration.LDAP_USER_QUERY_FILTER %>" 
                        id="<%= LDAPConfiguration.LDAP_USER_QUERY_FILTER %>" 
                        value="<c:out value="${ldap.getUserQueryFilter()}" />" tabindex="8" />
                    <span class="help-block">%s will be replaced with the actual ID</span>
                </div>
            </div>

            <div class="form-group">
                <label for="<%= LDAPConfiguration.LDAP_USERS_BASE_DN %>" class="col-sm-3 control-label">Users query Base DN *</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control"
                        placeholder="ex. ou=People,dc=example,dc=com"
                        name="<%= LDAPConfiguration.LDAP_USERS_BASE_DN %>" 
                        id="<%= LDAPConfiguration.LDAP_USERS_BASE_DN %>" 
                        value="<c:out value="${ldap.getUsersBaseDN()}" />" tabindex="9" />
                    <span class="help-block">Base DN for querying all users</span>
                </div>
            </div>

            <div class="form-group">
                <label for="<%= LDAPConfiguration.LDAP_USERS_QUERY_FILTER %>" class="col-sm-3 control-label">Query filter for users *</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control"
                        placeholder="o=CMS"
                        name="<%= LDAPConfiguration.LDAP_USERS_QUERY_FILTER %>" 
                        id="<%= LDAPConfiguration.LDAP_USERS_QUERY_FILTER %>" 
                        value="<c:out value="${ldap.getUsersQueryFilter()}" />" tabindex="10" />
                    <span class="help-block">Only users matching this filter will be returned as Contacts</span>
                </div>
            </div>
        </div>

        <div class="col col-md-6">
            <h3>Attribute mappings</h3>
            <hr />
            <div class="form-group">
                <label for="<%= LDAPConfiguration.LDAP_MAPPING_ID %>" class="col-sm-3 control-label">ID attribute *</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control"
                        placeholder="e.g. uid"
                        name="<%= LDAPConfiguration.LDAP_MAPPING_ID %>" 
                        id="<%= LDAPConfiguration.LDAP_MAPPING_ID %>" 
                        value="<%= ldap.getMapping(LDAPConfiguration.LDAP_MAPPING_ID)%>" tabindex="11" />
                    <span class="help-block">Must point to attribute containing user unique ID (DN is not working for filtering)</span>
                </div>
            </div>

            <div class="form-group">
                <label for="<%= LDAPConfiguration.LDAP_MAPPING_PREFIX %>" class="col-sm-3 control-label">User prefix</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control"
                        placeholder="e.g. prefix"
                        name="<%= LDAPConfiguration.LDAP_MAPPING_PREFIX %>" 
                        id="<%= LDAPConfiguration.LDAP_MAPPING_PREFIX %>" 
                        value="<%= ldap.getMapping(LDAPConfiguration.LDAP_MAPPING_PREFIX)%>" tabindex="12" />
                    <span class="help-block">User prefix (Mr., Mrs. etc.)</span>
                </div>
            </div>

            <div class="form-group">
                <label for="<%= LDAPConfiguration.LDAP_MAPPING_FIRST_NAME %>" class="col-sm-3 control-label">First name *</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control"
                        placeholder="e.g. givenName"
                        name="<%= LDAPConfiguration.LDAP_MAPPING_FIRST_NAME %>" 
                        id="<%= LDAPConfiguration.LDAP_MAPPING_FIRST_NAME %>" 
                        value="<%= ldap.getMapping(LDAPConfiguration.LDAP_MAPPING_FIRST_NAME)%>" tabindex="13" />
                    <span class="help-block"></span>
                </div>
            </div>

            <div class="form-group">
                <label for="<%= LDAPConfiguration.LDAP_MAPPING_LAST_NAME %>" class="col-sm-3 control-label">Last name *</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control"
                        placeholder="e.g. cn"
                        name="<%= LDAPConfiguration.LDAP_MAPPING_LAST_NAME %>" 
                        id="<%= LDAPConfiguration.LDAP_MAPPING_LAST_NAME %>" 
                        value="<%= ldap.getMapping(LDAPConfiguration.LDAP_MAPPING_LAST_NAME)%>" tabindex="14" />
                    <span class="help-block"></span>
                </div>
            </div>

            <div class="form-group">
                <label for="<%= LDAPConfiguration.LDAP_MAPPING_ADDRESS %>" class="col-sm-3 control-label">Address</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control"
                        placeholder="e.g. &dollar;{postalAddress} + &dollar;{postalCode}"
                        name="<%= LDAPConfiguration.LDAP_MAPPING_ADDRESS %>" 
                        id="<%= LDAPConfiguration.LDAP_MAPPING_ADDRESS %>" 
                        value="<%= ldap.getMapping(LDAPConfiguration.LDAP_MAPPING_ADDRESS)%>" tabindex="15" />
                    <span class="help-block">User's mailing address</span>
                </div>
            </div>

            <div class="form-group">
                <label for="<%= LDAPConfiguration.LDAP_MAPPING_COUNTRY %>" class="col-sm-3 control-label">Country</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control"
                        placeholder="e.g. c"
                        name="<%= LDAPConfiguration.LDAP_MAPPING_COUNTRY %>" 
                        id="<%= LDAPConfiguration.LDAP_MAPPING_COUNTRY %>" 
                        value="<%= ldap.getMapping(LDAPConfiguration.LDAP_MAPPING_COUNTRY)%>" tabindex="16" />
                    <span class="help-block">Field containing user's country represented as ISO 2 or 3-letter code</span>
                </div>
            </div>

            <div class="form-group">
                <label for="<%= LDAPConfiguration.LDAP_MAPPING_DEPARTMENT %>" class="col-sm-3 control-label">Department</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control"
                        placeholder="e.g. c"
                        name="<%= LDAPConfiguration.LDAP_MAPPING_DEPARTMENT %>" 
                        id="<%= LDAPConfiguration.LDAP_MAPPING_DEPARTMENT %>" 
                        value="<%= ldap.getMapping(LDAPConfiguration.LDAP_MAPPING_DEPARTMENT)%>" tabindex="17" />
                    <span class="help-block">User's department</span>
                </div>
            </div>

            <div class="form-group">
                <label for="<%= LDAPConfiguration.LDAP_MAPPING_EMAIL %>" class="col-sm-3 control-label">E-Mail</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control"
                        placeholder="e.g. mail"
                        name="<%= LDAPConfiguration.LDAP_MAPPING_EMAIL %>" 
                        id="<%= LDAPConfiguration.LDAP_MAPPING_EMAIL %>" 
                        value="<%= ldap.getMapping(LDAPConfiguration.LDAP_MAPPING_EMAIL)%>" tabindex="18" />
                    <span class="help-block"></span>
                </div>
            </div>

            <div class="form-group">
                <label for="<%= LDAPConfiguration.LDAP_MAPPING_FAX %>" class="col-sm-3 control-label">Fax number</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control"
                        placeholder="e.g. facsimileNumber"
                        name="<%= LDAPConfiguration.LDAP_MAPPING_FAX %>" 
                        id="<%= LDAPConfiguration.LDAP_MAPPING_FAX %>" 
                        value="<%= ldap.getMapping(LDAPConfiguration.LDAP_MAPPING_FAX)%>" tabindex="19" />
                    <span class="help-block"></span>
                </div>
            </div>

            <div class="form-group">
                <label for="<%= LDAPConfiguration.LDAP_MAPPING_INSTITUTION %>" class="col-sm-3 control-label">Institution name</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control"
                        placeholder="e.g. o"
                        name="<%= LDAPConfiguration.LDAP_MAPPING_INSTITUTION %>" 
                        id="<%= LDAPConfiguration.LDAP_MAPPING_INSTITUTION %>" 
                        value="<%= ldap.getMapping(LDAPConfiguration.LDAP_MAPPING_INSTITUTION)%>" tabindex="20" />
                    <span class="help-block">User's institution name</span>
                </div>
            </div>

            <div class="form-group">
                <label for="<%= LDAPConfiguration.LDAP_MAPPING_PHONE %>" class="col-sm-3 control-label">Phone</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control"
                        placeholder="e.g. facsimileNumber"
                        name="<%= LDAPConfiguration.LDAP_MAPPING_PHONE %>" 
                        id="<%= LDAPConfiguration.LDAP_MAPPING_PHONE %>" 
                        value="<%= ldap.getMapping(LDAPConfiguration.LDAP_MAPPING_PHONE)%>" tabindex="21" />
                    <span class="help-block">User's telephone number</span>
                </div>
            </div>

            <div class="form-group">
                <label for="<%= LDAPConfiguration.LDAP_MAPPING_POSITION %>" class="col-sm-3 control-label">Position</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control"
                        placeholder="e.g. position"
                        name="<%= LDAPConfiguration.LDAP_MAPPING_POSITION %>" 
                        id="<%= LDAPConfiguration.LDAP_MAPPING_POSITION %>" 
                        value="<%= ldap.getMapping(LDAPConfiguration.LDAP_MAPPING_POSITION)%>" tabindex="22" />
                    <span class="help-block">User's position within organisation</span>
                </div>
            </div>
    
            <div class="form-group">
                <label for="<%= LDAPConfiguration.LDAP_MAPPING_UPDATED %>" class="col-sm-3 control-label">Updated</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control"
                        placeholder="e.g. lastUpdate"
                        name="<%= LDAPConfiguration.LDAP_MAPPING_UPDATED %>" 
                        id="<%= LDAPConfiguration.LDAP_MAPPING_UPDATED %>" 
                        value="<%= ldap.getMapping(LDAPConfiguration.LDAP_MAPPING_UPDATED)%>" tabindex="23" />
                    <span class="help-block">Date type holding the last update date (not <code>modifyTimestamp</code>)</span>
                </div>
            </div>

            <div class="form-group">
                <label for="<%= LDAPConfiguration.LDAP_MAPPING_TREATIES %>" class="col-sm-3 control-label">Treaties</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control"
                        placeholder="e.g. organization"
                        name="<%= LDAPConfiguration.LDAP_MAPPING_TREATIES %>" 
                        id="<%= LDAPConfiguration.LDAP_MAPPING_TREATIES %>" 
                        value="<%= ldap.getMapping(LDAPConfiguration.LDAP_MAPPING_TREATIES)%>" tabindex="24" />
                    <span class="help-block">
                        Membership (type string, repetitive). If unavailable, write down <em>odata_name</em> of your treaty. 
                        This is encoded in URL of InforMEA <a target="_blank" href="http://www.informea.org/treaties">treaty page</a>.
                        Click there on your treaty and copy the last part of the URL, e.g. http://www.informea.org/treaties/<strong>aewa</strong>
                    </span>
                </div>
            </div>
    
            <div class="form-group">
                <label for="<%= LDAPConfiguration.LDAP_MAPPING_PRIMARY_NFP %>" class="col-sm-3 control-label">Primary NFP</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control"
                        placeholder="e.g. primary"
                        name="<%= LDAPConfiguration.LDAP_MAPPING_PRIMARY_NFP %>" 
                        id="<%= LDAPConfiguration.LDAP_MAPPING_PRIMARY_NFP %>" 
                        value="<%= ldap.getMapping(LDAPConfiguration.LDAP_MAPPING_PRIMARY_NFP)%>" tabindex="25" />
                    <span class="help-block">Boolean field holding true if this is primary NFP</span>
                </div>
            </div>
    </div><!-- /.col-md-8 -->
            <div class="form-group">
                <div class="col-sm-1">
                    <input id="next" type="submit" name="verify" value="Next" tabindex="26" class="btn btn-primary pull-right" />
                </div>
            </div>
        </form>
    </div><!-- /.row -->
</div>
<jsp:include page="../WEB-INF/includes/footer.jsp" />