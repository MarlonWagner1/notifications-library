package com.marlon.notifications.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.marlon.notifications.application.port.NotificationProvider;
import com.marlon.notifications.application.port.NotificationResult;
import com.marlon.notifications.application.service.NotificationService;
import com.marlon.notifications.domain.exception.NotificationDeliveryException;
import com.marlon.notifications.domain.model.EmailNotification;
import com.marlon.notifications.domain.model.SmsNotification;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationProvider<EmailNotification> emailProvider;

    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        lenient().when(emailProvider.getSupportedType()).thenReturn(EmailNotification.class);
        lenient().when(emailProvider.getName()).thenReturn("MockEmailProvider");

        notificationService = new NotificationService(List.of(emailProvider));
    }

    @Test
    @DisplayName("Debe enviar notificación correctamente usando la estrategia registrada")
    void shouldSendNotificationSuccessfully() {
        EmailNotification email = new EmailNotification("from@test.com", "to@test.com", "Subject", "Body");

        NotificationResult result = notificationService.notify(email);

        assertTrue(result.success());
        assertEquals("MockEmailProvider", result.providerName());
        verify(emailProvider, times(1)).send(email);
    }

    @Test
    @DisplayName("Debe lanzar NotificationDeliveryException cuando no hay un proveedor registrado para la notificación")
    void shouldThrowExceptionWhenNoProviderFound() {
        SmsNotification sms = new SmsNotification("+123", "+456", "Test message");

        assertThrows(NotificationDeliveryException.class, () -> notificationService.notify(sms));
    }
}