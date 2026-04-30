package com.gustavo.helpdesk_api.application.usecase.ticket;


import com.gustavo.helpdesk_api.application.usecase.dto.CatalogOutputDto;
import com.gustavo.helpdesk_api.application.usecase.dto.CreateTicketInputDto;
import com.gustavo.helpdesk_api.application.usecase.dto.RequesterOutputDto;
import com.gustavo.helpdesk_api.application.usecase.dto.TicketOutputDto;
import com.gustavo.helpdesk_api.application.util.EmailMessageBuilder;
import com.gustavo.helpdesk_api.application.util.TeamsMessageFormat;
import com.gustavo.helpdesk_api.core.entity.*;
import com.gustavo.helpdesk_api.core.exception.ResourceNotFoundException;
import com.gustavo.helpdesk_api.core.gateway.notification.EmailSenderGateway;
import com.gustavo.helpdesk_api.core.gateway.notification.TeamsSenderGateway;
import com.gustavo.helpdesk_api.core.gateway.repository.CategoryRepositoryGateway;
import com.gustavo.helpdesk_api.core.gateway.repository.SubcategoryRepositoryGateway;
import com.gustavo.helpdesk_api.core.gateway.repository.SystemRepositoryGateway;
import com.gustavo.helpdesk_api.core.gateway.repository.TicketRepositoryGateway;
import com.gustavo.helpdesk_api.core.service.PriorityCatalog;
import com.gustavo.helpdesk_api.core.service.StatusCatalog;
import com.gustavo.helpdesk_api.core.valueobject.Email;
import com.gustavo.helpdesk_api.core.valueobject.Priority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateTicketUseCase {

   private final TicketRepositoryGateway ticketRepository;
   private final CategoryRepositoryGateway categoryRepository;
   private final SubcategoryRepositoryGateway subcategoryRepository;
   private final SystemRepositoryGateway systemRepository;
   private final StatusCatalog statusCatalog;
   private final EmailSenderGateway emailSenderGateway;
   private final TeamsSenderGateway teamsSenderGateway;

   public CreateTicketUseCase(
      TicketRepositoryGateway ticketRepository,
      CategoryRepositoryGateway categoryRepository,
      SubcategoryRepositoryGateway subcategoryRepository,
      SystemRepositoryGateway systemRepository,
      StatusCatalog statusCatalog,
      EmailSenderGateway emailSenderGateway,
      TeamsSenderGateway teamsSenderGateway) {
      this.ticketRepository = ticketRepository;
      this.categoryRepository = categoryRepository;
      this.subcategoryRepository = subcategoryRepository;
      this.systemRepository = systemRepository;
      this.statusCatalog = statusCatalog;
      this.emailSenderGateway = emailSenderGateway;
      this.teamsSenderGateway = teamsSenderGateway;
   }

   public TicketOutputDto execute(CreateTicketInputDto input) {
      Category category = categoryRepository.findById(input.categoryId())
         .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

      Subcategory subcategory = subcategoryRepository.findById(input.subcategoryId())
         .orElseThrow(() -> new ResourceNotFoundException("Subcategoria não encontrada"));

      SystemEntity system = systemRepository.findById(input.systemId())
         .orElseThrow(() -> new ResourceNotFoundException("Sistema não encontrado"));


      Status status = statusCatalog.open();

      Priority priority = PriorityCatalog.fromLabel(input.priority());

      Ticket ticket = new Ticket(
         input.requester(),
         input.title(),
         input.description(),
         priority,
         category,
         subcategory,
         system,
         status
      );
      var ticketSaved = ticketRepository.save(ticket);

      // Criando email para ser enviado
      Email email = new Email(
         "chamadosti@akafloor.com.br",
         List.of(ticketSaved.getRequester().getEmail(), "otavio@akafloor.com.br", "desenvolvimento@akafloor.com.br", "sistemas@akafloor.com.br"),
         "Chamado " + ticketSaved.getTicketNumber() + " aberto",
         EmailMessageBuilder.createdTicket(ticketSaved)
      );

      // Enviando email
      try {
         emailSenderGateway.sendEmail(email);
      } catch (Exception e) {
         System.err.println("Falha ao enviar Email: " + e.getMessage());
      }

      // Montando msg pro Teams
      String teamsMessage = TeamsMessageFormat.execute(ticketSaved);

      // Enviando a mensagem pro teams
      try {
         teamsSenderGateway.sendMessage(teamsMessage);
      } catch (Exception e) {
         System.err.println("Falha ao enviar mensagem no teams: " + e.getMessage());
      }


      return new TicketOutputDto(
         ticketSaved.getId(),
         ticketSaved.getTicketNumber(),
         new CatalogOutputDto(ticketSaved.getStatus().getId(), ticketSaved.getStatus().getName()),
         ticketSaved.getPriority().getLabel(),
         ticketSaved.getTitle(),
         ticketSaved.getDescription(),
         new RequesterOutputDto(ticketSaved.getRequester().getUsername(), ticketSaved.getRequester().getEmail(), ticketSaved.getRequester().getSector()),
         new CatalogOutputDto(ticketSaved.getSystemEntity().getId(), ticketSaved.getSystemEntity().getName()),
         new CatalogOutputDto(ticketSaved.getCategory().getId(), ticketSaved.getCategory().getName()),
         new CatalogOutputDto(ticketSaved.getSubcategory().getId(), ticketSaved.getSubcategory().getName()),
         ticketSaved.getCreatedAt(),
         ticketSaved.getFinishedAt(),
         ticketSaved.getStartDate(),
         ticketSaved.getTiSector() != null
            ? new CatalogOutputDto(ticketSaved.getTiSector().getId(), ticketSaved.getTiSector().getName())
            : null,
         ticketSaved.getAssignee() != null
            ? new CatalogOutputDto(ticketSaved.getAssignee().getId(), ticketSaved.getAssignee().getName())
            : null,
         ticketSaved.getResolutionNote()
      );
   }
}
