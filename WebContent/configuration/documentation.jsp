
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
    The tables below shows the entities and their properties that must be exposed. More details can be found in the API document.
</p>

<h4>Summary</h4>
<ul>
    <li>
        <a href="#decisions">Decisions</a>
        <ol>
            <li><a href="#informea_decisions">informea_decisions</a></li>
            <li><a href="#informea_decisions_content">informea_decisions_content</a></li>
            <li><a href="#informea_decisions_documents">informea_decisions_documents</a></li>
            <li><a href="#informea_decisions_keywords">informea_decisions_keywords</a></li>
            <li><a href="#informea_decisions_longtitle">informea_decisions_longtitle</a></li>
            <li><a href="#informea_decisions_summary">informea_decisions_summary</a></li>
            <li><a href="#informea_decisions_title">informea_decisions_title</a></li>
        </ol>
    </li>
    <li>
        <a href="#contacts">Contacts</a>
        <ol>
            <li><a href="#informea_contacts">informea_contacts</a></li>
            <li><a href="#informea_contacts_treaties">informea_contacts_treaties</a></li>
        </ol>
    </li>
    <li>
        <a href="#country_profiles">Country Profiles</a>
        <ol>
            <li><a href="#informea_country_profiles">informea_country_profiles</a></li>
        </ol>
    </li>
    <li>
        <a href="#country_reports">Country Reports</a>
        <ol>
            <li><a href="#informea_country_reports">informea_country_reports</a></li>
            <li><a href="#informea_country_reports_title">informea_country_reports_title</a></li>
        </ol>
    </li>
    <li>
        <a href="#meetings">Meetings</a>
        <ol>
            <li><a href="#informea_meetings">informea_meetings</a></li>
            <li><a href="#informea_meetings_description">informea_meetings_description</a></li>
            <li><a href="#informea_meetings_title">informea_meetings_title</a></li>
        </ol>
    </li>
    <li>
        <a href="#national_plans">National Plans</a>
        <ol>
            <li><a href="#informea_national_plans">informea_national_plans</a></li>
            <li><a href="#informea_national_plans_title">informea_national_plans_title</a></li>
        </ol>
    </li>
    <li>
        <a href="#sites">Sites</a>
        <ol>
            <li><a href="#informea_sites">informea_sites</a></li>
        </ol>
    </li>
</ul>

