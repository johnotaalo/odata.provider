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
 * Type of a national plan. InforMEA portal will use one of these predefined constants
 * @see org.informea.odata.INationalPlan
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 0.5
 */
public enum NationalPlanType {

    /**
     * Nationally Appropriate Mitigation Actions
     * <br />
     * Related convention: <strong>UNFCCC</strong>
     * <br />
     * Related terms: <strong>mitigation</strong>, <strong>climate change</strong>
     */
    NAMA("nama"),

    /**
     * National Action Programmes
     * <br />
     * Related convention: <strong>UNCCD</strong>
     * <br />
     * Related terms: <strong>desertification</strong>
     */
    NAP("nap"),

    /**
     * National Adaptation Programmes of Action
     * <br />
     * Related convention: <strong>UNFCCC</strong>
     * <br />
     * Related terms: <strong>adaptation</strong>, <strong>climate change</strong>
     */
    NAPA("napa"),

    /**
     * 	National Biodiversity Strategies and Action Plans
     * <br />
     * Related convention: <strong>CBD, other BD related Conventions?</strong>
     * <br />
     * Related terms: <strong>Biodiversity</strong>, <strong>trade in species</strong>, <strong>migratory species</strong>, <strong>wetlands</strong>, <strong>world heritage sites</strong>
     */
    NBSAP("nbsap"),

    /**
     * National Implementation Plans
     * <br />
     * Related convention: <strong>Stockholm Convention</strong>
     * <br />
     * Related terms: <strong>chemicals</strong>, <strong>POPs</strong>
     */
    NIP("nip"),

    /**
     * National Wetland Policies
     * <br />
     * Related convention: <strong>Ramsar Convention</strong>
     * <br />
     * Related terms: <strong>wetlands</strong>
     */
    NWP("nwp"),

    /**
     * 	Programme of Work on Communication, Education and Public Awareness
     * <br />
     * Related convention: <strong>CBD, other BD related Conventions?</strong>
     * <br />
     * Related terms: <strong>Education and Training</strong>, <strong>Biodiversity</strong>
     */
    CEPA("cepa");


    private String name;

    private NationalPlanType(String name) {
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
    public static NationalPlanType fromString(String value) {
        if(value != null) {
            if(value.equalsIgnoreCase("nama")) {
                return NationalPlanType.NAMA;
            }
            if(value.equalsIgnoreCase("nap")) {
                return NationalPlanType.NAP;
            }
            if(value.equalsIgnoreCase("napa")) {
                return NationalPlanType.NAPA;
            }
            if(value.equalsIgnoreCase("nbsap")) {
                return NationalPlanType.NBSAP;
            }
            if(value.equalsIgnoreCase("nip")) {
                return NationalPlanType.NIP;
            }
            if(value.equalsIgnoreCase("nwp")) {
                return NationalPlanType.NWP;
            }
            if(value.equalsIgnoreCase("cepa")) {
                return NationalPlanType.CEPA;
            }
        }
        return null;
    }
}
