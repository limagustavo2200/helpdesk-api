package com.gustavo.helpdesk_api.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "`system`")
@Getter
public class SystemEntity extends CatalogEntity {

    protected SystemEntity() {
        // JPA
    }

    public SystemEntity(String name) {
        super(name);
    }

    public void renameSystem(String newName) {
        super.rename(newName);
    }
}