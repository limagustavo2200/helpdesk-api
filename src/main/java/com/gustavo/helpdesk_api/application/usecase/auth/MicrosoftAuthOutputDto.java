package com.gustavo.helpdesk_api.application.usecase.auth;

public record MicrosoftAuthOutputDto(
        String token,
        String username,
        String email,
        String role
) {
}
