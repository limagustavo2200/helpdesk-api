package com.gustavo.helpdesk_api.infra.config;

import com.gustavo.helpdesk_api.infra.security.SecurityFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

   private final SecurityFilter securityFilter;

   @Value("${security.enabled:true}")
   private boolean securityEnabled;

   public SecurityConfig(SecurityFilter securityFilter) {
      this.securityFilter = securityFilter;
   }

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      if (!securityEnabled) {
         return http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .build();
      }

      // Configuração de Produção (Segurança Ativa)
      http
         .cors(cors -> cors.configurationSource(corsConfigurationSource()))
         .csrf(csrf -> csrf.disable())
         .authorizeHttpRequests(auth -> auth
            .requestMatchers("/auth-microsoft/**").permitAll()
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
            .anyRequest().authenticated()
         )
         .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

      return http.build();
   }
   @Bean
   public CorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration configuration = new CorsConfiguration();

      configuration.setAllowedOrigins(List.of(
         "http://localhost:5173",
         "https://helpdesk.akafloor.com.br"
      ));

      configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

      configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", configuration);
      return source;
   }
}
