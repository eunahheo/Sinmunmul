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
	
	// 키워드로 뉴스 추천 DB 검색
	public List<Long> searchList(String keyword) {
		List<Long> result = jpaQueryFactory
				.select(qNewsR.newsSeq)
				.from(qNewsR)
				.where(qNewsR.delYn.eq("n"),(
						(qNewsR.keyword1.eq(keyword))
						.or(qNewsR.keyword2.eq(keyword))
						.or(qNewsR.keyword3.eq(keyword))))
				.fetch(); 
		
		return result;
	}
	
	// 키워드로 검색한 리스트 + 대분류 검색
	public Map<String,Object> searchByCodeGroupAndKeyword(int codeGroup, String keyword) {
		List<Long> list = searchList(keyword);
		List<News> newsList = jpaQueryFactory
				.select(qNews)
				.from(qNews)
				.where(qNews.delYn.eq("n"), qNews.newsSeq.in(list), qNews.commonCodeGroup.codeGroup.eq(codeGroup))
				.limit(50)
				.fetch(); 
		
		Map<String, Object> result = new HashMap<>();
//		System.out.println(codeGroup+","+keyword);
//		System.out.println("사이즈 : " + newsList.size());
		result.put("News", newsList);
		
		return result;
	}
	
}