package com.newsbig.sinmunmul.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.newsbig.sinmunmul.dto.TodayNewsDto;
import com.newsbig.sinmunmul.entity.NewsWordcloud;
import com.newsbig.sinmunmul.entity.QCommonCodeGroup;
import com.newsbig.sinmunmul.entity.QNews;
import com.newsbig.sinmunmul.entity.QNewsWordcloud;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class NewsRepositorySupport {

	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	 
	QNews qNews = QNews.news;
	QCommonCodeGroup qCommonCodeGroup = QCommonCodeGroup.commonCodeGroup;
	
	QNewsWordcloud qNewsWordcloud = QNewsWordcloud.newsWordcloud;

	public List<TodayNewsDto> todayNews(String start, String end) {
		List<Tuple> todayNews = jpaQueryFactory
				.select(qCommonCodeGroup.cgValue, qNews.count())
				.from(qNews)
				.where(qNews.delYn.eq("n"), qNews.newsRegDt.between(start, end)).groupBy(qNews.commonCodeGroup).fetch();
		
		List<TodayNewsDto> result = new ArrayList<>();
		for (int i = 0; i < todayNews.size(); i++) {
			result.add(new TodayNewsDto(todayNews.get(i).get(qCommonCodeGroup.cgValue).toString(), Integer.parseInt(todayNews.get(i).get(qNews.count()).toString())));
		}
		return result;
	}
	
	public NewsWordcloud mainWordcloud(int code) {
		List<NewsWordcloud> newsWordcloud = jpaQueryFactory
				.select(qNewsWordcloud)
				.from(qNewsWordcloud)
				.where(qNewsWordcloud.delYn.eq("n"), qNewsWordcloud.commonCodeGroup.codeGroup.eq(code))
				.orderBy(qNewsWordcloud.regDt.desc())
				.limit(1)
				.fetch();
		
		return newsWordcloud.get(0);
	}
	
}