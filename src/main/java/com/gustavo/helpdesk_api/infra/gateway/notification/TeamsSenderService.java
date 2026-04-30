package com.gustavo.helpdesk_api.infra.gateway.notification;

import com.gustavo.helpdesk_api.core.gateway.notification.TeamsSenderGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class TeamsSenderService implements TeamsSenderGateway {

   @Value("${api.teams.webhook.url}")
   private String teamsWebhookUrl;

   private final RestClient restClient;

   public TeamsSenderService(RestClient restClient) {
      this.restClient = restClient;
   }

   @Override
   public Void sendMessage(String message) {

      if (teamsWebhookUrl == null || teamsWebhookUrl.isBlank()) {
         throw new RuntimeException("Webhook do Teams não configurado");
      }

      Map<String, String> body = Map.of("text", message);

      restClient.post()
         .uri(teamsWebhookUrl)
         .header("Content-Type", "application/json")
         .body(body)
         .retrieve()
         .toBodilessEntity();

      return null;
   }
}
