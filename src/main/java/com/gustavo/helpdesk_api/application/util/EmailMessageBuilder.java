package com.gustavo.helpdesk_api.application.util;

import com.gustavo.helpdesk_api.core.entity.Ticket;

public class EmailMessageBuilder {

   /**
    * Retorna o valor como String ou um traço caso seja nulo,
    * evitando NullPointerException durante o replace.
    */
   private static String safeString(Object value) {
      return value == null ? "-" : String.valueOf(value);
   }

   public static String createdTicket(Ticket ticket) {
      String html = """
            <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
            <html xmlns="http://www.w3.org/1999/xhtml">
            <head>
                <meta charset="utf-8" />
                <style>
                    body, p, div { font-family: Arial, Helvetica, sans-serif; font-size: 14px; }
                    body { color: #0d0d0d; margin: 0; padding: 0; background-color: #636981; }
                    .field-label { font-weight: bold; color: #636981; margin-bottom: 2px; }
                    .field-value { margin-bottom: 15px; padding-left: 10px; border-left: 3px solid #636981; }
                </style>
            </head>
            <body>
            <center style="width:100%; background-color:#636981; padding: 20px 0;">
                <div style="max-width:600px; margin:0 auto; background-color:#ffffff; text-align: left; border-radius: 8px; overflow: hidden;">
                    <div style="background-color: #1e293b; padding: 20px; text-align: center;">
                        <h2 style="color: #ffffff; margin: 0;">Novo chamado aberto!</h2>
                    </div>
                    <div style="padding: 24px;">
                        <div class="field-label">Número do Chamado:</div>
                        <div class="field-value">{{ticketNumber}}</div>
                        
                        <div class="field-label">Status:</div>
                        <div class="field-value">{{status}}</div>

                        <div class="field-label">Título:</div>
                        <div class="field-value">{{title}}</div>

                        <div class="field-label">Descrição:</div>
                        <div class="field-value">{{description}}</div>

                        <div class="field-label">Solicitante:</div>
                        <div class="field-value">{{name}} ({{email}})</div>

                        <div class="field-label">Setor:</div>
                        <div class="field-value">{{sector}}</div>

                        <div class="field-label">Categoria / Sistema / Subcategoria:</div>
                        <div class="field-value">{{category}}  /  {{system}}  /  {{subcategory}}</div>

                        <div class="field-label">Prioridade:</div>
                        <div class="field-value">{{priority}}</div>

                        <p style="margin-top: 30px;">Atenciosamente,<br /><strong>T.I - Akafloor</strong></p>
                    </div>
                </div>
            </center>
            </body>
            </html>
            """;

      return html
         .replace("{{ticketNumber}}", safeString(ticket.getTicketNumber()))
         .replace("{{status}}", ticket.getStatus() != null ? safeString(ticket.getStatus().getName()) : "-")
         .replace("{{title}}", safeString(ticket.getTitle()))
         .replace("{{description}}", safeString(ticket.getDescription()))
         .replace("{{name}}", ticket.getRequester() != null ? safeString(ticket.getRequester().getUsername()) : "-")
         .replace("{{email}}", ticket.getRequester() != null ? safeString(ticket.getRequester().getEmail()) : "-")
         .replace("{{sector}}", ticket.getRequester() != null ? safeString(ticket.getRequester().getSector()) : "-")
         .replace("{{system}}", ticket.getSystemEntity() != null ? safeString(ticket.getSystemEntity().getName()) : "-")
         .replace("{{category}}", ticket.getCategory() != null ? safeString(ticket.getCategory().getName()) : "-")
         .replace("{{subcategory}}", ticket.getSubcategory() != null ? safeString(ticket.getSubcategory().getName()) : "-")
         .replace("{{priority}}", ticket.getPriority() != null ? safeString(ticket.getPriority().getLabel()) : "-");
   }

   public static String updatedTicket(Ticket ticket) {
      String html = """
            <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
            <html xmlns="http://www.w3.org/1999/xhtml">
            <head>
                <meta charset="utf-8" />
                <style>
                    body, p, div { font-family: Arial, Helvetica, sans-serif; font-size: 14px; }
                    body { color: #0d0d0d; margin: 0; padding: 0; background-color: #636981; }
                    .field-label { font-weight: bold; color: #636981; margin-bottom: 2px; }
                    .field-value { margin-bottom: 15px; padding-left: 10px; border-left: 3px solid #636981; }
                </style>
            </head>
            <body>
            <center style="width:100%; background-color:#636981; padding: 20px 0;">
                <div style="max-width:600px; margin:0 auto; background-color:#ffffff; text-align: left; border-radius: 8px; overflow: hidden;">
                    <div style="background-color: #1e293b; padding: 20px; text-align: center;">
                        <h2 style="color: #ffffff; margin: 0;">Atualização do chamado #{{ticketNumber}}</h2>
                    </div>
                    <div style="padding: 24px;">
                        <div class="field-label">Número do Chamado:</div>
                        <div class="field-value">{{ticketNumber}}</div>
                        
                        <div class="field-label">Status:</div>
                        <div class="field-value">{{status}}</div>

                        <div class="field-label">Título:</div>
                        <div class="field-value">{{title}}</div>

                        <div class="field-label">Descrição:</div>
                        <div class="field-value">{{description}}</div>

                        <div class="field-label">Solicitante:</div>
                        <div class="field-value">{{name}} ({{email}})</div>

                        <div class="field-label">Setor:</div>
                        <div class="field-value">{{sector}}</div>

                        <div class="field-label">Categoria / Sistema / Subcategoria:</div>
                        <div class="field-value">{{category}}  /  {{system}}  /  {{subcategory}}</div>

                        <div class="field-label">Prioridade:</div>
                        <div class="field-value">{{priority}}</div>
                                                
                        <div class="field-label">Nota de Resolução:</div>
                        <div class="field-value">{{resolution}}</div>

                        <p style="margin-top: 30px;">Atenciosamente,<br /><strong>T.I - Akafloor</strong></p>
                    </div>
                </div>
            </center>
            </body>
            </html>
            """;

      return html
         .replace("{{ticketNumber}}", safeString(ticket.getTicketNumber()))
         .replace("{{status}}", ticket.getStatus() != null ? safeString(ticket.getStatus().getName()) : "-")
         .replace("{{title}}", safeString(ticket.getTitle()))
         .replace("{{description}}", safeString(ticket.getDescription()))
         .replace("{{name}}", ticket.getRequester() != null ? safeString(ticket.getRequester().getUsername()) : "-")
         .replace("{{email}}", ticket.getRequester() != null ? safeString(ticket.getRequester().getEmail()) : "-")
         .replace("{{sector}}", ticket.getRequester() != null ? safeString(ticket.getRequester().getSector()) : "-")
         .replace("{{system}}", ticket.getSystemEntity() != null ? safeString(ticket.getSystemEntity().getName()) : "-")
         .replace("{{category}}", ticket.getCategory() != null ? safeString(ticket.getCategory().getName()) : "-")
         .replace("{{subcategory}}", ticket.getSubcategory() != null ? safeString(ticket.getSubcategory().getName()) : "-")
         .replace("{{priority}}", ticket.getPriority() != null ? safeString(ticket.getPriority().getLabel()) : "-")
         .replace("{{resolution}}", safeString(ticket.getResolutionNote()));
   }
}