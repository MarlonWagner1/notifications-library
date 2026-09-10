package com.marlon.notifications.application.port;

import com.marlon.notifications.domain.model.Notification;

/**
 * Contrato base para las estrategias de envío de notificaciones.
 * Cada proveedor concreto (SendGrid, Twilio, etc.) implementará este puerto.
 */
public interface NotificationProvider<T extends Notification> {

    /**
     * Envía la notificación correspondiente.
     *
     * @param notification Notificación a enviar.
     */
    void send(T notification);

    /**
     * Define el tipo de notificación soportado por este proveedor.
     *
     * @return Clase del tipo de notificación (EmailNotification, SmsNotification, etc.).
     */
    Class<T> getSupportedType();

    /**
     * Retorna el nombre único identificador del proveedor (ej: "SendGrid", "Twilio").
     *
     * @return Nombre del proveedor.
     */
    String getName();
}