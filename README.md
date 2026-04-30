# Helpdesk API

API REST de um sistema interno de helpdesk de TI. Usuários da empresa abrem chamados pelo frontend, e a equipe de TI gerencia, atribui e resolve. Projeto construído e mantido por um desenvolvedor solo em ambiente de produção real.

---

## Funcionalidades

- **Gestão de chamados** — criação, atualização e filtragem de tickets por status
- **Login via Microsoft** — autenticação com Azure AD usando OAuth 2.0 com PKCE
- **Controle de acesso por perfil** — perfil `admin` para o departamento de TI, `client` para os demais (resolvido automaticamente pelo campo de departamento do Azure AD)
- **Notificações automáticas** — envia e-mail e mensagem no Microsoft Teams ao criar ou atualizar chamados
- **Catálogo configurável** — gerenciamento independente de categorias, subcategorias, sistemas, responsáveis, setores e status
- **Versionamento de banco** — migrations gerenciadas pelo Liquibase
- **Documentação interativa** — Swagger UI integrado

---

## Tecnologias

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 21 |
| Framework | Spring Boot 3.5 |
| Segurança | Spring Security + JWT (JJWT 0.12) |
| Autenticação | Microsoft Azure AD (OAuth 2.0 / PKCE) |
| Banco de dados | MySQL |
| ORM | Spring Data JPA + Hibernate |
| Migrations | Liquibase |
| Notificações | API de e-mail customizada + Microsoft Teams Webhook |
| Documentação | SpringDoc OpenAPI (Swagger UI) |

---

## Arquitetura

O projeto segue **Clean Architecture**, separando responsabilidades em três camadas:

```
src/main/java/com/gustavo/helpdesk_api/
├── core/           # Entidades de domínio, interfaces (gateways), value objects, exceções
├── application/    # Use cases e DTOs — orquestram a lógica de negócio
└── infra/          # Implementações Spring: controllers, repositórios, segurança, serviços externos
```

As dependências apontam sempre para dentro: `infra` depende de `application`, que depende de `core`. O domínio não tem dependência de nenhum framework.

---

## Como o Sistema Funciona

### Perfis de Acesso

O perfil é determinado automaticamente pelo **departamento do usuário no Azure AD** no momento do login:

| Perfil | Condição | Permissões |
|---|---|---|
| `admin` | Departamento = "Tecnologia da Informação" | Vê todos os chamados do sistema |
| `client` | Qualquer outro departamento | Vê apenas seus próprios chamados |

### Ciclo de Vida de um Chamado

```
[Aberto] → [Em Andamento] → [Finalizado]
              ↕
           [Pausado]
```

| Status | Comportamento |
|---|---|
| **Aberto** | Status inicial de todo chamado recém-criado |
| **Em Andamento** | Ao mudar para este status, a data de início (`startDate`) é registrada automaticamente |
| **Pausado** | Chamado temporariamente suspenso |
| **Finalizado** | Chamado encerrado — torna-se imutável. A data de finalização (`finishedAt`) é registrada automaticamente |

### Regras para Finalizar um Chamado

Para mover um chamado para **Finalizado**, os três campos abaixo são obrigatórios:

- **Responsável** (`assignee`) — quem da TI resolveu
- **Setor de TI** (`tiSector`) — qual setor da TI atendeu
- **Nota de resolução** (`resolutionNote`) — descrição do que foi feito

Se qualquer um desses campos estiver ausente, a API retorna erro `400`.

### Chamados Finalizados são Imutáveis

Nenhuma alteração é permitida em chamados com status **Finalizado**. Qualquer tentativa de update retorna erro `400`.

### Prioridades

| Prioridade | Label |
|---|---|
| Baixa | `"Baixa"` |
| Média | `"Média"` |
| Alta | `"Alta"` |
| Urgente | `"Urgente"` |

A prioridade é informada na criação e pode ser alterada via update. O campo aceita o label de forma case-insensitive.

### Notificações Automáticas

| Evento | E-mail | Teams |
|---|---|---|
| Chamado criado | Enviado para o solicitante + equipe de TI | Mensagem enviada |
| Status atualizado | Enviado para o solicitante + equipe de TI (somente se o status mudou) | Não enviado |

Falhas nas notificações são logadas mas **não bloqueiam** a operação principal.

---

## Fluxo de Autenticação

A autenticação usa SSO da Microsoft com a extensão **PKCE** (Proof Key for Code Exchange) do OAuth 2.0:

1. Usuário acessa `GET /auth-microsoft/login`
2. A API gera um par `codeVerifier`/`codeChallenge` PKCE, salva o verifier em cookie HTTPOnly e redireciona para o login da Microsoft
3. A Microsoft autentica o usuário e redireciona para `GET /auth-microsoft/callback` com um código de autorização
4. A API troca o código + verifier por um access token via Microsoft identity platform
5. A API consulta a Microsoft Graph API para buscar dados do usuário (nome, e-mail, departamento)
6. Um JWT é gerado com e-mail, nome, departamento e perfil (`admin` ou `client`)
7. O usuário é redirecionado para o frontend com o token

