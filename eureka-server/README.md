docker login registry.gitlab.com -u assaduzzaman  
docker push registry.gitlab.com/assad-group/spring-cloud:eureka-server  
docker pull registry.gitlab.com/assad-group/spring-cloud:eureka-server  
docker run -d --name eureka-server -p 8761:8761 registry.gitlab.com/assad-group/spring-cloud:eureka-server  

