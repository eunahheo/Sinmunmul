package com.newsbig.sinmunmul.service;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

public interface NewsService {
	public void scrap(int newsSeq, int userSeq);
	
	public JSONObject todayNews();
}