<a name="decisions"></a>
<div class="panel-group" id="accordion">
  <div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-parent="#accordion" href="#decisions">
          Decisions
        </a>
      </h4>
    </div>
    <div id="decisions" class="panel-collapse collapse">
        <div class="panel-body">
        <ul>
            <li>
		        <a name="informea_decisions"></a>
		        1<sup>st</sup> view name: <strong>informea_decisions</strong> - keeps the decisions entities
		        <table class="table table-bordered table-striped">
		            <tr>
		                <th>Property</th><th>Data type (SQL)</th><th>Required (NOT NULL)</th>
		            </tr>
		            <tr>
		                <td>id</td>
		                <td>VARCHAR</td>
		                <td>YES</td>
		            </tr>
		            <tr>
		                <td>link</td>
		                <td>VARCHAR</td>
		                <td>NO</td>
		            </tr>
		            <tr>
		                <td>type</td>
		                <td>VARCHAR</td>
		                <td>YES</td>
		            </tr>
		            <tr>
		                <td>status</td>
		                <td>VARCHAR</td>
		                <td>YES</td>
		            </tr>
		            <tr>
		                <td>number</td>
		                <td>VARCHAR</td>
		                <td>YES</td>
		            </tr>
		            <tr>
		                <td>treaty</td>
		                <td>VARCHAR</td>
		                <td>YES</td>
		            </tr>
		            <tr>
		                <td>published</td>
		                <td>DATETIME/TIMESTAMP</td>
		                <td>YES</td>
		            </tr>
		            <tr>
		                <td>updated</td>
		                <td>DATETIME/TIMESTAMP</td>
		                <td>NO</td>
		            </tr>
		            <tr>
		                <td>meetingId</td>
		                <td>VARCHAR</td>
		                <td>either meetingTitle or meetingId not null</td>
		            </tr>
		            <tr>
		                <td>meetingTitle</td>
		                <td>VARCHAR</td>
		                <td>either meetingTitle or meetingId not null</td>
		            </tr>
		            <tr>
		                <td>meetingUrl</td>
		                <td>VARCHAR</td>
		                <td>NO</td>
		            </tr>
		        </table>
		    </li>
		    <li>
		        <a name="informea_decisions_content"></a>
		        2<sup>nd</sup> view name: <strong>informea_decisions_content</strong> - keeps the <em>content</em> property of decision, which is multilingual (many-to-one related with decision)
		        <table class="table table-bordered table-striped">
		            <tr><th>Property</th><th>Data type (SQL)</th><th>Required (NOT NULL)</th></tr>
		            <tr>
		                <td>id</td>
		                <td>INTEGER</td>
		                <td>YES (PK, composite PK, SQL sequence  etc.)</td>
		            </tr>
		            <tr>
		                <td>decision_id</td>
		                <td>VARCHAR</td>
		                <td>YES (FK to informea_decisions)</td>
		            </tr>
		            <tr>
		                <td>language</td>
		                <td>VARCHAR</td>
		                <td>YES</td>
		            </tr>
		            <tr>
		                <td>content</td>
		                <td>VARCHAR</td>
		                <td>YES</td>
		            </tr>
		        </table>
		    </li>
		    <li>
		        <a name="informea_decisions_documents"></a>
		        3<sup>rd</sup> view name: <strong>informea_decisions_documents</strong> - keeps the <em>documents</em> property of decision, which is related many-to-one related with decision
		        <table class="table table-bordered table-striped">
		            <tr><th>Property</th><th>Data type (SQL)</th><th>Required (NOT NULL)</th></tr>
		            <tr>
		                <td>id</td>
		                <td>INTEGER</td>
		                <td>YES (PK, composite PK, SQL sequence  etc.)</td>
		            </tr>
		            <tr>
		                <td>decision_id</td>
		                <td>VARCHAR</td>
		                <td>YES (FK to informea_decisions)</td>
		            </tr>
		            <tr>
		                <td>diskPath</td>
		                <td>VARCHAR</td>
		                <td>YES</td>
		            </tr>
		            <tr>
		                <td>url</td>
		                <td>VARCHAR</td>
		                <td>YES</td>
		            </tr>
		            <tr>
		                <td>mimeType</td>
		                <td>VARCHAR</td>
		                <td>YES</td>
		            </tr>
		            <tr>
		                <td>language</td>
		                <td>VARCHAR</td>
		                <td>YES</td>
		            </tr>
		            <tr>
		                <td>filename</td>
		                <td>VARCHAR</td>
		                <td>YES</td>
		            </tr>
		        </table>
		    </li>
		    <li>
		        <a name="informea_decisions_keywords"></a>
		        4<sup>th</sup> view name: <strong>informea_decisions_keywords</strong> - keeps the <em>keywords</em> property of decision, which is many-to-one related with decision
		        <table class="table table-bordered table-striped">
		            <tr><th>Property</th><th>Data type (SQL)</th><th>Required (NOT NULL)</th></tr>
		            <tr>
		                <td>id</td>
		                <td>INTEGER</td>
		                <td>YES (PK, composite PK, SQL sequence  etc.)</td>
		            </tr>
		            <tr>
		                <td>decision_id</td>
		                <td>VARCHAR</td>
		                <td>YES (FK to informea_decisions)</td>
		            </tr>
		            <tr>
		                <td>namespace</td>
		                <td>VARCHAR</td>
		                <td>YES</td>
		            </tr>
		            <tr>
		                <td>term</td>
		                <td>VARCHAR</td>
		                <td>YES</td>
		            </tr>
		        </table>
		    </li>
		    <li>
		        <a name="informea_decisions_longtitle"></a>
		        5<sup>th</sup> view name: <strong>informea_decisions_longtitle</strong> - keeps the <em>longTitle</em> property of decision, which is multilingual (many-to-one related with decision)
		        <table class="table table-bordered table-striped">
		            <tr><th>Property</th><th>Data type (SQL)</th><th>Required (NOT NULL)</th></tr>
		            <tr>
		                <td>id</td>
		                <td>INTEGER</td>
		                <td>YES (PK, composite PK, SQL sequence  etc.)</td>
		            </tr>
		            <tr>
		                <td>decision_id</td>
		                <td>VARCHAR</td>
		                <td>YES (FK to informea_decisions)</td>
		            </tr>
		            <tr>
		                <td>language</td>
		                <td>VARCHAR</td>
		                <td>YES</td>
		            </tr>
		            <tr>
		                <td>long_title</td>
		                <td>VARCHAR</td>
		                <td>YES</td>
		            </tr>
		        </table>
		    </li>
		    <li>
		        <a name="informea_decisions_summary"></a>
		        6<sup>th</sup> view name: <strong>informea_decisions_summary</strong> - keeps the <em>summary</em> property of decision, which is multilingual (many-to-one related with decision)
		        <table class="table table-bordered table-striped">
		            <tr><th>Property</th><th>Data type (SQL)</th><th>Required (NOT NULL)</th></tr>
		            <tr>
		                <td>id</td>
		                <td>INTEGER</td>
		                <td>YES (PK, composite PK, SQL sequence  etc.)</td>
		            </tr>
		            <tr>
		                <td>decision_id</td>
		                <td>VARCHAR</td>
		                <td>YES (FK to informea_decisions)</td>
		            </tr>
		            <tr>
		                <td>language</td>
		                <td>VARCHAR</td>
		                <td>YES</td>
		            </tr>
		            <tr>
		                <td>summary</td>
		                <td>VARCHAR</td>
		                <td>YES</td>
		            </tr>
		        </table>
		    </li>
		    <li>
		        <a name="informea_decisions_title"></a>
		        7<sup>th</sup> view name: <strong>informea_decisions_title</strong> - keeps the <em>content</em> property of decision, which is multilingual (many-to-one related with decision)
		        <table class="table table-bordered table-striped">
		            <tr><th>Property</th><th>Data type (SQL)</th><th>Required (NOT NULL)</th></tr>
		            <tr>
		                <td>id</td>
		                <td>INTEGER</td>
		                <td>YES (PK, composite PK, SQL sequence  etc.)</td>
		            </tr>
		            <tr>
		                <td>decision_id</td>
		                <td>VARCHAR</td>
		                <td>YES (FK to informea_decisions)</td>
		            </tr>
		            <tr>
		                <td>language</td>
		                <td>VARCHAR</td>
		                <td>YES</td>
		            </tr>
		            <tr>
		                <td>title</td>
		                <td>VARCHAR</td>
		                <td>YES</td>
		            </tr>
		        </table>
		    </li>
		</ul>
    </div>
