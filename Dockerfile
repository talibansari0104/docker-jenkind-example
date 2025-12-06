FROM openjdk:17
COPY target/docker-jenkins.jar  /usr/app
WORKDIR /use/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "docker-jenkins.jar"]
