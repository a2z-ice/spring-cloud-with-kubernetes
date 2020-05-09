# Maven build for docker
mvn clean package -Dspring.profiles.active="docker"

# To run using boot runner add following to VMArguments
-Dspring.profiles.active=dev

# fastpass-service call example
http://localhost:8086/fastpass?fastpassid=100

# Kubernetes rollout history
kubectl rollout history deployment.v1.apps/fastpass-service -n industry-4-0

# Rollout to specific version
kubectl rollout undo deploy fastpass-service -n industry-4-0 --to-revision=3
