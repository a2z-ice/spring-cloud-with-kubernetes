FROM java:8-jdk-alpine
ADD target/eureka-tollrate-service.jar eureka-tollrate-service.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "eureka-tollrate-service.jar"]