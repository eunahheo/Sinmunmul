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
    curtime = datetime.now().strftime('%Y-%m-%d %H:%M:%2S')

    code_num = [264,265,268,266,267,269,259,258,261,771,260,262,310,263,249,250,251,254,252,59,255,256,276,257,241,239,240,237,238,376,242,243,244,248,245,231,232,233,234,322,731,226,227,230,732,283,229,228]
    #code_num = [264];
    for code in code_num:
        path = 'recommend/output/' + str(code) + "/part-r-00000"

        # Connecting to Webhdfs by providing hdfs host ip and webhdfs port (9870 by default)
        client_hdfs = InsecureClient('http://172.26.4.211:9870')
        # 사용자 명을 지정하여 연결`
        client_hdfs = InsecureClient('http://172.26.4.211:9870', user='j6a406')

        encType = 'utf-8'
        try:
            with client_hdfs.read(path, encoding = encType) as reader:
                data = pd.read_csv(reader, header=None, engine='python', encoding = 'utf-8', on_bad_lines='skip')
                reader.close()
        except pd.errors.EmptyDataError as e:
            continue

        list = []
        map = {}
        print(code)
        for k in range(len(data)):
            row = data.iloc[k][0].split("\t")
            key = row[0].split("@")
            tfidf = row[1]
            #print("seq : ", tfidf)
            list.append([key, tfidf])

        list.sort(key = lambda x:x[1], reverse=True)
        #print(list[0][0])

        for row in list:
            keyword = row[0][0]
            seq = row[0][1]
            tfidf = row[1]

            if seq in map:
                if len(map[seq])>2:
                        continue
                elif len(map[seq]) == 2:
                    map[seq].append(keyword)
                    keyword1 = map[seq][0]
                    keyword2 = map[seq][1]
                    sql = "INSERT INTO news_recommend (code, news_seq, keyword1, keyword2, keyword3, del_yn, reg_dt, reg_id, mod_dt, mod_id) values (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)"
                    try:
                        curs.execute(sql, (code, seq, keyword1, keyword2, keyword, 'n', curtime, 'admin', curtime, 'admin'))
                    except pymysql.err.IntegrityError:
                        pass
                else:
                    map[seq].append(keyword)
            else:
                map[seq]=[keyword]
