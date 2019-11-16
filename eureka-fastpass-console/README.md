http://localhost:8082/customerdetails?fastpassid=101

#docker build steps:
docker login --username=assaduzzaman<br>
docker build -t assaduzzaman/eureka-fastpass-console:0.0.1-SNAPSHOT -f alpine-java.Dockerfile .<br>
docker push assaduzzaman/eureka-fastpass-console:0.0.1-SNAPSHOT <br>
docker pull assaduzzaman/eureka-fastpass-console:0.0.1-SNAPSHOT