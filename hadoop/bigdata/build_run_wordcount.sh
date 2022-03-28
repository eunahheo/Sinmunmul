#!/usr/bin/env bash

# 소스 빌드
./mvnw clean package

# 아웃풋 디렉토리 삭제
hdfs dfs -rm -r wordcount/output

# 맵리듀스 실행
hadoop jar target/bigdata*.jar wordcount wordcount/input wordcount/output

# 실행 결과 조회
hdfs dfs -cat wordcount/output/*
