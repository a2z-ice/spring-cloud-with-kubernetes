
```
kind create cluster --config kind-cluster-config.yaml
curl https://projectcalico.docs.tigera.io/manifests/calico.yaml -O
kubectl apply -f calico.yaml

```
# Access from host pc example on kind document
https://kind.sigs.k8s.io/docs/user/configuration/#networking
