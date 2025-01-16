# Step 1: Use Maven image for building the jar
FROM maven:3.9.4-eclipse-temurin-17 as builder

# Set the working directory inside the container
WORKDIR /app

# Copy the parent pom.xml
COPY pom.xml ./pom.xml

# Copy the notification-service module pom.xml
COPY notification-service/pom.xml ./notification-service/pom.xml

# Copy the src folder for the notification-service
COPY notification-service/src ./notification-service/src

# Build the application (skip tests for the build process)
RUN mvn clean install -DskipTests

# Step 2: Use a lightweight JDK image for runtime
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from the builder stage
COPY --from=builder /app/notification-service/target/*.jar app.jar

# Expose the port based on application.yml configuration (8081 for notification-service)
EXPOSE 8081

# Default command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
