# Container
```
# Container with selinux

podman login registry.redhat.io
 username: a2z....
 password: ***

 podman pull registry.redhat.io/rhel8/httpd-24

 mkdir -vp /web/1
 semanage fcontext -a -t container_file_t "/web/(/.*)?" 
 restorecon -R -v /web
 podman run -d -p 8800:80 --name web1 -v /web/1:/usr/local/apache2/htdocs/ httpd:2.4
 podman ps

 echo Welcome to our world > /web/1/index.html
 curl localhost:8800
 Welcome to our world

 cd /etc/systemd/system

 podman generate systemd --name web1 --files
 # since it is a root container we do not need to user --user
 systemctl daemon-reload
 systemctl enable --now container-web1
 systemctl status container-web1


# Exit from container without stoping shortcut
ctr + p, ctr + q

# Container as a service 

# First create user
useradd container-mgr
echo container-mgr:hello | chpasswd

# The user can run services even the user container-mgr is not loged on (login)
loginctl enable-linger container-mgr
loginctl show-user container-mgr

# Switch to user container-mgr
su - container-mgr
mkdir -p .config/systemd/user
cd .config/systemd/user

podman run -d -p 8888:80 --name web001 nginx
# Generate system file for container service
podman generate systemd --name web001 --files
ls
container-web001.service

systemctl --user daemon-reload # Will not work

# Add container-mgr user to ssh_users group
su - # back to root user
usermod -aG ssh_users container-mgr

# Now ssh with container-mgr
ssh container-mgr@localhost
# Now try to do daemon reload
systemctl --user daemon-reload # will work now
systemctl --user enable --now container-web001

```

# NFS
```
# Search package in yum
yum search nfs

# Allow service to firewall
firewall-cmd --add-service={nfs,mountd,rpc-bind} --perm
firewall-cmd --list-services


```
# Time server
```
yum info chrony
yum install -y chrony
# To see installed chrony service file info with status
systemctl list-unit-files | grep chrony

# to see configuration file of chrony
rpm -qc chrony

# to show default time service. The /etc/chrony.conf is found from rpm -qc chrony command
grep iburst /etc/chrony.conf

```
# Selinux work with port
```
seinfo --portcon 80

semanage port -l | grep http_port_t

# help / manual option
man semanage-port

# allow port 666
semanage port -a -t http_port_t -p tcp 666

# verify
semanage port -l | grep http_port_t

# To see all custom port like 666 selinux policy
semanage port -l -C
# Don't forgate to allow the port on firewall
fireall-cmd --add-port=666/tcp --permanent
fireall-cmd --add-port=666/tcp
```
# Selinux log
```
grep AVC /var/log/audit/audit.log

# To show possible solution to allow 
audit2allow -wa <==Need to understand what actually audit2allow does
```
# Selinux context view
```
# To see selinux possible type of home
seinfo -t | grep home

# To see whether the domain (httpd) is interaction with apache httpd process
sesearch --allow | grep wine_home_t | grep httpd

# return empty means there is no interaction




# For user
id -Z
# For file 
ls -Z
ls -Z /etc/group
ps -Z
```
# Selinux status
```
sestatus
```
# Selinux booleans
```
# Example on ftp service which is vftpd
ps -efZ | grep -i vftpd
# The home directory of vftpd service is /var/ftp
ls /var/ftp # this will show security context with public_content_t:s0 which is public means it is open to access for all

# login to ftp
ftp localhost
User: anonymous
Pass: abc@gmail.com (anything like email address)

# To see service specific boolean settings
getsebool -a | grep -i ftp_home
# To allow ftp_home access
setsebool -P ftp_home_dir on

# To see logs 
cd /var/log/audit
cat audit.log | grep -i vsftpd
# Also able to see the log graphically using selinux troublesooter from Application (redhat manue) > Sundry 

getsebool
# To see more detail
semanage boolean -l
# To on the value and to make permanent add -P option like  setsebool -P ftp_home_dir on
setsebool ftp_home_dir on
setsebool ftp_home_dir 1
# To deactivate 
setsebool ftp_home_dir off
# Selinux bool policies store /sys/fs/selinux/booleans directory
```

```
# show SELinux level/context with Z option

ls -ldZ /root

ls -ltrdZ /var

# For file 
ls -lZ index.html

# SELinux context for process
ps -efZ | less

# See SELinux context for specific process
ps -efZ | grep -i httpd

# Change SELinux context chcon -R -t context_name /folder_or_file_location

# It is temporary mean when someone restore the context it will restore previous
chcon -R -t httpd_sys_content_t /webdata/

# To restore
restorecon -v /webdata/
# To make permanent have to write policy fcontext file context -a append -t type
semanage fcontext -a -t httpd_sys_content_t /webdata
# check -d option for directory
ls -ltrdZ /webdata
# No reflection will be found because the previously added security context policy is for file

# Location of selinux policy file. Go to the location and do ll to list the all file context policies
/etc/selinux/targeted/contexts/files
ll

# To reflect the changes do restore as follow
restorecon -v /webdata
# Now check and the reflectionu with be there
ls -ltrdZ /webdata
```
