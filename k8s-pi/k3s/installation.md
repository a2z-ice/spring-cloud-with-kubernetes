```
curl -sfL https://get.k3s.io | INSTALL_K3S_EXEC="--write-kubeconfig-mode 644 --no-deploy traefik --disable traefik --tls-scan "$MASTER_IP" --node-external-ip "$MASTER_IP" --disable servicelb" sh -s -


```