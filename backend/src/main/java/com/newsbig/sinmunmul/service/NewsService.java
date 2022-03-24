package com.newsbig.sinmunmul.service;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.newsbig.sinmunmul.entity.News;

public interface NewsService {
	
	public void scrap(long newsSeq, int userSeq);
	
	public JSONObject todayNews();
	
	public List<JSONObject> searchNews(String keyword, int page, int cnt);
	
	public Map<String, String> newsDetail(long newsSeq);
}
