# Step 1: Use Maven image for building the jar
FROM maven:3.9.4-eclipse-temurin-17 as builder

# Set the working directory inside the container
WORKDIR /app

# Copy only the necessary pom.xml for the auth-service module
COPY auth-service/pom.xml ./auth-service/pom.xml
COPY pom.xml ./pom.xml  # Parent pom.xml, if needed to resolve shared dependencies
COPY auth-service/src ./auth-service/src

# Build the application for the specific service
RUN mvn clean install -DskipTests
# Step 2: Use a lightweight JDK image for runtime
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from the builder stage (adjust the path if necessary)
COPY --from=builder /app/auth-service/target/*.jar app.jar



# Default command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
