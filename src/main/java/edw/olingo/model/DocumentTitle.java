package edw.olingo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by miahi on 5/13/2016.
 */
@Entity
@Table(name = "informea_documents_titles")
public class DocumentTitle {

    @Id
    @Column(name="id")
    private String id;

    @SuppressWarnings("unused")
    private Document document;

    private String language;
    private String value;

    public String getId() {
        return id;
    }

    public Document getDocument() {
        return document;
    }

    public String getLanguage() {
        return language;
    }

    public String getValue() {
        return value;
    }
}
