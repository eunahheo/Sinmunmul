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
    desc = scrapy.Field()
    date = scrapy.Field()
    topic1 = scrapy.Field()
    topic2 = scrapy.Field()
    pass
