<ul>
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