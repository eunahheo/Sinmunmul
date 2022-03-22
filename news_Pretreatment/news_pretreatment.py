from konlpy.tag import Okt
import pymysql
import json
from datetime import datetime
import os

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

    # 코드 그룹
    # 0 : 전체, 100 : 정치, 101 : 경제, 102 : 사회, 103 : 생활/문화, 104 : 세계, 105 : IT/과학
    code_group_num = [0, 100, 101, 102, 103, 104, 105]
    code_group_value = ["all", "politics", "economy", "society", "life_culture", "international", "it_science"]

    # 현재시간 가져오기
    # 2022-03-23 00:53:10.181418
    now = datetime.now()

    # 폴더 생성
    directory_path = '/home/ubuntu/Workspace/bigdata/data' + now.strftime("%Y%M%D")
    createFolder(directory_path)

    # 년월일시 20220323-13 : 2022년 03월 23일 13시
    file_name_template = now.strftime("%Y%M%D-%H")

    # 한 시간 단위
    start_time = now.strftime("%Y-%M-%D %H") + ":00:00"

    for index, code_group in code_group_num:
        # 코드 그룹에 따른 sql 문
        # 현재 시각 hour를 기준으로 hour ~ hour + 1, ex) 13시 ~ 14시 사이의 기사를 가져온다.
        # news_reg_dt or reg_dt
        if code_group == 0 :
             sql = "SELECT news_title, news_desc FROM news WHERE del_yn='n' AND reg_dt  >= DATE_ADD('" + start_time + "', INTERVAL +1 HOUR);"
        else:
             sql = "SELECT news_title, news_desc FROM news WHERE del_yn='n' AND code_group=" + code_group + " AND reg_dt >= DATE_ADD('" + start_time + "', INTERVAL +1 HOUR);"

        # sql 문 실행
        curs.execute(sql)

        # sql 결과
        rows = curs.fetchall()
   
        # KoNLPy 형태소 분석기
        hannanum = Okt()

        # 파일 이름
        # politics-20220323-13
        # 대분류-년월일-시
        file_name = code_group_value[i] + "-" + file_name_template + ".txt"

        # 현재 디렉터리에 텍스트 파일 생성, 쓰기 모드
        f = open(file_name, "w")

        # row 하나씩 돌기
        # 뉴스 기사 텍스트 전처리
        # " "를 구분자로 하는 단어 문자열을 파일에 쓰기
        for news in rows:
            title = news['news_title']
            desc = news['news_desc']

            # 단어만 뽑아내기
            data_pretreatment = hannanum.nouns(desc)

            # 단어 리스트 문자열로 변환
            # " ".join() : " "를 구분자로 한다. **배열 안의 요소가 String형이 아니면 에러 발생  
            news_desc = ' '.join(data_pretreatment)            

            # 파일 쓰기
            f.write(news_desc)
            f.write(" ")
        
        # 쓰기 모드 닫기
        f.close()

    # 폴더 생성 함수
    def createFolder(directory):
        try:
            # 해당 경로에 폴더가 존재하지 않으면 폴더 생성
            if not os.path.exists(directory):
                os.makedirs(directory)
        except OSError:
            print ('Error: Creating directory. ' +  directory)

    print("DB 연결 성공")
