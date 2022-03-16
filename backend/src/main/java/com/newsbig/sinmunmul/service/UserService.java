package com.newsbig.sinmunmul.service;

import java.util.List;
import java.util.Map;

import com.newsbig.sinmunmul.entity.User;

public interface UserService {
	public List<Map<String, Object>> userAllInfo();
	public void regist(User user);
}
