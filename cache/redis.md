```
Initial Tuning
 Bookmark this page
We love Redis because it’s fast (and fun!), so as we begin to consider scaling out Redis, we first want to make sure we've done everything we can to maximize its performance.

Let's start by looking at some important tuning parameters.

Max Clients
Redis has a default of max of 10,000 clients; after that maximum has been reached, Redis will respond to all new connections with an error. If you have a lot of connections (or a lot of application instances), then you may need to go higher. You can set the max number of simultaneous clients in the Redis config file:

maxclients 20000
Max Memory
By default, Redis has no max memory limit, so it will use all available system memory. If you are using replication, you will want to limit the memory usage in order to have overhead for replica output buffers. It’s also a good idea to leave memory for the system. Something like 25% overhead. You can update this setting in Redis config file:

# memory size in bytes  
maxmemory 1288490188
Set tcp-backlog
The Redis server uses the value of tcp-backlog to specify the size of the complete connection queue.

Redis passes this configuration as the second parameter of the listen(int s, int backlog) call.

If you have many connections, you will need to set this higher than the default of 511. You can update this in Redis config file:

# TCP listen() backlog. 
# 
# In high requests-per-second environments you need an high backlog in order 
# to avoid slow clients connections issues. Note that the Linux kernel 
# will silently truncate it to the value of /proc/sys/net/core/somaxconn so 
# make sure to raise both the value of somaxconn and tcp_max_syn_backlog 
# in order to get the desired effect.
tcp-backlog 65536
As the comment in redis.conf indicates, the value of somaxconn and tcp_max_syn_backlog may need to be increased at the OS level as well.

Set read replica configurations
One simple way to scale Redis is to add read replicas and take load off of the primary. This is most effective when you have a read-heavy (as opposed to write-heavy) workload. You will probably want to have the replica available and still serving stale data, even if the replication is not completed. You can update this in the Redis config:

slave-serve-stale-data yes
You will also want to prevent any writes from happening on the replicas. You can update this in the Redis config:

slave-read-only yes
Kernel memory
Under high load, occasional performance dips can occur due to memory allocation. This is something Salvatore, the creator of Redis, blogged about in the past. The performance issue is related to transparent hugepages, which you can disable at the OS level if needed.

$ echo 'never' > /sys/kernel/mm/transparent_hugepage/enabled
Kernel network stack
If you plan on handling a large number of connections in a high performance environment, we recommend tuning the following kernel parameters:

vm.swappiness=0                       # turn off swapping
net.ipv4.tcp_sack=1                   # enable selective acknowledgements
net.ipv4.tcp_timestamps=1             # needed for selective acknowledgements
net.ipv4.tcp_window_scaling=1         # scale the network window
net.ipv4.tcp_congestion_control=cubic # better congestion algorithm
net.ipv4.tcp_syncookies=1             # enable syn cookies
net.ipv4.tcp_tw_recycle=1             # recycle sockets quickly
net.ipv4.tcp_max_syn_backlog=NUMBER   # backlog setting
net.core.somaxconn=NUMBER             # up the number of connections per port
net.core.rmem_max=NUMBER              # up the receive buffer size
net.core.wmem_max=NUMBER              # up the buffer size for all connections
File descriptor limits
If you do not set the correct number of file descriptors for the Redis user, you will see errors indicating that “Redis can’t set maximum open files..” You can increase the file descriptor limit at the OS level.

Here's an example on Ubuntu using systemd:

/etc/systemd/system/redis.service
[Service] 
... 
User=redis 
Group=redis 
...
LimitNOFILE=65536 
...
You will then need to reload the daemon and restart the redis service.

Enabling RPS (Receive Packet Steering) and CPU preferences
One way we can improve performance is to prevent Redis from running on the same CPUs as those handling any network traffic. This can be accomplished by enabling RPS for our network interfaces and creating some CPU affinity for our Redis process.

Here is an example. First we can enable RPS on CPUs 0-1:

$ echo '3' > /sys/class/net/eth1/queues/rx-0/rps_cpus
Then we can set the CPU affinity for redis to CPUs 2-8:

# config is set to write pid to /var/run/redis.pid
$ taskset -pc 2-8 `cat /var/run/redis.pid`
pid 8946's current affinity list: 0-8
pid 8946's new affinity list: 2-8
```

# Exercise - Saving a Snapshot
```
As we learned in the previous unit, Redis will save a snapshot of your database every hour if at least one key has changed, every five minutes if at least 100 keys have changed, or every 60 seconds if at least 10000 keys have changed.

Let’s update this to a simplified hypothetical scenario where we want to save a snapshot if three keys have been modified in 20 seconds.

Step 1
Create a directory named 2.2 and in it prepare a redis.conf file.

$ mkdir 2.2
$ cd 2.2
$ vim redis.conf
The redis.conf file should specify a filename that will be used for the rdb file and a directive that will trigger the creation of a snapshot if 3 keys have been modified in 20 seconds, as described above.

dbfilename my_backup_file.rdb
save 20 3
Step 2
While inside of the 2.2 directory start a Redis server, passing it the redis.conf configuration file you just created.

$ redis-server ./redis.conf
In a separate terminal tab use the redis-cli to create three random keys, one after the other. For example:

127.0.0.1:6379> SET a 1
127.0.0.1:6379> SET b 2
127.0.0.1:6379> SET c 3
Run the ls command in the 2.2 directory to list all its contents. What changed?

Step 3
Now we’re ready to take our persistence a level higher and set up an AOF file. Modify your redis.conf file so that the server will log every new write command and force writing it to disk.

Be careful! We have a running server and we want this configuration to be applied without restarting it.

127.0.0.1:6379> CONFIG SET appendonly yes
127.0.0.1:6379> CONFIG SET appendfsync always
In order for these settings to be persisted to the redis.conf file we need to save them:

127.0.0.1:6379> CONFIG REWRITE
Step 4
Create a few random keys through redis-cli. Check the contents of the directory 2.2 again. What changed?

Step 5
As a final step, restart the Redis server process (you can press Ctrl+C in the terminal to stop the process and re-run it again). If you run the SCAN 0 command you will see that all the keys you created are still in the database, even though we restarted the process.
```

