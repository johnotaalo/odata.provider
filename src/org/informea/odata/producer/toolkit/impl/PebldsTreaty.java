package org.informea.odata.producer.toolkit.impl;

// Generated Mar 29, 2012 4:48:04 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.informea.odata.pojo.AbstractPebldsTreaty;
import org.odata4j.core.OEntities;
import org.odata4j.core.OEntity;
import org.odata4j.core.OEntityKey;
import org.odata4j.core.OLink;
import org.odata4j.core.OProperties;
import org.odata4j.core.OProperty;
import org.odata4j.edm.EdmEntitySet;

/**
 * PebldsTreaty generated by hbm2java
 */
@Entity
@Table(name = "peblds_treaty")
public class PebldsTreaty extends AbstractPebldsTreaty implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
	private Integer id;
    @Column(name = "name", nullable = false)
    @NotNull
	private String name;
    @Column(name = "url")
	private String url;
    @Column(name = "logo_medium")
	private String logoMedium;
    @Column(name = "rec_author", nullable = false, length = 32)
    @NotNull
    @Length(max = 32)
	private String recAuthor;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "rec_created", nullable = false, length = 19)
    @NotNull
	private Date recCreated;
    @Column(name = "odata_name", length = 32)
    @Length(max = 32)
	private String odataName;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pebldsTreaty")
	private Set<PebldsBestPractice> pebldsBestPractices = new HashSet<PebldsBestPractice>(0);
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "peblds_project_treaty", joinColumns = { @JoinColumn(name = "id_treaty", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "id_project", nullable = false, updatable = false) })
	private Set<PebldsProject> pebldsProjects = new HashSet<PebldsProject>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pebldsTreaty")
	private Set<PebldsTechnicalReport> pebldsTechnicalReports = new HashSet<PebldsTechnicalReport>(0);

	public PebldsTreaty() {
	}

	public PebldsTreaty(String name, String recAuthor, Date recCreated) {
		this.name = name;
		this.recAuthor = recAuthor;
		this.recCreated = recCreated;
	}

	public PebldsTreaty(String name, String url, String logoMedium,
			String recAuthor, Date recCreated, String odataName,
			Set<PebldsBestPractice> pebldsBestPractices,
			Set<PebldsProject> pebldsProjects,
			Set<PebldsTechnicalReport> pebldsTechnicalReports) {
		this.name = name;
		this.url = url;
		this.logoMedium = logoMedium;
		this.recAuthor = recAuthor;
		this.recCreated = recCreated;
		this.odataName = odataName;
		this.pebldsBestPractices = pebldsBestPractices;
		this.pebldsProjects = pebldsProjects;
		this.pebldsTechnicalReports = pebldsTechnicalReports;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLogoMedium() {
		return this.logoMedium;
	}

	public void setLogoMedium(String logoMedium) {
		this.logoMedium = logoMedium;
	}

	public String getRecAuthor() {
		return this.recAuthor;
	}

	public void setRecAuthor(String recAuthor) {
		this.recAuthor = recAuthor;
	}

	public Date getRecCreated() {
		return this.recCreated;
	}

	public void setRecCreated(Date recCreated) {
		this.recCreated = recCreated;
	}

	public String getOdataName() {
		return this.odataName;
	}

	public void setOdataName(String odataName) {
		this.odataName = odataName;
	}

	public Set<PebldsBestPractice> getPebldsBestPractices() {
		return this.pebldsBestPractices;
	}

	public void setPebldsBestPractices(
			Set<PebldsBestPractice> pebldsBestPractices) {
		this.pebldsBestPractices = pebldsBestPractices;
	}

	public Set<PebldsProject> getPebldsProjects() {
		return this.pebldsProjects;
	}

	public void setPebldsProjects(Set<PebldsProject> pebldsProjects) {
		this.pebldsProjects = pebldsProjects;
	}

	public Set<PebldsTechnicalReport> getPebldsTechnicalReports() {
		return this.pebldsTechnicalReports;
	}

	public void setPebldsTechnicalReports(
			Set<PebldsTechnicalReport> pebldsTechnicalReports) {
		this.pebldsTechnicalReports = pebldsTechnicalReports;
	}

    @Override
    public OEntity asEntity(EdmEntitySet ees){
        final List<OProperty<?>> properties = new ArrayList<OProperty<?>>();
        properties.add(OProperties.int32("id", getId()));
        properties.add(OProperties.string("name", getName()));
        properties.add(OProperties.string("url", getUrl()));
        properties.add(OProperties.string("logoMedium", getLogoMedium()));
        properties.add(OProperties.string("odataName", getOdataName()));
        return OEntities.create(ees, OEntityKey.create(getId()), properties, new ArrayList<OLink>(), null, null);
    }
}
