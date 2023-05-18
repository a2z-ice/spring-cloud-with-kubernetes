```yaml
#Spring security log setting
logging:
  level:
    root: INFO
    org.springframework.web: INFO
    org.springframework.security: INFO
    org.springframework.security.oauth2: INFO
```
```java
package com.example.l2cache;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class CityService {
    private final CityRepo cityRepo;
    private final EntityManager entityManager;
    private final PlatformTransactionManager transactionManager;
    public List<City> getAllCity(){
        List<City> list = StreamSupport.stream(cityRepo.findAll().spliterator(), false).map(city -> city).toList();
        return list;
    }
    public City getCityById(Integer id){
        return cityRepo.findById(id).get();
    }

    public City getCityByCityName(String name) {
        return cityRepo.findByCityName(name).get();
    }

    public City saveCity(City city){
        return cityRepo.save(city);
    }


    public City update (City city) {

        return new TransactionTemplate(transactionManager)
                .execute(status -> {
                    return entityManager.merge(city);
                });




    }
}

```
# schemq.sql
```sql
DROP SEQUENCE IF EXISTS CITY_SEQ;
CREATE SEQUENCE CITY_SEQ START WITH 1 INCREMENT BY 1;
DROP TABLE IF EXISTS CITY;
CREATE TABLE CITY (
id   INTEGER      NOT NULL DEFAULT nextval('CITY_SEQ'),
city_name VARCHAR(128) NOT NULL,
city_pincode INTEGER,
PRIMARY KEY (id)
);


```

# data.sql
```sql
INSERT INTO CITY (city_name,city_pincode) VALUES ('Dhaka', 1230);
INSERT INTO CITY (city_name,city_pincode) VALUES ('Delhi', 110001);
INSERT INTO CITY (city_name,city_pincode) VALUES ('Kanpur', 208001);
INSERT INTO CITY (city_name,city_pincode) VALUES ('Lucknow', 226001);
INSERT INTO CITY (city_name,city_pincode) VALUES ('Lucknow bd', 226002);
```
