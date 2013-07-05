/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.informea.odata.producer.toolkit.impl;

import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Site name entity
 * <br />
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 1.3.3
 */
@Entity
@Table(name="informea_sites_name")
@Cacheable
public class SiteName implements Serializable {

    private static final long serialVersionUID = -2476909362289089881L;

    @Id
    private String id;

    @Column(name = "site_id")
    private Integer siteId;
    private String language;
    private String name;


    public String getLanguage() {
        return language;
    }


    public String getName() {
        return name;
    }

}
