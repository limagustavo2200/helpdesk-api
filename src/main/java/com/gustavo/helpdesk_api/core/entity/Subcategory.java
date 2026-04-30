package com.gustavo.helpdesk_api.core.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "subcategory")
@Getter
public class Subcategory extends CatalogEntity {

    protected Subcategory() {
        // JPA
    }

    public Subcategory(String name) {
        super(name);
    }

    public void renameSubcategory(String newName) {
        super.rename(newName);
    }
}