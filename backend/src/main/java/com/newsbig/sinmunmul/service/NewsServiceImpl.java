package com.newsbig.sinmunmul.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsbig.sinmunmul.dto.TodayNewsDto;
import com.newsbig.sinmunmul.entity.Scrap;
import com.newsbig.sinmunmul.entity.User;
import com.newsbig.sinmunmul.repository.NewsRepository;
import com.newsbig.sinmunmul.repository.NewsRepositorySupport;
import com.newsbig.sinmunmul.repository.ScrapRepository;
import com.newsbig.sinmunmul.repository.UserRepository;
import com.newsbig.sinmunmul.util.TimeUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

 
@Service
public class NewsServiceImpl implements NewsService {

	@Autowired
	ScrapRepository scrapRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	NewsRepository newsRepository;
	@Autowired
	NewsRepositorySupport newsRepositorySupport;

	@Override
	public void scrap(int newsSeq, int userSeq) {
		String now = TimeUtils.curTime();
		User user = userRepository.getById(userSeq);

		scrapRepository.save(
				Scrap.builder()
 					 .user(user)
					 .news(newsRepository.getById(newsSeq))
					 .regDt(now)
					 .regId(user.getUserEmail())
					 .modDt(now)
					 .modId(user.getUserEmail())
					 .build());
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject todayNews() {
		// 오늘의 뉴스 현황
		
		// 오늘 시작 시간과 끝 시간
		String startDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(00, 00, 00)).toString().replace("T", " ");
		String endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59, 999999999)).toString().replace("T", " ");
		
		// 모든 뉴스 개수 : % 구할 때 사용
		int allCount = newsRepository.countBydelYnAndNewsRegDtBetween("n", startDatetime, endDatetime);
		
		// 분야 별 뉴스 개수
		List<TodayNewsDto> newsList = newsRepositorySupport.todayNews(startDatetime, endDatetime);
		
		JSONObject jsonObject = new JSONObject();
	    jsonObject.put("allCount", allCount);
		for (TodayNewsDto news : newsList) {
			JSONObject data = new JSONObject();
		    data.put("count", news.getNum());
		    data.put("percent", news.getNum()/allCount * 100);
		    
		    jsonObject.put(news.getCodeGroupValue(), data);
		}
		
		/* 예시
		{
			politics:{
				count : 1,
				percent: 10
			}
		}
		*/

		return jsonObject;
	}
}
