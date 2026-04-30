package com.gustavo.helpdesk_api.application.usecase.dto;

import java.util.UUID;

public record CatalogOutputDto(
        UUID id,
        String name
) {
}
