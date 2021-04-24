FROM java:8-jdk-alpine
RUN mkdir test
ADD target/demo-0.0.1-SNAPSHOT.jar demo-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-Xmx500m","-jar","demo-0.0.1-SNAPSHOT.jar"]

