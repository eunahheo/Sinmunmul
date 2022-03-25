# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy

class NaverNewsCrawlingItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    title = scrapy.Field()
    link = scrapy.Field()
    press = scrapy.Field()
    author = scrapy.Field()
    author_email = scrapy.Field()
    img = scrapy.Field()
    desc = scrapy.Field()
    dateReg = scrapy.Field()
    dateMod = scrapy.Field()
    topic1 = scrapy.Field()
    topic2 = scrapy.Field()
    reg_dt = scrapy.Field()
    mod_dt = scrapy.Field()
    pass
