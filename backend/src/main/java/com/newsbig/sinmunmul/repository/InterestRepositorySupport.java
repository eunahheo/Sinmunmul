package com.newsbig.sinmunmul.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.newsbig.sinmunmul.entity.Interest;
import com.newsbig.sinmunmul.entity.QCommonCode;
import com.newsbig.sinmunmul.entity.QCommonCodeGroup;
import com.newsbig.sinmunmul.entity.QInterest;
import com.newsbig.sinmunmul.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class InterestRepositorySupport {
	
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	QUser qUser = QUser.user;
	QInterest qInterest = QInterest.interest;
	QCommonCodeGroup qCommonCodeGroup = QCommonCodeGroup.commonCodeGroup;
	QCommonCode qCommonCode = QCommonCode.commonCode;
	
	public List<Interest> findByUserSeq(int userSeq) {
		List<Interest> interests = jpaQueryFactory
				.select(qInterest)
				.from(qInterest)
					.leftJoin(qUser).on(qInterest.user.eq(qUser))
					.leftJoin(qCommonCodeGroup).on(qInterest.commonCodeGroup.eq(qCommonCodeGroup))
					.leftJoin(qCommonCode).on(qInterest.commonCode.eq(qCommonCode))
				.where(qInterest.user.userSeq.eq(userSeq)).fetch();
		return interests;
	}
}