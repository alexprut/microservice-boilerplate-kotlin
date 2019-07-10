FROM openjdk:8-jdk-alpine
EXPOSE 8080
ADD /build/libs/microservice-kotlin-boilerplate.jar microservice-kotlin-boilerplate.jar
ENTRYPOINT ["java", "-jar", "microservice-kotlin-boilerplate.jar"]