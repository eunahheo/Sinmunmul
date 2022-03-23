package com.newsbig.sinmunmul.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.newsbig.sinmunmul.dto.CodeDto;
import com.newsbig.sinmunmul.dto.InterestDto;
import com.newsbig.sinmunmul.entity.CommonCode;
import com.newsbig.sinmunmul.entity.Interest;
import com.newsbig.sinmunmul.entity.News;
import com.newsbig.sinmunmul.entity.QCommonCode;
import com.newsbig.sinmunmul.entity.QCommonCodeGroup;
import com.newsbig.sinmunmul.entity.QInterest;
import com.newsbig.sinmunmul.entity.QNews;
import com.newsbig.sinmunmul.entity.QScrap;
import com.newsbig.sinmunmul.entity.QUser;
import com.newsbig.sinmunmul.entity.Scrap;
import com.newsbig.sinmunmul.exception.NotExistsMyInterestException;
import com.newsbig.sinmunmul.exception.NotExistsNotMyInterestException;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class NewsRepositorySupport {

	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	@Autowired
	private CommonCodeRepository commonCodeRepository;
	QNews qNews = QNews.news;
	QCommonCodeGroup qCommonCodeGroup = QCommonCodeGroup.commonCodeGroup;

	public List<News> todayNews() {
		List<News> todayNews = jpaQueryFactory
				.select(qNews)
				.from(qNews)
					.leftJoin(qCommonCodeGroup)
					.on(qNews.commonCodeGroup.eq(qCommonCodeGroup))
				.where(qNews.delYn.eq("n")).fetch();
		
		for (int i = 0; i < todayNews.size(); i++) {
			
		}
		
		return todayNews;
	}
}