import scrapy
import time
import datetime
import csv
import os, sys
from naverNewsCrawling.items import NaverNewsCrawlingItem

class NewsUrlSpider(scrapy.Spider):
    name = 'naverNewsCrawling'
    now_topic1 = ''
    now_topic2 = ''

    def start_requests(self):
        urls = [
            ['100','264','265','268','266','267','269'],
            ['101','259','258','261','771','260','262','310','263'],
            ['102','249','250','251','254','252','59b','255','256','276','257'],
            ['103','241','239','240','237','238','276','242','243','244','248','245'],
            ['105','731','226','227','230','732','283','229','228'],
            ['104','231','232','233','234','322'],
        ]
        dateList = ['12','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31']
        for a in dateList:
            for url in urls:
                for i in range(1,len(url)):
                    # maxNum = scrapy.Request(url=f'https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1={url[0]}&sid2={url[i]}&page=100', callback=self.parse_page)
                    # print(maxNum)
                    self.now_topic1 = url[0]
                    self.now_topic2 = url[i]
                    for topicPage in range(1,30):
                    # topicPage = 1 
                        yield scrapy.Request(url=f'https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1={url[0]}&sid2={url[i]}&page={topicPage}&date=202203{a}', callback=self.parse_news)
                        # yield scrapy.Request(url=f'https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1={url[0]}&sid2={url[i]}&page={topicPage}', callback=self.parse_news)
                        time.sleep(1)

    def parse_page(self, response):
        return response.xpath('//*[@id="main_content"]/div[3]/strong/text()').extract()

    def parse_news(self, response):
        for sel in response.xpath('//*[@id="main_content"]/div[2]/ul/li'):
            request = scrapy.Request(sel.xpath('dl/dt/a/@href').extract()[-1], callback=self.parse_news_detail)
            yield request
            time.sleep(0.7)

    def parse_news_detail(self, response):
        item = NaverNewsCrawlingItem()

        item['title'] = ''.join(response.xpath('//*[@id="articleTitle"]/text()').extract())
        item['link'] = response.url
        item['press'] = ''.join(response.xpath('//*[@id="main_content"]/div[1]/div[1]/a/img/@title').extract())
        item['author'] = ''.join(response.xpath('//*[@id="articleBody"]/div[2]/p/text()').extract())[9:12]
        #parse author email
        if (''.join(response.xpath('//*[@id="articleBody"]/div[2]/p/text()').extract())[13:-5]).find(',') > 0:
            t_email = ''.join(response.xpath('//*[@id="articleBody"]/div[2]/p/text()').extract())[13:-5].split(',')[0]
        else:
            t_email = ''.join(response.xpath('//*[@id="articleBody"]/div[2]/p/text()').extract())[13:-5]

        if t_email.find(' ') > 0:
            t_email = t_email.split(' ')[0]

        if len(t_email) < 2:
            item['author_email'] = 'None'
        else:
            while not ('a' <= t_email[0] <= 'z' or 'A' <= t_email[0] <= 'Z' or '0' <= t_email[0] <= '9' or len(t_email) == 0):
                t_email = t_email[1:]
            while not ('a' <= t_email[-1] <= 'z' or 'A' <= t_email[-1] <= 'Z' or len(t_email) == 0):
                t_email = t_email[:-1]
            item['author_email'] = t_email
        # parse img_link
        if(len(response.xpath('//*[@class="end_photo_org"]/img/@src').extract()) > 0):
            item['img'] = response.xpath('//*[@class="end_photo_org"]/img/@src').extract()[0]
        else:
            item['img'] = ''
        # parse desc
        item['desc'] = ''.join(response.xpath('//*[@id="articleBodyContents"]/text()').extract())[11:]
        # parse datetime
        print(''.join(response.xpath('//*[@id="main_content"]/div[1]/div[3]/div/span/text()').extract()))
        if(len(''.join(response.xpath('//*[@id="main_content"]/div[1]/div[3]/div/span/text()').extract())) > 34):
            f_reg_dt = '2022' + (''.join(response.xpath('//*[@id="main_content"]/div[1]/div[3]/div/span/text()').extract())).split('2022')[1]
            f_mod_dt = '2022' + (''.join(response.xpath('//*[@id="main_content"]/div[1]/div[3]/div/span/text()').extract())).split('2022')[2]
        
            d_reg_dt = (f_reg_dt.split(' ')[0])[:-1].replace('.','-')
            d_mod_dt = (f_mod_dt.split(' ')[0])[:-1].replace('.','-')

            ap_reg_dt = f_reg_dt.split(' ')[1]
            ap_mod_dt = f_mod_dt.split(' ')[1]

            t_reg_dt = f_reg_dt.split(' ')[2]
            t_mod_dt = f_mod_dt.split(' ')[2]

            if(ap_reg_dt == '오후'):
                item['dateReg'] = d_reg_dt + ' ' + str(int(t_reg_dt.split(':')[0])+12).zfill(2) + ':' + t_reg_dt.split(':')[1].zfill(2) + ':00'
            else:
                item['dateReg'] = d_reg_dt + ' ' + t_reg_dt.split(':')[0].zfill(2) + ':' + t_reg_dt.split(':')[1].zfill(2) + ':00'
            if(ap_mod_dt == '오후'):
                item['dateMod'] = d_mod_dt + ' ' + str(int(t_mod_dt.split(':')[0])+12).zfill(2) + ':' + t_mod_dt.split(':')[1].zfill(2) + ':00'
            else:
                item['dateMod'] = d_mod_dt + ' ' + t_mod_dt.split(':')[0].zfill(2) + ':' + t_mod_dt.split(':')[1].zfill(2) + ':00'
        else:
            temp_dt = ''.join(response.xpath('//*[@id="main_content"]/div[1]/div[3]/div/span/text()').extract())
            if(temp_dt[0] == '2'):
                d_dt = (temp_dt.split(' ')[0])[:-1].replace('.','-')
                ap_dt = temp_dt.split(' ')[1]
                t_dt = temp_dt.split(' ')[2]
            else:
                d_dt = (temp_dt.split(' ')[1])[:-1].replace('.','-')
                ap_dt = temp_dt.split(' ')[2]
                t_dt = temp_dt.split(' ')[3]
            while not (d_dt[0] == '2' and d_dt[1] == '0'):
                d_dt = d_dt[1:]
            if(ap_dt == '오후'):
                item['dateReg'] = d_dt + ' ' + str(int(t_dt.split(':')[0])+12).zfill(2) + ':' + t_dt.split(':')[1].zfill(2) + ':00'
                item['dateMod'] = d_dt + ' ' + str(int(t_dt.split(':')[0])+12).zfill(2) + ':' + t_dt.split(':')[1].zfill(2) + ':00'
            else:
                item['dateReg'] = d_dt + ' ' + t_dt.split(':')[0].zfill(2) + ':' + t_dt.split(':')[1].zfill(2) + ':00'
                item['dateMod'] = d_dt + ' ' + t_dt.split(':')[0].zfill(2) + ':' + t_dt.split(':')[1].zfill(2) + ':00'
        # parse topic1 code
        item['topic1'] = self.now_topic1
        # parse topic2 code
        if(self.now_topic2 == "59b"):
            item['topic2'] = "59"
        else:
            item['topic2'] = self.now_topic2
        # parse current time
        item['reg_dt'] = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
        item['mod_dt'] = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')

        yield item
        time.sleep(0.2)
