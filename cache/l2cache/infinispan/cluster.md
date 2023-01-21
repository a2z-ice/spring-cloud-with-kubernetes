# Here is an example of how to configure an Infinispan cluster in a Kubernetes environment:
1.  Create a headless Service for the Infinispan cluster. This Service will be used to manage the communication between the cluster nodes.
```yaml
apiVersion: v1
kind: Service
metadata:
  name: infinispan-service
  labels:
    app: infinispan
spec:
  selector:
    app: infinispan
  ports:
    - name: jgroups
      port: 7800
      targetPort: 7800
  clusterIP: None

```
2. Create a StatefulSet for the cluster. The StatefulSet will manage the deployment of the Infinispan pods, and will use the headless Service created in step 1 for communication between the pods.
```yaml
apiVersion: apps/v1
kind: StatefulSet
metadata:
  name: infinispan
spec:
  serviceName: infinispan-service
  replicas: 3
  selector:
    matchLabels:
      app: infinispan
  template:
    metadata:
      labels:
        app: infinispan
    spec:
      containers:
        - name: infinispan
          image: infinispan/server:latest
          ports:
            - containerPort: 11222
            - containerPort: 7800
          volumeMounts:
            - name: infinispan-config
              mountPath: /opt/jboss/infinispan/standalone/configuration/infinispan.xml
              subPath: infinispan.xml
          env:
            - name: JGROUPS_DISCOVERY_PROTOCOL
              value: DNS_PING
            - name: JGROUPS_DISCOVERY_PROPERTIES
              value: dns_query=_infinispan._tcp.infinispan.default.svc.cluster.local
      volumes:
        - name: infinispan-config
          configMap:
            name: infinispan-config

```
3. Create a ConfigMap to store the Infinispan configuration file. The ConfigMap will be used to inject the configuration file into the pods via a volume mount.
```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: infinispan-config
data:
  infinispan.xml: |
    <infinispan>
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
          <dns-ping timeout="1000" dns-query="_infinispan._tcp.infinispan.default.svc.cluster.local"/>
        </discovery>
      </cache-container>
    </infinispan>

```
4. Apply the kubernetes resources to your kubernetes cluster

