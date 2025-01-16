# Step 1: Use Maven image for building the jar
FROM maven:3.9.4-eclipse-temurin-17 as builder

# Set the working directory inside the container
WORKDIR /app

COPY . .

# Build the application (skip tests for the build process)
RUN mvn clean install -DskipTests

# Step 2: Use a lightweight JDK image for runtime
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from the builder stage
COPY --from=builder /app/geo-location-service/target/*.jar app.jar


# Default command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
