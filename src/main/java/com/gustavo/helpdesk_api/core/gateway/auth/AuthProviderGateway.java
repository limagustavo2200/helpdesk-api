package com.gustavo.helpdesk_api.core.gateway.auth;

import java.util.concurrent.CompletableFuture;

public interface AuthProviderGateway {

    TokenResponseDto getTokens(String code, String codeVerifier);
    GraphUserDto getUserInfoFromGraph(String accessToken);
}