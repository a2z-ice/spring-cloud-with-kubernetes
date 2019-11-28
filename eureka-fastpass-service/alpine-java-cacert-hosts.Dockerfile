FROM java:8-jdk-alpine
USER root
ADD target/eureka-fastpass-service.jar eureka-fastpass-service.jar
COPY ./cacerts cacerts
RUN \
    cp cacerts $JAVA_HOME/jre/lib/security
EXPOSE 8086
#ENTRYPOINT ["java", "-jar", "eureka-fastpass-service.jar"]
ENTRYPOINT ["/bin/sh", "-c" , "echo 192.168.0.101  example.com example >> /etc/hosts && exec java -jar ./eureka-fastpass-service.jar"]

# Install certificate file to cacert
# RUN \
# cp oss_net_bd.crt $JAVA_HOME/jre/lib/security \
# && cd $JAVA_HOME/jre/lib/security \
# && keytool -keystore cacerts -storepass changeit -noprompt -trustcacerts -importcert -alias oss_net_bd -file oss_net_bd.crt


