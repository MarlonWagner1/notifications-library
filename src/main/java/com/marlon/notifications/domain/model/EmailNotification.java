package com.marlon.notifications.domain.model;

public record EmailNotification(
        String from,
        String to,
        String subject,
        String body
) implements Notification {

    public EmailNotification {
        if (to == null || to.isBlank()) {
            throw new IllegalArgumentException("El destinatario 'to' no puede estar vacío");
        }
        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("El asunto 'subject' no puede estar vacío");
        }
    }

    @Override
    public String recipient() {
        return to;
    }
}