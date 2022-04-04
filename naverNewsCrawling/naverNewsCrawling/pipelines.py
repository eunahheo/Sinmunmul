# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html


# useful for handling different item types with a single interface
from __future__ import unicode_literals
from scrapy.exporters import CsvItemExporter
from scrapy import settings
from scrapy.exceptions import DropItem
# from scrapy import log
from itemadapter import ItemAdapter
import mysql.connector
from mysql.connector import errorcode

class JsonPipeline(object):
    def __init__(self):
        self.file = open("newsCrawl.json", 'wb')
        self.exporter = JsonItemExporter(self.file, encoding='utf-8', ensure_ascii=False)
        self.exporter.start_exporting()
 
    def close_spider(self, spider):
        self.exporter.finish_exporting()
        self.file.close()
 
    def process_item(self, item, spider):
        self.exporter.export_item(item)
        return item

class CsvPipeline(object):
    def __init__(self):
        self.file = open(f'newsUrlCrawl-001.csv', 'wb')
        self.exporter = CsvItemExporter(self.file, encoding='utf-8')
        self.exporter.start_exporting()
 
    def close_spider(self, spider):
        self.exporter.finish_exporting()
        self.file.close()
 
    def process_item(self, item, spider):
        self.exporter.export_item(item)
        return item

class msqltestPipeline(object):
    table = 'news'
    conf = {
        'host': 'j6a406.p.ssafy.io',
        'user': 'newsbig',
        'password': 'ssafy406!',
        'database': 'sinmunmul',
        'raise_on_warnings': True,
    }
    
    def __init__(self, **kwargs):
        self.dbcon = self.mysql_connect()

    def mysql_connect(self):
        try:
            return mysql.connector.connect(**self.conf)
        except mysql.connector.Error as err:
            if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
                print("Something is wrong with your user name or password")
            elif err.errno == errorcode.ER_BAD_DB_ERROR:
                print("Database does not exist")
            else:
                print(err)
            return None

    def select_item(self):
        cursor = self.dbcon.cursor()
        sql = f"SELECT * FROM user"
        cursor.execute(sql)
        result = cursor.fetchall()
        print(result)

class MysqlPipeline(object):
    table = 'news'
    conf = {
        'host': 'j6a406.p.ssafy.io',
        'user': 'newsbig',
        'password': 'ssafy406!',
        'database': 'sinmunmul',
        'raise_on_warnings': True,
    }
    
    def __init__(self, **kwargs):
        self.dbcon = self.mysql_connect()
        
    def mysql_connect(self):
        try:
            return mysql.connector.connect(**self.conf)
        except mysql.connector.Error as err:
            if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
                print("Something is wrong with your user name or password")
            elif err.errno == errorcode.ER_BAD_DB_ERROR:
                print("Database does not exist")
            else:
                print(err)

    def process_item(self, item, spider):
        cursor = self.dbcon.cursor()
        cursor.execute(f"SELECT * FROM sinmunmul.news WHERE news_link = '{item['link']}' ORDER BY news_seq DESC LIMIT 1")
        result = cursor.fetchall()
        if len(result) != 0:
            print(f"{item['link']} already exists")
            raise DropItem("Duplicate item found: %s" % item)
        sql = f"INSERT INTO {self.table} (news_title, news_link, news_press, news_author, news_author_email, news_photo, news_desc, news_summary, news_reg_dt, news_mod_dt, code_group, code, reg_dt, reg_id, mod_dt, mod_id) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)"
        cursor.execute(sql, (item['title'], item['link'], item['press'], item['author'], item['author_email'], item['img'], item['desc'], item['desc'][0:50], item['dateReg'], item['dateMod'], item['topic1'], item['topic2'], item['reg_dt'], 'admin', item['mod_dt'], 'admin'))
        self.dbcon.commit()
        # cursor.close()
        # self.dbcon.close()
        return item
