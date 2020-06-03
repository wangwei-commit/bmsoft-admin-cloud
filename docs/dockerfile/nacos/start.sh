#!/bin/bash

docker stop bmsoft-nacos
docker rm bmsoft-nacos
docker run -idt --name bmsoft-nacos --restart=always \
        -e JVM_XMS=512m -e JVM_XMX=512m -e JVM_XMN=384m \
        -e PREFER_HOST_MODE=hostname -e MODE=standalone -e SPRING_DATASOURCE_PLATFORM=mysql \
        -e MYSQL_DATABASE_NUM=1 \
        -e MYSQL_SERVICE_HOST=$1 -e MYSQL_SERVICE_DB_NAME=bmsoft_nacos_120 -e MYSQL_SERVICE_PORT=3306 \
        -e MYSQL_SERVICE_USER=root \
        -e MYSQL_SERVICE_PASSWORD="root" \
        -p 8848:8848 \
        -v `pwd`/logs/:/home/nacos/logs \
        -v `pwd`/init.d:/home/nacos/init.d \
        192.168.8.197:5000/nacos/nacos-server:1.2.0
