FROM arm32v7/openjdk:8
ADD target/eureka-fastpass-console-0.0.1-SNAPSHOT.jar eureka-fastpass-console-0.0.1-SNAPSHOT.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "eureka-fastpass-console-0.0.1-SNAPSHOT.jar"]