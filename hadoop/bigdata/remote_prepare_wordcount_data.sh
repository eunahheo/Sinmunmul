#!/usr/bin/env bash

if [ "x$1" == "x" ]; then
  echo "usage: $0 j6a406@cluster.p.ssafy.io"
  exit 0
fi

# 하둡 클러스터 HDFS에 wordcount input 디렉토리 생성
ssh -i ../hadoop.pem $1 'bash -ic "hdfs dfs -mkdir -p wordcount/input"'

# 데이터 생성
ssh -i ../hadoop.pem $1 'bash -ic "echo \"Hello World Bye World\" | hdfs dfs -put - wordcount/input/file01"'
ssh -i ../hadoop.pem $1 'bash -ic "echo \"Hello Hadoop Goodbye Hadoop\" | hdfs dfs -put - wordcount/input/file02"'

# input 파일 내용 조회
ssh -i ../hadoop.pem $1 'bash -ic "hdfs dfs -cat wordcount/input/*"'
