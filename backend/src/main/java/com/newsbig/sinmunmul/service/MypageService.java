package com.newsbig.sinmunmul.service;

import java.util.List;

import com.newsbig.sinmunmul.dto.CodeDto;
import com.newsbig.sinmunmul.dto.InterestDto;

public interface MypageService {
	void updatePassword(int userSeq, String userPwd);
	void updateInterest(int userSeq, List<CodeDto> interests);
	void deleteUser(int userSeq);
	InterestDto searchInterest(int userSeq);
}
