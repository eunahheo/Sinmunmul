package com.newsbig.sinmunmul.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.newsbig.sinmunmul.entity.News;
import com.newsbig.sinmunmul.entity.QNews;
import com.newsbig.sinmunmul.entity.QNewsRecommend;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class NewsRecommendRepositorySupport {

	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	 
	QNewsRecommend qNewsR = QNewsRecommend.newsRecommend;
	QNews qNews = QNews.news;
	
	public List<Long> searchList(int code, String keyword) {
		List<Long> result = jpaQueryFactory
				.select(qNewsR.newsSeq)
				.from(qNewsR)
				.where(qNewsR.delYn.eq("n"),qNewsR.code.eq(code),(
						(qNewsR.keyword1.eq(keyword))
						.or(qNewsR.keyword2.eq(keyword))
						.or(qNewsR.keyword3.eq(keyword))))
				.fetch(); 
		
		return result;
	}
	
	public Map<String,Object> searchByCodeAndKeyword(int code, String keyword) {
		List<Long> list = searchList(code, keyword);
		List<News> positiveNews = jpaQueryFactory
				.select(qNews)
				.from(qNews)
				.where(qNews.delYn.eq("n"), qNews.newsSeq.in(list))
				.orderBy(qNews.newsPositive.desc())
				.offset(0)
				.limit(1)
				.fetch(); 
		List<News> NegativeNews = jpaQueryFactory
				.select(qNews)
				.from(qNews)
				.where(qNews.delYn.eq("n"), qNews.newsSeq.in(list))
				.orderBy(qNews.newsNegative.desc())
				.offset(1)
				.limit(1)
				.fetch(); 
		
		Map<String, Object> result = new HashMap<>();
		result.put("code", code);
		result.put("keyword", keyword);
		result.put("posivite", positiveNews.get(0));
		result.put("negative", NegativeNews.get(0));
		
		return result;
	}
	
}