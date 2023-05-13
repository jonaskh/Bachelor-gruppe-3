FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY api/Postnummerregister.csv Postnummerregister.csv
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]