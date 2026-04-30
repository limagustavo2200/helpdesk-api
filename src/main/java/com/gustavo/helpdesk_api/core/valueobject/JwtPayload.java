package com.gustavo.helpdesk_api.core.valueobject;


public record JwtPayload(
   String username,
   String email,
   String department,
   String role
) { }