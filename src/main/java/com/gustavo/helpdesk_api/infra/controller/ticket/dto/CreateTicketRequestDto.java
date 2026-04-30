package com.gustavo.helpdesk_api.infra.controller.ticket.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;


public record CreateTicketRequestDto(

        @NotBlank
        String username,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String sector,

        @NotBlank
        String title,

        @NotBlank
        String description,

        @NotBlank
        String priority,

        @NotNull
        UUID categoryId,

        @NotNull
        UUID subcategoryId,

        @NotNull
        UUID systemId
) {}