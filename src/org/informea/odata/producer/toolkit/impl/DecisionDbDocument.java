/* Copyright 2011 UNEP (http://www.unep.org)
 * This file is part of InforMEA Toolkit project.
 * InforMEA Toolkit is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * InforMEA Toolkit is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * You should have received a copy of the GNU General Public License along with
 * InforMEA Toolkit. If not, see http://www.gnu.org/licenses/.
 */
package org.informea.odata.producer.toolkit.impl;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.informea.odata.constants.MimeType;
import org.informea.odata.producer.InvalidValueException;


/**
 * Decision document entity
 * <br />
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 1.3.3
 */
@Entity
@Table(name="informea_decisions_documents")
@Cacheable
public class DecisionDbDocument {

    @Id
    private String id;

    @Column(name = "decision_id")
    private String decisionId;
    private String diskPath;
    private String url;
    private String mimeType;
    private String language;
    private String filename;


    public String getId() {
        return id;
    }


    public String getDiskPath() {
        return diskPath;
    }


    public String getFilename() {
        return filename;
    }


    public String getLanguage() {
        return language;
    }


    public String getUrl() {
        return url;
    }


    public MimeType getMimeType() {
        MimeType ret = MimeType.fromString(mimeType);
        if(ret == null) {
            throw new InvalidValueException(String.format("Invalid 'mimeType' value. Each document must have valid value for 'mimeType' (Affected document with ID:%s)", id));
        }
        return ret;
    }
}
