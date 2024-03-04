# Use the official Kotlin image as the base image
FROM openjdk:8-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY SieveOfEratosthenes.jar /app

# Command to run the application
CMD ["java", "-jar", "SieveOfEratosthenes.jar"]