FROM eclipse-temurin:17-jre-alpine
ADD l2cache-8081.jar l2cache-8081.jar
ADD infinispan-configs.xml infinispan-configs.xml
ADD default-configs default-configs
EXPOSE 8081
ENTRYPOINT ["java","-jar","l2cache-8081.jar"]
