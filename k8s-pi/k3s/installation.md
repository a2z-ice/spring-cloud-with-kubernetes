# Main video link
https://www.youtube.com/watch?v=9sgPRxDiRzQ

# Cilium
```
https://cilium-genbit.readthedocs.io/en/latest/gettingstarted/k3s.html

# to get token 
sudo /var/lib/rancher/k3s/server/node-token

#Master
curl -sfL https://get.k3s.io | INSTALL_K3S_EXEC='--flannel-backend=none' sh -

#Agent or Worker
curl -sfL https://get.k3s.io | K3S_URL="https://${MASTER_IP}:6443" K3S_TOKEN=${NODE_TOKEN} sh -

```

```
# Make sure that you have added following in  /boot/firmware/cmdline.txt 

cgroup_enable=cpuset cgroup_enable=memory cgroup_memory=1 swapaccount=1 

# Not working showing error
curl -sfL https://get.k3s.io | INSTALL_K3S_EXEC="--write-kubeconfig-mode 644 --no-deploy traefik --disable traefik --tls-scan "$MASTER_IP" --node-external-ip "$MASTER_IP" --disable servicelb" sh -s -


# Working after removing some flag used in previous command
curl -sfL https://get.k3s.io | INSTALL_K3S_EXEC="--write-kubeconfig-mode 644  --disable traefik  --node-external-ip "$MASTER_IP" --disable servicelb" sh -s -

# To extract token for worker/agent to connect
cat /var/lib/rancher/k3s/server/node-token


# Add token to variable in worker/agent node

export TOKEN=value of token
MASTER_IP=ip of master node

curl -sfL https://get.k3s.io | sh -s - agent --server https://$MASTER_IP:6443 --token ${TOKEN}


```