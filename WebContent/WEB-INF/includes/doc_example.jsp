<h3>Example</h3>
<p>
    As an example, let's suppose we have a MEA that wants to expose its meetings data. 
    The existing structure of the custom meetings table (<code>m_meetings</code>) looks like this:
</p>

<table class="table table-bordered table-striped">
    <tr>
        <th>ID</th>
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

We want to transform this table into InforMEA required structure, so we would need to create the required views on 
top of this table. The SQL code for the <code>CREATE VIEW</code> statement will look like this:

<p>
    <pre>
    CREATE VIEW `informea_meetings` AS
        SELECT
            TO_STR(ID) as id,
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
</p>

<p>
    Since we don't have information about <code>URL</code>, <code>kind</code>, <code>repetition</code> etc. we set them 
    to <code>NULL</code>, as they are not required.
</p>

<p>
    The <code>ID</code> is integer, so we make it <code>string</code>, as view is required to have <code>VARCHAR</code>.
</p>

<p>
    The <code>updated</code> field is unknown, so we make it current date time, so the system will pick it up and 
    update if necessary.
</p>
<p>
    We filter out meetings having the title <code>null</code> since they are invalid. InforMEA required valid titles
    for the meetings.
    <br />
    <code>m_lat</code> and <code>m_lng</code> are correct, but we assign them the appropriate aliases
    <code>latitude</code> and <code>longitude</code>.
</p>

<p>
    Now, we have to set the title of the meeting. Since it is a multilingual field and many-to-one related to meeting, 
    we create the second view called <code>informea_meetings_title</code>.
    <br />
    We have titles in english and spanish into two columns, we will need to transform these into rows by using the
    SQL UNION feature of SQL language.
</p>

<p>
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
</p>
<p>
    Now we have both english and spanish titles for the meetings gathered into this view.
</p>
<p>
    This way the toolkit will pickup the data from these two views and expose the meetings correctly in the OData service.
</p>
<p>
    After you set-up these two views, you move to the next step which is the toolkit configuration. 
    The toolkit will look into the database for this predefined views and, depending on which views are found, will
    let you select the appropriate entities to expose via OData.
</p>