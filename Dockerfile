# Stage 1: Build the application using Gradle
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

# Copy all project files into the Docker container
COPY . .

# Grant execution rights to the Gradle wrapper and build the app
RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar --no-daemon

# Stage 2: Run the built application
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy only the compiled JAR file from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]