package com.gustavo.helpdesk_api.core.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@Getter
public abstract class CatalogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Date createdAt;


    protected CatalogEntity() {
        // construtor do JPA
    }

    protected CatalogEntity(String name) {
        validateName(name);
        this.name = name;
        this.createdAt = new Date();
    }

    private void validateName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Nome é obrigatório");

        if (name.length() < 2)
            throw new IllegalArgumentException("Nome deve ter no mínimo 2 caracteres");
    }

    protected void rename(String newName) {
        validateName(newName);
        this.name = newName;
    }

}