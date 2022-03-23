package com.newsbig.sinmunmul.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsbig.sinmunmul.entity.Scrap;
import com.newsbig.sinmunmul.entity.User;
import com.newsbig.sinmunmul.repository.NewsRepository;
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

	@Override
	public void scrap(int newsSeq, int userSeq) {
		String now = TimeUtils.curTime();
		User user = userRepository.getById(userSeq);
		
		scrapRepository.save(Scrap.builder()
				.user(user)
				.news(newsRepository.getById(newsSeq))
				.delYn("n")
				.regDt(now)
				.regId(user.getUserEmail())
				.modDt(now)
				.modId(user.getUserEmail())
				.build());
	}
}
