package com.marlon.notifications.infrastructure.provider.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marlon.notifications.application.port.NotificationProvider;
import com.marlon.notifications.domain.model.SmsNotification;

public class TwilioSmsProvider implements NotificationProvider<SmsNotification> {

    private static final Logger log = LoggerFactory.getLogger(TwilioSmsProvider.class);

    private final String accountSid;
    private final String authToken;

    public TwilioSmsProvider(String accountSid, String authToken) {
        if (accountSid == null || accountSid.isBlank() || authToken == null || authToken.isBlank()) {
            throw new IllegalArgumentException("Las credenciales de Twilio (AccountSID/AuthToken) son obligatorias.");
        }
        this.accountSid = accountSid;
        this.authToken = authToken;
    }

    @Override
    public void send(SmsNotification notification) {
        log.info("[Twilio REST API SIMULATION] AccountSid: {} - Enviando SMS a: '{}' | Mensaje: '{}'",
                accountSid,
                notification.phoneNumber(),
                notification.message());
    }

    @Override
    public Class<SmsNotification> getSupportedType() {
        return SmsNotification.class;
    }

    @Override
    public String getName() {
        return "Twilio";
    }
}