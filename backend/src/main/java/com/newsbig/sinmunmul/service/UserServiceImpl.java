package com.newsbig.sinmunmul.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.newsbig.sinmunmul.dto.SigninDto;
import com.newsbig.sinmunmul.entity.User;
import com.newsbig.sinmunmul.exception.NotExistsUserException;
import com.newsbig.sinmunmul.repository.UserRepository;
import com.newsbig.sinmunmul.util.TimeUtils;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public List<Map<String, Object>> userAllInfo() {
		List<Map<String, Object>> result = new ArrayList<>();
		
		for(User user : userRepository.findAll()) {
			Map<String, Object> obj = new HashMap<>();
			obj.put("email", user.getUserEmail());
			
			result.add(obj);
		}
		
		
		return result;
	}

	@Override
	public void signin(User user) {
		
		userRepository.save(User.builder()
				.userEmail(user.getUserEmail())
				.userPwd(passwordEncoder.encode(user.getUserPwd()))
				.userGender(user.getUserGender())
				.userAge(user.getUserAge())
				.regDt(user.getRegDt())
				.regId(user.getUserEmail())
				.modDt(user.getModDt())
				.modId(user.getUserEmail())
				.build());
	}

	@Override
	public User getUserByEmail(String email) {
		
		User user = userRepository.findBydelYnAndUserEmail("n", email).orElseThrow(() -> new NotExistsUserException());
		
		return user;
	}
}
