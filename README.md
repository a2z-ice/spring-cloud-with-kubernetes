# Deployment rollout
<pre><code>
kubectl rollout history deploy fastpass-service -n industry-4-0 ⇐ fastpass-service name of deployment
kubectl rollout undo deploy fastpass-service -n industry-4-0 --to-revision=1
</code></pre>

# spring-cloud
# Deploy pod to specific node:
[change node label and assign pod to that node](https://kubernetes.io/docs/tasks/configure-pod-container/assign-pods-nodes/)

# For docker build run following maven command
mvn package -Dspring.profiles.active=docker
# After build run following command to run the microservices -d is for detached mode
docker-compose -f docker-compose-dev.yml up -d
# Alpine version docker compose up
docker-compose -f docker-compose-dev-alpine-env.yml up -d<br>
# Alpine version docker compose down
docker-compose -f docker-compose-dev-alpine-env.yml down


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
if you do not set any default value then go to eclipse maven runconfigure and there you need to select environment tab and there need to add environment veriable you need

# To see certificate detail
keytool -list -keystore cacerts -storepass changeit -noprompt |grep -i "gts ca"
# To import TLS certificate into cacert
keytool -keystore cacerts -storepass changeit -noprompt -trustcacerts -importcert -alias oss_net_bd -file oss_net_bd.crt

# add hosts to running container hosts file
<code><pre>
docker exec -u 0 <container-name> /bin/sh -c "echo '<ip> <name> >> /etc/hosts"
</code></pre>                      
             


