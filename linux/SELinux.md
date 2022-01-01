# Selinux booleans
```
# Example on ftp service which is vftpd
ps -efZ | grep -i vftpd
# The home directory of vftpd service is /var/ftp
ls /var/ftp # this will show security context with public_content_t:s0 which is public means it is open to access for all



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
