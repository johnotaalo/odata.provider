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

    @SuppressWarnings("unused")
    private Site site;

    private String language;
    private String name;

    public String getLanguage() {
        return language;
    }

    public String getName() {
        return name;
    }
}
