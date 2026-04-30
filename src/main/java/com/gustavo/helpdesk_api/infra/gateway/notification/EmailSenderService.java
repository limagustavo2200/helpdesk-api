package com.gustavo.helpdesk_api.infra.gateway.notification;


import com.gustavo.helpdesk_api.core.gateway.notification.EmailSenderGateway;
import com.gustavo.helpdesk_api.core.valueobject.Email;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
public class EmailSenderService implements EmailSenderGateway {

   @Value("${api.email-service.url}")
   private String url;

   @Value("${api.email-service.secret}")
   private String secret;

   private final RestClient restClient;

   public EmailSenderService(RestClient restClient) {
      this.restClient = restClient;
   }

   @Override
   public void sendEmail(Email email) {
      String token = generateToken();

      restClient.post()
         .uri(url)
         .header("Authorization", "Bearer " + token)
         .body(email)
         .retrieve()
         .toBodilessEntity();
   }

   private String generateToken() {

      SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

      Instant now = Instant.now();
      Instant expiry = now.plusSeconds(120);

      return Jwts.builder()
         .subject("helpdesk-api")
         .issuedAt(Date.from(now))
         .expiration(Date.from(expiry))
         .signWith(key)
         .compact();
   }
}
