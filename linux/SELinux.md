```
# show SELinux level/context with Z option

ls -ldZ /root

ls -ltrdZ /var


# SELinux context for process
ps -efZ | less

# See SELinux context for specific process
ps -efZ | grep -i httpd

# Change SELinux context chcon -R -t context_name /folder_or_file_location

chcon -R -t httpd_sys_content_t /webdata/

```
