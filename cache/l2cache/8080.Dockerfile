FROM eclipse-temurin:17-jre-alpine
ADD l2cache-8080.jar l2cache-8080.jar
ADD infinispan-configs.xml infinispan-configs.xml
ADD default-configs default-configs
EXPOSE 8080
ENTRYPOINT ["java","-jar","l2cache-8080.jar"]
