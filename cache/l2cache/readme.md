# To enable hibernate audit support in spring data jap add following properties in application.yaml file

```yaml
spring:
    jpa:
    show-sql: false
    properties:
      hibernate:
        org.hibernate.envers.audit_table_suffix: _aud
```
# add @Audited annotation to the entity class
# create the same table with same column and _aud suffix with following additional properties

```bash
              - column:
                  name: REV
                  type: INT
                  defaultValue: 0
                  constraints:
                    nullable: false
              - column:
                  name: REVTYPE
                  type: TINYINT
                  defaultValue: 0
                  constraints:
                    nullable: false
```

# Available APIs
```
http://localhost:8080/city
http://localhost:8080/city/1
http://localhost:8080/city/Dhaka/name

# To clear cache
http://localhost:8080/cache/clean
```
