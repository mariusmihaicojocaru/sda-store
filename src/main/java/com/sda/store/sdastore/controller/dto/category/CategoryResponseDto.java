package com.sda.store.sdastore.controller.dto.category;

import com.sda.store.sdastore.model.Category;
import com.sda.store.sdastore.model.Product;

import javax.persistence.*;
import java.util.List;

public class CategoryResponseDto {

    private Long id;
    private String name;
    private List<CategoryResponseDto> subCategories;
    private String parentName;

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

    public List<CategoryResponseDto> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<CategoryResponseDto> subCategories) {
        this.subCategories = subCategories;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
