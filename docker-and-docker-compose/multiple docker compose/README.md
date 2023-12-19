
# First create docker network
<pre><code>
docker network create dashboard
docker network ls
</code></pre>
 
# Now run docker-compose up for each compose file

<pre><code>
docker-compose -f docker-compose-eureka.yml up -d
docker-compose -f docker-compose-fastpass-service.yml up -d
</code></pre>

