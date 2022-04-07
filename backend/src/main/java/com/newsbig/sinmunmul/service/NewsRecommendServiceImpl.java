package com.newsbig.sinmunmul.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsbig.sinmunmul.dto.CodeDto;
import com.newsbig.sinmunmul.dto.InterestDto;
import com.newsbig.sinmunmul.dto.RecomNewsDto;
import com.newsbig.sinmunmul.entity.News;
import com.newsbig.sinmunmul.exception.NotExistsInterestException;
import com.newsbig.sinmunmul.exception.NotExistsRecommendException;
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
	public Map<String, Object> recommentArticle(int userSeq) {
		
		Map<String, Object> result = new HashMap<>();
		// 사용자 관심사 코드들 불러오기
		InterestDto interestDto = interestRepositorySupport.searchInterest(userSeq);
		List<CodeDto> list = interestDto.getYesInterest();
		if(list.isEmpty()) throw new NotExistsInterestException();

		// 소분류에 해당하는 키워드들 불러오기
		List<Keyword> keywords = new ArrayList<>();
		StringTokenizer st = null;
		
		for(CodeDto codeDto : list) {
			String keyword = newsTopicRepository.findBydelYnAndCode("n", codeDto.getCode()).getKeyword();
			st = new StringTokenizer(keyword,",");
			
			while(st.hasMoreTokens()) {
				keywords.add(new Keyword(codeDto.getCodeGroup(), st.nextToken()));
			}
		}
		
		
		
		Map<String, Object> obj = new HashMap<>();
		List<News> newsList = new ArrayList<>();
		List<RecomNewsDto> miniNewsList = new ArrayList<>();		
		Keyword keyword = null;
		Random random = new Random();
		int cnt = 0;
		
		while(newsList.isEmpty() || newsList.size()<2) {
			// 키워드 중 랜덤으로 1개 선택
			int idx = random.nextInt(keywords.size());		
			keyword = keywords.get(idx);
			System.out.println("인덱스 : " + idx +"코드 : "+ keyword.code +"키워드 : " + keyword.word);
			
			obj = newsrRepositorySupport.searchByCodeGroupAndKeyword(keyword.code, keyword.word);			
			newsList = (ArrayList<News>) obj.get("News");
			if(cnt++>50) {
				throw new NotExistsRecommendException();
			}
		}
		
		for(News news : newsList) {
			miniNewsList.add(new RecomNewsDto(news.getNewsSeq(),news.getNewsPositive()+(news.getNewsNegative()*-1),
					news.getNewsPhoto(), news.getNewsTitle(), news.getNewsDesc()));
		}
		
		Collections.sort(miniNewsList);
		
		result.put("codeGroup", keyword.code);
		result.put("keyword", keyword.word);
		result.put("positive", miniNewsList.get(0));
		result.put("negative", miniNewsList.get(miniNewsList.size()-1));
		
		return result;
	}

}
