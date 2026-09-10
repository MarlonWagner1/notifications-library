package com.marlon.notifications;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marlon.notifications.application.service.NotificationService;
import com.marlon.notifications.application.service.TemplateEngine;
import com.marlon.notifications.configuration.NotificationClientBuilder;
import com.marlon.notifications.domain.model.EmailNotification;
import com.marlon.notifications.domain.model.PushNotification;
import com.marlon.notifications.domain.model.SlackNotification;
import com.marlon.notifications.domain.model.SmsNotification;
import com.marlon.notifications.infrastructure.decorator.RetryNotificationProvider;
import com.marlon.notifications.infrastructure.provider.email.SendGridEmailProvider;
import com.marlon.notifications.infrastructure.provider.push.FirebasePushProvider;
import com.marlon.notifications.infrastructure.provider.slack.SlackProvider;
import com.marlon.notifications.infrastructure.provider.sms.TwilioSmsProvider;

public class NotificationExamples {

    private static final Logger log = LoggerFactory.getLogger(NotificationExamples.class);

    public static void main(String[] args) {
        log.info("=== DEMOSTRACIÓN COMPLETA DE NOTIFICATIONS LIBRARY (JAVA 21) ===");

        TemplateEngine templateEngine = new TemplateEngine();
        String plantillaSms = "Hola {{nombre}}, tu código de verificación es {{codigo}}.";
        String mensajeSmsProcesado = templateEngine.render(plantillaSms, Map.of("nombre", "Marlon", "codigo", "482019"));

        NotificationService notificationService = NotificationClientBuilder.builder()
                .registerProvider(new RetryNotificationProvider<>(new SendGridEmailProvider("SG.api_key_demo_12345"), 3))
                .registerProvider(new TwilioSmsProvider("AC_account_sid_demo", "auth_token_demo"))
                .registerProvider(new FirebasePushProvider("/path/to/firebase-service-account.json"))
                .registerProvider(new SlackProvider("https://hooks.slack.com/services/T000/B000/XXXX"))
                .build();

        EmailNotification email = new EmailNotification(
                "no-reply@financiera.com",
                "cliente@correo.com",
                "Estado de tu Préstamo",
                "Tu préstamo ha sido aprobado exitosamente."
        );

        SmsNotification sms = new SmsNotification(
                "+51999000000",
                "+51987654321",
                mensajeSmsProcesado
        );

        PushNotification push = new PushNotification(
                "fcm_device_token_xyz_987",
                "Alerta de Seguridad",
                "Se ha detectado un inicio de sesión desde un nuevo dispositivo."
        );

        SlackNotification slack = new SlackNotification(
                "#alertas-sistema",
                "Servicio de pagos restablecido correctamente."
        );

        log.info("\n--- PROCESAMIENTO SINCRÓNICO ---");
        log.info("Resultado Email: {}", notificationService.notify(email));
        log.info("Resultado SMS: {}", notificationService.notify(sms));
        log.info("Resultado Push: {}", notificationService.notify(push));
        log.info("Resultado Slack: {}", notificationService.notify(slack));

        log.info("\n--- PROCESAMIENTO ASINCRÓNICO (CompletableFuture) ---");
        CompletableFuture.supplyAsync(() -> notificationService.notify(slack))
                .thenAccept(result -> log.info("[ASYNC OK] Notificación enviada a Slack con ID: {}", result.notificationId()))
                .exceptionally(ex -> {
                    log.error("[ASYNC ERROR] Fallo en procesamiento: {}", ex.getMessage());
                    return null;
                })
                .join();
    }
}