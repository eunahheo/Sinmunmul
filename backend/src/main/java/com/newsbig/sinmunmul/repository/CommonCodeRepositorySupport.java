package com.newsbig.sinmunmul.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.newsbig.sinmunmul.entity.CommonCode;
import com.newsbig.sinmunmul.entity.QCommonCode;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class CommonCodeRepositorySupport {
	
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	QCommonCode qCommonCode = QCommonCode.commonCode;
	
	public CommonCode findByCode(int codeGroup, int code) {
		CommonCode commonCode = jpaQueryFactory
				.select(qCommonCode).from(qCommonCode)
				.where(qCommonCode.commonCodeGroup.codeGroup.eq(codeGroup), qCommonCode.code.eq(code)).fetchOne();
		return commonCode;
	}
}