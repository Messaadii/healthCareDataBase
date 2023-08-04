# Use an official OpenJDK 11 image as the base image
FROM adoptopenjdk:11-jre-hotspot

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled Java application jar file into the container
COPY target/healthCareDataBase-0.0.1-SNAPSHOT.jar /app/healthCareDataBase-0.0.1-SNAPSHOT.jar

# Expose the port on which your Spring Boot application listens (replace 8080 with your actual application port)
EXPOSE 8080

# Command to run your Spring Boot application
CMD ["java", "-jar", "healthCareDataBase-0.0.1-SNAPSHOT.jar"]