https://drive.google.com/file/d/1iZFC7JIyDd6E8Ue9XV4uCMqj_Pz2ehRv/view?usp=sharing


```
Topic: reload.emabc.data.topic

Queue: reload.emabc.queue
Queue: reload.data.debug.queue
Queue: reload.kukabc.queue

Route key: reload.data.sync.emabc [for reload.emabc.queue on reload.emabc.data.topic]
Route key:  reload.data.sync.* [for reload.data.debug.queue on reload.emabc.data.topic ]
Route key: reload.data.sync.kukabc [ for reload.kukabc.queue on reload.emabc.data.topic]

Topic: sync.emabc.data.topic
Topic: sync.kukabc.data.topic

Queue: sync.emabc.data.queue
Queue: debug.sync.data.queue
Queue: sync.kukabc.data.queue

Route key: sync.emabc.data [for sync.emabc.data.queue]
Route key for debug : sync.*.data [for sync.emabc.data.queue queue]
Route key: sync.kukabc.data [for sync.kukabc.data.queue]
```



##########Rabit-MQ-Auto-PV-create#####
https://github.com/kubernetes-retired/external-storage/tree/master/nfs-client/deploy

###########Mirroring-Command########
```

Before run following command first exec to any pod like the first rabbitmq-0 pod and run the following command

rabbitmqctl set_policy ha-fed \
    ".*" '{"federation-upstream-set":"all", "ha-sync-mode":"automatic", "ha-mode":"nodes", "ha-params":["rabbit@rabbitmq-0.rabbitmq.rabbits.svc.cluster.local","rabbit@rabbitmq-1.rabbitmq.rabbits.svc.cluster.local","rabbit@rabbitmq-2.rabbitmq.rabbits.svc.cluster.local"]}' \
    --priority 1 \
    --apply-to queues
```
####After complete cluster than build docker image and deploy the same at K8##########
```
docker build -t docker-hub.land.gov.bd/rabbit-haproxy .
docker push docker-hub.land.gov.bd/rabbit-haproxy
```

