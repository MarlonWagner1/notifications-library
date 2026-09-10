package com.marlon.notifications.application.port;

import java.time.Instant;

public record NotificationResult(
        String notificationId,
        boolean success,
        String providerName,
        String message,
        Instant timestamp
) {
    public static NotificationResult ok(String notificationId, String providerName, String message) {
        return new NotificationResult(notificationId, true, providerName, message, Instant.now());
    }

    public static NotificationResult fail(String notificationId, String providerName, String message) {
        return new NotificationResult(notificationId, false, providerName, message, Instant.now());
    }
}