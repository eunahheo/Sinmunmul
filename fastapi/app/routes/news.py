from fastapi import APIRouter, Request
from gensim.models.word2vec import Word2Vec
from konlpy.tag import Okt
import pymysql
from datetime import datetime
import collections

router = APIRouter(prefix="/fapi/news")

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

    stopword_file = open('C:/Users/SSAFY/git/S06P22A406/news_Pretreatment/stopwords.txt', 'r', encoding='utf-8')
    #stopword_file = open('/var/lib/jenkins/workspace/sinmunmul/news_Pretreatment/stopwords.txt', 'r', encoding='utf-8')
    stopword = []
    for word in stopword_file.readlines():
        stopword.append(word.rstrip())
    stopword_file.close()

    # 현재시간 가져오기
    now = datetime.now()
    today = str(now.strftime('%Y-%m-%d %H')) + ":00:00"
    #sql = "SELECT news_title, news_desc FROM news WHERE del_yn='n' And news_reg_dt between DATE_ADD('" + today + "', INTERVAL -7 DAY) AND '" + today + "' AND news_desc like '%" + keyword + "%';"
    #sql = "SELECT news_title, news_desc FROM news WHERE del_yn='n' And news_reg_dt between DATE_ADD('" + today + "', INTERVAL -14 DAY) AND '" + today + "' AND news_desc like '%" + keyword + "%' order by news_reg_dt limit 100;"
    sql = "SELECT news_title, news_desc FROM news WHERE MATCH(news_title) AGAINST('" + keyword + "' IN BOOLEAN MODE) OR MATCH(news_desc) AGAINST('" + keyword + "' IN BOOLEAN MODE) AND del_yn ='n' order by news_reg_dt desc limit 100;"

    # sql 문 실행
    curs.execute(sql)

    # sql 결과
    rows = curs.fetchall()

    # KoNLPy 형태소 분석기
    hannanum = Okt()
    result = []
    print(keyword, len(rows))

    wordcloud = []
    count = []
    for news in rows:
        title = news['news_title']
        desc = news['news_desc']

        if desc.find(keyword) == -1:
            continue

        # 단어만 뽑아내기
        data_pretreatment = hannanum.nouns(desc)

        # 한 글자인 명사와 불용어 제거
        for i, v in reversed(list((enumerate(data_pretreatment)))):
            if len(v) < 2 or v in stopword:
                data_pretreatment.pop(i)
            else:
                count.append(v)

        result.append(data_pretreatment)

    counts = collections.Counter(count)

    model = Word2Vec(sentences=result, vector_size=100, window=5, min_count=5, workers=4, sg=0)
    most_similar = model.wv.most_similar(keyword, topn=20)

    for similar in most_similar:
        wordcloud.append({'keyword': similar[0], 'count': int(similar[1] * counts[similar[0]])})

    return wordcloud



