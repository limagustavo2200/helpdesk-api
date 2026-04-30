package com.gustavo.helpdesk_api.infra.gateway.auth;

import com.gustavo.helpdesk_api.core.gateway.auth.TokenGateway;
import com.gustavo.helpdesk_api.core.valueobject.JwtPayload;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;


@Component
public class JwtTokenGateway implements TokenGateway {

   private final SecretKey secretKey;
   private final long expiration;

   public JwtTokenGateway(
      @Value("${jwt.secret}") String secret,
      @Value("${jwt.expiration}") long expiration
   ) {
      this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
      this.expiration = expiration;
   }

   @Override
   public String generateToken(JwtPayload payload) {
      Date now = new Date();
      Date expiryDate = new Date(now.getTime() + expiration);

      return Jwts.builder()
         .subject(payload.email())
         .claim("username", payload.username())
         .claim("email", payload.email())
         .claim("department", payload.department())
         .claim("role", payload.role())
         .issuedAt(now)
         .expiration(expiryDate)
         .signWith(secretKey)
         .compact();
   }

   @Override
   public JwtPayload validateToken(String token) {
      try {
         var claims = Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();

         // Mapeia de volta para o seu Value Object JwtPayload
         return new JwtPayload(
            claims.get("username", String.class),
            claims.get("email", String.class),
            claims.get("department", String.class),
            claims.get("role", String.class)
         );
      } catch (Exception e) {
         throw new RuntimeException("Token inválido ou expirado");
      }
   }
}
