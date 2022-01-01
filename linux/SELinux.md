```
# show SELinux level/context with Z option

ls -ldZ /root

ls -ltrdZ /var


# SELinux context for process
ps -efZ | less

# See SELinux context for specific process
ps -efZ | grep -i httpd
```
