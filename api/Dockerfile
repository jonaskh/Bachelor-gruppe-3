FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=target/*.jar
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]