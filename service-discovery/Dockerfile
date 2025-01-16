# Step 1: Use Maven image for building the jar
FROM maven:3.9.4-eclipse-temurin-17 as builder

# Set the working directory inside the container
WORKDIR /app

# Copy the parent pom.xml
COPY pom.xml ./pom.xml

# Copy the service-discovery module pom.xml
COPY service-discovery/pom.xml ./service-discovery/pom.xml

# Copy the src folder for the service-discovery
COPY service-discovery/src ./service-discovery/src

# Build the application (skip tests for the build process)
RUN mvn clean package -f service-discovery/pom.xml -DskipTests

# Step 2: Use a lightweight JDK image for runtime
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from the builder stage
COPY --from=builder /app/service-discovery/target/*.jar app.jar

# Expose the service's port
EXPOSE 8761

# Default command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
