package com.marlon.notifications.domain.model;

/**
 * Representa una notificación genérica dentro del dominio.
 * Utiliza 'sealed' para limitar las implementaciones permitidas únicamente a Email, SMS y Push.
 */
public sealed interface Notification
        permits EmailNotification, SmsNotification, PushNotification, SlackNotification {
    String recipient();
}