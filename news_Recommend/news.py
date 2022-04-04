from konlpy.tag import Okt
import pymysql
import json
from datetime import datetime
import os
from math import log

# DB 연결 정보
class db_conn:
    host = 'j6a406.p.ssafy.io'
    user = 'newsbig'
    pwd = 'ssafy406!'
    db = 'sinmunmul'
    char = 'utf8'

if __name__ == '__main__':
     # DB를 연결할때 autocommit 부분을 True로 설정해주면, 별도의 커밋없이 자동으로 커밋
    conn = pymysql.connect(host=db_conn.host, user=db_conn.user, password=db_conn.pwd, db=db_conn.db, charset=db_conn.char, autocommit=True)
    curs = conn.cursor(pymysql.cursors.DictCursor)

    code_num = [264,265,268,266,267,269,259,258,261,771,260,262,310,263,249,250,251,254,252,59,255,256,276,257,241,239,240,237,238,376,242,243,244,248,245,231,232,233,234,322,731,226,227,230,732,283,229,228]

    stopword_file = open('/var/lib/jenkins/workspace/sinmunmul/news_Pretreatment/stopwords.txt', 'r', encoding='utf-8')
    stopword = []
    for word in stopword_file.readlines():
        stopword.append(word.rstrip())
    stopword_file.close()

    for code in code_num:
        # 폴더 생성
        directory = '/home/ubuntu/recommend/data/'+str(code)+'/news/'
        try:
            # 해당 경로에 폴더가 존재하지 않으면 폴더 생성
            if not os.path.exists(directory):
                os.makedirs(directory)
        except OSError:
            print ('Error: Creating directory. ' +  directory)

        sql = "SELECT news_seq, news_title, news_desc FROM news WHERE del_yn='n' And code = " + str(code) + " ORDER BY news_reg_dt desc LIMIT 30;"
        
        # sql 문 실행
        curs.execute(sql)

        # sql 결과
        rows = curs.fetchall()
        # KoNLPy 형태소 분석기
        hannanum = Okt()

        # 현재 디렉터리에 텍스트 파일 생성, 쓰기 모드

        docs = []
        for news in rows:
            seq = news['news_seq']
            desc = news['news_desc']

            # 단어만 뽑아내기
            data_pretreatment = hannanum.nouns(desc) 
            for i, v in reversed(list((enumerate(data_pretreatment)))):
                if len(v) < 2 or v in stopword:
                    data_pretreatment.pop(i)
            # 단어 리스트 문자열로 변환
            # " ".join() : " "를 구분자로 한다. **배열 안의 요소가 String형이 아니면 에러 발생
            
            news_desc = str(seq)+' '+' '.join(data_pretreatment)
            file_name = str(code)+'_'+str(seq)+'.txt'
            #print(file_name)
            f = open(directory + file_name, "w")

            # 파일 쓰기
            f.write(news_desc)
            f.write(" ")
            # 쓰기 모드 닫기
            f.close()

