package com.newsbig.sinmunmul.service;

import java.util.Map;

import com.newsbig.sinmunmul.entity.News;

public interface NewsRecommendService {
	
	public Map<String,News> recommentArticle(int userSeq); 
}
