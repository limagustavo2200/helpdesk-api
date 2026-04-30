package com.gustavo.helpdesk_api.core.valueobject;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Priority {

    @Column(nullable = false)
    private String label;

    protected Priority() {
        // JPA
    }

    private Priority(String label) {
        this.label = label;
    }

    public static Priority low() {
        return new Priority("Baixa");
    }

    public static Priority medium() {
        return new Priority("Média");
    }

    public static Priority high() {
        return new Priority("Alta");
    }

    public static Priority urgent() {
        return new Priority("Urgente");
    }

    public String getLabel() { return label; }
}
