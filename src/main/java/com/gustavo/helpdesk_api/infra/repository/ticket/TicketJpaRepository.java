package com.gustavo.helpdesk_api.infra.repository.ticket;

import com.gustavo.helpdesk_api.core.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketJpaRepository extends JpaRepository<Ticket, UUID> {

   Optional<Ticket> findByTicketNumber(Integer ticketNumber);

   List<Ticket> findByStatusIdIn(List<UUID> statusIds);

   List<Ticket> findByRequesterEmailAndStatusIdIn(String email, List<UUID> statusIds);
}