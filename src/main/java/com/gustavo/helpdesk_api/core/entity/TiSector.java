package com.gustavo.helpdesk_api.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "ti_sector")
@Getter
public class TiSector extends CatalogEntity {

    protected TiSector() {
        // JPA
    }

    public TiSector(String name) {
        super(name);
    }

    public void renameTiSector(String newName) {
        super.rename(newName);
    }
}