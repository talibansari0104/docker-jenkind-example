FROM eclipse-temurin:17-jdk-alpine
COPY target/docker-jenkins.jar  /usr/app/
WORKDIR /usr/app/
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "docker-jenkins.jar"]
