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

    # 년월일시 20220323-13 : 2022년 03월 23일 13시
    file_date = str(now.strftime("%Y%m%d%H"))
 
    for i, code_group in enumerate(code_group_num):
        # 파일 이름
        # politics-2022032313
        # 대분류-년월일-시
        file_name = code_group_value[i] + "_" + file_date
        path = 'wordcount/output/' + file_name + "/part-r-00000"

        # Connecting to Webhdfs by providing hdfs host ip and webhdfs port (9870 by default)
        client_hdfs = InsecureClient('http://172.26.4.211:9870')
        # 사용자 명을 지정하여 연결`
        client_hdfs = InsecureClient('http://172.26.4.211:9870', user='j6a406')

        encType = 'utf-8'
        try:
            with client_hdfs.read(path, encoding = encType) as reader:
                data = pd.read_csv(reader, header=None, engine='python', encoding = 'utf-8', on_bad_lines='skip')
                reader.close()
        except:
            continue
        wordcloud = []
        for k in range(len(data)):
            row = data.iloc[k][0].split("\t")
            keyword = row[0]
            count = row[1]
            wordcloud.append({'keyword' : keyword, 'count' : count})

        wordcloud.sort(key = lambda object:object["count"], reverse = True)

        curtime = now.strftime('%Y-%m-%d %H:%M:%2S')
        sql = "INSERT INTO news_wordcloud (code_group, wordcloud, del_yn, reg_dt, reg_id, mod_dt, mod_id) values (%s, %s, %s, %s, %s, %s, %s)"

        # sql 문 실행
        curs.execute(sql, (code_group_num[i], json.dumps(wordcloud[0:20], ensure_ascii=False), 'n', curtime, 'admin', curtime, 'admin'))
