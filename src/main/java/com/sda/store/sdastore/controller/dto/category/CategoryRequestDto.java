package com.sda.store.sdastore.controller.dto.category;

public class CategoryRequestDto {

    private String name;
    private Long parentId; //daca are id inseamna ca e child, daca nu inseamna ca e parent

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
