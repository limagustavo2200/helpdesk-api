package com.gustavo.helpdesk_api.infra.controller.status.dto;

import java.util.UUID;

public record UpdateStatusRequestDto(UUID id, String name) {
}
