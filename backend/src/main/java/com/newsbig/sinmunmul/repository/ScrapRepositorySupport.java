package com.newsbig.sinmunmul.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.newsbig.sinmunmul.entity.QNews;
import com.newsbig.sinmunmul.entity.QScrap;
import com.newsbig.sinmunmul.entity.QUser;
import com.newsbig.sinmunmul.entity.Scrap;
import com.newsbig.sinmunmul.util.TimeUtils;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class ScrapRepositorySupport {
	
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	@Autowired
	private ScrapRepository scrapRepository;
	QScrap qScrap = QScrap.scrap;
	QUser qUser = QUser.user;
	QNews qNews = QNews.news;
	
	public Page<Scrap> findByUserSeq(int userSeq, Pageable pageable) {
		
		QueryResults <Scrap> queryResults = jpaQueryFactory.select(qScrap)
				.from(qScrap)
					.leftJoin(qUser).on(qUser.userSeq.eq(qScrap.user.userSeq))
					.leftJoin(qNews).on(qNews.newsSeq.eq(qScrap.news.newsSeq))
				.where(qScrap.user.userSeq.eq(userSeq), qScrap.delYn.eq("n"))
				.offset(pageable.getOffset()).limit(pageable.getPageSize()).orderBy(qScrap.regDt.desc())
				.fetchResults();
	
		
		List<Scrap> scraps=queryResults.getResults();
				long total=queryResults.getTotal();
				
		return new PageImpl<>(scraps, pageable, total);
	}
	
	public boolean checkScrap(int userSeq, long newsSeq) {
		Scrap s;
		
		try {
			s = jpaQueryFactory.select(qScrap)
				.from(qScrap)
					.leftJoin(qUser).on(qUser.userSeq.eq(qScrap.user.userSeq))
					.leftJoin(qNews).on(qNews.newsSeq.eq(qScrap.news.newsSeq))
				.where(qScrap.user.userSeq.eq(userSeq), qScrap.news.newsSeq.eq(newsSeq) ,qScrap.delYn.eq("n")).fetchOne();
		} catch(NullPointerException e) {
			return false;
		}
		
		if(s==null) return false;
		
		return true;
	}
	
	public void deleteScrap(int userSeq, long newsSeq) {
		List<Scrap> scraps = jpaQueryFactory.select(qScrap)
				.from(qScrap)
					.leftJoin(qUser).on(qUser.userSeq.eq(qScrap.user.userSeq))
					.leftJoin(qNews).on(qNews.newsSeq.eq(qScrap.news.newsSeq))
				.where(qScrap.user.userSeq.eq(userSeq), qScrap.news.newsSeq.eq(newsSeq), qScrap.delYn.eq("n")).fetch();
		
		for(Scrap s : scraps) {
			s.setDelYn("y");
			s.setModDt(TimeUtils.curTime());
			scrapRepository.save(s);
		}
	}
}
