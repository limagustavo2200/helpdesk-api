package com.gustavo.helpdesk_api.application.usecase.dto;

import com.gustavo.helpdesk_api.core.valueobject.Priority;
import com.gustavo.helpdesk_api.core.valueobject.Requester;

import java.util.UUID;

public record CreateTicketInputDto(
        Requester requester,
        String title,
        String description,
        String priority,
        UUID categoryId,
        UUID subcategoryId,
        UUID systemId
) {
}
