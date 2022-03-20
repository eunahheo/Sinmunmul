package com.newsbig.sinmunmul.service;

import java.util.List;

import com.newsbig.sinmunmul.dto.CodeDto;

public interface MypageService {
	void updatePassword(int userSeq, String userPwd);
	void updateInterest(int userSeq, List<CodeDto> interests);
	void deleteUser(int userSeq);
}
