# 하둡 Maven 예제

## 1. 하둡 클러스터에서 wordcount 실행
```bash

# C 드라이브 dev 폴더에 소스코드 압축 해제(C:\dev\skeleton-hadoop-maven) 후 Git Bash 실행
# (인증서는 프로젝트 상위 예시->c:/dev/hadoop.pem 에 위치시킴)
# (다른위치라면 remote_*.sh 파일들에서 hadoop.pem 및 위치정보 수정)
$ cd c:/dev/skeleton-hadoop-maven

# 하둡 클러스터 서버에 환경변수를 설정(.bashrc)합니다. (서버당 1번만 실행)
# 접속할 서버의 실제 주소를 사용합니다.(예: j6<Team ID>@cluster.p.ssafy.io)
$ ./remote_set_hadoop_env.sh test@cluster.p.ssafy.io

# 하둡 클러스터에 wordcount input 데이터 생성 (1번만 실행)
$ ./remote_prepare_wordcount_data.sh test@cluster.p.ssafy.io

# 로컬에서 빌드 후 빌드된 jar를 하둡 클러스터에 업로드 후 wordcount 예제 실행
$ ./remote_build_run_wordcount.sh test@cluster.p.ssafy.io
```


## 2. 하둡 클러스터에서 movielens 실행 (이후 도커에서 Hive로 비슷하게 진행)
```bash

# 하둡 클러스터에 2,500만건 영화 평점 데이터 추가 (1번만 실행)
$ ./remote_prepare_movielens_data.sh test@cluster.p.ssafy.io

# 로컬에서 빌드 후 빌드한 jar를 하둡 클러스터에 업로드 후 movielens 영화별 평점 평균 구하기 예제 실행
$ ./remote_build_run_movielens.sh test@cluster.p.ssafy.io
```


## 3. 도커 하둡에서 wordcount 실행
```bash

# 프로젝트 위치에서 도커 실행 및 컨테이너 Bash 실행
# (다른 위치 및 폴더명 사용시 docker_up.sh 에서 수정)
/c/dev/skeleton-hadoop-maven$ ./docker_up.sh

# 컨테이너 Bash 실행에서 wordcount input 데이터 생성 (1번만 실행)
~/skeleton-hadoop-maven$ ./prepare_wordcount_data.sh

# 빌드 및 wordcount 맵리듀스 실행
~/skeleton-hadoop-maven$ ./build_run_wordcount.sh

# 결과 확인
Bye	1
Goodbye	1
Hadoop	2
Hello	2
World	2
```


## 4. 도커 하둡에서 Spark Shell 실행 후 분석하기
```bash

# Spark에서 읽을 파일을 HDFS에 저장하기
~$ hdfs dfs -put $SPARK_HOME/README.md

~/$ spark-shell
Welcome to
      ____              __
     / __/__  ___ _____/ /__
    _\ \/ _ \/ _ `/ __/  '_/
   /___/ .__/\_,_/_/ /_/\_\   version 3.1.3
      /_/

scala> val textFile = spark.read.textFile("README.md")
textFile: org.apache.spark.sql.Dataset[String] = [value: string]

scala> val wordCounts = textFile.flatMap(line => line.split(" ")).groupByKey(identity).count()
wordCounts: org.apache.spark.sql.Dataset[(String, Long)] = [key: string, count(1): bigint]

