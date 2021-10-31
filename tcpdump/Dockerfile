FROM openjdk:8u222-slim
RUN apt-get update && apt-get install tcpdump -y
ADD target/ccie-producer-1.0.jar ccie-producer-1.0.jar
EXPOSE 8051
ENTRYPOINT ["java","-jar","ccie-producer-1.0.jar"]
