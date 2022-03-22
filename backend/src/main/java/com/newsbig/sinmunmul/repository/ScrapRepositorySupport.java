package com.newsbig.sinmunmul.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.newsbig.sinmunmul.entity.QNews;
import com.newsbig.sinmunmul.entity.QScrap;
import com.newsbig.sinmunmul.entity.QUser;
import com.newsbig.sinmunmul.entity.Scrap;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class ScrapRepositorySupport {
	
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	QScrap qScrap = QScrap.scrap;
	QUser qUser = QUser.user;
	QNews qNews = QNews.news;
	
	public List<Scrap> findByUserSeq(int userSeq) {
		List<Scrap> scraps = jpaQueryFactory.select(qScrap)
				.from(qScrap)
					.leftJoin(qUser).on(qUser.userSeq.eq(qScrap.user.userSeq))
					.leftJoin(qNews).on(qNews.newsSeq.eq(qScrap.news.newsSeq))
				.where(qScrap.user.userSeq.eq(userSeq), qScrap.delYn.eq("n")).fetch();
	
		return scraps;
	}
	
	public Page<Scrap> findByUserSeq(int userSeq, Pageable pageable) {
		List<Scrap> scraps = jpaQueryFactory.select(qScrap)
				.from(qScrap)
					.leftJoin(qUser).on(qUser.userSeq.eq(qScrap.user.userSeq))
					.leftJoin(qNews).on(qNews.newsSeq.eq(qScrap.news.newsSeq))
				.where(qScrap.user.userSeq.eq(userSeq), qScrap.delYn.eq("n")).fetch();
	
		return new PageImpl<>(scraps, pageable, scraps.size());
	}
}