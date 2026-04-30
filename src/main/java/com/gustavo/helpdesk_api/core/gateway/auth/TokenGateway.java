package com.gustavo.helpdesk_api.core.gateway.auth;

import com.gustavo.helpdesk_api.core.valueobject.JwtPayload;

public interface TokenGateway {

   String generateToken(JwtPayload payload);
   JwtPayload validateToken(String token);
}