</div>
</div>
</div>

<br />
<br />
<a name="contacts"></a>
<h4>Contacts (composed from 2 views)</h4>

<ul class="full">
    <li>
        <a name="informea_contacts"></a>
        1<sup>st</sup> view name: <strong>informea_contacts</strong> - keeps the contacts entities
        <table class="table table-bordered table-striped">
            <tr><th>Property</th><th>Data type (SQL)</th><th>Required (NOT NULL)</th></tr>
            <tr>
                <td>id</td>
                <td>VARCHAR</td>
                <td>YES</td>
            </tr>
            <tr>
                <td>country</td>
                <td>VARCHAR</td>
                <td>YES</td>
            </tr>
            <tr>
                <td>prefix</td>
                <td>VARCHAR</td>
                <td>NO</td>
            </tr>
            <tr>
                <td>firstName</td>
                <td>VARCHAR</td>
                <td>NO</td>
            </tr>
            <tr>
                <td>lastName</td>
                <td>VARCHAR</td>
                <td>NO</td>
            </tr>
            <tr>
                <td>position</td>
                <td>VARCHAR</td>
                <td>NO</td>
            </tr>
            <tr>
                <td>institution</td>
                <td>VARCHAR</td>
                <td>NO</td>
            </tr>
            <tr>
                <td>department</td>
                <td>VARCHAR</td>
                <td>NO</td>
            </tr>
            <tr>
                <td>address</td>
                <td>VARCHAR</td>
                <td>NO</td>
            </tr>
            <tr>
                <td>email</td>
                <td>VARCHAR</td>
                <td>NO</td>
            </tr>
            <tr>
                <td>phoneNumber</td>
                <td>VARCHAR</td>
                <td>NO</td>
            </tr>
            <tr>
                <td>fax</td>
                <td>VARCHAR</td>
                <td>NO</td>
            </tr>
            <tr>
                <td>primary</td>
                <td>BOOLEAN/TINYINT (1 byte)</td>
                <td>NO</td>
            </tr>
            <tr>
                <td>updated</td>
                <td>DATETIME/TIMESTAMP</td>
                <td>NO</td>
            </tr>
        </table>
    </li>
    <li>
        <a name="informea_contacts_treaties"></a>
        2<sup>nd</sup> view name: <strong>informea_contacts_treaties</strong> - keeps the <em>treaties</em> property of contact, which is  many-to-one related with contact
        <table class="table table-bordered table-striped">
            <tr><th>Property</th><th>Data type (SQL)</th><th>Required (NOT NULL)</th></tr>
            <tr>
                <td>id</td>
                <td>INTEGER</td>
                <td>YES (PK, composite PK, SQL sequence  etc.)</td>
            </tr>
            <tr>
                <td>contact_id</td>
                <td>VARCHAR</td>
                <td>YES (FK to informea_contacts)</td>
            </tr>
            <tr>
                <td>treaty</td>
                <td>VARCHAR</td>
                <td>YES</td>
            </tr>
        </table>
    </li>
