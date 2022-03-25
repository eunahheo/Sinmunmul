package com.newsbig.sinmunmul.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.newsbig.sinmunmul.dto.TodayNewsDto;
import com.newsbig.sinmunmul.entity.News;
import com.newsbig.sinmunmul.entity.Scrap;
import com.newsbig.sinmunmul.entity.User;
import com.newsbig.sinmunmul.exception.NotExistsNewsException;
import com.newsbig.sinmunmul.repository.NewsRepository;
import com.newsbig.sinmunmul.repository.NewsRepositorySupport;
import com.newsbig.sinmunmul.repository.ScrapRepository;
import com.newsbig.sinmunmul.repository.UserRepository;
import com.newsbig.sinmunmul.util.TimeUtils;

 
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
	public void scrap(long newsSeq, int userSeq) {
		String now = TimeUtils.curTime();
		User user = userRepository.getById(userSeq);

		scrapRepository.save(
				Scrap.builder()
 					 .user(user)
					 .news(newsRepository.getById(newsSeq))
					 .delYn("n")
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
	
	@SuppressWarnings("unchecked")
	public List<JSONObject> searchNews(String keyword, int page, int size) {
		List<JSONObject> result = new ArrayList<>();
		PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("newsRegDt").descending());
		Page<News> pageNews = newsRepository.findBydelYnAndNewsTitleContainingOrNewsDescContaining("n", keyword, keyword, pageRequest);
		
		for (News news : pageNews) {
			JSONObject obj = new JSONObject();

			obj.put("news_seq", news.getNewsSeq());
			obj.put("news_photo", news.getNewsPhoto());
			obj.put("news_Title", news.getNewsTitle());
			obj.put("news_desc", news.getNewsDesc());
			
			obj.put("pageable", pageNews.getPageable());
			obj.put("totalPages", pageNews.getTotalPages());
			obj.put("numberOfElements", pageNews.getNumberOfElements());
			obj.put("totalElements", pageNews.getTotalElements());
			
			result.add(obj);
			}
		 
		return result;
	}

	@Override
	public Map<String, Object> newsDetail(long newsSeq) {
		Map<String, Object> result = new HashMap<>();
		News news = newsRepository.findBydelYnAndNewsSeq("n", newsSeq).orElseThrow(() -> new NotExistsNewsException());

		result.put("newsSeq",news.getNewsSeq());
		result.put("newsTitle",news.getNewsTitle());
		result.put("newsLink",news.getNewsLink());
		result.put("newsPress",news.getNewsPress());
		result.put("newsAuthor",news.getNewsAuthor());
		result.put("newsAuthorEmail",news.getNewsAuthorEmail());
		result.put("newsDesc",news.getNewsDesc());
		result.put("newsSummary",news.getNewsSummary());
		result.put("newsRegDt",news.getNewsRegDt());
		result.put("newsModDt",news.getNewsModDt());
		result.put("newsPhoto",news.getNewsPhoto());
		result.put("newsCommonCodeGroup",news.getCommonCodeGroup().getCodeGroup());
		result.put("newsCommonCodeGroupSeq",news.getCommonCodeGroup().getCodeGroupSeq());
		result.put("newsCommonCode",news.getCommonCode().getCode());
		result.put("newsCommonCodeSeq",news.getCommonCode().getCodeSeq());
		
		return result;
	}
}
