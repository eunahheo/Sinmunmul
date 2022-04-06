package com.newsbig.sinmunmul.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsbig.sinmunmul.dto.CodeDto;
import com.newsbig.sinmunmul.dto.InterestDto;
import com.newsbig.sinmunmul.entity.News;
import com.newsbig.sinmunmul.repository.InterestRepositorySupport;
import com.newsbig.sinmunmul.repository.NewsRecommendRepositorySupport;
import com.newsbig.sinmunmul.repository.NewsTopicRepository;

@Service
public class NewsRecommendServiceImpl implements NewsRecommendService{

	@Autowired
	NewsRecommendRepositorySupport newsrRepositorySupport;
	
	@Autowired
	InterestRepositorySupport interestRepositorySupport;
	
	@Autowired
	NewsTopicRepository newsTopicRepository;
	
	public class Keyword {
		int code;
		String word;
		
		public Keyword(int code, String word) {
			super();
			this.code = code;
			this.word = word;
		}
	}
	
	@Override
	public Map<String, News> recommentArticle(int userSeq) {
		
		// 사용자 관심사 코드들 불러오기
		InterestDto interestDto = interestRepositorySupport.searchInterest(userSeq);
		List<CodeDto> list = interestDto.getYesInterest();
		
		// 소분류에 해당하는 키워드들 불러오기
		List<Keyword> keywords = new ArrayList<>();
		StringTokenizer st = null;
		
		for(CodeDto codeDto : list) {
			String keyword = newsTopicRepository.findBydelYnAndCode("n", codeDto.getCode()).getKeyword();
			st = new StringTokenizer(keyword,",");
			
			while(st.hasMoreTokens()) {
				keywords.add(new Keyword(codeDto.getCode(), st.nextToken()));
			}
		}
		
		// 키워드 중 랜덤으로 1개 선택
		Random random = new Random();
		int idx = random.nextInt(list.size());
		
		Keyword keyword = keywords.get(idx);
		System.out.println("코드 : " +  keyword.code + ", 단어 : " + keyword.word);
		Map<String, News> result = new HashMap<>();
		result = newsrRepositorySupport.searchByCodeAndKeyword(keyword.code, keyword.word);
		return result;
	}

}
