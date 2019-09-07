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
mvn package
docker build -t eureka-server .
docker run -d --name eureka-server -p 8761:8761 eureka-server


