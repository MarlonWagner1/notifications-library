package com.marlon.notifications.infrastructure.provider.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marlon.notifications.application.port.NotificationProvider;
import com.marlon.notifications.domain.model.EmailNotification;

public class SendGridEmailProvider implements NotificationProvider<EmailNotification> {

    private static final Logger log = LoggerFactory.getLogger(SendGridEmailProvider.class);

    private final String apiKey;

    public SendGridEmailProvider(String apiKey) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalArgumentException("La API Key de SendGrid es obligatoria.");
        }
        this.apiKey = apiKey;
    }

    @Override
    public void send(EmailNotification notification) {
        log.info("[SendGrid API SIMULATION] Usando API Key: {}... - Enviando correo a: '{}' con asunto: '{}'",
                apiKey.substring(0, Math.min(apiKey.length(), 4)),
                notification.to(),
                notification.subject());
    }

    @Override
    public Class<EmailNotification> getSupportedType() {
        return EmailNotification.class;
    }

    @Override
    public String getName() {
        return "SendGrid";
    }
}