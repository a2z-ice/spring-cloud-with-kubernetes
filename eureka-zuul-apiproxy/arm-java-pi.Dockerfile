FROM arm32v7/openjdk:8
ADD target/eureka-zuul-apiproxy-0.0.1-SNAPSHOT.jar eureka-zuul-apiproxy-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-Xmx200m", "-jar", "eureka-zuul-apiproxy-0.0.1-SNAPSHOT.jar"]
EXPOSE 8087
HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:8087/actuator/health || exit 1
