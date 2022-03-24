package com.newsbig.sinmunmul.service;

import java.util.Map;

import com.newsbig.sinmunmul.entity.User;

public interface UserService {
	// 이메일과 가입 형식으로 유저 정보 조회
	public User getUserByEmail(String email);
	// 이메일 회원가입
	public void signIn(User user);
	// 카카오 서버에서 카카오 계정 정보 조회
	public Map<String, String> getKakaoUserInfo(String accessToken);
	// 비밀번호 찾기를 통한 임시 비밀번호로 변경
	public String changePassword(String email);
}
