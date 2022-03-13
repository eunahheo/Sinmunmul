import scrapy
import time

class QuotesSpider(scrapy.Spider):
    name = "quotes"

    def start_requests(self):
        urls = [
            '100','101','102','103','104','105','106','107',
        ]
        for url in urls:
            for topicPage in range(1,1):
                yield scrapy.Request(url=f'https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid2={url}&page={topicPage}', callback=self.parse)
                time.sleep(0.1)

    def parse(self, response):
        topic = response.url.split("sid2=")[-1]
        filename = f'quotes-{topic}.html'
        with open(filename, 'wb') as f:
            f.write((response.body).decode('euc-kr').encode('utf-8'))
        self.log(f'Saved file {filename}')
            
            
            