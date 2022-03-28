#!/usr/bin/env bash

# 하둡 컨테이너 HDFS에 wordcount input 디렉토리 생성
hdfs dfs -mkdir -p /user/hadoop/wordcount/input

# HDFS에 데이터 생성
echo "file01 삭제" | hdfs dfs -rm wordcount/input/file01
echo "file02 삭제" | hdfs dfs -rm wordcount/input/file02
echo "Hello World Bye 싸피" | hdfs dfs -put /home/j6a406/hadoop/home/j6a406/hadoop/wordcount-data.txt wordcount/input/file01 

# input 파일 내용 조회
hdfs dfs -cat wordcount/input/*
