package com.newsbig.sinmunmul.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.newsbig.sinmunmul.entity.User;
import com.newsbig.sinmunmul.exception.NotExistsUserException;
import com.newsbig.sinmunmul.repository.MypageRepository;

@Service("mypageService")
public class MypageServiceImpl implements MypageService {
	
	@Autowired
	MypageRepository mypageRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	// 비밀번호 수정
	@Override
	public void updatePassword(int userSeq, String userPwd) {
		User user = getUserByUserSeq(userSeq);
		
		if(!getUserByUserSeq(userSeq).getUserPwd().equals(passwordEncoder.encode(userPwd)))
			user.setUserPwd(passwordEncoder.encode(userPwd));

		mypageRepository.save(user);
	}
	
	@Override
	public User getUserByUserSeq(int userSeq) {
		return mypageRepository.findById(userSeq).orElseThrow(() -> new NotExistsUserException());
	}
}
