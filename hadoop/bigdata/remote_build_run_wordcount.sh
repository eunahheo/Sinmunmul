#!/usr/bin/env bash

if [ "x$1" == "x" ]; then
  echo "usage: $0 j6a406@cluster.p.ssafy.io"
  exit 0
fi

# 소스 빌드
./mvnw clean package

# 하둡 클러스터 HDFS에 wordcount input 디렉토리 생성
ssh -i ../hadoop.pem $1 'bash -ic "hdfs dfs -rm -r wordcount/output"'

# 하둡 클러스터 서버에 jar 업로드
scp -i ../hadoop.pem target/ssafy-*.jar $1:~/ssafy.jar

# 맵리듀스 실행
ssh -i ../hadoop.pem $1 'bash -ic "hadoop jar wordcount-0.0.1-SNAPSHOT.jar wordcount wordcount/input wordcount/output"'

# 실행 결과 조회
ssh -i ../hadoop.pem $1 'bash -ic "hdfs dfs -cat wordcount/output/all_2022032723"'

hadoop jar /home/j6a406/hadoop/home/j6a406/hadoop/wordcount-0.0.1-SNAPSHOT.jar wordcount wordcount/input/all_2022032723 wordcount/output/all_2022032723
