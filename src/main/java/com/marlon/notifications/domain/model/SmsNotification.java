package com.marlon.notifications.domain.model;

public record SmsNotification(
        String from,
        String phoneNumber,
        String message
) implements Notification {

    public SmsNotification {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("El número de teléfono 'phoneNumber' no puede estar vacío");
        }
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("El mensaje 'message' no puede estar vacío");
        }
    }

    @Override
    public String recipient() {
        return phoneNumber;
    }
}