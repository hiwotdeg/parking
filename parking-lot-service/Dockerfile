# Step 1: Use Maven image for building the jar
FROM maven:3.9.4-eclipse-temurin-17 as builder

# Set the working directory inside the container
WORKDIR /app

# Copy the parent pom.xml
COPY pom.xml ./pom.xml

# Copy the parking-lot-service module pom.xml
COPY parking-lot-service/pom.xml ./parking-lot-service/pom.xml

# Copy the src folder for the parking-lot-service
COPY parking-lot-service/src ./parking-lot-service/src

# Build the application (skip tests for the build process)
RUN mvn clean package -f parking-lot-service/pom.xml -DskipTests

# Step 2: Use a lightweight JDK image for runtime
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from the builder stage
COPY --from=builder /app/parking-lot-service/target/*.jar app.jar

# Default command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
