package com.gustavo.helpdesk_api.infra.controller.assignee.dto;

import java.util.UUID;

public record UpdateAssigneeRequestDto(UUID id, String name) {
}
