FROM java:8-jdk-alpine
ADD target/tollrate-svc-0.0.1-SNAPSHOT.jar tollrate-svc-0.0.1-SNAPSHOT.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "tollrate-svc-0.0.1-SNAPSHOT.jar"]