package com.newsbig.sinmunmul.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.newsbig.sinmunmul.entity.User;
import com.newsbig.sinmunmul.repository.MypageRepository;
import com.newsbig.util.TimeUtils;

@Service("/mypageService")
public class MypageServiceImpl implements MypageService {
	
	@Autowired
	MypageRepository mypageRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	// 비밀번호 수정
	@Override
	public void updatePassword(int userSeq, String userPwd) {
		User user = mypageRepository.getById(userSeq);
		
		if(!mypageRepository.getById(userSeq).getUserPwd().equals(passwordEncoder.encode(userPwd)))
			user.setUserPwd(passwordEncoder.encode(userPwd));

		mypageRepository.save(user);
	}
	
	// 회원 탈퇴
	@Override
	public void deleteUser(int userSeq) {
		User user = mypageRepository.getById(userSeq);
		user.setDelYn("y");
		user.setModDt(TimeUtils.curTime());
		mypageRepository.save(user);
	}
}
