#!/bin/bash

mkdir -p /data/docker-data/rabbitmq-data/

docker stop bmsoft_rabbitmq
docker rm bmsoft_rabbitmq

docker run -d --hostname bmsoft_rabbitmq --name bmsoft_rabbitmq --restart=always \
    -e RABBITMQ_DEFAULT_USER=bmsoft -e RABBITMQ_DEFAULT_PASS=bmsoft \
    -v /data/docker-data/rabbitmq-data/:/var/rabbitmq/lib \
    -p 15672:15672 -p 5672:5672 -p 25672:25672 -p 61613:61613 -p 1883:1883 \
    -e TZ="Asia/Shanghai" \
    192.168.8.197:5000/rabbitmq:management
