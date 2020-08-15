<pre><code>
#Run maven on docker
docker run --rm \
-v /home/assad/rand/java-memory/spring-cloud-with-kubernetes/eureka-server:/app \
-v /home/assad/.m2:/root/.m2/ -w /app \
maven:3-alpine \
mvn clean package -B \
-Dmaven.test.skip=true


# Building and pushing image to gitlab
docker build -t registry.gitlab.com/assad-group/spring-cloud:eureka-server -f Dockerfile.multi .  
docker login registry.gitlab.com -u assaduzzaman  
docker push registry.gitlab.com/assad-group/spring-cloud:eureka-server  
docker pull registry.gitlab.com/assad-group/spring-cloud:eureka-server  
docker run -d --name eureka-server -p 8761:8761 registry.gitlab.com/assad-group/spring-cloud:eureka-server  


# pushing image to docker hub
docker login --username=assaduzzaman
$ docker images
REPOSITORY                   TAG                 IMAGE ID            CREATED             SIZE
eureka-server                pi                  b8390e1be8d2        7 minutes ago       575MB
arm32v7/openjdk              8                   403fda3916a3        5 months ago        531MB
$ docker tag b8390e1be8d2 assaduzzaman/eureka-server:pi
$ docker push assaduzzaman/eureka-server
$ docker run -d --name eureka-server -p 8761:8761 assaduzzaman/eureka-server:pi
</code></pre>

