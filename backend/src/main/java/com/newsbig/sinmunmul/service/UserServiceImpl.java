package com.newsbig.sinmunmul.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsbig.sinmunmul.entity.User;
import com.newsbig.sinmunmul.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;

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
	public void regist(User user) {
		// TODO Auto-generated method stub
		
	}
}
