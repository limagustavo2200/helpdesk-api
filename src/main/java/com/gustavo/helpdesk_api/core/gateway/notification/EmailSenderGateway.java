package com.gustavo.helpdesk_api.core.gateway.notification;

import com.gustavo.helpdesk_api.core.valueobject.Email;

public interface EmailSenderGateway {

   void sendEmail(Email email);
}
