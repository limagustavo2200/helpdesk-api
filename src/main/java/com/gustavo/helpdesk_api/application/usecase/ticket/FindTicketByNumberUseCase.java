package com.gustavo.helpdesk_api.application.usecase.ticket;


import com.gustavo.helpdesk_api.application.usecase.dto.CatalogOutputDto;
import com.gustavo.helpdesk_api.application.usecase.dto.RequesterOutputDto;
import com.gustavo.helpdesk_api.application.usecase.dto.TicketOutputDto;
import com.gustavo.helpdesk_api.core.exception.ResourceNotFoundException;
import com.gustavo.helpdesk_api.core.gateway.repository.TicketRepositoryGateway;
import org.springframework.stereotype.Service;

@Service
public class FindTicketByNumberUseCase {

    private final TicketRepositoryGateway ticketRepository;

    public FindTicketByNumberUseCase(TicketRepositoryGateway ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public TicketOutputDto execute(Integer ticketNumber) {
        var ticket = ticketRepository.findByTicketNumber(ticketNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket com essa numeração não existe"));

        return new TicketOutputDto(
                ticket.getId(),
                ticket.getTicketNumber(),
                new CatalogOutputDto(ticket.getStatus().getId(), ticket.getStatus().getName()),
                ticket.getPriority().getLabel(),
                ticket.getTitle(),
                ticket.getDescription(),
                new RequesterOutputDto(ticket.getRequester().getUsername(), ticket.getRequester().getEmail(), ticket.getRequester().getSector()),
                new CatalogOutputDto(ticket.getSystemEntity().getId(), ticket.getSystemEntity().getName()),
                new CatalogOutputDto(ticket.getCategory().getId(), ticket.getCategory().getName()),
                new CatalogOutputDto(ticket.getSubcategory().getId(), ticket.getSubcategory().getName()),
                ticket.getCreatedAt(),
                ticket.getFinishedAt(),
                ticket.getStartDate(),
                ticket.getTiSector() != null
                        ? new CatalogOutputDto(ticket.getTiSector().getId(), ticket.getTiSector().getName())
                        : null,
                ticket.getAssignee() != null
                        ? new CatalogOutputDto(ticket.getAssignee().getId(), ticket.getAssignee().getName())
                        : null,
                ticket.getResolutionNote()
        );
    }
}
