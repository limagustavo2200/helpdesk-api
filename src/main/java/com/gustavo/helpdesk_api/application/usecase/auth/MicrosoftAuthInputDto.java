package com.gustavo.helpdesk_api.application.usecase.auth;

public record MicrosoftAuthInputDto(
        String code,
        String codeVerifier
) {
}
