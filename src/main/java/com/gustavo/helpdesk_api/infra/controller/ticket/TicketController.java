package com.gustavo.helpdesk_api.infra.controller.ticket;


import com.gustavo.helpdesk_api.application.usecase.dto.CreateTicketInputDto;
import com.gustavo.helpdesk_api.application.usecase.dto.UpdateTicketInputDto;
import com.gustavo.helpdesk_api.application.usecase.ticket.CreateTicketUseCase;
import com.gustavo.helpdesk_api.application.usecase.ticket.FindAllTicketsUseCase;
import com.gustavo.helpdesk_api.application.usecase.dto.TicketOutputDto;
import com.gustavo.helpdesk_api.application.usecase.ticket.FindTicketByNumberUseCase;
import com.gustavo.helpdesk_api.application.usecase.ticket.UpdateTicketUseCase;
import com.gustavo.helpdesk_api.core.valueobject.JwtPayload;
import com.gustavo.helpdesk_api.core.valueobject.Requester;
import com.gustavo.helpdesk_api.infra.controller.ticket.dto.CreateTicketRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/ticket")
public class TicketController {

   private final CreateTicketUseCase createTicketUseCase;
   private final FindAllTicketsUseCase findAllTicketsUseCase;
   private final FindTicketByNumberUseCase findTicketByNumberUseCase;
   private final UpdateTicketUseCase updateTicketUseCase;

   public TicketController(
      CreateTicketUseCase createTicketUseCase,
      FindAllTicketsUseCase findAllTicketsUseCase,
      FindTicketByNumberUseCase findTicketByNumberUseCase,
      UpdateTicketUseCase updateTicketUseCase
   ) {
      this.createTicketUseCase = createTicketUseCase;
      this.findAllTicketsUseCase = findAllTicketsUseCase;
      this.findTicketByNumberUseCase = findTicketByNumberUseCase;
      this.updateTicketUseCase = updateTicketUseCase;
   }


   @PostMapping
   public ResponseEntity<TicketOutputDto> create(@Valid @RequestBody CreateTicketRequestDto request) {
      CreateTicketInputDto inputDto = new CreateTicketInputDto(
         new Requester(request.username(), request.email(), request.sector()),
         request.title(),
         request.description(),
         request.priority(),
         request.categoryId(),
         request.subcategoryId(),
         request.systemId()
      );

      var ticketSaved = createTicketUseCase.execute(inputDto);
      return ResponseEntity.status(HttpStatus.CREATED).body(ticketSaved);
   }

   @GetMapping
   public ResponseEntity<List<TicketOutputDto>> findAll(
      @RequestParam(required = false) List<UUID> statusIds
   ) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      JwtPayload jwt = (authentication != null)
         ? (JwtPayload) authentication.getPrincipal()
         : new JwtPayload(null, null, null, "admin");
      var tickets = findAllTicketsUseCase.execute(statusIds, jwt);
      return ResponseEntity.status(HttpStatus.OK).body(tickets);
   }


   @GetMapping("/{ticketNumber}")
   public ResponseEntity<TicketOutputDto> findByNumber(
      @PathVariable
      @NotNull
      @Positive
      Integer ticketNumber) {
      var ticket = findTicketByNumberUseCase.execute(ticketNumber);
      return ResponseEntity.status(HttpStatus.OK).body(ticket);
   }

   @PutMapping
   public ResponseEntity<Void> update(@Valid @RequestBody UpdateTicketInputDto request) {
      updateTicketUseCase.execute(request);

      return ResponseEntity.status(HttpStatus.OK).build();
   }
}