</ul>


<br />
<br />
<a name="country_profiles"></a>
<h4>Country Profiles (composed from 1 view)</h4>

<ul class="full">
    <li>
        <a name="informea_country_profiles"></a>
        1<sup>st</sup> view name: <strong>informea_country_profiles</strong> - keeps the country profiles entities
        <table class="table table-bordered table-striped">
            <tr><th>Property</th><th>Data type (SQL)</th><th>Required (NOT NULL)</th></tr>
            <tr>
                <td>id</td>
                <td>VARCHAR</td>
                <td>YES</td>
            </tr>
            <tr>
                <td>country</td>
                <td>VARCHAR</td>
                <td>YES</td>
            </tr>
            <tr>
                <td>treaty</td>
                <td>VARCHAR</td>
                <td>YES</td>
            </tr>
            <tr>
                <td>entryIntoForce</td>
                <td>SMALLINT/INTEGER (2 bytes)</td>
                <td>YES</td>
            </tr>
            <tr>
                <td>entryIntoForce</td>
                <td>SMALLINT/INTEGER (2 bytes)</td>
                <td>YES</td>
            </tr>
            <tr>
                <td>updated</td>
                <td>DATETIME/TIMESTAMP</td>
                <td>NO</td>
            </tr>
        </table>
    </li>
