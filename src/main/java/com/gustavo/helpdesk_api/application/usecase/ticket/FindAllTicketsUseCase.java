package com.gustavo.helpdesk_api.application.usecase.ticket;


import com.gustavo.helpdesk_api.application.usecase.dto.CatalogOutputDto;
import com.gustavo.helpdesk_api.application.usecase.dto.RequesterOutputDto;
import com.gustavo.helpdesk_api.application.usecase.dto.TicketOutputDto;
import com.gustavo.helpdesk_api.core.entity.Ticket;
import com.gustavo.helpdesk_api.core.gateway.repository.TicketRepositoryGateway;
import com.gustavo.helpdesk_api.core.valueobject.JwtPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FindAllTicketsUseCase {

    private final TicketRepositoryGateway ticketRepository;

    public FindAllTicketsUseCase(TicketRepositoryGateway ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Page<TicketOutputDto> execute(List<UUID> statusIds, JwtPayload jwt, Pageable pageable) {
       List<UUID> filterStatus = (statusIds == null) ? List.of() : statusIds;

       Page<Ticket> tickets;

       if ("admin".equalsIgnoreCase(jwt.role())) {
          tickets = ticketRepository.findByStatusId(filterStatus, pageable);
       } else {
          tickets = ticketRepository.findByEmailAndStatusId(jwt.email(), filterStatus, pageable);
       }

       return tickets.map(ticket -> new TicketOutputDto(
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
       ));
    }
}
