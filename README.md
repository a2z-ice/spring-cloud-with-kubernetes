# spring-cloud
# For docker build run following maven command
mvn package -Dspring.profiles.active=docker
# After build run following command to run the microservices -d is for detached mode
docker-compose -f docker-compose-dev.yml up -d
# Alpine version docker compose
docker-compose -f docker-compose-dev-alpine.yml up -d
docker-compose -f docker-compose-dev-alpine.yml down

# Environment veriables docker-compose exec {service-name} env:
docker-compose exec eureka-tollrate-service env

# Run microservices by detached mode
docker-compose up -d

# Enjoy your Eereka server and there you will find all other microservice please explore
http://localhost:8761/

#eureka-fastpass-console
http://localhost:8082/customerdetails?fastpassid=100

#eureka-tollrate-billboard
http://localhost:8081/dashboard?stationId=1

# To run only one server like eurika server only, run following commands:
mvn package<br>
docker build -t eureka-server .<br>
docker run -d --name eureka-server -p 8761:8761 eureka-server<br>

# Imperetive style of adding kubernetes secret for private containter registry in gitlab/other
<pre><code>
kubectl create secret docker-registry gitlab-registry \
                      --docker-server=registry.gitlab.com \
                      --docker-username=gitlabusername \
                      --docker-password=gitlabpassword \
                      --docker-email=gitlabemailaddress
</code></pre>  

# Do not forgate to add imagePullSecrets in container section example:
https://github.com/a2z-ice/spring-cloud/blob/advance/deploy-eureka-server.yaml

# For environment veriable to run maven successfully
if you do not set any default value then go to eclipse maven runconfigure and there you need to set environment tab and there need to add environment veriable you need


                      
             