</ul>


        <br />
        <br />
        <a name="country_reports"></a>
        <h4>Country Reports (composed from 2 views)</h4>

        <ul class="full">
            <li>
                <a name="informea_country_reports"></a>
                1<sup>st</sup> view name: <strong>informea_country_reports</strong> - keeps the country reports entities
                <table class="table table-bordered table-striped">
                    <tr><th>Property</th><th>Data type (SQL)</th><th>Required (NOT NULL)</th></tr>
                    <tr>
                        <td>id</td>
                        <td>VARCHAR</td>
                        <td>YES</td>
                    </tr>
                    <tr>
                        <td>treaty</td>
                        <td>VARCHAR</td>
                        <td>YES</td>
                    </tr>
                    <tr>
                        <td>country</td>
                        <td>VARCHAR</td>
                        <td>YES</td>
                    </tr>
                    <tr>
                        <td>submission</td>
                        <td>DATETIME/TIMESTAMP</td>
                        <td>NO</td>
                    </tr>
                    <tr>
                        <td>url</td>
                        <td>VARCHAR</td>
                        <td>YES</td>
                    </tr>
                    <tr>
                        <td>updated</td>
                        <td>DATETIME/TIMESTAMP</td>
                        <td>NO</td>
                    </tr>
                </table>
            </li>
            <li>
                <a name="informea_country_reports_title"></a>
                2<sup>nd</sup> view name: <strong>informea_country_reports_title</strong> - keeps the <em>title</em> property of country report, which is multilingual (many-to-one related with country report)
                <table class="table table-bordered table-striped">
                    <tr><th>Property</th><th>Data type (SQL)</th><th>Required (NOT NULL)</th></tr>
                    <tr>
                        <td>id</td>
                        <td>INTEGER</td>
                        <td>YES (PK, composite PK, SQL sequence  etc.)</td>
                    </tr>
                    <tr>
                        <td>country_report_id</td>
                        <td>VARCHAR</td>
                        <td>YES (FK to informea_country_reports)</td>
                    </tr>
                    <tr>
                        <td>language</td>
                        <td>VARCHAR</td>
                        <td>YES</td>
                    </tr>
                    <tr>
                        <td>title</td>
                        <td>VARCHAR</td>
                        <td>YES</td>
                    </tr>
                </table>
            </li>
        </ul>


        <br />
        <br />
        <a name="meetings"></a>
        <h4>Meetings (composed from 3 views)</h4>

        <ul class="full">
            <li>
                <a name="informea_meetings"></a>
                1<sup>st</sup> view name: <strong>informea_meetings</strong> - keeps the meetings entities
                <table class="table table-bordered table-striped">
                    <tr><th>Property</th><th>Data type (SQL)</th><th>Required (NOT NULL)</th></tr>
                    <tr>
                        <td>id</td>
                        <td>VARCHAR</td>
                        <td>YES</td>
                    </tr>
                    <tr>
                        <td>treaty</td>
                        <td>VARCHAR</td>
                        <td>YES</td>
                    </tr>
                    <tr>
                        <td>url</td>
                        <td>VARCHAR</td>
                        <td>NO</td>
                    </tr>
                    <tr>
                        <td>start</td>
                        <td>DATETIME/TIMESTAMP</td>
                        <td>YES</td>
                    </tr>
                    <tr>
                        <td>end</td>
                        <td>DATETIME/TIMESTAMP</td>
                        <td>NO</td>
                    </tr>
                    <tr>
                        <td>repetition</td>
                        <td>VARCHAR</td>
                        <td>NO</td>
                    </tr>
                    <tr>
                        <td>kind</td>
                        <td>VARCHAR</td>
                        <td>NO</td>
                    </tr>
                    <tr>
                        <td>type</td>
                        <td>VARCHAR</td>
                        <td>NO</td>
                    </tr>
                    <tr>
                        <td>access</td>
                        <td>VARCHAR</td>
                        <td>NO</td>
                    </tr>
                    <tr>
                        <td>status</td>
                        <td>VARCHAR</td>
                        <td>NO</td>
                    </tr>
                    <tr>
                        <td>imageUrl</td>
                        <td>VARCHAR</td>
                        <td>NO</td>
                    </tr>
                    <tr>
                        <td>imageCopyright</td>
                        <td>VARCHAR</td>
                        <td>NO</td>
                    </tr>
                    <tr>
                        <td>location</td>
                        <td>VARCHAR</td>
                        <td>NO</td>
                    </tr>
                    <tr>
                        <td>city</td>
                        <td>VARCHAR</td>
                        <td>YES</td>
                    </tr>
                    <tr>
                        <td>country</td>
                        <td>VARCHAR</td>
                        <td>YES</td>
                    </tr>
                    <tr>
                        <td>latitude</td>
                        <td>DOUBLE</td>
                        <td>NO</td>
                    </tr>
                    <tr>
                        <td>longitude</td>
                        <td>DOUBLE</td>
                        <td>NO</td>
                    </tr>
                    <tr>
                        <td>updated</td>
                        <td>DATETIME/TIMESTAMP</td>
                        <td>NO</td>
                    </tr>
                </table>
            </li>
            <li>
                <a name="informea_meetings_description"></a>
                2<sup>nd</sup> view name: <strong>informea_meetings_description</strong> - keeps the <em>description</em> property of decision, which is multilingual (many-to-one related with meeting)
                <table class="table table-bordered table-striped">
                    <tr><th>Property</th><th>Data type (SQL)</th><th>Required (NOT NULL)</th></tr>
                    <tr>
                        <td>id</td>
                        <td>INTEGER</td>
                        <td>YES (PK, composite PK, SQL sequence  etc.)</td>
                    </tr>
                    <tr>
                        <td>meeting_id</td>
                        <td>meeting_id</td>
                        <td>YES (FK to informea_meetings)</td>
                    </tr>
                    <tr>
                        <td>language</td>
                        <td>VARCHAR</td>
                        <td>YES</td>
                    </tr>
                    <tr>
                        <td>description</td>
                        <td>VARCHAR</td>
                        <td>YES</td>
                    </tr>
                </table>
            </li>
            <li>
                <a name="informea_meetings_title"></a>
                3<sup>rd</sup> view name: <strong>informea_meetings_title</strong> - keeps the <em>title</em> property of meeting, which is multilingual (many-to-one related with meeting)
                <table class="table table-bordered table-striped">
                    <tr><th>Property</th><th>Data type (SQL)</th><th>Required (NOT NULL)</th></tr>
                    <tr>
                        <td>id</td>
                        <td>INTEGER</td>
                        <td>YES (PK, composite PK, SQL sequence  etc.)</td>
                    </tr>
                    <tr>
                        <td>meeting_id</td>
                        <td>INTEGER</td>
                        <td>YES (FK to informea_meetings)</td>
                    </tr>
                    <tr>
                        <td>language</td>
                        <td>VARCHAR</td>
                        <td>YES</td>
                    </tr>
                    <tr>
                        <td>title</td>
                        <td>VARCHAR</td>
                        <td>YES</td>
                    </tr>
                </table>
            </li>
        </ul>

        <br />
        <br />
        <a name="national_plans"></a>
        <h4>National Plans (composed from 2 views)</h4>

        <ul class="full">
            <li>
                <a name="informea_national_plans"></a>
                1<sup>st</sup> view name: <strong>informea_national_plans</strong> - keeps the national plans entities
                <table class="table table-bordered table-striped">
                    <tr><th>Property</th><th>Data type (SQL)</th><th>Required (NOT NULL)</th></tr>
                    <tr>
                        <td>id</td>
                        <td>VARCHAR</td>
                        <td>YES</td>
                    </tr>
                    <tr>
                        <td>treaty</td>
                        <td>VARCHAR</td>
                        <td>YES</td>
                    </tr>
                    <tr>
                        <td>country</td>
                        <td>VARCHAR</td>
                        <td>YES</td>
                    </tr>
                    <tr>
                        <td>type</td>
                        <td>VARCHAR</td>
                        <td>YES</td>
                    </tr>
                    <tr>
                        <td>url</td>
                        <td>VARCHAR</td>
                        <td>NO</td>
                    </tr>
                    <tr>
                        <td>submission</td>
                        <td>DATETIME/TIMESTAMP</td>
                        <td>NO</td>
                    </tr>
                    <tr>
                        <td>updated</td>
                        <td>DATETIME/TIMESTAMP</td>
                        <td>NO</td>
                    </tr>
                </table>
            </li>
            <li>
                <a name="informea_national_plans_title"></a>
                2<sup>nd</sup> view name: <strong>informea_national_plans_title</strong> - keeps the <em>title</em> property of national plans, which is multilingual (many-to-one related with national plan)
                <table class="table table-bordered table-striped">
                    <tr><th>Property</th><th>Data type (SQL)</th><th>Required (NOT NULL)</th></tr>
                    <tr>
                        <td>id</td>
                        <td>INTEGER</td>
                        <td>YES (PK, composite PK, SQL sequence  etc.)</td>
                    </tr>
                    <tr>
                        <td>national_plan_id</td>
                        <td>VARCHAR</td>
                        <td>YES (FK to informea_national_plans)</td>
                    </tr>
                    <tr>
                        <td>language</td>
                        <td>VARCHAR</td>
                        <td>YES</td>
                    </tr>
                    <tr>
                        <td>title</td>
                        <td>VARCHAR</td>
                        <td>YES</td>
                    </tr>
                </table>
            </li>
        </ul>


        <br />
        <br />
        <a name="sites"></a>
        <h4>Sites (composed from 2 views)</h4>

        <ul class="full">
            <li>
                <a name="informea_sites"></a>
                1<sup>st</sup> view name: <strong>informea_sites</strong> - keeps the sites entities
                <table class="table table-bordered table-striped">
                    <tr><th>Property</th><th>Data type (SQL)</th><th>Required (NOT NULL)</th></tr>
                    <tr>
                        <td>id</td>
                        <td>VARCHAR</td>
                        <td>YES</td>
                    </tr>
                    <tr>
                        <td>type</td>
                        <td>VARCHAR</td>
                        <td>YES</td>
                    </tr>
                    <tr>
                        <td>country</td>
                        <td>VARCHAR</td>
                        <td>YES</td>
                    </tr>
                    <tr>
                        <td>treaty</td>
                        <td>VARCHAR</td>
                        <td>YES</td>
                    </tr>
                    <tr>
                        <td>name</td>
                        <td>VARCHAR</td>
                        <td>YES</td>
                    </tr>
                    <tr>
                        <td>url</td>
                        <td>VARCHAR</td>
                        <td>NO</td>
                    </tr>
                    <tr>
                        <td>latitude</td>
                        <td>DOUBLE</td>
                        <td>NO</td>
                    </tr>
                    <tr>
                        <td>longitude</td>
                        <td>VARCHAR</td>
                        <td>NO</td>
                    </tr>
                    <tr>
                        <td>updated</td>
                        <td>DATETIME/TIMESTAMP</td>
                        <td>NO</td>
                    </tr>
                </table>
            </li>
            <li>
                <a name="informea_sites_name"></a>
                3<sup>rd</sup> view name: <strong>informea_sites_name</strong> - keeps the <em>name</em> property of site, which is multilingual (many-to-one related with site)
                <table class="table table-bordered table-striped">
                    <tr><th>Property</th><th>Data type (SQL)</th><th>Required (NOT NULL)</th></tr>
                    <tr>
                        <td>id</td>
                        <td>INTEGER</td>
                        <td>YES (PK, composite PK, SQL sequence  etc.)</td>
                    </tr>
                    <tr>
                        <td>site_id</td>
                        <td>INTEGER</td>
                        <td>YES (FK to informea_sites)</td>
                    </tr>
                    <tr>
                        <td>language</td>
                        <td>VARCHAR</td>
                        <td>YES</td>
                    </tr>
                    <tr>
                        <td>name</td>
                        <td>VARCHAR</td>
                        <td>YES</td>
                    </tr>
                </table>
            </li>
        </ul>
        <br />
        <br />
        <h3>Example</h3>
        As an example, let's suppose we have a MEA that wants to expose its meetings data. The existing structure of the meetings table (m_meetings) is something like this:
        <table class="table table-bordered table-striped">
            <tr>
                <th>Id</th>
                <th>m_start</th>
                <th>m_end</th>
                <th>title</th>
                <th>title_es</th>
                <th>locality</th>
                <th>country_code</th>
                <th>m_lat</th>
                <th>m_lng</th>
            </tr>
            <tr>
                <td>1</td>
                <td>2011-04-03 10:00</td>
                <td>2011-04-05 18:00</td>
                <td>First meeting of parties</td>
                <td>Primera reunión de los partidos</td>
                <td>Bucharest</td>
                <td>ro</td>
                <td>30.0432689</td>
                <td>44.9484175</td>
            </tr>
            <tr>
                <td>2</td>
                <td>2010-11-4 09:00</td>
                <td>2010-11-9 13:00</td>
                <td>COP reunión</td>
                <td>Frankfurt</td>
                <td>de</td>
                <td>30.0432689</td>
                <td>44.9484175</td>
            </tr>
        </table>

        We want to transform this table into our structure, so the queries would look like this:
        <pre>
