openssl genrsa -out ca.key 2048
openssl req -new -key ca.key -subj "/CN=KUBERNETES-CA" -out ca.csr
openssl x509 -req -in ca.csr -signkey ca.key -out ca.crt

# Generating the client certificate
openssl genrsa -out admin.key 2048
openssl req -new -key admin.key -subj "/CN=kube-admin" -out admin.csr
# with group (must be included)
openssl req -new -key admin.key -subj "/CN=kube-admin/O=system:masters" -out admin.csr

openssl x509 -req -in admin.csr -CA ca.crt -CAkey ca.key -out admin.crt

# to Check certificate 
openssl x509 -in certificatefile.crt -text -noout
# check journalctl
journalctl -u etcd.service -l

