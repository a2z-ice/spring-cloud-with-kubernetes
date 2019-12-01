# Maven build for docker
mvn clean package -Dspring.profiles.active="docker"

# To run using boot runner add following to VMArguments
-Dspring.profiles.active=dev

# fastpass-service call example
http://localhost:8086/fastpass?fastpassid=100