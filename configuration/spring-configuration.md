# Load configuration from location specified on spring.config.location and active profile is dev
```
http://localhost:8080/test
# With dev profile
java -jar .\target\config-from-filesystem-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev --spring.config.location=C:/U
sers/DELL/Desktop/config/

# With dev profile. This will take config/application-dev.properties file for configuration
java -jar target/config-from-filesystem-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev --spring.config.location=config/

# Without profile. This will take config/application.properties file for configuration
java -jar target/config-from-filesystem-0.0.1-SNAPSHOT.jar --spring.config.location=config/

 Directory: C:\Users\DELL\Desktop\config


Mode                 LastWriteTime         Length Name
----                 -------------         ------ ----
-a----          9/1/2021  10:06 AM             67 application-dev.yaml

You can also specify the configuration location in application.properties or application.yaml file which is 
in your jar file with following configuration

application.properties

spring.config.import=configtree:C:/Users/DELL/Desktop/config/


```
