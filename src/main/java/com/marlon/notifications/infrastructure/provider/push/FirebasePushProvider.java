package com.marlon.notifications.infrastructure.provider.push;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marlon.notifications.application.port.NotificationProvider;
import com.marlon.notifications.domain.model.PushNotification;

public class FirebasePushProvider implements NotificationProvider<PushNotification> {

    private static final Logger log = LoggerFactory.getLogger(FirebasePushProvider.class);

    private final String serviceAccountJsonPath;

    public FirebasePushProvider(String serviceAccountJsonPath) {
        if (serviceAccountJsonPath == null || serviceAccountJsonPath.isBlank()) {
            throw new IllegalArgumentException("La ruta del archivo Service Account de Firebase es obligatoria.");
        }
        this.serviceAccountJsonPath = serviceAccountJsonPath;
    }

    @Override
    public void send(PushNotification notification) {
        log.info("[FCM HTTP v1 SIMULATION] Usando credenciales de: {} - Enviando Push a DeviceToken: '{}' | Título: '{}'",
                serviceAccountJsonPath,
                notification.deviceToken(),
                notification.title());
    }

    @Override
    public Class<PushNotification> getSupportedType() {
        return PushNotification.class;
    }

    @Override
    public String getName() {
        return "Firebase";
    }
}