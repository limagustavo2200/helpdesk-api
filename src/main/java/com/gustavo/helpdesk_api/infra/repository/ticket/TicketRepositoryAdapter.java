package com.gustavo.helpdesk_api.infra.repository.ticket;

import com.gustavo.helpdesk_api.core.entity.Ticket;
import com.gustavo.helpdesk_api.core.gateway.repository.TicketRepositoryGateway;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public class TicketRepositoryAdapter implements TicketRepositoryGateway {

    private final TicketJpaRepository jpaRepository;

    public TicketRepositoryAdapter(TicketJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Ticket save(Ticket ticket) {
        return jpaRepository.save(ticket);
    }

    @Override
    public Optional<Ticket> findById(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<Ticket> findByTicketNumber(Integer ticketNumber) {
        return jpaRepository.findByTicketNumber(ticketNumber);
    }


    @Override
    public List<Ticket> findByStatusId(List<UUID> statusIds) {
      return jpaRepository.findByStatusIdIn(statusIds);
    }

   @Override
   public List<Ticket> findByEmailAndStatusId(String email, List<UUID> statusIds) {
      return jpaRepository.findByRequesterEmailAndStatusIdIn(email, statusIds);
   }
}
