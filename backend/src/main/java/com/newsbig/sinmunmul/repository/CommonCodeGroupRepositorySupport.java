package com.newsbig.sinmunmul.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.newsbig.sinmunmul.entity.CommonCodeGroup;
import com.newsbig.sinmunmul.entity.QCommonCodeGroup;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class CommonCodeGroupRepositorySupport {
	
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	QCommonCodeGroup qCommonCodeGroup = QCommonCodeGroup.commonCodeGroup;
	
	public CommonCodeGroup findByCodeGroup(int codeGroup) {
		CommonCodeGroup commonCodeGroup = jpaQueryFactory
				.select(qCommonCodeGroup).from(qCommonCodeGroup).where(qCommonCodeGroup.codeGroup.eq(codeGroup)).fetchOne();
		return commonCodeGroup;
	}
}