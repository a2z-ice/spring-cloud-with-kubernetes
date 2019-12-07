FROM java:8-jdk-alpine
ADD target/eureka-zuul-apiproxy-0.0.1-SNAPSHOT.jar eureka-zuul-apiproxy-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-Xmx200m", "-jar", "eureka-zuul-apiproxy-0.0.1-SNAPSHOT.jar"]
HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:8761/actuator/health || exit 1
EXPOSE 8087

