package com.gustavo.helpdesk_api.core.valueobject;

import java.util.List;

public record Email (
   String from,
   List<String> to,
   String subject,
   String body
) { }
