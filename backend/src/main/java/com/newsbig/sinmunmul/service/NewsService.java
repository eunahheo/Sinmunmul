package com.newsbig.sinmunmul.service;

import java.util.List;

import org.json.simple.JSONObject;

public interface NewsService {
	public void scrap(int newsSeq, int userSeq);
	
	public JSONObject todayNews();
	
	public List<JSONObject> searchNews(String keyword, int page, int cnt);
}
