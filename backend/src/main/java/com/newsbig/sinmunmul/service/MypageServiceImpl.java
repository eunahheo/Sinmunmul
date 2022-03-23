package com.newsbig.sinmunmul.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.newsbig.sinmunmul.dto.CodeDto;
import com.newsbig.sinmunmul.dto.InterestDto;
import com.newsbig.sinmunmul.entity.Interest;
import com.newsbig.sinmunmul.entity.Scrap;
import com.newsbig.sinmunmul.entity.User;
import com.newsbig.sinmunmul.exception.NotExistsScrapException;
import com.newsbig.sinmunmul.repository.CommonCodeGroupRepository;
import com.newsbig.sinmunmul.repository.CommonCodeGroupRepositorySupport;
import com.newsbig.sinmunmul.repository.CommonCodeRepository;
import com.newsbig.sinmunmul.repository.CommonCodeRepositorySupport;
import com.newsbig.sinmunmul.repository.InterestRepository;
import com.newsbig.sinmunmul.repository.InterestRepositorySupport;
import com.newsbig.sinmunmul.repository.ScrapRepositorySupport;
import com.newsbig.sinmunmul.repository.UserRepository;
import com.newsbig.sinmunmul.util.TimeUtils;

@Service("/mypageService")
public class MypageServiceImpl implements MypageService {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	InterestRepository interestRepository;
	@Autowired
	InterestRepositorySupport interestRepositorySupport;
	@Autowired
	CommonCodeRepositorySupport commonCodeRepositorySupport;
	@Autowired
	CommonCodeGroupRepositorySupport commonCodeGroupRepositorySupport;
	@Autowired
	ScrapRepositorySupport scrapRepositorySupport;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	// 비밀번호 수정
	@Override
	public void updatePassword(int userSeq, String newUserPwd) {
		User user = userRepository.getById(userSeq);
		
		user.setUserPwd(passwordEncoder.encode(newUserPwd));
		user.setModDt(TimeUtils.curTime());
		userRepository.save(user);
	}
	
	// 관심사 수정
	@Override
	public void updateInterest(int userSeq, List<CodeDto> interests) {
		String now = TimeUtils.curTime();
		
		List<Interest> nowInterests = interestRepositorySupport.findByUserSeq(userSeq);
		boolean[] checked = new boolean[interests.size()];
		
		for(int i = 0; i < nowInterests.size(); i++) {
			boolean delete = true;
			for(int j = 0; j < interests.size(); j++) {
				if(nowInterests.get(i).getCommonCodeGroup().getCodeGroup() == interests.get(j).getCodeGroup()
					&& nowInterests.get(i).getCommonCode().getCode() == interests.get(j).getCode()) {
					if(nowInterests.get(i).getDelYn().equals("y")) {
						nowInterests.get(i).setDelYn("n");
						nowInterests.get(i).setModDt(now);
						interestRepository.save(nowInterests.get(i));
					}
					delete = false;
					checked[j] = true;
					break;
				}
			}
			if(!delete) continue;
			
			nowInterests.get(i).setDelYn("y");
			nowInterests.get(i).setModDt(now);
			interestRepository.save(nowInterests.get(i));
		}
		
		for(int i = 0; i < checked.length; i++) {
			if(!checked[i]) {
				interestRepository.save(Interest.builder()
						.user(userRepository.getById(userSeq))
						.commonCodeGroup(commonCodeGroupRepositorySupport.findByCodeGroup(interests.get(i).getCodeGroup()))
						.commonCode(commonCodeRepositorySupport.findByCode(interests.get(i).getCodeGroup(), interests.get(i).getCode()))
						.delYn("n")
						.regDt(now)
						.regId(userRepository.getById(userSeq).getUserEmail())
						.modDt(now)
						.modId(userRepository.getById(userSeq).getUserEmail()).build());
			}
		}
	}
	
	// 관심사 조회
	@Override
	public InterestDto searchInterest(int userSeq) {
		return interestRepositorySupport.searchInterest(userSeq);
	}
	
	// 회원 탈퇴
	@Override
	public void deleteUser(int userSeq) {
		User user = userRepository.getById(userSeq);
		user.setDelYn("y");
		user.setModDt(TimeUtils.curTime());
		userRepository.save(user);
	}
	
	// 스크랩 조회
	@Override
	public Page<Scrap> searchScrap(int userSeq, int page) {
		PageRequest pageable = PageRequest.of(page-1, 6);

		return scrapRepositorySupport.findByUserSeq(userSeq, pageable);
	}
	
	// 스크랩 삭제
	@Override
	public void deleteScrap(int userSeq, int newsSeq) {
		scrapRepositorySupport.deleteScrap(userSeq, newsSeq);
	}
}
