get_ipython().system('pip install pymysql')
get_ipython().system('sudo apt-get install python3-dev default-libmysqlclient-dev')


import pandas as pd
from sqlalchemy import create_engine
import pymysql
pymysql.install_as_MySQLdb()
import MySQLdb

conn = pymysql.connect(host='j6a406.p.ssafy.io', port=3306, user='newsbig', password='ssafy406!', db='sinmunmul', charset='utf8')
cursor = conn.cursor()

sql = "SELECT news_seq, news_desc FROM news WHERE news_positive=0 and news_negative=0 and del_yn='n'"
cursor.execute(sql)
news = cursor.fetchall()

for n in news:
  result = predict(n[1])
  sql = "UPDATE news SET news_positive=" + str(result[0]) + ", news_negative=" + str(result[1]) + " WHERE news_seq=" + str(n[0]) + " and del_yn='n'"
  cursor.execute(sql)

conn.commit()
conn.close()