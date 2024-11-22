# Use OpenJDK 17 as the base image for Spring Boot
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file into the container (adjust the JAR filename as necessary)
COPY target/authentication-system-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that Spring Boot will use
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]