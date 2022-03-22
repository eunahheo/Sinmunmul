# _*_ coding: euc-kr _*_

import scrapy
import time
import csv
import os, sys
from naverNewsCrawling.items import NaverNewsCrawlingItem

class NewsUrlSpider(scrapy.Spider):
    name = 'naverNewsCrawling'

    def start_requests(self):
        urls = [
            ['100','264',]#'265','268','266','267','269'],
            # ['101','259','258','261','771','260','262','310','263'],
            # ['102','249','250','251','254','252','59b','255','256','276','257'],
            # ['103','241','239','240','237','238','276','242','243','244','248','245'],
            # ['105','731','226','227','230','732','283','229','228'],
            # ['104','231','232','233','234','322'],
        ]
        for url in urls:
            for i in range(1,len(url)):
                maxNum = scrapy.Request(url=f'https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1={url[0]}&sid2={url[i]}&page=100', callback=self.parse_page)
                print(maxNum)
                # for topicPage in range(1,10):
                #     if sc
                topicPage = 1
                yield scrapy.Request(url=f'https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1={url[0]}&sid2={url[i]}&page={topicPage}', callback=self.parse_news)
                time.sleep(0.1)

    def parse_page(self, response):
        return response.xpath('//*[@id="main_content"]/div[3]/strong/text()').extract()

    def parse_news(self, response):
        # print(response.xpath('//*[@id="main_content"]/div[2]/ul').getall()) # title parse
        # print(response.xpath('//*[@id="main_content"]/div[1]/ul/li/dl/dd').extract()) # desc parse
        for sel in response.xpath('//*[@id="main_content"]/div[2]/ul/li'):
            # print(sel.xpath('dt[1]/a/@href').extract())
            request = scrapy.Request(sel.xpath('dl/dt/a/@href').extract()[-1], callback=self.parse_news_detail)
            yield request
            time.sleep(0.1)

    def parse_news_detail(self, response):
        item = NaverNewsCrawlingItem()

        item['title'] = response.xpath('//*[@id="articleTitle"]/text()').extract()
        item['link'] = response.url
        item['press'] = response.xpath('//*[@id="main_content"]/div[1]/div[1]/a/img/@title').extract()
        item['author'] = response.xpath('//*[@id="articleBody"]/div[2]/p/text()').extract()
        if(len(response.xpath('//*[@class="end_photo_org"]/img/@src').extract()) > 0):
            item['img'] = response.xpath('//*[@class="end_photo_org"]/img/@src').extract()[0]
        item['desc'] = response.xpath('//*[@id="articleBodyContents"]/text()').extract()
        for i in item['desc']:
            i.replace('\t','')
        item['date'] = response.xpath('//*[@id="main_content"]/div[1]/div[3]/div/span/text()').extract()
        item['topic1'] = response.url.split('sid1=')[-1].split('&sid2')[0]
        item['topic2'] = response.url.split('sid2=')[-1].split('&oid')[0]
        yield item
        time.sleep(0.1)

