```text
kubectl exec \
  -it infinispan-server-0 \
  -- curl -v -u test:changeme -H 'Content-type: text/plain' -d 'test' infinispan-server-admin:8080/rest/default/stuff


kubectl exec -it infinispan-server-0 \
  -- curl -v -u test:changeme infinispan-server-admin:8080/rest/default/stuff
```

```text
Infinispan, Spring, and Hibernate can be configured to work together in a Kubernetes (K8s) environment to achieve replication using the DNS_PING discovery protocol.

Here is an example of how to configure this setup:

In your Infinispan configuration file (e.g. infinispan.xml), configure the cache to use the "replication" mode, and set the number of owners to 3. Also, configure the "discovery" element to use the "dns_ping" protocol, and specify the DNS service name to use for discovery.

```
```yaml
<cache-container name="clustered" default-cache="default">
    <transport stack="tcp"/>
    <replicated-cache name="default">
        <transaction mode="NON_XA"/>
        <eviction strategy="LRU" max-entries="10000"/>
        <expiration lifespan="600000"/>
        <state-transfer timeout="240000"/>
        <partition-handling when-split="ALLOW_READ_WRITES"/>
    </replicated-cache>
    <discovery>
        <dns-ping timeout="1000" dns-query="_infinispan._tcp.my-cluster.my-namespace.svc.cluster.local."/>
    </discovery>
</cache-container>

```
```text
In your Spring application, configure the Hibernate second level cache to use Infinispan as the caching provider, and specify the cache container name from the Infinispan configuration file.

```
```java
@Configuration
@EnableCaching
public class HibernateConfig {

    @Bean
    public InfinispanRegionFactory cacheRegionFactory() {
        return new InfinispanRegionFactory();
    }

    @Bean
    public HibernatePropertiesCustomizer cachePropertiesCustomizer() {
        return hibernateProperties -> hibernateProperties.put(
                "hibernate.cache.infinispan.cachemanager", "clustered"
        );
    }
}

```

```text
In your Kubernetes deployment, configure each pod to use a ConfigMap or Secret to store the Infinispan configuration file, and inject it into the pod via a volume mount. Also, configure the pod to use a headless Service for communication between the pods.
```
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: my-app
spec:
  replicas: 3
  selector:
    matchLabels:
      app: my-app
  template:
    metadata:
      labels:
        app: my-app
    spec:
      containers:
        - name: my-app
          image: my-app:latest
          ports:
            - containerPort: 8080
          volumeMounts:
            - name: infinispan-config
              mountPath: /etc/infinispan
          env:
            - name: JGROUPS_DISCOVERY_PROTOCOL
              value: DNS_PING
            - name: JGROUPS_DISCOVERY_PROPERTIES
              value: dns_query=_infinispan._tcp.my-cluster.my-namespace.svc.cluster.local.
      volumes:
       

```
```text
In a Kubernetes configuration for Infinispan, Spring, and Hibernate, the value of the jgroups.dns.query parameter would be the DNS service name that is used for discovery of the Infinispan cluster nodes.

This value would typically be in the format of "_infinispan._tcp.{cluster-name}.{namespace}.svc.cluster.local", where {cluster-name} is the name of the Infinispan cluster and {namespace} is the Kubernetes namespace where the cluster is running.

For example, if your Infinispan cluster is named "my-cluster" and is running in the "my-namespace" namespace, the value for jgroups.dns.query would be "_infinispan._tcp.my-cluster.my-namespace.svc.cluster.local"
It's important to validate that the DNS service name is correct and that the DNS service is properly configured for your kubernetes cluster.
```