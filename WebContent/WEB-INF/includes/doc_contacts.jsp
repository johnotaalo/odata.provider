<ul>
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
