# Stop specific/single service from docker compose
docker-compose -f 3-land-microservice.yml rm -s -v  broker-cron-job
docker-compose -f 3-land-microservice.yml up -d broker-cron-job


# for maven build go to desirec project and run following
```
docker run --rm \
-v /home/landnid/source-code/broker-producer-service:/app -v /home/landnid/.m2/:/root/.m2/ -w /app \
maven:3-alpine \
mvn clean package -B \
-Dmaven.test.skip=true \
-Dactive.profile=k8s

-------------or------------------------
docker run --rm -v $(pwd):/app -v /home/landnid/.m2/:/root/.m2/ -w /app maven:3-alpine mvn clean package -B -Dmaven.test.skip=true -Dactive.profile=k8s 

```
# Other build
```
docker run -d --restart always -p 8080:8080 --name keycloak \
-e DB_VENDOR=mariadb \
-e DB_ADDR=172.17.9.140 \
-e DB_PORT=3306 \
-e DB_USER=landnid -e DB_PASSWORD=OWP20Z1Klo \
-e JDBC_PARAMS='useSSL=false' \
-e PROXY_ADDRESS_FORWARDING=true \
-e KEYCLOAK_USER=admin \
-e KEYCLOAK_PASSWORD=admin \
jboss/keycloak:9.0.2


--------------------maven and docker build--------------
mvn clean package -B -Dmaven.test.skip=true -Dactive.profile=k8s
docker build -t docker-reg.oss.net.bd/ec-producer-service:land-v3 -f Dockerfile .

docker run -d --hostname my-rabbit --name some-rabbit \
-e RABBITMQ_DEFAULT_USER=admin \
-e RABBITMQ_DEFAULT_PASS=admin \
-p 15672:15672 -p 5672:5672 \
rabbitmq:3-management


docker run --rm -v $PROJECT_LOCATION:/app -v $M2_REPO:/root/.m2/ -w /app \
                maven:3-alpine \
                mvn clean package -B \
                -Dmaven.test.skip=true \
                -Dactive.profile=k8s \
```

# Debuging
```
#First create Dockerfile with followings
FROM alpine
#Set environemnt from docker file below example
#ENV TZ="Africa/Lusaka"
RUN apk add --no-cache tzdata
# Now build docker file like
docker build -t alpine-timezone .
docker run -it --rm -e TZ=Asia/Dhaka alpine-timezone /bin/ash

# in shell prompt run following 
date
```
