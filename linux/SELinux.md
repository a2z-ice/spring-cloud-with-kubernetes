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
semanage fcontext -a -t httpd_sys_content_t /webdata/

```
