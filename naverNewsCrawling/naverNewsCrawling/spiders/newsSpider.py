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
        if(datetime.datetime.now().hour == 0 and datetime.datetime.now().minute < 20):
            if (datetime.datetime.now().day - 1) < 1:
                curDate = str(30+((datetime.datetime.now().month) % 2))
            else:
                curDate = str(datetime.datetime.now().day - 1).zfill(2)
        else:
            curDate = str(datetime.datetime.now().day).zfill(2)
        
        for url in urls:
            for i in range(1,len(url)):
                self.now_topic1 = url[0]
                self.now_topic2 = url[i]
                print("currently parsing : " + self.now_topic1 + ", " + self.now_topic2)
                for topicPage in range(1,3):
                    yield scrapy.Request(url=f'https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1={url[0]}&sid2={url[i]}&page={topicPage}&date=202204{curDate}', callback=self.parse_news)
                    time.sleep(2)

    def parse_page(self, response):
        return response.xpath('//*[@id="main_content"]/div[3]/strong/text()').extract()

    def parse_news(self, response):
        for sel in response.xpath('//*[@id="main_content"]/div[2]/ul/li'):
            request = scrapy.Request(sel.xpath('dl/dt/a/@href').extract()[-1], callback=self.parse_news_detail)
            yield request
            time.sleep(0.1)

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

        if t_email.find(' ') > 3:
            t_email = t_email.split(' ')[1]

        while True:
            if (len(t_email) < 2):
                break
            elif not (('a' <= t_email[0] <= 'z') or ('A' <= t_email[0] <= 'Z') or ('0' <= t_email[0] <= '9')):
                t_email = t_email[1:]
            elif not (('a' <= t_email[-1] <= 'z') or ('A' <= t_email[-1] <= 'Z')):
                t_email = t_email[:-1]
            else:
                break
            
        if len(t_email) < 5:
            item['author_email'] = 'None'
        else:                
            item['author_email'] = t_email
        # parse img_link
        if(len(response.xpath('//*[@class="end_photo_org"]/img/@src').extract()) > 0):
            item['img'] = response.xpath('//*[@class="end_photo_org"]/img/@src').extract()[0]
        else:
            item['img'] = ''
        # parse desc
        t_desc = '\n'.join(response.xpath('//*[@id="articleBodyContents"]/text()').extract())[11:]
        while t_desc[0] == " ":
            t_desc = t_desc[1:]
        item['desc'] = t_desc
        # parse datetime
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
                if (t_reg_dt.split(':')[0] == '12'):
                    item['dateReg'] = d_reg_dt + ' ' + '12' + ':' + t_reg_dt.split(':')[1].zfill(2) + ':00'
                else:
                    item['dateReg'] = d_reg_dt + ' ' + str(int(t_reg_dt.split(':')[0])+12).zfill(2) + ':' + t_reg_dt.split(':')[1].zfill(2) + ':00'
            else:
                if(ap_reg_dt == '오전' and t_reg_dt.split(':')[0] == '12'):
                    item['dateReg'] = d_reg_dt + ' ' + '00' + ':' + t_reg_dt.split(':')[1].zfill(2) + ':00'
                else:
                    item['dateReg'] = d_reg_dt + ' ' + t_reg_dt.split(':')[0].zfill(2) + ':' + t_reg_dt.split(':')[1].zfill(2) + ':00'

            if(ap_mod_dt == '오후'):
                if (t_mod_dt.split(':')[0] == '12'):
                    item['dateMod'] = d_mod_dt + ' ' + '12' + ':' + t_mod_dt.split(':')[1].zfill(2) + ':00'
                else:
                    item['dateMod'] = d_mod_dt + ' ' + str(int(t_mod_dt.split(':')[0])+12).zfill(2) + ':' + t_mod_dt.split(':')[1].zfill(2) + ':00'
            else:
                if(ap_mod_dt == '오전' and t_mod_dt.split(':')[0] == '12'):
                    item['dateMod'] = d_mod_dt + ' ' + '00' + ':' + t_mod_dt.split(':')[1].zfill(2) + ':00'
                else:
                    item['dateMod'] = d_mod_dt + ' ' + t_mod_dt.split(':')[0].zfill(2) + ':' + t_mod_dt.split(':')[1].zfill(2) + ':00'
        else:
            temp_dt = ''.join(response.xpath('//*[@id="main_content"]/div[1]/div[3]/div/span/text()').extract())
            
            while not (temp_dt[0:4] == '2022'):
                temp_dt = temp_dt[1:]

            if(temp_dt.find('오전') == -1 and temp_dt.find('오후') == -1):
                d_dt = (temp_dt.split(' ')[0])[:-1].replace('.','-')
                t_dt = temp_dt.split(' ')[1]
                
                item['dateReg'] = d_dt + ' ' + t_dt
                item['dateMod'] = d_dt + ' ' + t_dt

            else:
                d_dt = (temp_dt.split(' ')[0])[:-1].replace('.','-')
                ap_dt = temp_dt.split(' ')[1]
                t_dt = temp_dt.split(' ')[2]
                
                if(ap_dt == '오후'):
                    if (t_dt.split(':')[0] == '12'):
                        item['dateReg'] = d_dt + ' ' + '00' + ':' + t_dt.split(':')[1].zfill(2) + ':00'
                        item['dateMod'] = d_dt + ' ' + '00' + ':' + t_dt.split(':')[1].zfill(2) + ':00'
                    else:
                        item['dateReg'] = d_dt + ' ' + str(int(t_dt.split(':')[0])+12).zfill(2) + ':' + t_dt.split(':')[1].zfill(2) + ':00'
                        item['dateMod'] = d_dt + ' ' + str(int(t_dt.split(':')[0])+12).zfill(2) + ':' + t_dt.split(':')[1].zfill(2) + ':00'
                elif(ap_dt == '오전'):
                    if (t_dt.split(':')[0] == '12'):
                        item['dateReg'] = d_dt + ' ' + '00' + ':' + t_dt.split(':')[1].zfill(2) + ':00'
                        item['dateMod'] = d_dt + ' ' + '00' + ':' + t_dt.split(':')[1].zfill(2) + ':00'
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
        time.sleep(0.1)