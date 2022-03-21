package com.newsbig.sinmunmul.service;

import com.newsbig.sinmunmul.entity.User;

public interface MypageService {
	void updatePassword(int userSeq, String userPwd);
	User getUserByUserSeq(int userSeq);
}
