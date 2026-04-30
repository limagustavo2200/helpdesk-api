package com.gustavo.helpdesk_api.core.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "assignee")
@Getter
public class Assignee extends CatalogEntity {

    protected Assignee() {
        // JPA
    }

    public Assignee(String name) {
        super(name);
    }

    public void renameAssignee(String newName) {
        super.rename(newName);
    }
}