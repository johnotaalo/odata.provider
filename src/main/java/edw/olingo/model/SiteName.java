/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edw.olingo.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Site name entity
 * <br />
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 1.3.3
 */
@Entity
@Table(name="informea_sites_name")
public class SiteName {

    @Id
    @Column(name="id")
    private String id;

    private Site site;

    private String language;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
