package com.gustavo.helpdesk_api.core.valueobject;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Requester {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String sector;

    protected Requester() {
        // JPA
    }

    public Requester(String username, String email, String sector) {
        require(username, "username");
        require(email, "email");
        require(sector, "sector");

        this.username = username;
        this.email = email;
        this.sector = sector;
    }

    private void require(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Campo obrigatório não informado: " + field);
        }
    }
}
