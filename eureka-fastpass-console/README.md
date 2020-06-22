http://localhost:8082/customerdetails?fastpassid=101

#docker build steps:
docker login --username=assaduzzaman<br>
docker build -t assaduzzaman/eureka-fastpass-console:0.0.1-SNAPSHOT -f alpine-java.Dockerfile .<br>
docker push assaduzzaman/eureka-fastpass-console:0.0.1-SNAPSHOT <br>
docker pull assaduzzaman/eureka-fastpass-console:0.0.1-SNAPSHOT

#webclient loadbalancer support
<pre><code>
	 @Bean
	    WebClient webClient(LoadBalancerClient loadBalancerClient) {
	        return WebClient.builder()
	                .filter(new LoadBalancerExchangeFilterFunction(loadBalancerClient))
	                .build();
	    }	
</code></pre>	    
reference https://github.com/spring-cloud/spring-cloud-commons/issues/295