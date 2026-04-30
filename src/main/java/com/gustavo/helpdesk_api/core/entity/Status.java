package com.gustavo.helpdesk_api.core.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "status")
@Getter
public class Status extends CatalogEntity {

    protected Status() {
        // JPA
    }

    public Status(String name) {
        super(name);
    }

    public void renameStatus(String newName) {
        super.rename(newName);
    }

    public boolean isInProgress() {
        return "Em Andamento".equalsIgnoreCase(this.getName());
    }

    public boolean isFinished() {
        return "Finalizado".equalsIgnoreCase(this.getName());
    }

    public boolean isOpen() {
        return "Aberto".equalsIgnoreCase(this.getName());
    }

    public boolean isPaused() {
        return "Pausado".equalsIgnoreCase(this.getName());
    }
}