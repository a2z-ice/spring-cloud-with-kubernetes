FROM openjdk:11-slim-buster
ADD target/config-from-filesystem-0.0.1-SNAPSHOT.jar config-from-filesystem-0.0.1-SNAPSHOT.jar
ADD config config
EXPOSE 8080
ENTRYPOINT ["java","-jar","config-from-filesystem-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=dev", "--spring.config.location=config/"]
