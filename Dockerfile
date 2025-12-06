FROM  openjdk:17-jdk-slim
COPY target/docker-jenkins.jar  /usr/app
WORKDIR /use/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "docker-jenkins.jar"]
