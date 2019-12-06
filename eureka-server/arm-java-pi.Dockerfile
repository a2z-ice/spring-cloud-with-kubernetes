FROM arm32v7/openjdk:8
ADD target/eureka-server.jar eureka-server.jar
ENTRYPOINT ["java", "-Xmx200m","-jar", "eureka-server.jar"]
HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:8761/actuator/health || exit 1
EXPOSE 8761
