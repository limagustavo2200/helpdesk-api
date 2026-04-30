package com.gustavo.helpdesk_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
   // --- Configurações de Banco de Dados (H2) ---
   "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
   "spring.datasource.driver-class-name=org.h2.Driver",
   "spring.datasource.username=sa",
   "spring.datasource.password=",
   "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
   "spring.jpa.hibernate.ddl-auto=create-drop",

   // --- Desativação de Migrations ---
   "spring.liquibase.enabled=false",
   "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration",

   // --- Mock de Variáveis de Ambiente (Satisfaz o @Value("${...}")) ---
   "AZURE_CLIENT_ID=test-client",
   "AZURE_CLIENT_SECRET=test-secret",
   "AZURE_TENANT_ID=test-tenant",
   "JWT_SECRET_KEY=test-secret-test-secret-test-secret-test-secret",
   "API_EMAIL_SERVICE_URL=http://localhost:9999",
   "API_EMAIL_SERVICE_SECRET=test-secret",
   "API_TEAMS_WEBHOOK_URL=http://localhost:9999/webhook",
   "DB_USERNAME=sa",
   "DB_PASSWORD="
})
class HelpdeskApiApplicationTests {

   @Test
   void contextLoads() {
      // Se este método rodar sem erros, o Spring conseguiu subir 100% dos Beans!
   }
}