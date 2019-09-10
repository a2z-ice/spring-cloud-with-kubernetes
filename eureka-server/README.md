<pre><code>
docker build -t registry.gitlab.com/assad-group/spring-cloud:eureka-server -f Dockerfile.multi .  
docker login registry.gitlab.com -u assaduzzaman  
docker push registry.gitlab.com/assad-group/spring-cloud:eureka-server  
docker pull registry.gitlab.com/assad-group/spring-cloud:eureka-server  
docker run -d --name eureka-server -p 8761:8761 registry.gitlab.com/assad-group/spring-cloud:eureka-server  
</code></pre>

