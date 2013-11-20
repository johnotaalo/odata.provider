<ul>
    <li>
        <a name="informea_decisions"></a>
        1<sup>st</sup> view name: <strong>informea_decisions</strong> - keeps the decisions entities
        <table class="table table-bordered table-striped">
            <tr>
                <th>Property</th>
                <th>Data type (SQL)</th>
                <th>Required (NOT NULL)</th>
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