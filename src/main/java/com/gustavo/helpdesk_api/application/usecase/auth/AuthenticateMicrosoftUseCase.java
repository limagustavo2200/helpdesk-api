package com.gustavo.helpdesk_api.application.usecase.auth;


import com.gustavo.helpdesk_api.core.gateway.auth.AuthProviderGateway;
import com.gustavo.helpdesk_api.core.gateway.auth.GraphUserDto;
import com.gustavo.helpdesk_api.core.gateway.auth.TokenGateway;
import com.gustavo.helpdesk_api.core.valueobject.JwtPayload;
import org.springframework.stereotype.Service;


@Service
public class AuthenticateMicrosoftUseCase {

   private final AuthProviderGateway authProviderGateway;
   private final TokenGateway tokenGateway;

   public AuthenticateMicrosoftUseCase(
      AuthProviderGateway authProviderGateway,
      TokenGateway tokenGateway
   ) {
      this.authProviderGateway = authProviderGateway;
      this.tokenGateway = tokenGateway;
   }

   public MicrosoftAuthOutputDto execute(MicrosoftAuthInputDto input) {
      // 1. Troca o código pelo token (Síncrono)
      var tokens = authProviderGateway.getTokens(input.code(), input.codeVerifier());

      // 2. Busca informações no Graph (Síncrono)
      var user = authProviderGateway.getUserInfoFromGraph(tokens.accessToken());

      // 3. Constrói o DTO de saída e gera o JWT
      return buildOutput(user);
   }

   private MicrosoftAuthOutputDto buildOutput(GraphUserDto user) {

      String email = extractEmail(user);
      String role = extractRole(user);
      String username = user.givenName();

      String token = tokenGateway.generateToken(
         new JwtPayload(
            username,
            email,
            user.department(),
            role
         )
      );

      return new MicrosoftAuthOutputDto(
         token,
         username,
         email,
         role
      );
   }

   private String extractEmail(GraphUserDto user) {
      String email = user.mail() != null
         ? user.mail()
         : user.userPrincipalName();

      if (email == null) {
         throw new RuntimeException(
            "Ausência de email no login Microsoft"
         );
      }

      return email;
   }

   private String extractRole(GraphUserDto user) {
      return "Tecnologia da Informação".equals(user.department())
         ? "admin"
         : "client";
   }
}