package com.gustavo.helpdesk_api.application.util;

import com.gustavo.helpdesk_api.core.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TeamsMessageFormat {

   public static String execute(Ticket ticket) {
      return """
        📌 **Novo Chamado Aberto!**

        **Número:** %s

        **Título:** %s

        **Descrição:** %s

        **Solicitante:** %s (%s)

        **Setor:** %s

        **Prioridade:** %s

        **Sistema:** %s

        **Categoria:** %s

        **Subcategoria:** %s

        **Status:** %s
        """.formatted(
         ticket.getTicketNumber(),
         ticket.getTitle(),
         ticket.getDescription(),
         ticket.getRequester().getUsername(),
         ticket.getRequester().getEmail(),
         ticket.getRequester().getSector(),
         ticket.getPriority().getLabel(),
         ticket.getSystemEntity().getName(),
         ticket.getCategory().getName(),
         ticket.getSubcategory().getName(),
         ticket.getStatus().getName()
      );
   }
}
