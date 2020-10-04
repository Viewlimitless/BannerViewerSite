package com.platunov.bannerviewer.domain;

import javax.persistence.*;

@Entity
public class Category extends AbstractEntity{

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "varchar(255) default 'request' not null", name = "reqname", nullable = false)
    private String reqName;

    @Column
    private boolean deleted;

    public Category() {
    }

    public Category(String name, String reqName, boolean deleted) {
        this.name = name;
        this.reqName = reqName;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReqName() {
        return reqName;
    }

    public void setReqName(String reqName) {
        this.reqName = reqName;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
