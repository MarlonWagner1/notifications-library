package com.marlon.notifications.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.marlon.notifications.domain.model.EmailNotification;
import com.marlon.notifications.domain.model.PushNotification;
import com.marlon.notifications.domain.model.SmsNotification;

class NotificationDomainTest {

    @Test
    @DisplayName("Debe crear EmailNotification válidamente")
    void shouldCreateEmailNotification() {
        EmailNotification email = new EmailNotification("no-reply@test.com", "user@test.com", "Bienvenido", "Hola");
        assertEquals("user@test.com", email.recipient());
        assertEquals("Bienvenido", email.subject());
    }

    @Test
    @DisplayName("Debe lanzar excepción si el destinatario de Email está vacío")
    void shouldThrowExceptionWhenEmailToIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> 
            new EmailNotification("from@test.com", "", "Subject", "Body")
        );
    }

    @Test
    @DisplayName("Debe obtener el destinatario correcto en Sms y Push")
    void shouldReturnCorrectRecipients() {
        SmsNotification sms = new SmsNotification("+51999999999", "+51988888888", "Hola");
        PushNotification push = new PushNotification("token-123", "Título", "Cuerpo");

        assertEquals("+51988888888", sms.recipient());
        assertEquals("token-123", push.recipient());
    }
}