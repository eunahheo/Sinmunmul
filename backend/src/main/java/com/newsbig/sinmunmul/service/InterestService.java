package com.newsbig.sinmunmul.service;

import java.util.List;

import com.newsbig.sinmunmul.dto.CodeDto;

public interface InterestService {
	
	public void registInterest(List<CodeDto> list, int userSeq);
}
