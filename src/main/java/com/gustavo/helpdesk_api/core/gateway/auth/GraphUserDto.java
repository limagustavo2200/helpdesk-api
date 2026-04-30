package com.gustavo.helpdesk_api.core.gateway.auth;

public record GraphUserDto(
        String mail,
        String userPrincipalName,
        String givenName,
        String department
) {
}
