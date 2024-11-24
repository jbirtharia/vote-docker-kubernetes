FROM openjdk:17-jdk-slim
LABEL authors="jayendrabirtharia"
RUN apt-get update && apt-get install -y iputils-ping
RUN apt-get update && apt-get install -y redis-tools
COPY target/Vote-0.0.1-SNAPSHOT.jar Vote-0.0.1-SNAPSHOT.jar
EXPOSE 8080 6379
ENTRYPOINT ["java", "-jar", "Vote-0.0.1-SNAPSHOT.jar"]