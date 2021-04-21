FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Xms450m", "-Dserver.port=$PORT", "$JAVA_OPTS", "-jar","/app.jar"]
