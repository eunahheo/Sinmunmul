from konlpy.tag import Okt
import pymysql
import json
from datetime import datetime
import os
import pandas as pd
from hdfs import InsecureClient

# DB 연결 정보
class db_conn:
    host = 'j6a406.p.ssafy.io'
    user = 'newsbig'
    pwd = 'ssafy406!'
    db = 'sinmunmul'
    char = 'utf8'

if __name__ == '__main__':
     # autocommit : True, 별도의 커밋없이 자동으로 커밋된다.
    conn = pymysql.connect(host=db_conn.host, user=db_conn.user, password=db_conn.pwd, db=db_conn.db, charset=db_conn.char, autocommit=True)
    curs = conn.cursor(pymysql.cursors.DictCursor)

    # 코드 그룹
    # 0 : 전체, 100 : 정치, 101 : 경제, 102 : 사회, 103 : 생활/문화, 104 : 세계, 105 : IT/과학
    code_group_num = [0, 100, 101, 102, 103, 104, 105]
    code_group_value = ["all", "politics", "economy", "society", "life_culture", "international", "it_science"]

    # 현재시간 가져오기
    # 2022-03-23 00:53:10.181418
    now = datetime.now()

    # 폴더 생성
    directory = '/home/ubuntu/Workspace/bigdata/data/'
    # createFolder(directory_path)
    
    try:
        # 해당 경로에 폴더가 존재하지 않으면 폴더 생성
        if not os.path.exists(directory):
            os.makedirs(directory)
    except OSError:
        print ('Error: Creating directory. ' +  directory)

    # 년월일시 2022032313 : 2022년 03월 23일 13시
    file_date = now.strftime("%Y%m%d%H")
    file_name_template = file_date

    # 한 시간 단위
    start_time = str(now.strftime('%Y-%m-%d %H')) + ":00:00"

    i = 0
    for code_group in code_group_num:
        # 코드 그룹에 따른 sql 문
        # 현재 시각 hour를 기준으로 hour - 1 ~ hour, ex) 현재 14시이면, 13시 ~ 14시 사이에 등록된 기사들을 가져온다.
        # news_reg_dt or reg_dt
       if i == 0 :
             sql = "SELECT news_title, news_desc FROM news WHERE del_yn='n' And news_reg_dt between DATE_ADD('" + start_time + "', INTERVAL -1 HOUR) AND '" + start_time + "';"
        else:
             sql = "SELECT news_title, news_desc FROM news WHERE del_yn='n' AND code_group=" + str(code_group) + " And news_reg_dt between DATE_ADD('" + start_time + "', INTERVAL -1 HOUR) AND '" + start_time + "';"
        print(sql)

        # sql 문 실행
        curs.execute(sql)

        # sql 결과
        rows = curs.fetchall()

        # KoNLPy 형태소 분석기
        hannanum = Okt()

         # 파일 이름
        # politics-20220323-13
        # 대분류-년월일-시
        file_name = code_group_value[i] + "_" + file_name_template + ".txt"
        print(file_name)

        # 현재 디렉터리에 텍스트 파일 생성, 쓰기 모드
        f = open(directory + file_name, "w")

        # Connecting to Webhdfs by providing hdfs host ip and webhdfs port (50070 by default)
        client_hdfs = InsecureClient('http://172.26.4.211:9870')
        # 사용자 명을 지정하여 연결`
        client_hdfs = InsecureClient('http://172.26.4.211:9870', user='j6a406')

        path = 'wordcount/input/'
        # row 하나씩 돌기
        # 뉴스 기사 텍스트 전처리
        # " "를 구분자로 하는 단어 문자열을 파일에 쓰기
        data =""

        for news in rows:
            title = news['news_title']
            desc = news['news_desc']

            # 단어만 뽑아내기
            data_pretreatment = hannanum.nouns(desc)

            # 한 글자인 명사 제외
            for i,v in reversed(list((enumerate(data_pretreatment)))):
                if(len(v)<2):
                    data_pretreatment.pop(i)


            # 단어 리스트 문자열로 변환
            # " ".join() : " "를 구분자로 한다. **배열 안의 요소가 String형이 아니면 에러 발생
            news_desc = ' '.join(data_pretreatment)

            data = data + news_desc + ","

            # 파일 쓰기
            f.write(news_desc)
            f.write(" ")

        # 하둡 클러스터 hdfs에 업로드
        path =  'wordcount/input/' + code_group_value[i] + "_" + file_name_template
        client_hdfs.upload(path, directory+file_name)

        # 쓰기 모드 닫기
        f.close()
        i = i + 1
