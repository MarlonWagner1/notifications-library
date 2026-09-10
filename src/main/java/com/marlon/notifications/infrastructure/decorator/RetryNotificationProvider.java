package com.marlon.notifications.infrastructure.decorator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marlon.notifications.application.port.NotificationProvider;
import com.marlon.notifications.domain.exception.NotificationDeliveryException;
import com.marlon.notifications.domain.model.Notification;

public class RetryNotificationProvider<T extends Notification> implements NotificationProvider<T> {

    private static final Logger log = LoggerFactory.getLogger(RetryNotificationProvider.class);

    private final NotificationProvider<T> delegate;
    private final int maxRetries;

    public RetryNotificationProvider(NotificationProvider<T> delegate, int maxRetries) {
        this.delegate = delegate;
        this.maxRetries = maxRetries;
    }

    @Override
    public void send(T notification) {
        int attempt = 0;
        while (attempt < maxRetries) {
            try {
                attempt++;
                delegate.send(notification);
                return;
            } catch (Exception e) {
                log.warn("[RETRY WARN] Intento {}/{} falló para el proveedor '{}'. Motivo: {}",
                        attempt, maxRetries, getName(), e.getMessage());
                if (attempt >= maxRetries) {
                    throw new NotificationDeliveryException("Fallaron todos los reintentos para: " + getName(), e);
                }
            }
        }
    }

    @Override
    public Class<T> getSupportedType() {
        return delegate.getSupportedType();
    }

    @Override
    public String getName() {
        return delegate.getName() + " (Con Reintentos)";
    }
}