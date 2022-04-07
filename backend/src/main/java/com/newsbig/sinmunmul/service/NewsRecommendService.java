package com.newsbig.sinmunmul.service;

import java.util.Map;

import com.newsbig.sinmunmul.entity.News;

public interface NewsRecommendService {
	
	public Map<String,Object> recommentArticle(int userSeq); 
}
