# 크롤링 스크립트 실행
#cd /var/lib/jenkins/workspace/sinmunmul/naverNewsCrawling/naverNewsCrawling
#scrapy crawl naverNewsCrawling


# 크롤링 한 데이터 전처리 -> 하둡 클러스터 hdfs에 저장
# news_pretreatment.py
python3 /var/lib/jenkins/workspace/sinmunmul/news_Pretreatment/news_pretreatment.py
echo "전처리 완료"


### 하둡 클러스터
# 하둡 클러스터에 있는 쉘 스크립트 실행하기
# wordcount shell script
# 하둡 클러스터 ssh키 로컬에 위치시키고 sudo chmod 400 /var/lib/jenkins/workspace/sinmunmul/cluster.pem 권한 주기
today=$(date)
echo "워드카운트 시작 "${today}
sudo ssh -i /var/lib/jenkins/workspace/sinmunmul/cluster.pem j6a406@172.26.4.211 "./wordcount.sh"
today=$(date)
echo "워드카운트 완료 "${today}

      # 하둡 클러스터에서 분야 별로 wordcount mapreduce 실행
      # wordcount.sh
      
              # 하둡 클러스터에서 wordcount mapreduce 실행
              # 분야 별로
              # code_num=(0 100 101 102 103 104 105)
              # code_group=("all" "politics" "economy" "society" "life_culture" "international" "it_science")
              # DATE=$(date +"%Y%m%d%H")

              # for i in {0,6}
              # do
              #   file_name=${code_group[$i]}"_"${DATE}
              #   echo ${file_name}
              #   hadoop jar /home/j6a406/hadoop/wordcount-0.0.1-SNAPSHOT.jar wordcount wordcount/input/${file_name} wordcount/output/${file_name}
              # done




### 원래 서버
# wordcount_to_db.py 실행
echo "워드클라우드 DB에 넣기 시작"
python3 /var/lib/jenkins/workspace/sinmunmul/news_Pretreatment/wordcount_to_db.py
echo "워드클라우드 DB에 넣기 완료"

