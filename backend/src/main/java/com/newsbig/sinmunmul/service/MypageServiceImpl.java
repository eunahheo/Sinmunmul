package com.newsbig.sinmunmul.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.newsbig.sinmunmul.dto.CodeDto;
import com.newsbig.sinmunmul.entity.User;
import com.newsbig.sinmunmul.repository.InterestRepositorySupport;
import com.newsbig.sinmunmul.repository.UserRepository;
import com.newsbig.util.TimeUtils;

@Service("/mypageService")
public class MypageServiceImpl implements MypageService {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	InterestRepositorySupport interestRepositorySupport;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	// 비밀번호 수정
	@Override
	public void updatePassword(int userSeq, String userPwd) {
		User user = userRepository.getById(userSeq);
		
		if(!userRepository.getById(userSeq).getUserPwd().equals(passwordEncoder.encode(userPwd)))
			user.setUserPwd(passwordEncoder.encode(userPwd));

		userRepository.save(user);
	}
	
	// 관심사 수정
	@Override
	public void updateInterest(int userSeq, List<CodeDto> interests) {
		interestRepositorySupport.updateInterest(userSeq, interests);
	}
	
	// 회원 탈퇴
	@Override
	public void deleteUser(int userSeq) {
		User user = userRepository.getById(userSeq);
		user.setDelYn("y");
		user.setModDt(TimeUtils.curTime());
		userRepository.save(user);
	}
}
