FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} am-coding-challenge.jar
ENTRYPOINT ["java","-jar","/am-coding-challenge.jar"]