package com.marlon.notifications.infrastructure.provider.slack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marlon.notifications.application.port.NotificationProvider;
import com.marlon.notifications.domain.model.SlackNotification;

public class SlackProvider implements NotificationProvider<SlackNotification> {

    private static final Logger log = LoggerFactory.getLogger(SlackProvider.class);
    private final String webhookUrl;

    public SlackProvider(String webhookUrl) {
        if (webhookUrl == null || webhookUrl.isBlank()) {
            throw new IllegalArgumentException("El Webhook URL de Slack es obligatorio.");
        }
        this.webhookUrl = webhookUrl;
    }

    @Override
    public void send(SlackNotification notification) {
        log.info("[Slack Webhook SIMULATION] Webhook: {}... - Enviando a canal: '{}' | Mensaje: '{}'",
                webhookUrl.substring(0, Math.min(webhookUrl.length(), 15)),
                notification.channel(),
                notification.message());
    }

    @Override
    public Class<SlackNotification> getSupportedType() {
        return SlackNotification.class;
    }

    @Override
    public String getName() {
        return "Slack";
    }
}