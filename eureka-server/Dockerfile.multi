FROM maven:latest as appserver
WORKDIR /usr/src/eureka-server
COPY pom.xml .
RUN mvn -B -f pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
COPY . .
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml package -Dspring.profiles.active=docker -DskipTests

FROM java:8-jdk-alpine AS production
WORKDIR /app
COPY --from=appserver /usr/src/eureka-server/target/eureka-server.jar .
ENTRYPOINT ["java", "-jar", "eureka-server.jar"]
