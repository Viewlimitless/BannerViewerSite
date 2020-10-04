package com.platunov.bannerviewer.dto;

public class CategoryDto {

    private Long id;

    private String name;

    private String reqName;

    private boolean deleted;

    public CategoryDto() {
    }

    public CategoryDto(String name, String reqName, boolean deleted) {
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
