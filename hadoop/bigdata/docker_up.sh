#!/usr/bin/env bash
export PJT_DIR_NAME=bigdata

if [ ! "$( docker container inspect -f '{{.State.Running}}' bigdata )" == "true" ]; then
    docker run -d -v C:/Users/SSAFY/git/S06P22A406/bigdata:/home/bigdata \
    -p 9870:9870 -p 8088:8088 -p 16010:16010 -p 10002:10002 -p 14040:4040 -p 9995:9995 \
    --name bigdata hibuz/zeppelin-dev yarn,hbase,hive
    sleep 2
    docker start bigdata
fi

if [[ "$OSTYPE" =~ ^msys  || "$OSTYPE" =~ ^cygwin ]]; then
    winpty docker exec -it bigdata sh -c "cd /home/bigdata && /bin/sh"
else
    docker exec -it bigdata sh -c "cd /home/bigdata && /bin/sh"
fi
