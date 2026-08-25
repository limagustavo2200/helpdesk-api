package com.gustavo.helpdesk_api.core.gateway.repository;

import com.gustavo.helpdesk_api.core.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepositoryGateway {

   Ticket save(Ticket ticket);

   Optional<Ticket> findById(UUID id);

   Optional<Ticket> findByTicketNumber(Integer ticketNumber);

   Page<Ticket> findByStatusId(List<UUID> statusIds, Pageable pageable);

   Page<Ticket> findByEmailAndStatusId(String email, List<UUID> statusIds, Pageable pageable);

}
