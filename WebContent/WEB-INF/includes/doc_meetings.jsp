<ul>
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