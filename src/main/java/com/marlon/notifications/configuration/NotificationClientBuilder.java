package com.marlon.notifications.configuration;

import java.util.ArrayList;
import java.util.List;

import com.marlon.notifications.application.port.NotificationProvider;
import com.marlon.notifications.application.service.NotificationService;

public class NotificationClientBuilder {

    private final List<NotificationProvider<?>> providers = new ArrayList<>();

    public static NotificationClientBuilder builder() {
        return new NotificationClientBuilder();
    }

    public NotificationClientBuilder registerProvider(NotificationProvider<?> provider) {
        if (provider != null) {
            this.providers.add(provider);
        }
        return this;
    }

    public NotificationService build() {
        if (providers.isEmpty()) {
            throw new IllegalStateException("Debe registrar al menos un proveedor antes de construir el servicio.");
        }
        return new NotificationService(providers);
    }
}