package com.gustavo.helpdesk_api.core.gateway.repository;

import com.gustavo.helpdesk_api.core.entity.Ticket;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepositoryGateway {

   Ticket save(Ticket ticket);

   Optional<Ticket> findById(UUID id);

   Optional<Ticket> findByTicketNumber(Integer ticketNumber);

   List<Ticket> findByStatusId(List<UUID> statusIds);

   List<Ticket> findByEmailAndStatusId(String email, List<UUID> statusIds);

}
