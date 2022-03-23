package com.newsbig.sinmunmul.service;

import java.util.Map;

public interface NewsService {
	public void scrap(int newsSeq, int userSeq);
	
	public Map<String, Map<String, String>> todayNews();
}
