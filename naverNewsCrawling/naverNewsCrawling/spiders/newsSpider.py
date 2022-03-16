# _*_ coding: euc-kr _*_

import scrapy
import time
import csv
from naverNewsCrawling.items import NaverNewsCrawlingItem

class NewsUrlSpider(scrapy.Spider):
    name = 'newsUrlCrawler'

    def start_requests(self):
        urls = [
            ['100', '264','265','268','266','267','269'],
            ['101','259','258','261','771','260','262','310','263'],
            ['102','249','250','251','254','252','59b','255','256','276','257'],
            ['103','241','239','240','237','238','276','242','243','244','248','245'],
            ['105','731','226','227','230','732','283','229','228'],
            ['104','231','232','233','234','322'],
        ]
        for url in urls:
            for i in range(1,len(url)):
                # for topicPage in range(1,2):
                topicPage = 1
                yield scrapy.Request(url=f'https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1={url[0]}&sid2={url[i]}&page={topicPage}', callback=self.parse)
                time.sleep(0.1)

    def parse_news(self, response):
        for sel in response.xpath('//*[@id="main_content"]/div[2]/ul[1]/li[1]/dl'):    
            request = scrapy.request(sel.xpath('dt[2]/a/@href').extract(), callback=self.parse_news_detail)
            yield request

    def parse_news_detail(self, response):
        item = NavernewscrawlingItem()
        item['title'] = response.xpath('//*[@id="articleTitle"]/text()').extract()[0]
        item['link'] = response.url
        item['press'] = response.xpath('//*[@id="main_content"]/div[1]/div[1]/a/img/@title').extract()[0]
        item['author'] = response.xpath('//*[@id="articleBody"]/div[2]/p/text()').extract()[0]
        item['desc'] = response.xpath('//*[@id="articleBodyContents"]/#text').extract()[0]
        item['date'] = response.xpath('//*[@id="main_content"]/div[1]/div[3]/div/span/text()').extract()[0]

        yield item