# Etapa 1: Compilación y generación del Fat-JAR
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app

COPY pom.xml .
COPY src ./src

# Compilamos forzando la ejecución de Shade
RUN mvn clean package -DskipTests

# Etapa 2: Imagen de ejecución ligera
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiamos el Fat-JAR empaquetado
COPY --from=builder /app/target/notifications-library-1.0.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]