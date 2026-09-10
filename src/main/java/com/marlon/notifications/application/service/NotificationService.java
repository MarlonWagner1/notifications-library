package com.marlon.notifications.application.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marlon.notifications.application.port.NotificationProvider;
import com.marlon.notifications.application.port.NotificationResult;
import com.marlon.notifications.domain.exception.NotificationDeliveryException;
import com.marlon.notifications.domain.exception.NotificationValidationException;
import com.marlon.notifications.domain.model.EmailNotification;
import com.marlon.notifications.domain.model.Notification;
import com.marlon.notifications.domain.model.PushNotification;
import com.marlon.notifications.domain.model.SlackNotification;
import com.marlon.notifications.domain.model.SmsNotification;

public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final Map<Class<? extends Notification>, NotificationProvider<?>> providers = new ConcurrentHashMap<>();

    public NotificationService(List<NotificationProvider<?>> providerList) {
        if (providerList == null || providerList.isEmpty()) {
            throw new IllegalArgumentException("Debe registrar al menos un proveedor de notificaciones.");
        }
        for (NotificationProvider<?> provider : providerList) {
            registerProvider(provider);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Notification> void registerProvider(NotificationProvider<T> provider) {
        log.info("Registrando proveedor: '{}' para el tipo: {}", provider.getName(), provider.getSupportedType().getSimpleName());
        providers.put(provider.getSupportedType(), provider);
    }

    @SuppressWarnings("unchecked")
    public NotificationResult notify(Notification notification) {
        if (notification == null) {
            throw new NotificationValidationException("La notificación no puede ser nula.");
        }

        String id = UUID.randomUUID().toString();

        Class<? extends Notification> targetClass = switch (notification) {
            case EmailNotification email -> EmailNotification.class;
            case SmsNotification sms -> SmsNotification.class;
            case PushNotification push -> PushNotification.class;
            case SlackNotification slack -> SlackNotification.class;
        };

        NotificationProvider<Notification> provider = (NotificationProvider<Notification>) providers.get(targetClass);

        if (provider == null) {
            String errorMsg = "No hay un proveedor configurado para el tipo: " + targetClass.getSimpleName();
            log.error(errorMsg);
            throw new NotificationDeliveryException(errorMsg);
        }

        try {
            log.info("Iniciando envío de notificación [{}] vía proveedor '{}'", id, provider.getName());
            provider.send(notification);
            log.info("Notificación [{}] enviada con éxito.", id);
            return NotificationResult.ok(id, provider.getName(), "Notificación procesada exitosamente");
        } catch (Exception e) {
            log.error("Error al enviar notificación [{}]: {}", id, e.getMessage(), e);
            throw new NotificationDeliveryException("Error durante el envío con " + provider.getName() + ": " + e.getMessage(), e);
        }
    }
}