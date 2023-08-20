FROM openjdk:20-jdk-slim
VOLUME /tmp
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=stg", "-jar", "/app.jar"]