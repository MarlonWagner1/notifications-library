package com.marlon.notifications.domain.model;

public record PushNotification(
        String deviceToken,
        String title,
        String body
) implements Notification {

    public PushNotification {
        if (deviceToken == null || deviceToken.isBlank()) {
            throw new IllegalArgumentException("El 'deviceToken' no puede estar vacío");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("El título 'title' no puede estar vacío");
        }
    }

    @Override
    public String recipient() {
        return deviceToken;
    }
}