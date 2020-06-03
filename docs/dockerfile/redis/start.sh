#!/bin/bash

docker stop bmsoft_redis
docker rm bmsoft_redis
docker run -idt -p 16379:16379 --name bmsoft_redis --restart=always \
    -v `pwd`/conf/:/etc/redis \
    -v /data/docker-data/redis-data/:/data \
    -e TZ="Asia/Shanghai" \
    192.168.8.197:5000/redis:4.0.12 redis-server /etc/redis/redis.conf --appendonly yes

