package com.gustavo.helpdesk_api.core.gateway.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponseDto(
   @JsonProperty("access_token")
   String accessToken,

   @JsonProperty("id_token")
   String idToken
) {}