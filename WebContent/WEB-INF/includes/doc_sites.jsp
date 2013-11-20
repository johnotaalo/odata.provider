<ul>
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