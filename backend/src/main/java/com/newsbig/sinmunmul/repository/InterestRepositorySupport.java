package com.newsbig.sinmunmul.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.newsbig.sinmunmul.dto.CodeDto;
import com.newsbig.sinmunmul.dto.InterestDto;
import com.newsbig.sinmunmul.entity.CommonCode;
import com.newsbig.sinmunmul.entity.Interest;
import com.newsbig.sinmunmul.entity.QCommonCode;
import com.newsbig.sinmunmul.entity.QCommonCodeGroup;
import com.newsbig.sinmunmul.entity.QInterest;
import com.newsbig.sinmunmul.entity.QUser;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class InterestRepositorySupport {
	
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	@Autowired
	private CommonCodeRepository commonCodeRepository;
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
	
	// 등록된 관심사 조회
	public List<CodeDto> searchMyInterest(int userSeq) {
		List<Tuple> tuple = jpaQueryFactory
				.select(qInterest.commonCodeGroup.codeGroup, qInterest.commonCode.code)
				.from(qInterest)
					.leftJoin(qUser).on(qInterest.user.eq(qUser))
					.leftJoin(qCommonCodeGroup).on(qInterest.commonCodeGroup.eq(qCommonCodeGroup))
					.leftJoin(qCommonCode).on(qInterest.commonCode.eq(qCommonCode))
				.where(qInterest.user.userSeq.eq(userSeq), qInterest.delYn.eq("n")).fetch();
		
		List<CodeDto> interests = new ArrayList<>();
		for(int i = 0; i < tuple.size(); i++)
			interests.add(new CodeDto(tuple.get(i).get(qInterest.commonCodeGroup.codeGroup), tuple.get(i).get(qInterest.commonCode.code)));
	
		return interests;
	}
	
	// 등록된 관심사와 등록되지 않은 관심사 조회
	public InterestDto searchInterest(int userSeq) {
		List<CodeDto> myInterest = searchMyInterest(userSeq);
		
		List<CodeDto> notMyInterest = new ArrayList<>();
		List<CommonCode> allInterests = commonCodeRepository.findAll();
		
		for(int i = 0; i < allInterests.size(); i++) {
			boolean exist = false;
			for(int j = 0; j < myInterest.size(); j++) {
				if(allInterests.get(i).getCode() == myInterest.get(j).getCode()) {
					exist = true;
					break;
				}
			}
			if(!exist) notMyInterest.add(new CodeDto(allInterests.get(i).getCommonCodeGroup().getCodeGroup(), allInterests.get(i).getCode()));
		}
		
		return new InterestDto(myInterest, notMyInterest);
	}
}