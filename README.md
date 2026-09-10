# Notifications Library (Java 21)

Librería desacoplada y agnóstica a frameworks para el envío unificado de notificaciones multicanal (Email, SMS y Push). Diseñada bajo principios SOLID, Clean Architecture y las capacidades modernas de Java 21.

---

## 🏛️ Decisiones de Arquitectura y Diseño

### 1. Dominio Inmutable con `sealed` interfaces y `record` (Java 21)
* **`sealed interface Notification`**: Define los tipos de notificación explícitamente permitidos (`EmailNotification`, `SmsNotification`, `PushNotification`). Garantiza un **Pattern Matching exhaustivo** en bloques `switch` sin depender de cláusulas `default` artificiales[cite: 1].
* **`record`**: Representa las notificaciones de forma inmutable por diseño (propiedades `final`), asegurando que los datos transmitidos no puedan ser alterados[cite: 1].

### 2. Patrón Strategy (Inversión de Dependencias)
* **`NotificationProvider<T>`**: Contrato base que desacopla la lógica de aplicación de las API concretas de los proveedores[cite: 1].
* Permite intercambiar o agregar proveedores (ej. SendGrid por Mailgun) cumpliendo con el **Principio Open/Closed (OCP)**[cite: 1].

### 3. Agnóstico a Frameworks (Zero-Framework Policy)
* Sin anotaciones ni contenedores de dependencias como Spring Boot (`@Component`, `@Service`)[cite: 1].
* Configuración explícita y programática mediante `NotificationClientBuilder`[cite: 1].

---

## 🛠️ Requisitos de Entorno

* **Java**: SE 21 o superior[cite: 1].
* **Build Tool**: Apache Maven 3.8+[cite: 1].

---

## 🚀 Guía de Instalación y Uso

### 1. Compilación y Pruebas Unitarias

```bash
mvn clean test