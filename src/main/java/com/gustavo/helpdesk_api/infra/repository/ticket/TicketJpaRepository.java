package com.gustavo.helpdesk_api.infra.repository.ticket;

import com.gustavo.helpdesk_api.core.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketJpaRepository extends JpaRepository<Ticket, UUID> {

   Optional<Ticket> findByTicketNumber(Integer ticketNumber);

   @Query("SELECT t FROM Ticket t " +
          "JOIN FETCH t.status " +
          "JOIN FETCH t.category " +
          "JOIN FETCH t.subcategory " +
          "JOIN FETCH t.systemEntity " +
          "LEFT JOIN FETCH t.assignee " +
          "LEFT JOIN FETCH t.tiSector " +
          "WHERE t.status.id IN :statusIds")
   List<Ticket> findByStatusIdIn(@Param("statusIds") List<UUID> statusIds);

   @Query("SELECT t FROM Ticket t " +
          "JOIN FETCH t.status " +
          "JOIN FETCH t.category " +
          "JOIN FETCH t.subcategory " +
          "JOIN FETCH t.systemEntity " +
          "LEFT JOIN FETCH t.assignee " +
          "LEFT JOIN FETCH t.tiSector " +
          "WHERE t.requester.email = :email " +
          "AND t.status.id IN :statusIds")
   List<Ticket> findByRequesterEmailAndStatusIdIn(@Param("email") String email, @Param("statusIds") List<UUID> statusIds);
}