Todas as requisições seguintes devem incluir `Authorization: Bearer <token>`. Os tokens expiram em **6 horas**.

---

## Endpoints

### Autenticação

| Método | Caminho | Auth | Descrição |
|---|---|---|---|
| GET | `/auth-microsoft/login` | Público | Inicia o fluxo de login com a Microsoft |
| GET | `/auth-microsoft/callback` | Público | Callback do OAuth 2.0 |
| GET | `/auth-microsoft/me` | Bearer | Retorna dados do usuário autenticado |

### Chamados

| Método | Caminho | Auth | Descrição |
|---|---|---|---|
| POST | `/ticket` | Bearer | Cria um novo chamado |
| GET | `/ticket` | Bearer | Lista chamados (admin vê todos, client vê os seus) |
| GET | `/ticket/{ticketNumber}` | Bearer | Busca chamado por número |
| PUT | `/ticket` | Bearer | Atualiza um chamado |

O `GET /ticket` aceita o parâmetro opcional `?statusIds=` para filtrar por um ou mais status.

### Catálogo

| Método | Caminho | Auth | Descrição |
|---|---|---|---|
| GET | `/priority` | Público | Lista as prioridades disponíveis |
| GET/POST/PUT/DELETE | `/category` | Bearer | Gerencia categorias |
| GET/POST/PUT/DELETE | `/subcategory` | Bearer | Gerencia subcategorias |
| GET/POST/PUT/DELETE | `/system` | Bearer | Gerencia sistemas |
| GET/POST/PUT/DELETE | `/assignee` | Bearer | Gerencia responsáveis |
| GET/POST/PUT/DELETE | `/status` | Bearer | Gerencia status |
| GET/POST/PUT/DELETE | `/ti-sector` | Bearer | Gerencia setores de TI |

A documentação interativa completa com schemas de request/response está disponível via **Swagger UI** após subir o projeto.

---

## Rodando Localmente

### Pré-requisitos

- Java 21
- Maven 3.9+
- MySQL 8+
- Um app registrado no Azure AD (para o SSO)

### Passo a passo

**1. Clone o repositório**
```bash
git clone https://github.com/limagustavo2200/helpdesk-api.git
cd helpdesk-api
```

**2. Configure as variáveis de ambiente**

Copie o arquivo de exemplo e preencha com seus valores:
```bash
cp .env.example .env
```

Veja a seção [Variáveis de Ambiente](#variáveis-de-ambiente) para descrição de cada uma.

**3. Configure o `application.yml`**

Copie o arquivo de exemplo e ajuste para seu ambiente local:
```bash
cp src/main/resources/application.yml.example src/main/resources/application.yml
```

**4. Suba o projeto**
```bash
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`.  
O Swagger UI estará em `http://localhost:8080/swagger-ui.html`.

---

## Variáveis de Ambiente

| Variável | Descrição |
|---|---|
| `SECURITY_ENABLED` | `true` para exigir autenticação JWT (use `false` apenas para testes locais) |
| `AZURE_CLIENT_ID` | Client ID do app registrado no Azure AD |
| `AZURE_CLIENT_SECRET` | Client Secret do app registrado no Azure AD |
| `AZURE_TENANT_ID` | Tenant ID do Azure AD |
| `AZURE_REDIRECT_URI` | URI de redirecionamento OAuth 2.0 (deve coincidir com o registro no Azure) |
| `DB_URL` | URL JDBC de conexão (ex: `jdbc:mysql://host:3306/banco?serverTimezone=UTC`) |
| `DB_USERNAME` | Usuário do banco de dados |
| `DB_PASSWORD` | Senha do banco de dados |
| `JWT_SECRET_KEY` | Chave de assinatura dos JWTs (mínimo 64 caracteres) |
| `API_EMAIL_SERVICE_URL` | URL do serviço de envio de e-mail |
| `API_EMAIL_SERVICE_SECRET` | Secret do serviço de e-mail |
| `API_TEAMS_WEBHOOK_URL` | URL do webhook de entrada do Microsoft Teams |
| `FRONTEND_URL` | URL base do frontend (usado no redirecionamento pós-autenticação) |

Veja `.env.example` para um template pronto.

---

## Banco de Dados

As migrations são gerenciadas pelo **Liquibase**. Na inicialização, o schema é validado e atualizado automaticamente com base nos changelogs em `src/main/resources/db/changelog/`.

Para uso local, certifique-se de que o banco existe antes de subir a aplicação (ou use `createDatabaseIfNotExist=true` na URL JDBC).
