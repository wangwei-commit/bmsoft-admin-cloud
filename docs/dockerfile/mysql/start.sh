#!/bin/bash
cur_dir=`pwd`

docker stop bmsoft_mysql
docker rm bmsoft_mysql
docker run --name bmsoft_mysql --restart=always \
    -v `pwd`/conf:/etc/mysql/conf.d \
    -v /data/docker-data/mysql-data/:/var/lib/mysql \
    -p 3306:3306 \
    -e MYSQL_ROOT_PASSWORD="root" \
    -e TZ=Asia/Shanghai \
    -d 192.168.8.197:5000/mysql:5.7.9
