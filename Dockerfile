# Use OpenJDK 23 as the base image for building
FROM openjdk:23-ea-slim AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle wrapper and necessary files
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle settings.gradle /app/

# Make the Gradle wrapper executable
RUN chmod +x gradlew

# Download dependencies (this step caches dependencies in Docker layers)
RUN ./gradlew --no-daemon dependencies

# Copy the source code
COPY src src

# Build the JAR file
RUN ./gradlew bootJar --no-daemon

# Use a minimal JDK image for running the application
FROM openjdk:23-ea-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/build/libs/item-service.jar item-service.jar

# Expose the application port
EXPOSE 8081

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "item-service.jar"]

