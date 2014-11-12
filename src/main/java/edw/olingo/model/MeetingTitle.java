package edw.olingo.model;

import javax.persistence.*;

@Entity(name = "MeetingTitle")
@Table(name = "informea_meetings_title")
public class MeetingTitle {

	@Id
    @Column(name = "id")
	private String id;

	private String language;
	private String title;

	@SuppressWarnings("unused")
	private Meeting meeting;

	public String getLanguage() {
		return language;
	}

	public String getTitle() {
		return title;
	}
}
