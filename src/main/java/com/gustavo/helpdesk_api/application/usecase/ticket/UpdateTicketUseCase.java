package com.gustavo.helpdesk_api.application.usecase.ticket;


import com.gustavo.helpdesk_api.application.usecase.dto.TicketOutputDto;
import com.gustavo.helpdesk_api.application.usecase.dto.UpdateTicketInputDto;
import com.gustavo.helpdesk_api.application.util.EmailMessageBuilder;
import com.gustavo.helpdesk_api.core.entity.Assignee;
import com.gustavo.helpdesk_api.core.entity.TiSector;
import com.gustavo.helpdesk_api.core.entity.Ticket;
import com.gustavo.helpdesk_api.core.exception.ResourceNotFoundException;
import com.gustavo.helpdesk_api.core.gateway.notification.EmailSenderGateway;
import com.gustavo.helpdesk_api.core.gateway.repository.*;
import com.gustavo.helpdesk_api.core.service.PriorityCatalog;
import com.gustavo.helpdesk_api.core.valueobject.Email;
import com.gustavo.helpdesk_api.core.valueobject.Priority;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UpdateTicketUseCase {

   private final EmailSenderGateway emailSenderGateway;
   private final TicketRepositoryGateway ticketRepository;
   private final CategoryRepositoryGateway categoryRepository;
   private final SubcategoryRepositoryGateway subcategoryRepository;
   private final SystemRepositoryGateway systemRepository;
   private final AssigneeRepositoryGateway assigneeRepository;
   private final TiSectorRepositoryGateway tiSectorRepository;
   private final StatusRepositoryGateway statusRepository;

   public UpdateTicketUseCase(
      EmailSenderGateway emailSenderGateway,
      TicketRepositoryGateway ticketRepository,
      CategoryRepositoryGateway categoryRepository,
      SubcategoryRepositoryGateway subcategoryRepository,
      SystemRepositoryGateway systemRepository,
      AssigneeRepositoryGateway assigneeRepository,
      TiSectorRepositoryGateway tiSectorRepository,
      StatusRepositoryGateway statusRepository) {
      this.emailSenderGateway = emailSenderGateway;
      this.ticketRepository = ticketRepository;
      this.categoryRepository = categoryRepository;
      this.subcategoryRepository = subcategoryRepository;
      this.systemRepository = systemRepository;
      this.assigneeRepository = assigneeRepository;
      this.tiSectorRepository = tiSectorRepository;
      this.statusRepository = statusRepository;
   }

   public void execute(UpdateTicketInputDto input) {
      Ticket ticket = ticketRepository.findByTicketNumber(input.ticketNumber())
         .orElseThrow(() -> new ResourceNotFoundException("Ticket não encontrado"));

      var status = statusRepository.findById(input.statusId())
         .orElseThrow(() -> new ResourceNotFoundException("Status não encontrado"));

      // verifica se o status foi alterado
      boolean statusChanged = ticket.hasStatusChanged(status);

      var category = categoryRepository.findById(input.categoryId())
         .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

      var subcategory = subcategoryRepository.findById(input.subcategoryId())
         .orElseThrow(() -> new ResourceNotFoundException("Subcategoria não encontrada"));

      var system = systemRepository.findById(input.systemId())
         .orElseThrow(() -> new ResourceNotFoundException("Sistema não encontrado"));


      var currentTiSectorId = ticket.getTiSector() != null
         ? ticket.getTiSector().getId()
         : null;

      Assignee assignee = null;
      if (input.assigneeId() != null) {
         assignee = assigneeRepository.findById(input.assigneeId())
            .orElseThrow(() -> new ResourceNotFoundException("Responsável não encontrado"));
      }

      TiSector tiSector = null;
      if (input.tiSectorId() != null) {
         tiSector = tiSectorRepository.findById(input.tiSectorId())
            .orElseThrow(() -> new ResourceNotFoundException("Setor TI não encontrado"));
      }

      Priority priority = PriorityCatalog.fromLabel(input.priority());

      ticket.updateTicket(
         status,
         priority,
         category,
         subcategory,
         system,
         assignee,
         tiSector,
         input.resolutionNote()
      );

      // verifica se o status mudou, para entao chamar o servico de envio de email
      if (statusChanged) {
         Email email = new Email(
            "chamadosti@akafloor.com.br",
            List.of(ticket.getRequester().getEmail(), "otavio@akafloor.com.br", "desenvolvimento@akafloor.com.br", "sistemas@akafloor.com.br"),
            "Status do chamado #" + ticket.getTicketNumber() + " atualizado",
            EmailMessageBuilder.updatedTicket(ticket)
         );
         emailSenderGateway.sendEmail(email);
      }

      ticketRepository.save(ticket);
   }
}
