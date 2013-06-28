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
 * Status of the meeting. InforMEA portal will use one of these predefined constants
 * @see org.informea.odata.IMeeting
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 0.5
 */
public enum MeetingStatus {

    TENTATIVE("tentative"),
    CONFIRMED("confirmed"),
    POSTPONED("postponed"),
    CANCELLED("cancelled"),
    NODATE("nodate"),
    OVER("over");

    private String name;

    private MeetingStatus(String name) {
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
    public static MeetingStatus fromString(String value) {
        if(value != null) {
            if(value.equalsIgnoreCase("tentative")) {
                return MeetingStatus.TENTATIVE;
            }
            if(value.equalsIgnoreCase("confirmed")) {
                return MeetingStatus.CONFIRMED;
            }
            if(value.equalsIgnoreCase("postponed")) {
                return MeetingStatus.POSTPONED;
            }
            if(value.equalsIgnoreCase("cancelled")) {
                return MeetingStatus.CANCELLED;
            }
            if(value.equalsIgnoreCase("nodate")) {
                return MeetingStatus.NODATE;
            }
            if(value.equalsIgnoreCase("over")) {
                return MeetingStatus.OVER;
            }
        }
        return null;
    }
}
