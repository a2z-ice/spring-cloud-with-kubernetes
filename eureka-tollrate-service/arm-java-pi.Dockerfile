FROM arm32v7/openjdk:8
ADD target/eureka-tollrate-service.jar eureka-tollrate-service.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "eureka-tollrate-service.jar"]
