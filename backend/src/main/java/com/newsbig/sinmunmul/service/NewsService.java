package com.newsbig.sinmunmul.service;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.newsbig.sinmunmul.dto.KeywordTrendMonth;
import com.newsbig.sinmunmul.dto.KeywordTrendWeek;

public interface NewsService {
	
	public void scrap(long newsSeq, int userSeq);
	
	public JSONObject todayNews();
	
	public List<JSONObject> searchNews(String keyword, int page, int cnt);
	
	public Map<String, Object> newsDetail(long newsSeq);
	
	public List<KeywordTrendWeek> keywordTrendWeek(String keyword);
	
	public List<KeywordTrendMonth> keywordTrendMonth(String keyword);
	
	public JSONArray mainWordcloud(int codeGroup) throws ParseException;
}
