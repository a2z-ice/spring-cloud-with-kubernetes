# spring-cloud
# For docker build run following maven command
mvn package -Dspring.profiles.active=docker
# After build durn following command to run the microservices
docker-compose -f docker-compose-dev.yml up

# Run microservices by detached mode
docker-compose up -d

# Enjoy your Eereka server and there you will find all other microservice please explore
http://localhost:8761/

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

# Do not forgate to add imagePullSecrets in container section example found https://github.com/a2z-ice/spring-cloud/blob/advance/deploy-eureka-server.yaml[here]
                      
             


