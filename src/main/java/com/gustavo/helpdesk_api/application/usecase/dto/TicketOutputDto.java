package com.gustavo.helpdesk_api.application.usecase.dto;


import java.util.Date;
import java.util.UUID;

public record TicketOutputDto(
        UUID id,
        Integer ticketNumber,
        CatalogOutputDto status,
        String priority,
        String title,
        String description,
        RequesterOutputDto requester,
        CatalogOutputDto system,
        CatalogOutputDto category,
        CatalogOutputDto subcategory,
        Date createdAt,
        Date finishedAt,
        Date startDate,
        CatalogOutputDto tiSector,
        CatalogOutputDto assignee,
        String resolutionNote
) {
}
