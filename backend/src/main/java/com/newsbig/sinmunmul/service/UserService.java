package com.newsbig.sinmunmul.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.newsbig.sinmunmul.entity.User;

public interface UserService {
	public List<Map<String, Object>> userAllInfo();
	public User getUserByEmail(String email);
	public void signin(User user);
}
