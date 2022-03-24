package com.newsbig.sinmunmul.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.newsbig.sinmunmul.dto.CodeDto;
import com.newsbig.sinmunmul.dto.InterestDto;
import com.newsbig.sinmunmul.entity.Scrap;

public interface MypageService {
	void updatePassword(int userSeq, String userPwd);
	void updateInterest(int userSeq, List<CodeDto> interests);
	InterestDto searchInterest(int userSeq);
	void deleteUser(int userSeq);
	Page<Scrap> searchScrap(int userSeq, int page);
	void deleteScrap(int userSeq, int newsSeq);
}
