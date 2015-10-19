package edw.olingo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "TreatyTitle")
@Table(name = "informea_treaties_title")
public class TreatyTitle {

	@Id
	@Column(name = "id")
	private String id;

	private String language;
	private String title;

	public String getId() {
		return id;
	}

	@SuppressWarnings("unused")
	private Treaty treaty;

	public String getLanguage() {
		return language;
	}

	public String getTitle() {
		return title;
	}
}
