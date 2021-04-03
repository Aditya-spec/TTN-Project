package com.Bootcamp.Project.Application.entities;



import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@MappedSuperclass
public class BaseDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "identity_generator")
    @SequenceGenerator(name = "identity_generator", sequenceName = "identity_table", allocationSize = 1)
    private Long id;

    private boolean deleted;

    @CreationTimestamp
    private Date dateCreated;

    @UpdateTimestamp()
    private Date lastModified;

    //Getters

    public Long getId() {
        return id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public boolean getDeleted() {
        return deleted;
    }

    //Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
