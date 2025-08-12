# ===== Stage 1: Build the app =====
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
# Copy pom.xml and download dependencies (better layer caching)
COPY pom.xml .
RUN mvn dependency:go-offline
# Copy source code
COPY src ./src
# Build the application (skip tests for faster build)
RUN mvn clean package -DskipTests

# ===== Stage 2: Run the app =====
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copy jar from the builder stage
COPY --from=builder /app/target/*.jar ahl-alquran-app.jar
# Expose application port
EXPOSE 8080
# Run the application
ENTRYPOINT ["java", "-jar", "ahl-alquran-app.jar"]
