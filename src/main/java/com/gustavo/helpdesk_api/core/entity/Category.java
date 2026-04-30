package com.gustavo.helpdesk_api.core.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "category")
@Getter
public class Category extends CatalogEntity {

    protected Category() {
        // JPA
    }

    public Category(String name) {
        super(name);
    }

    public void renameCategory(String newName) {
        super.rename(newName);
    }
}