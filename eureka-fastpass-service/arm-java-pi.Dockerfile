FROM arm32v7/openjdk:8
ADD target/eureka-fastpass-service.jar eureka-fastpass-service.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "eureka-fastpass-service.jar"]