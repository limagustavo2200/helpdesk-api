package com.gustavo.helpdesk_api.infra.repository.ticket;

import com.gustavo.helpdesk_api.core.entity.Ticket;
import com.gustavo.helpdesk_api.core.gateway.repository.TicketRepositoryGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<Ticket> findByStatusId(List<UUID> statusIds, Pageable pageable) {
        return jpaRepository.findByStatusIdIn(statusIds, pageable);
    }

    @Override
    public Page<Ticket> findByEmailAndStatusId(String email, List<UUID> statusIds, Pageable pageable) {
        return jpaRepository.findByRequesterEmailAndStatusIdIn(email, statusIds, pageable);
    }
}
