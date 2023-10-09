# Use an official OpenJDK runtime as a parent image
#FROM eclipse-temurin:17-jdk
#FROM adoptopenjdk/maven-openjdk11:latest as build

#ADD target/living-eco-system.jar living-eco-system.jar
#ENTRYPOINT ["java", "-jar","living-eco-system.jar"]
#EXPOSE 8080


# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project's POM file into the container
COPY pom.xml .

# Install Maven
RUN apt-get update && \
    apt-get install -y maven

# Copy the Maven project's target JAR file into the container
COPY target/living-eco-system-*.jar ./living-eco-system.jar

# Expose the port your application listens on (if applicable)
EXPOSE 8080

# Define the command to run your Java application
CMD ["java", "-jar", "living-eco-system.jar"]