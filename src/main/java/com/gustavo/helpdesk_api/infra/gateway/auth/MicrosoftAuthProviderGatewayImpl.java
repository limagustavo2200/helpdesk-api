package com.gustavo.helpdesk_api.infra.gateway.auth;

import com.gustavo.helpdesk_api.core.gateway.auth.AuthProviderGateway;
import com.gustavo.helpdesk_api.core.gateway.auth.GraphUserDto;
import com.gustavo.helpdesk_api.core.gateway.auth.TokenResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.concurrent.CompletableFuture;

@Component
public class MicrosoftAuthProviderGatewayImpl implements AuthProviderGateway {

   private final RestClient restClient;

   @Value("${azure.tenant-id}")
   private String tenantId;

   @Value("${azure.client-id}")
   private String clientId;

   @Value("${azure.client-secret}")
   private String clientSecret;

   @Value("${azure.redirect-uri}")
   private String redirectUri;

   @Value("${azure.graph-uri}")
   private String graphUri;

   public MicrosoftAuthProviderGatewayImpl(RestClient.Builder restClientBuilder) {
      this.restClient = restClientBuilder.build();
   }

   @Override
   public TokenResponseDto getTokens(String code, String codeVerifier) {
      MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
      formData.add("client_id", clientId);
      // Use exatamente os mesmos scopes que estão no seu Controller
      formData.add("scope", "openid profile email offline_access User.Read.All Directory.Read.All Group.Read.All");
      formData.add("code", code);
      formData.add("redirect_uri", redirectUri);
      formData.add("grant_type", "authorization_code");
      formData.add("code_verifier", codeVerifier);
      formData.add("client_secret", clientSecret);

      return restClient.post()
         .uri("https://login.microsoftonline.com/{tenant}/oauth2/v2.0/token", this.tenantId)
         .contentType(MediaType.APPLICATION_FORM_URLENCODED)
         .body(formData)
         .retrieve()
         .onStatus(HttpStatusCode::isError, (request, response) -> {
            throw new RuntimeException("Azure OAuth ERROR: " + response.getStatusCode() + " - " + response.getStatusText());
         })
         .body(TokenResponseDto.class);
   }

   @Override
   public GraphUserDto getUserInfoFromGraph(String accessToken) {
      return restClient.get()
         .uri(graphUri)
         .headers(headers -> headers.setBearerAuth(accessToken))
         .retrieve()
         .onStatus(HttpStatusCode::isError, (request, response) -> {
            // ISSO VAI MOSTRAR O MOTIVO REAL NO CONSOLE
            byte[] body = response.getBody().readAllBytes();
            String message = new String(body);
            System.err.println("MS GRAPH ERROR BODY: " + message);
            throw new RuntimeException("Graph API ERROR: " + response.getStatusCode() + " - " + message);
         })
         .body(GraphUserDto.class);
   }
}