CREATE VIEW `informea_meetings` AS
    SELECT
        TO_STR(Id) as id,
        "vienna" AS treaty,
        NULL AS url,
        m_start AS start,
        m_end AS end,
        NULL AS repetition,
        NULL AS kind,
        NULL AS type,
        NULL AS access,
        NULL AS status,
        NULL AS imageUrl,
        NULL AS imageCopyright,
        NULL AS location,
        locality NULL AS city,
        country_code AS country,
        m_lat AS latitude,
        m_lng AS longitude,
        NOW() AS updated,
    FROM m_meetings WHERE title IS NOT NULL;
        </pre>
    Since we don't have information about URL, kind, repetition etc. we set them to NULL, as they are not required.
    <br />
    The Id is integer, so we make it string, as view is required to have VARCHAR.
    <br />
    The updated is unknown, so we make it current date time, so the system will pick it up and updated if necessary.
    <br />
    We filter the meetings having title null since they are illegal.
    <br />
    m_lat and m_lng are correct, but we assign them the appropriate alias: latitude and longitude.

    <br />
    <br />
    Now, we have to set the title of the meeting. Since is multilingual and many-to-one related to meeting, we create the second view called informea_meetings_title.
    We have titles in english and spanish into two columns, we will need to transform these into rows by using SQL UNION.
    <pre>
CREATE VIEW `informea_meetings_title` AS
    SELECT
        MD5(CONCAT(TO_STR(Id), title)) AS id,
        'en' AS language,
        title
    FROM m_meetings WHERE title IS NOT NULL;

    UNION

    SELECT
        MD5(CONCAT(TO_STR(Id), title_es)) AS id,
        'es' AS language,
        title_es
    FROM m_meetings WHERE title_es IS NOT NULL;

    </pre>

    Now we have both english and spanish titles for the meetings gathered into this view.
    <br />
    <br />
    This way the toolkit will pickup the data from these two views and expose the meetings correctly in the OData service.