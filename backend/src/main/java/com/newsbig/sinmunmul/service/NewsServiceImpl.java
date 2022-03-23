package com.newsbig.sinmunmul.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsbig.sinmunmul.entity.Scrap;
import com.newsbig.sinmunmul.entity.User;
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
	public void scrap(int newsSeq, int userSeq) {
		String now = TimeUtils.curTime();
		User user = userRepository.getById(userSeq);

		scrapRepository.save(Scrap.builder().user(user).news(newsRepository.getById(newsSeq)).regDt(now)
				.regId(user.getUserEmail()).modDt(now).modId(user.getUserEmail()).build());
	}

	@Override
	public Map<String, Map<String, String>> todayNews() {
		Map<String, Map<String, String>> result = new HashMap<>();

		Calendar cal = Calendar.getInstance();
		// 현재 (시,분,초)
		int hour = cal.get(cal.HOUR_OF_DAY);

		LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(hour, 0, 0));
		LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(hour + 1, 59, 59));

		newsRepositorySupport.todayNews();
		
//		newsRepository.countBydelYnAndCommonCodeGroupAndRegDtBetween("n", "100", startDatetime, endDatetime);

//	       result.put("email", email);
//	       result.put("gender", gender);

		return null;
	}
}
