package com.Bootcamp.Project.Application.entities;



import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;


@MappedSuperclass
public class BaseDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreationTimestamp
    private Date dateCreated;

    @UpdateTimestamp()
    private Date lastModified;

    //Getters

    public long getId() {
        return id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getLastModified() {
        return lastModified;
    }

    //Setters

    public void setId(long id) {
        this.id = id;
    }


    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
