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
package org.informea.odata.constants;

/**
 * Mime type for a document. InforMEA portal will use one of these predefined constants
 * @see org.informea.odata.IDecision
 * @see org.informea.odata.pojo.DecisionDocument
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 0.5
 */
public enum MimeType {

    DOC("application/msword"),
    PDF("application/pdf"),
    RTF("application/rtf"),
    ODT("application/vnd.oasis.opendocument.text"),
    TXT("text/plain"),
    HTML("text/html");

    private String name;

    private MimeType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Construct new enum type from string value.
     * @param value Value to be parsed (case-insensitive)
     * @return Parsed value as instance of enum or null if no match is found
     */
    public static MimeType fromString(String value) {
        if(value != null) {
            if(value.equalsIgnoreCase("doc") || value.equalsIgnoreCase("application/msword")) {
                return MimeType.DOC;
            }
            if(value.equalsIgnoreCase("pdf")
                    || value.equalsIgnoreCase("application/pdf")
                    || value.equalsIgnoreCase("application/x-pdf") ) {
                return MimeType.PDF;
            }
            if(value.equalsIgnoreCase("rtf")
                    || value.equalsIgnoreCase("application/rtf")) {
                return MimeType.RTF;
            }
            if(value.equalsIgnoreCase("odt")
                    || value.equalsIgnoreCase("application/vnd.oasis.opendocument.text")
                    || value.equalsIgnoreCase("application/x-vnd.oasis.opendocument.text")) {
                return MimeType.ODT;
            }
            if(value.equalsIgnoreCase("txt")
                    || value.equalsIgnoreCase("text/plain")) {
                return MimeType.TXT;
            }
            if(value.equalsIgnoreCase("html")
                    || value.equalsIgnoreCase("text/html")) {
                return MimeType.HTML;
            }
        }
        return null;
    }
}
