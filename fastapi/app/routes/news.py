from fastapi import APIRouter, Request
from gensim.models.word2vec import Word2Vec
from konlpy.tag import Okt
import pymysql
from datetime import datetime

router = APIRouter(prefix="/news")

# DB 연결 정보
class db_conn:
    host = 'j6a406.p.ssafy.io'
    user = 'newsbig'
    pwd = 'ssafy406!'
    db = 'sinmunmul'
    char = 'utf8'

@router.get("/search/wordcloud", tags=["news"])
async def wordcloud(keyword: str):
    # DB를 연결할때 autocommit 부분을 True로 설정해주면, 별도의 커밋없이 자동으로 커밋
    conn = pymysql.connect(host=db_conn.host, user=db_conn.user, password=db_conn.pwd, db=db_conn.db,
                           charset=db_conn.char, autocommit=True)
    curs = conn.cursor(pymysql.cursors.DictCursor)

    # 현재시간 가져오기
    now = datetime.now()
    today = str(now.strftime('%Y-%m-%d %H')) + ":00:00"
    #sql = "SELECT news_title, news_desc FROM news WHERE del_yn='n' And news_reg_dt between DATE_ADD('" + today + "', INTERVAL -7 DAY) AND '" + today + "' AND news_desc like '%" + keyword + "%';"
    sql = "SELECT news_title, news_desc FROM news WHERE del_yn='n' And news_reg_dt between DATE_ADD('" + today + "', INTERVAL -14 DAY) AND '" + today + "' AND news_desc like '%" + keyword + "%';"

    # sql 문 실행
    curs.execute(sql)

    # sql 결과
    rows = curs.fetchall()

    # KoNLPy 형태소 분석기
    hannanum = Okt()
    result = []

    for news in rows:
        title = news['news_title']
        desc = news['news_desc']

        # desc_array = desc.

        if desc.find(keyword) == -1:
            continue

        # 단어만 뽑아내기
        data_pretreatment = hannanum.nouns(desc)

        # 한 글자인 명사 제외
        for i, v in reversed(list((enumerate(data_pretreatment)))):
            if (len(v) < 2):
                data_pretreatment.pop(i)

        # print(data_pretreatment)
        result.append(data_pretreatment)

    model = Word2Vec(sentences=result, vector_size=100, window=5, min_count=5, workers=4, sg=0)
    # model = Word2Vec(sentences=result)
    a = model.wv.most_similar(keyword, topn=20)
    print(a)

    return a




