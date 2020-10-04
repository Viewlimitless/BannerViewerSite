package com.platunov.bannerviewer.dto;

public class CategoryDto {

    private Long id;

    private String name;

    private String reqname;

    private boolean deleted;

    public CategoryDto() {
    }

    public CategoryDto(String name, String reqname, boolean deleted) {
        this.name = name;
        this.reqname = reqname;
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

    public String getReqname() {
        return reqname;
    }

    public void setReqname(String reqname) {
        this.reqname = reqname;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
