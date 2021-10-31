# TCP Dump for debian base os
```
apt-get update
apt-get install tcpdump -y

# Store tcp dump content into given .pcap file eth0 is ethernet interface
tcpdump -w eth0-2.pcap -i eth0

```
