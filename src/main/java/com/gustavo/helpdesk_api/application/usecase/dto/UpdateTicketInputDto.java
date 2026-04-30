package com.gustavo.helpdesk_api.application.usecase.dto;

import com.gustavo.helpdesk_api.core.valueobject.Priority;

import java.util.UUID;

public record UpdateTicketInputDto(
        Integer ticketNumber,
        UUID statusId,
        String priority,
        UUID categoryId,
        UUID subcategoryId,
        UUID systemId,
        UUID assigneeId,
        UUID tiSectorId,
        String resolutionNote
) {
}
