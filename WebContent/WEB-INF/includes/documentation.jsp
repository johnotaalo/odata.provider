
<h2>Documetation</h2>
<h3>How to get help</h3>
There are few ways to get help:
<ul>
    <li>Use the tutorial below</li>
    <li>Head over to the InforMEA support <a target="_blank" href="http://support.informea.org/projects/informea/wiki">wiki page</a></li>
    <li>Ask a question on the <a target="_blank" href="http://support.informea.org/projects/informea/boards/6">support forum</a></li>
</ul>
We are trying to get back to your inquiry as soon as possible.

<h3>Preparing the database views for exposing entities</h3>

<p>
    Each entity exposed through the OData protocol is defined by a set of attributes. 
    Some of these attributes are required, some of them are optional.
    Take for example a <em>Contact</em> which has attributes such as <em>first_name, last_name, address, email etc</em>.
    All the entities and their attributes are defined by the <a target="_blank" href="http://www.informea.org/api">InforMEA API document</a>
</p>

<p>
    Each MEA may have the entities stored in different tables inside their database, and the tables/columns may have 
    different names. In order to make the toolkit portable for all the MEAs, we made a convention:
    <strong>
        <em>
            create views inside your database that expose the entities and those views are built on top of SQL 
            queries that gather the data from appropriate tables and name the columns with appropriate aliases.
        </em>
    </strong>
    <br />
    This way we achieve a common interface to communicate with the database. The toolkit will query those views and extract the data accordingly.
</p>

<p>
    Below we describe configuration for each entity and its corresponding required databaase views
</p>

<div class="panel-group" id="accordion">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a data-toggle="collapse" data-parent="#accordion" href="#acc_decisions">
                    Decisions <span class="glyphicon glyphicon-chevron-down pull-right"></span>
                </a>
            </h4>
        </div>
        <div id="acc_decisions" class="panel-collapse collapse">
            <div class="panel-body">
                <jsp:include page="doc_decisions.jsp" />
            </div>
        </div>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a data-toggle="collapse" data-parent="#accordion" href="#acc_contacts">
                    Contacts <span class="glyphicon glyphicon-chevron-down pull-right"></span>
                </a>
            </h4>
        </div>
        <div id="acc_contacts" class="panel-collapse collapse">
            <div class="panel-body">
                <jsp:include page="doc_contacts.jsp" />
            </div>
        </div>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a data-toggle="collapse" data-parent="#accordion" href="#acc_contacts">
                    Contacts <span class="glyphicon glyphicon-chevron-down pull-right"></span>
                </a>
            </h4>
        </div>
        <div id="acc_contacts" class="panel-collapse collapse">
            <div class="panel-body">
                <jsp:include page="doc_contacts.jsp" />
            </div>
        </div>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a data-toggle="collapse" data-parent="#accordion" href="#acc_countryprofiles">
                    Ratification data (CountryProfiles) <span class="glyphicon glyphicon-chevron-down pull-right"></span>
                </a>
            </h4>
        </div>
        <div id="acc_countryprofiles" class="panel-collapse collapse">
            <div class="panel-body">
                <jsp:include page="doc_countryprofiles.jsp" />
            </div>
        </div>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a data-toggle="collapse" data-parent="#accordion" href="#acc_countryreports">
                    Country Reports <span class="glyphicon glyphicon-chevron-down pull-right"></span>
                </a>
            </h4>
        </div>
        <div id="acc_countryreports" class="panel-collapse collapse">
            <div class="panel-body">
                <jsp:include page="doc_countryreports.jsp" />
            </div>
        </div>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a data-toggle="collapse" data-parent="#accordion" href="#acc_meetings">
                    Meetings <span class="glyphicon glyphicon-chevron-down pull-right"></span>
                </a>
            </h4>
        </div>
        <div id="acc_meetings" class="panel-collapse collapse">
            <div class="panel-body">
                <jsp:include page="doc_meetings.jsp" />
            </div>
        </div>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a data-toggle="collapse" data-parent="#accordion" href="#acc_nationalplans">
                    National Plans <span class="glyphicon glyphicon-chevron-down pull-right"></span>
                </a>
            </h4>
        </div>
        <div id="acc_nationalplans" class="panel-collapse collapse">
            <div class="panel-body">
                <jsp:include page="doc_nationalplans.jsp" />
            </div>
        </div>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a data-toggle="collapse" data-parent="#accordion" href="#acc_sites">
                   WHC/Ramsar sites <span class="glyphicon glyphicon-chevron-down pull-right"></span>
                </a>
            </h4>
        </div>
        <div id="acc_sites" class="panel-collapse collapse">
            <div class="panel-body">
                <jsp:include page="doc_sites.jsp" />
            </div>
        </div>
    </div>
</div>

<jsp:include page="doc_example.jsp" />