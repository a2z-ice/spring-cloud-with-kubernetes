FROM java:8-jdk-alpine
ADD target/eureka-fastpass-service.jar eureka-fastpass-service.jar
EXPOSE 8086
#ENTRYPOINT ["java", "-jar", "eureka-fastpass-service.jar"]
ENTRYPOINT ["java", "-Xmx100m", "-jar", "eureka-fastpass-service.jar"]