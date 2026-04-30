package com.gustavo.helpdesk_api.infra.controller.system.dto;

import java.util.UUID;

public record UpdateSystemRequestDto(UUID id, String name) {}
