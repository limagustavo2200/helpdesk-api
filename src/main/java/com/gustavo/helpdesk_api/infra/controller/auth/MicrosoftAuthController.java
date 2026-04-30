package com.gustavo.helpdesk_api.infra.controller.auth;

import com.gustavo.helpdesk_api.application.usecase.auth.AuthenticateMicrosoftUseCase;
import com.gustavo.helpdesk_api.application.usecase.auth.MicrosoftAuthInputDto;
import com.gustavo.helpdesk_api.application.usecase.auth.MicrosoftAuthOutputDto;
import com.gustavo.helpdesk_api.core.gateway.auth.TokenGateway;
import com.gustavo.helpdesk_api.core.valueobject.JwtPayload;
import com.gustavo.helpdesk_api.infra.util.PkceUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@RestController
@RequestMapping("/auth-microsoft")
public class MicrosoftAuthController {

   private final AuthenticateMicrosoftUseCase authenticateUseCase;
   private final TokenGateway tokenGateway;

   @Value("${azure.tenant-id}") private String tenantId;
   @Value("${azure.client-id}") private String clientId;
   @Value("${azure.redirect-uri}") private String redirectUri;
   @Value("${frontend.url}") private String frontendUrl;

   public MicrosoftAuthController(AuthenticateMicrosoftUseCase authenticateUseCase, TokenGateway tokenGateway) {
      this.authenticateUseCase = authenticateUseCase;
      this.tokenGateway = tokenGateway;
   }

   @GetMapping("/login")
   public void redirectToMicrosoft(@RequestParam(required = false) String prompt, HttpServletResponse response) throws IOException {
      String verifier = PkceUtil.generateCodeVerifier();
      String challenge = PkceUtil.generateCodeChallenge(verifier);

      // Criando o cookie PKCE usando ResponseCookie para suportar SameSite=Lax
      ResponseCookie pkceCookie = ResponseCookie.from("pkce_verifier", verifier)
         .httpOnly(true)
         .secure(false) // Mude para true apenas se usar HTTPS (produção)
         .path("/")
         .maxAge(300)
         .sameSite("Lax") // Permite que o cookie seja enviado no redirect da Microsoft
         .build();

      response.addHeader(HttpHeaders.SET_COOKIE, pkceCookie.toString());

      String scope = "openid profile email offline_access User.Read.All Directory.Read.All Group.Read.All";

      String authUrl = UriComponentsBuilder
         .fromHttpUrl("https://login.microsoftonline.com/" + tenantId + "/oauth2/v2.0/authorize")
         .queryParam("client_id", clientId)
         .queryParam("response_type", "code")
         .queryParam("redirect_uri", redirectUri)
         .queryParam("scope", scope)
         .queryParam("code_challenge", challenge)
         .queryParam("code_challenge_method", "S256")
         .queryParam("prompt", prompt)
         .build().toUriString();

      response.sendRedirect(authUrl);
   }

   @GetMapping("/callback")
   public void callback(
      @RequestParam String code,
      @CookieValue(name = "pkce_verifier", required = false) String codeVerifier,
      HttpServletResponse response
   ) throws IOException {

      if (codeVerifier == null) {
         response.sendRedirect(frontendUrl + "/login?error=cookie_missing");
         return;
      }

      try {
         var input = new MicrosoftAuthInputDto(code, codeVerifier);

         // Executa a troca do code pelo token
         MicrosoftAuthOutputDto output = authenticateUseCase.execute(input);

         clearCookie("pkce_verifier", response);

         String targetUrl = UriComponentsBuilder.fromHttpUrl(frontendUrl + "/auth-success")
            .queryParam("token", output.token())
            .build().toUriString();

         response.sendRedirect(targetUrl);

      } catch (Exception ex) {
         clearCookie("pkce_verifier", response);
         response.sendRedirect(frontendUrl + "/login?error=auth_failed");
      }
   }

   private void clearCookie(String name, HttpServletResponse response) {
      ResponseCookie cookie = ResponseCookie.from(name, "")
         .httpOnly(true)
         .path("/")
         .maxAge(0)
         .sameSite("Lax")
         .build();
      response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
   }

   @GetMapping("/me")
   public ResponseEntity<?> me(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
         return ResponseEntity.status(401).body("Token não encontrado ou formato inválido!");
      }

      try {
         String token = authHeader.substring(7); // Remove o "Bearer "
         JwtPayload userClaims = tokenGateway.validateToken(token);
         return ResponseEntity.ok(userClaims);
      } catch (Exception e) {
         return ResponseEntity.status(401).body("Token inválido ou expirado");
      }
   }
}