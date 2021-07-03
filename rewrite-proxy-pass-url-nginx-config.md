
# Rewrite proxypass url
```

The upstream url has http://192.168.151.127:8089/getlit
where as the front url need to have https://docmainname.com/proxy/getlist
the "proxy" in the url needed to be stripped and the proxypass will get 
the remaining url except "proxy" url

location /proxy {
   rewrite /proxy/(.*) /$1  break;
   proxy_pass       http://192.168.151.127:8089;
   proxy_redirect     off;
   proxy_set_header   Host $host;
  
   }
```
