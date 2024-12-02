# Use an OpenJDK base image
FROM amazoncorretto:21

# Copy the application JAR file
COPY target/*.jar /app.jar

# Expose the application port
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app.jar"]