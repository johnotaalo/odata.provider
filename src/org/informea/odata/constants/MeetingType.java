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
 * Type of meeting. InforMEA portal will use one of these predefined constants
 * @see org.informea.odata.IMeeting
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 0.5
 */
public enum MeetingType {

	COP("cop"),
    SUBSIDIARY("subsidiary"),
    EXPERT("expert"),
    WORKING("working"),
    SYMPOSIA("symposia"),
    CONFERENCE("conference"),
    WORKSHOP("workshop"),
    INFORMAL("informal");

	private String name;

    private MeetingType(String name) {
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
    public static MeetingType fromString(String value) {
        if(value != null) {
            if(value.equalsIgnoreCase("cop")) {
                return MeetingType.COP;
            }
            if(value.equalsIgnoreCase("subsidiary")) {
                return MeetingType.SUBSIDIARY;
            }
            if(value.equalsIgnoreCase("expert")) {
                return MeetingType.EXPERT;
            }
            if(value.equalsIgnoreCase("working")) {
                return MeetingType.WORKING;
            }
            if(value.equalsIgnoreCase("symposia")) {
                return MeetingType.SYMPOSIA;
            }
            if(value.equalsIgnoreCase("conference")) {
                return MeetingType.CONFERENCE;
            }
            if(value.equalsIgnoreCase("workshop")) {
                return MeetingType.WORKSHOP;
            }
            if(value.equalsIgnoreCase("informal")) {
                return MeetingType.INFORMAL;
            }
        }
        return null;
    }
}
