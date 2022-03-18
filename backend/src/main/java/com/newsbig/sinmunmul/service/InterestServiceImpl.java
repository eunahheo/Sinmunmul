package com.newsbig.sinmunmul.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsbig.sinmunmul.entity.Interest;
import com.newsbig.sinmunmul.entity.User;
import com.newsbig.sinmunmul.exception.NotExistsUserException;
import com.newsbig.sinmunmul.repository.InterestRepository;
import com.newsbig.sinmunmul.repository.UserRepository;
import com.newsbig.sinmunmul.util.TimeUtils;

@Service
public class InterestServiceImpl implements InterestService {

	@Autowired
	InterestRepository interestRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public void registInterest(List<Integer> list, int userSeq) {
		
		String now = TimeUtils.curTime();
		User user = userRepository.findBydelYnAndUserSeq("n", userSeq).orElseThrow(() -> new NotExistsUserException());
		for(int code : list) {
			interestRepository.save(Interest.builder()
					.userSeq(userSeq)
					.code(code)
					.regDt(now)
					.regId(user.getUserEmail())
					.modDt(now)
					.modId(user.getUserEmail()).build());
		}
	}
	
}
