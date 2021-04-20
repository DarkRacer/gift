FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

CMD java -Dserver.port=$PORT $JAVA_OPTS
