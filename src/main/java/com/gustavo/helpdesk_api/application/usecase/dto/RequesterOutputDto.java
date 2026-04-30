package com.gustavo.helpdesk_api.application.usecase.dto;

public record RequesterOutputDto(
        String username,
        String email,
        String sector
) {
}
