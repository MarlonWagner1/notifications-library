# Notifications Library (Java 21)

Librería desacoplada y agnóstica a frameworks para el envío unificado de notificaciones multicanal (Email, SMS, Push y Slack). Diseñada bajo principios SOLID, Clean Architecture y las capacidades modernas de Java 21.

---

## Decisiones de Arquitectura y Diseño

### 1. Dominio Inmutable con `sealed` interfaces y `record` (Java 21)
* **`sealed interface Notification`**: Define los tipos de notificación explícitamente permitidos (`EmailNotification`, `SmsNotification`, `PushNotification`, `SlackNotification`). Garantiza un **Pattern Matching exhaustivo** en bloques `switch` sin depender de cláusulas `default` artificiales.
* **`record`**: Representa las notificaciones de forma inmutable por diseño (propiedades `final`), asegurando que los datos transmitidos no puedan ser alterados.

### 2. Patrón Strategy (Inversión de Dependencias)
* **`NotificationProvider<T>`**: Contrato base que desacopla la lógica de aplicación de las API concretas de los proveedores.
* Permite intercambiar o agregar proveedores (ej. SendGrid por Mailgun) cumpliendo con el **Principio Open/Closed (OCP)**.

### 3. Patrón Decorator (Resiliencia)
* **`RetryNotificationProvider<T>`**: Envuelve a cualquier proveedor para añadir reintentos automáticos configurables sin modificar la lógica interna del adaptador ni acoplarlo a bibliotecas externas.

### 4. Agnóstico a Frameworks (Zero-Framework Policy)
* Sin anotaciones ni contenedores de dependencias como Spring Boot (`@Component`, `@Service`).
* Configuración explícita y programática mediante `NotificationClientBuilder`.

---

## Requisitos de Entorno

* **Java**: SE 21 o superior.
* **Build Tool**: Apache Maven 3.8+.

---

## Guía de Instalación y Uso

### 1. Compilación y Pruebas Unitarias

Ejecuta el ciclo de vida de Maven para verificar las pruebas unitarias y empaquetar el proyecto:

```bash
mvn clean test