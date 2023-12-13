FROM openjdk:17
COPY build/libs/async-task-service.jar /app/app.jar
COPY src/main/resources/public_key.der /public_key.der
CMD ["java", "-jar", "/app/app.jar"]
