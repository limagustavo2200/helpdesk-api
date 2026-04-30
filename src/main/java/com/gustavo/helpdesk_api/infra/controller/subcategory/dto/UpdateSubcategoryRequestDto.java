package com.gustavo.helpdesk_api.infra.controller.subcategory.dto;

import java.util.UUID;

public record UpdateSubcategoryRequestDto(UUID id, String name) {
}
