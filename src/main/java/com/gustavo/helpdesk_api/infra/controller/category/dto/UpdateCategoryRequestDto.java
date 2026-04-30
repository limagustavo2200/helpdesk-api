package com.gustavo.helpdesk_api.infra.controller.category.dto;

import java.util.UUID;

public record UpdateCategoryRequestDto(UUID id, String name) {
}