scala> wordCounts.collect()
res0: Array[(String, Long)] = Array(([![PySpark,1), (online,1), (graphs,1)...

scala> :q
```

```bash
# 동일한 작업을 하둡 wordcount 맵리듀스로도 실행해 봅니다.
~/$ hadoop jar $HADOOP_HOME/share/hadoop/mapreduce/hadoop-mapreduce-examples-3.3.1.jar wordcount ./README.md output2

~/$ hdfs dfs -cat output2/* |grep PySpark

[![PySpark      1
```

## 5. 제플린 노트북에서 Spark SQL (PySpark)로 분석하기

Create new note 클릭 후 `wordcount` 노트 이름과 Default Interpreter는 spark를 선택 후 생성합니다.
```python

%spark.pyspark

# HDFS에 저장된 파일을 읽고 Spark DataFrame으로 보기
textFile = spark.read.text("README.md")
textFile.show()

from pyspark.sql.functions import explode, split

# 워드 카운팅 및 결과 보기
wordCounts = textFile.select(explode(split(textFile.value, "\s+")).alias("word")).groupBy("word").count()
wordCounts.show(1)

+----------+-----+
|      word|count|
+----------+-----+
|[![PySpark|    1|
+----------+-----+
only showing top 1 row
```

영화 평점 데이터(MovieLens 2,500만건: ml-25m.zip -> ratings.csv) 다운로드 및 준비 (wget unzip 패키지도 설치됨)
```bash
~/skeleton-hadoop-maven$ ./prepare_movielens_data.sh
```

새로운 `movielens` 노트를 생성합니다.
```python
%spark.pyspark

# HDFS에 저장된 파일을 읽고 Spark DataFrame 생성
userRatings = spark.read.format("csv").options(header = True, inferSchema = True).load("ml/input/ratings.csv")

userRatings.printSchema()

# 전체 데이터 건수 확인
print("Total number of records in df:", userRatings.count())

userRatings.show(3)
userRatings.registerTempTable("user_ratings")

Total number of records in df: 25000095
+------+-------+------+----------+
|userId|movieId|rating| timestamp|
+------+-------+------+----------+
|     1|    296|   5.0|1147880044|
|     1|    306|   3.5|1147868817|
|     1|    307|   5.0|1147868828|
+------+-------+------+----------+
only showing top 3 rows
```
전체 데이터를 챠트로 분석합니다.
```sql
%sql

select * from user_ratings
```

```python
%spark.pyspark

# 아이디가 1000인 유저가 1점보다 낮은 평점을 준 영화 찾기
# Spark SQL로 데이터 다루기 -> 내부적으로 DataFrame API로 변환되어 동작됨
spark.sql("select * from user_ratings where userId = 1000 and rating < 1.0").show()

# Spark DataFrame API로 데이터 다루기
userRatings.filter("userId = 1000 and rating < 1.0").show()

+------+-------+------+----------+
|userId|movieId|rating| timestamp|
+------+-------+------+----------+
|  1000|    480|   0.5|1111542140|
|  1000|   1057|   0.5|1111554033|
|  1000|   2053|   0.5|1111554132|
|  1000|   2054|   0.5|1111539709|
|  1000|   6936|   0.5|1111540790|
+------+-------+------+----------+
```
```python
from pyspark.sql.functions import col, avg, desc

# 영화별 평점 평균 집계 요약 정보
userRatingsAgg = userRatings.groupBy(col("movieId"))\
.agg(avg(col("rating")).alias("avg"))\
.orderBy(desc("avg"), desc("movieId"))

userRatingsAgg.show(3)

+-------+---+
|movieId|avg|
+-------+---+
| 209155|5.0|
| 208859|5.0|
| 208795|5.0|
+-------+---+
```


## 6. 도커 하둡에서 movielens 실행 (Hive 쿼리로 진행)

```bash
# 쿼리를 실행하기 위한 도구 비라인 접속
~/skeleton-hadoop-maven$ beeline -n hadoop -u jdbc:hive2://localhost:10000
...
Connecting to jdbc:hive2://localhost:10000
Connected to: Apache Hive (version 3.1.2)
Driver: Hive JDBC (version 3.1.2)
Transaction isolation: TRANSACTION_REPEATABLE_READ
Beeline version 3.1.2 by Apache Hive

# MovieLens User Ratings 테이블 생성
0: jdbc:hive2://localhost:10000> CREATE TABLE user_ratings (
  userid INT,
  movieid INT,
  rating DOUBLE,
  unixtime INT)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
TBLPROPERTIES("skip.header.line.count" = "1");

...
INFO  : OK
INFO  : Concurrency mode is disabled, not creating a lock manager
No rows affected (0.334 seconds)

# 테이블 조회
0: jdbc:hive2://localhost:10000> show tables;
...
+---------------+
|   tab_name    |
+---------------+
| user_ratings  |
+---------------+
1 row selected (0.257 seconds)

# Hive에서 사용 가능하도록 HDFS(/user/hive/warehouse/user_ratings)에 저장
0: jdbc:hive2://localhost:10000> LOAD DATA LOCAL INPATH '/home/hadoop/ml-25m/ratings.csv' OVERWRITE INTO TABLE user_ratings;

# 맵리듀스 작업으로 10만건 조회
0: jdbc:hive2://localhost:10000> SELECT COUNT(*) FROM user_ratings;
...
INFO  : Job running in-process (local Hadoop)
INFO  : 2021-09-26 10:15:36,026 Stage-1 map = 100%,  reduce = 100%
INFO  : Ended Job = job_local193815146_0002
...
+-----------+
|    _c0    |
+-----------+
| 25000095  |
+-----------+
1 row selected (6.858 seconds)

# 아이디가 1000인 유저가 1점보다 낮은 평점을 준 영화 필터링 정보
0: jdbc:hive2://localhost:10000> SELECT * FROM user_ratings WHERE userid = 1000 AND rating < 1.0;
...
+----------------------+-----------------------+----------------------+------------------------+
| user_ratings.userid  | user_ratings.movieid  | user_ratings.rating  | user_ratings.unixtime  |
+----------------------+-----------------------+----------------------+------------------------+
| 1000                 | 480                   | 0.5                  | 1111542140             |
| 1000                 | 1057                  | 0.5                  | 1111554033             |
| 1000                 | 2053                  | 0.5                  | 1111554132             |
| 1000                 | 2054                  | 0.5                  | 1111539709             |
| 1000                 | 6936                  | 0.5                  | 1111540790             |
+----------------------+-----------------------+----------------------+------------------------+

# 영화별 평점 평균 요약 정보
0: jdbc:hive2://localhost:10000> SELECT movieid, AVG(rating) as avg 
FROM user_ratings GROUP BY movieid
ORDER BY avg DESC, movieid DESC LIMIT 3;
...
+----------+------+
| movieid  | avg  |
+----------+------+
| 209155   | 5.0  |
| 208859   | 5.0  |
| 208795   | 5.0  |
+----------+------+
3 rows selected (54.114 seconds)

# 테이블 정리(선택사항)
0: jdbc:hive2://localhost:10000> drop table user_ratings;

# 비라인 쿼리 프롬프트 종료
0: jdbc:hive2://localhost:10000> !q
Closing: 0: jdbc:hive2://localhost:10000
```


# 데이터 및 도커 컨테이너 정리하기
``` bash
# HDFS 데이터 삭제
~/skeleton-hadoop-maven$ hdfs dfs -rm -r /user/hadoop/*

# 도커 bash 쉘 종료
~/skeleton-hadoop-maven$ exit

# 컨테이너 정지 또는
$ docker stop hadoop

# 컨테이너 삭제 (데이터도 함께 삭제 됨)
$ docker rm -f hadoop
```

# 웹 UI 대시보드
* Hadoop Namenode information: http://localhost:9870
* Yarn All Applications: http://localhost:8088
* HiveServer2: http://localhost:10002
* Spark context: http://localhost:14040 (spark-shell, pyspark shell 실행 중 확인가능)
* Zeppelin Home: http://localhost:9995
* HBase Master: http://localhost:16010

# 참고
- https://github.com/hibuz/ubuntu-docker/tree/main/hadoop