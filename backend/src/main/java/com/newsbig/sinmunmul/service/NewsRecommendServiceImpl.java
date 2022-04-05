package com.newsbig.sinmunmul.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsbig.sinmunmul.entity.News;
import com.newsbig.sinmunmul.repository.NewsRecommendRepositorySupport;

@Service
public class NewsRecommendServiceImpl implements NewsRecommendService{

	@Autowired
	NewsRecommendRepositorySupport newsrRepositorySupport;
	
	@Override
	public Map<String, News> recommentArticle(int code, String keyword) {
		Map<String, News> result = newsrRepositorySupport.searchByCodeAndKeyword(code, keyword);
		
		return result;
	}

}
