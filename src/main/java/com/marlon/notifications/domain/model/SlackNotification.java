package com.marlon.notifications.domain.model;

public record SlackNotification(String channel, String message) implements Notification {
    public SlackNotification {
        if (channel == null || channel.isBlank()) {
            throw new IllegalArgumentException("El canal de Slack es obligatorio.");
        }
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("El mensaje de Slack es obligatorio.");
        }
    }

    @Override
    public String recipient() {
        return channel;
    }
}