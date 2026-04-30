package com.gustavo.helpdesk_api.infra.security;

import com.gustavo.helpdesk_api.core.valueobject.JwtPayload;
import com.gustavo.helpdesk_api.infra.gateway.auth.JwtTokenGateway;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@Component
public class SecurityFilter extends OncePerRequestFilter {

   private final JwtTokenGateway jwtGatewayImpl;

   public SecurityFilter(JwtTokenGateway jwtGatewayImpl) {
      this.jwtGatewayImpl = jwtGatewayImpl;
   }

   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

      String token = recoverToken(request);

      if (token != null ) {
         JwtPayload jwt = jwtGatewayImpl.validateToken(token);

         if (jwt != null) {
            var authorities = List.of(
               new SimpleGrantedAuthority("ROLE_" + jwt.role())
            );

            var auth = new UsernamePasswordAuthenticationToken(jwt, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(auth);
         }
      }

      filterChain.doFilter(request, response);
   }

   private String recoverToken(HttpServletRequest request) {

      var authHeader = request.getHeader("Authorization");

      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
         return null;
      }
      // corta os 7 primeiros caracteres ("Bearer ") e retorna apenas o JWT
      return authHeader.substring(7);
   }
}
