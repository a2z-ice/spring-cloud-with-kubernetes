
```
kind create cluster --config kind-cluster-config.yaml
curl https://projectcalico.docs.tigera.io/manifests/calico.yaml -O
kubectl apply -f calico.yaml

```