```shell
# for multiple option put comma separated value like  -D"spring-boot.run.arguments=--server.port=8082,--application.name=NAME_OF_APP"
./mvnw spring-boot:run  -D"spring-boot.run.arguments=--server.port=8082"
./mvnw spring-boot:run  -D"spring-boot.run.arguments=--server.port=8083"

# java jar example after build package
java -jar .\tollrate-svc-0.0.1-SNAPSHOT.jar --server.port=8082

http://localhost:{port}/tollrate/1
```
