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
package edw.olingo.model;

import javax.persistence.*;


/**
 * National plan title entity
 * <br />
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 2.0.0, 11/12/2014
 * @since 1.3.3
 */
@Entity
@Table(name="informea_national_plans_title")
public class NationalPlanTitle {

    @Id
    @Column (name = "id")
    private String id;

    @SuppressWarnings("unused")
	private NationalPlan national_plan;

    private String language;
    private String title;

    public String getLanguage() {
        return language;
    }

    public String getTitle() {
        return title;
    }
}
