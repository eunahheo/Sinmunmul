package com.newsbig.sinmunmul.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.newsbig.sinmunmul.dto.CodeDto;
import com.newsbig.sinmunmul.entity.Interest;
import com.newsbig.sinmunmul.entity.QInterest;
import com.newsbig.sinmunmul.entity.QUser;
import com.newsbig.sinmunmul.entity.User;
import com.newsbig.util.TimeUtils;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class InterestRepositorySupport {
	
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CommonCodeGroupRepository commonCodeGroupRepository;
	@Autowired
	private CommonCodeRepository commonCodeRepository;
	@Autowired
	private InterestRepository interestRepository;
	QUser qUser = QUser.user;
	QInterest qInterest = QInterest.interest;
	
	public void updateInterest(int userSeq, List<CodeDto> interests) {
		String now = TimeUtils.curTime();
		
		List<Tuple> before = jpaQueryFactory
				.select(qInterest.interestSeq, qInterest.commonCodeGroup.codeGroup, qInterest.commonCode.code)
				.from(qInterest).where(qInterest.user.userSeq.eq(userSeq)).fetch(); // 수정 전 관심사
		
		List<CodeDto> beforeInterests = new ArrayList<>(); // 수정 전 관심사
		for(int i = 0; i < before.size(); i++)
			beforeInterests.add(new CodeDto(before.get(0).get(qInterest.commonCodeGroup).getCodeGroup(), before.get(0).get(qInterest.commonCode).getCode()));
		
		List<CodeDto> addInterests = new ArrayList<>(); // 추가해야 할 관심사
		List<CodeDto> deleteInterests = new ArrayList<>(); // 삭제해야 할 관심사
		
		if(interests.size() == 0)
			deleteInterests = beforeInterests;
		else if(beforeInterests.size() == 0)
			addInterests = interests;
		else {
			for(CodeDto i : interests) {
				if(!beforeInterests.contains(i)) addInterests.add(i);
			}
			
			for(CodeDto i : beforeInterests) {
				if(!interests.contains(i)) deleteInterests.add(i);
			}
		}
		
		// 관심사 추가
		for(CodeDto i : addInterests) {
			interestRepository.save(Interest.builder()
					.user(userRepository.getById(userSeq))
					.commonCodeGroup(commonCodeGroupRepository.getById(i.getCodegroup()))
					.commonCode(commonCodeRepository.getById(i.getCode()))
					.delYn("n")
					.regDt(now)
					.regId(userRepository.getById(userSeq).getUserEmail())
					.modDt(now)
					.modId(userRepository.getById(userSeq).getUserEmail()).build());
		}
		
		// 관심사 삭제
		for(CodeDto i : deleteInterests) {
			interestRepository.save(Interest.builder()
					.user(userRepository.getById(userSeq))
					.commonCodeGroup(commonCodeGroupRepository.getById(i.getCodegroup()))
					.commonCode(commonCodeRepository.getById(i.getCode()))
					.delYn("y")
					.regDt(now)
					.regId(userRepository.getById(userSeq).getUserEmail())
					.modDt(now)
					.modId(userRepository.getById(userSeq).getUserEmail()).build());
		}
		
	}
}