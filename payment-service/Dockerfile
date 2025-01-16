# Step 1: Use Maven image for building the jar
FROM maven:3.9.4-eclipse-temurin-17 as builder

# Set the working directory inside the container
WORKDIR /app

# Copy the parent pom.xml
COPY pom.xml ./pom.xml

# Copy the payment-service module pom.xml
COPY payment-service/pom.xml ./payment-service/pom.xml

# Copy the src folder for the payment-service
COPY payment-service/src ./payment-service/src

# Build the application (skip tests for the build process)
RUN mvn clean package -f payment-service/pom.xml -DskipTests

# Step 2: Use a lightweight JDK image for runtime
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from the builder stage
COPY --from=builder /app/payment-service/target/*.jar app.jar

# Default command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
