package com.newsbig.sinmunmul.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.newsbig.sinmunmul.entity.User;

public interface UserService {
	// 이메일로 유저 정보 조회
	public User getUserByEmail(String email);
	// 이메일 회원가입
	public void signIn(User user);
	// 카카오 서버에서 카카오 계정 정보 조회
	public Map<String, String> getKakaoUserInfo(String accessToken);
	// 카카오 회원가입
	public void signInKakao(User user);
}
