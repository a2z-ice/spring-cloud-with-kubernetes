https://drive.google.com/file/d/1iZFC7JIyDd6E8Ue9XV4uCMqj_Pz2ehRv/view?usp=sharing


```
Topic: reload.emutation.data.topic

Queue: reload.emutation.queue
Queue: reload.data.debug.queue
Queue: reload.muktapath.queue

Route key: reload.data.sync.emutation [for reload.emutation.queue on reload.emutation.data.topic]
Route key:  reload.data.sync.* [for reload.data.debug.queue on reload.emutation.data.topic ]
Route key: reload.data.sync.muktapath [ for reload.muktapath.queue on reload.emutation.data.topic]

Topic: sync.emutation.data.topic
Topic: sync.muktapath.data.topic

Queue: sync.emutation.data.queue
Queue: debug.sync.data.queue
Queue: sync.muktapath.data.queue

Route key: sync.emutation.data [for sync.emutation.data.queue]
Route key for debug : sync.*.data [for sync.emutation.data.queue queue]
Route key: sync.muktapath.data [for sync.muktapath.data.queue]
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

